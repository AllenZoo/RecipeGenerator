package model;

import exceptions.InvalidCookingUtensilException;
import exceptions.InvalidFoodItemException;
import org.json.JSONArray;
import org.json.JSONObject;

// A cooking utensil that has a name.
public class CookingUtensil extends Item {
    private boolean isDamaged;

    // EFFECTS: creates a cooking utensil with given name and is in good condition
    public CookingUtensil(String name) {
        super(name);
        isDamaged = false;
    }

    // EFFECTS: creates a cooking utensil with given name and given condition
    public CookingUtensil(String name, boolean isDamaged) {
        super(name);
        this.isDamaged = isDamaged;
    }

    // EFFECTS: converts cooking utensil into json form.
    @Override
    public JSONObject toJson() {
        System.out.println("reading cooking utensil");
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("isDamaged", isDamaged);
        return json;
    }

    // getter
    public boolean getIsDamaged() {
        return isDamaged;
    }

    // EFFECTS: checks if the cooking utensil exists within the database. returns true if so and throws
    //          exception if not.
    @Override
    public boolean isValid() throws InvalidCookingUtensilException {
        // CHECK IF THE NAME OF COOKING UTENSIL IS CONTAINED WITHIN DATABASE
        if (Database.getInstance().getCookingUtensilNames().contains(this.getName())) {
            return true;
        } else {
            throw new InvalidCookingUtensilException();
        }
    }
}

