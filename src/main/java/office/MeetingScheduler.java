package office;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;


import static java.lang.Integer.parseInt;

/**
 * User: sameer
 * Date: 15/05/2013
 * Time: 15:12
 */
public class MeetingScheduler {

    private DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    private DateTimeFormatter separatedTimeFormatter = DateTimeFormat.forPattern("HH:mm");


    /**
     *
     * @param meetingRequest
     * @return
     */
    public MeetingsSchedule schedule(String meetingRequest) {
        String[] requestLines = meetingRequest.split("\n");

        String[] officeHoursTokens = requestLines[0].split(" ");
        LocalTime officeStartTime =  new LocalTime(parseInt(officeHoursTokens[0].substring(0, 2)),
                parseInt(officeHoursTokens[0].substring(2, 4)));

        //I made change officeHoursToken[?] index "0" to "1"
        LocalTime officeFinishTime =  new LocalTime(parseInt(officeHoursTokens[1].substring(0, 2)),
                parseInt(officeHoursTokens[1].substring(2, 4)));


        Map<LocalDate, SortedSet<Meeting>> meetings = new TreeMap<>();
        // i should starts from 2 not 1, since 2 is referred to the request meetingstarttime, with i = i+2
        for(int i=2;i<requestLines.length;i=i+2){

            String[] meetingSlotRequest = requestLines[i].split(" ");
            // I changed the meetingSLotRequest index 1 to 0 ,since 1 is time rather than the meetingDate,which is wanted
            LocalDate meetingDate = dateFormatter.parseLocalDate(meetingSlotRequest[0]);
            //helper reuqested part
            String[] requestedTime = requestLines[i-1].split(" ");
            LocalTime submittedTime = LocalTime.parse(requestedTime[1]);
            ;
            //LocalTime submittedTime =  new LocalTime(parseInt(requestedTime[1].substring(0, 2)),
                   //parseInt(requestedTime[1].substring(2, 4)));
            //below is original
            //Meeting meeting = extractMeeting(requestLines[i+1], officeStartTime, officeFinishTime, meetingSlotRequest);
            Meeting meeting = extractMeeting(requestLines[0+i-1], officeStartTime, officeFinishTime, meetingSlotRequest);
            if(meeting!= null){meeting.setRecordSubmittedTime(submittedTime);}
            //correspond to ine 47-50, set meeting.submittime
//            if(i>=2){
//                meeting.setRecordSubmittedTime(submittedTime);
//            }
            SortedSet<Meeting> meetingsForDay = new TreeSet<>();
            if(meetings.containsKey(meetingDate)){
            //want to compare time for each meeting
                meetings.get(meetingDate).remove(meeting);
                meetings.get(meetingDate).add(meeting);
            }else {
                //Set<Meeting> meetingsForDay = new HashSet<Meeting>();
                //I added if meeting is not null to make we add exact existed meeting
                if (meeting != null) {
                    meetingsForDay.add(meeting);
                    meetings.put(meetingDate, meetingsForDay);
                }
            }
        }
        return new MeetingsSchedule(officeStartTime, officeFinishTime, meetings);
    }
    //ChronologicalVerify
//    private LocalDate dateChronologicalVerify(){
//
//    }
//    private LocalTime timeChronologicalVerift(){
//
//    }

    private Meeting extractMeeting(String requestLine, LocalTime officeStartTime, LocalTime officeFinishTime, String[] meetingSlotRequest) {
        String[] employeeRequest = requestLine.split(" ");
        String employeeId = employeeRequest[2];

        LocalTime meetingStartTime =  separatedTimeFormatter.parseLocalTime(meetingSlotRequest[1]);
        LocalTime meetingFinishTime = new LocalTime(meetingStartTime.getHourOfDay(), meetingStartTime.getMinuteOfHour())
                .plusHours(parseInt(meetingSlotRequest[2]));

        if(meetingTimeOutsideOfficeHours(officeStartTime, officeFinishTime, meetingStartTime, meetingFinishTime)){
            System.out.println("EmployeeId:"+employeeId+" has requested booking which is outside office hour.");
            return null;
        }else{
            return new Meeting(employeeId, meetingStartTime, meetingFinishTime);

        }
    }

    private boolean meetingTimeOutsideOfficeHours(LocalTime officeStartTime, LocalTime officeFinishTime, LocalTime meetingStartTime, LocalTime meetingFinishTime) {
        // officeStart2officeFinishtime is 9:00-17:30, meetingStartTime is 16:00-19:00(last for 3 hours) In @test shouldNotBookMeetingRoomOutsideOfficeHours
        //below is original version
       return meetingStartTime.isBefore(officeStartTime)
                || meetingStartTime.isAfter(officeFinishTime)
                || meetingFinishTime.isAfter(officeFinishTime)
                || meetingFinishTime.isBefore(officeStartTime);

//        return meetingStartTime.isAfter(officeStartTime)
//                && meetingStartTime.isBefore(officeFinishTime)
//                && meetingFinishTime.isBefore(officeFinishTime)
//                && meetingFinishTime.isAfter(officeStartTime);


    }
}
