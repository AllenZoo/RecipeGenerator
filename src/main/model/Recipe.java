package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// A recipe that has a name, list of ingredients required, and instructions.
public class Recipe {
    private String name;
    private List<Item> recipeItems;
    private List<String> instructions;

    // MODIFIES: this
    // EFFECTS: creates a recipe with a name, recipe and instructions.
    public Recipe(String name, List<Item> recipe, List<String> instructions) {
        this.name = name;
        this.recipeItems = recipe;
        this.instructions = instructions;
    }

    // getters
    public String getName() {
        return name;
    }

    public List<Item> getRecipeItems() {
        return recipeItems;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    // EFFECTS: prints out the recipe in name, items, instruction format.
    public String toString() {
        String res = "Recipe Name: " + "*" + name + "* \n";
        res += "Recipe items" + "(" + recipeItems.size() + "): ";
        for (Item item: recipeItems) {
            res += "*" + item.toString() + "*";
        }
        res += "\n";
        res += "Recipe Instructions: \n";
        int count = 1;
        for (String instruction: instructions) {
            res += "*" + count + "* " + instruction + "\n";
            count++;
        }
        return res;
    }

    // EFFECTS: converts the list of instructions to one string
    public String instructionToOneString() {
        String string = "";

        for (String instruction: instructions) {
            string += instruction + "\n";
        }
        return  string;
    }

    // EFFECTS: converts the list of recipe items to one string
    public String recipeItemsToString() {
        String res = "";
        for (Item item: recipeItems) {
            res += "*" + item.toString() + "*";
        }

        return res;
    }


}
