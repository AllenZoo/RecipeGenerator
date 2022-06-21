package model;

import exceptions.InvalidFoodItemException;
import exceptions.NegativePortionException;
import org.json.JSONObject;

//A food item that has a name and number of portions/meals that the item can provide
public class FoodItem extends Item {
    private int portions;

    // EFFECTS: create food item with given name and a portion of 1.
    public FoodItem(String name) {
        super(name.trim());
        portions = 1;
    }

    // EFFECTS: create food item with given name and a given amount of portions.
    public FoodItem(String name, int portions) {
        super(name.trim());
        this.portions = portions;
    }

    // EFFECTS: converts food item into json form.
    @Override
    public JSONObject toJson() {
        System.out.println("reading food item");
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("portions", this.portions);
        return json;
    }

    // EFFECTS: checks if the food item exists within the database. returns true if so and throws exception if not.
    @Override
    public boolean isValid() throws InvalidFoodItemException, NegativePortionException {
        if (portions <= -1) {
            throw new NegativePortionException();
        }

        // CHECK IF THE NAME OF FOOD ITEM IS CONTAINED WITHIN DATABASE
        if (Database.getInstance().getFoodItemNames().contains(this.getName())) {
            return true;
        } else {
            throw new InvalidFoodItemException();
        }
    }

    // getter
    public int getPortions() {
        return portions;
    }

    // setter
    // MODIFIES: this
    // EFFECTS: sets the current portion to given amount.
    public void setPortions(int amount) {
        portions = amount;
    }

}
