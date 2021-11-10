package Pizza;

import java.util.Stack;

/**
 * Created by Felix on 23.02.2016.
 */
public class Pizza {

    public Stack slices;
    public double calories;

    public Pizza(Topping topping){
        this.calories = topping.calories;
    }
    // I made nrOfslices from int to double, but it is not good.
    public void slice(int nrOfSlices){
        slices = new Stack();
        for (int i = 0;i < nrOfSlices; i++){
            slices.push(new Slice(calories/ nrOfSlices));
        }
    }
}
