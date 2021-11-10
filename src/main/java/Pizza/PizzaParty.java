package Pizza;
//my import
import java.util.Random;

//original import
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Felix on 23.02.2016(https://github.com/Felix0301/DebuggingSession).
 */
public class PizzaParty {

    public Pizza pizza;
    public String hostName;
    public Stack beers = new Stack();
    public List<Guest> guests;
    //how many calories were consumend?
    //I made change "int" to "double" for var consumedCalories and providedCalories
    public double consumedCalories;
    //how many calories did the host provide?
    public double providedCalories;

    public PizzaParty(int nrOfGuests, Topping pizzaTopping, int nrOfBeers){
        //add all groceries to list of provided calories
        providedCalories = pizzaTopping.calories + nrOfBeers * Drink.BEER.calories + Drink.BIRTHDAYSHOT.calories;

        //invite guests
        guests = new ArrayList<Guest>();
        for (int i = 0; i < nrOfGuests; i++){
            guests.add(new Guest(Util.NAMES[i]));
        }

        //define host
        hostName =  guests.get(nrOfGuests-1).name;

        //buy beer
        for(int i = 0; i < nrOfBeers; i++){
            beers.push(Drink.BEER);
        }

        //order and slice Pizza
        pizza = new Pizza(pizzaTopping);
        pizza.slice(nrOfGuests);
    }

    public void startTheFeast(){
        //eat pizza and drink beer
        for (Guest guest : guests){
            guest.takeSlice(pizza);
            if (beers.size()>0){
                guest.drink(beers);
            }
            //test consumedCalories if changed?
            //System.out.println(this.consumedCalories);
        }
        //if bottle of beers < the number of people, let them drink again
        while(beers.size()>0){
            Random rand = new Random();
            int randomNumber = rand.nextInt(guests.size());
            guests.get(randomNumber).drink(beers);
        }


        //give special birthday shot to host
        for (Guest guest : guests){
            if (guest.name.equals(hostName)) {
                guest.consume(Drink.BIRTHDAYSHOT.calories);
            }
        }
        countCalories();
    }

    private void countCalories() {
        for (Guest guest : guests){
            this.consumedCalories += guest.consumedCalories;
        }
    }
}
