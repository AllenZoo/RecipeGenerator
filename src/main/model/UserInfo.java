package model;

import exceptions.CookingUtensilNotInListException;
import exceptions.FoodItemNotInListException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

// Represents the information of a user. Stores the food items, cooking utensils, and custom recipes currently
// associated with it. Also holds a reference to the database, so it knows what items and recipes are valid.
public class UserInfo implements Writable {
    private String name;
    private List<Item> foodItems;
    private List<Item> cookingUtensils;
    private List<Recipe> customRecipes;

    private Database database;

    // EFFECTS: creates a new recipe generators with nothing inside available food items and cooking utensils list.
    public UserInfo(String name) {
        init(name);
    }

    // EFFECTS: generates a list of viable recipes that can be made with the current food items and cooking utensils
    //          the user has.
    public List<Recipe> generateRecipes() {
        Set<Recipe> allRecipes = database.getRecipes();
        List<Recipe> viableRecipes = new ArrayList<Recipe>();
        List<Item> availableItems = new ArrayList<Item>();
        availableItems.addAll(foodItems);
        availableItems.addAll(cookingUtensils);

        List<String> availableItemNames = convertListOfItemToString(availableItems);


        for (Recipe recipe : allRecipes) {
            boolean viable = true;
            for (Item item : recipe.getRecipeItems()) {

                if (!availableItemNames.contains(item.getName())) {
                    viable = false;
                }
            }
            if (viable) {
                viableRecipes.add(recipe);
            }
        }
        return viableRecipes;
    }

    // REQUIRES: foodItem != null
    // MODIFIES: this, foodItem
    // EFFECTS: adds a given food item into the list of available food items to the end of the list.
    //          if given food item is already in the list, add the portion size.
    public void addFoodItem(FoodItem foodItem) {
        boolean inList = false;
        EventLog.getInstance().logEvent(new Event("Added Food Item: " + foodItem.getName()
                + " with portion size of " + foodItem.getPortions()));
        for (Item food : foodItems) {
            if (food.getName().equals(foodItem.getName())) {
                FoodItem ref = (FoodItem) food;
                ref.setPortions(ref.getPortions() + foodItem.getPortions());
                inList = true;
            }
        }
        if (!inList) {
            foodItems.add(foodItem);
        }
    }

    // REQUIRES: cookingUtensil != null
    // MODIFIES: this
    // EFFECTS: adds a given cooking utensil into the list of available utensils to the end of the list.
    public void addCookingUtensil(CookingUtensil cookingUtensil) {
        EventLog.getInstance().logEvent(new Event("Added Cooking Utensil: " + cookingUtensil.getName()));
        cookingUtensils.add(cookingUtensil);
    }

    // REQUIRES: recipe != null
    // MODIFIES: this
    // EFFECTS: adds a given custom recipe into the list of custom recipes to the end of the list.
    public void addCustomRecipe(Recipe recipe) {
        customRecipes.add(recipe);
    }

    // MODIFIES: this, foodItem
    // EFFECTS: removes the given food item from the list.
    //          if portions to remove is smaller than total portion, subtract the total portion by
    //          the portion to remove.
    public void removeFoodItem(FoodItem foodItem) throws FoodItemNotInListException {
        int index = 0;
        int indexToRemove = -1;
        int portionsToRemove = foodItem.getPortions();

        for (Item item : foodItems) {

            if (foodItem.getName().equals(item.getName())) {
                indexToRemove = index;
            } else {
                index++;
            }
        }
        if (indexToRemove >= 0) {
            FoodItem foodItemToRemove = (FoodItem) foodItems.get(indexToRemove);
            int foodItemPortions = foodItemToRemove.getPortions();

            if (foodItemToRemove.getPortions() > portionsToRemove) {
                foodItemToRemove.setPortions(foodItemPortions - portionsToRemove);
            } else {
                foodItems.remove(indexToRemove);
            }
            EventLog.getInstance().logEvent(new Event("Removed Food Item: " + foodItem.getName()
                    + " with portion size of " + foodItem.getPortions()));
        } else {
            throw new FoodItemNotInListException();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the given cooking utensil from the list.
    public void removeCookingUtensil(CookingUtensil cookingUtensil) throws CookingUtensilNotInListException {
        int index = 0;
        int indexToRemove = -1;
        for (Item item : cookingUtensils) {
            CookingUtensil cookingItem = (CookingUtensil) item;
            if (cookingUtensil.getName().equals(cookingItem.getName())
                    && (cookingUtensil.getIsDamaged() == cookingItem.getIsDamaged())) {
                indexToRemove = index;
            } else {
                index++;
            }
        }
        if (indexToRemove >= 0) {
            cookingUtensils.remove(indexToRemove);
            EventLog.getInstance().logEvent(new Event("Removed Cooking Item: " + cookingUtensil));
        } else {
            throw new CookingUtensilNotInListException();
        }
    }

    public List<Item> getFoodItems() {
        return foodItems;
    }

    public List<Item> getCookingUtensils() {
        return cookingUtensils;
    }

    public List<Recipe> getCustomRecipes() {
        return customRecipes;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("food items", itemsToJson(foodItems));
        json.put("cooking utensils", itemsToJson(cookingUtensils));
        return json;
    }

    // EFFECTS: converts items list to json object
    private JSONArray itemsToJson(List<Item> items) {
        JSONArray jsonArray = new JSONArray();

        for (Item item: items) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }


    // MODIFIES: this
    // EFFECTS: initializes user info.
    private void init(String name) {
        foodItems = new ArrayList<>();
        cookingUtensils = new ArrayList<>();
        customRecipes = new ArrayList<>();
        database = new Database();
        this.name = name;
    }

    // EFFECTS: returns the conversion of a given list of items to be a list of string of each item's names
    private List<String> convertListOfItemToString(List<Item> items) {
        List<String> list = new ArrayList<>();

        for (Item item : items) {
            list.add(item.getName());
        }

        return list;
    }


}
