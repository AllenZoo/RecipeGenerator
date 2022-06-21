package model;

import exceptions.InvalidFoodItemException;
import exceptions.InvalidItemException;
import org.json.JSONObject;

// An item that has a name.
public abstract class Item {
    protected String name;

    // EFFECTS: creates an item with a given name.
    public Item(String name) {
        this.name = name;
    }

    // getter
    public String getName() {
        return name;
    }

    // EFFECTS: gets JSONObject of item.
    public abstract JSONObject toJson();

    // EFFECTS: checks if item is a valid item.
    public abstract boolean isValid() throws InvalidItemException;

    // EFFECTS: returns the name of the item.
    public String toString() {
        return name;
    }
}
