package Pizza;
/**
 * Created by Felix on 23.02.2016.
 * My Lord
 */
public enum Drink {
        BEER (310) ,
        BIRTHDAYSHOT (280);

        public final int calories;

        Drink(int calories){
            this.calories = calories;
        }
}
