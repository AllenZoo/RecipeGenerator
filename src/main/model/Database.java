package model;

import javax.xml.crypto.Data;
import java.util.*;

// A database that stores the names of foods, cooking utensils, and recipes.
public class Database {

    private static Database INSTANCE = new Database();

    private Set<Recipe> recipes = new HashSet<>();
    private Set<String> foodItemNames = new HashSet<>();
    private Set<String> cookingUtensilNames = new HashSet<>();


    // EFFECTS: processes the initialization of the database
    public Database() {
        init();
    }

    // REQUIRES: recipe != null
    // MODIFIES: this
    // EFFECTS: add a given recipe into the list of recipes.
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    // getters
    public Set<String> getFoodItemNames() {
        return foodItemNames;
    }

    public Set<String> getCookingUtensilNames() {
        return cookingUtensilNames;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    // MODIFIES: this
    // EFFECTS: initializes the database and its values.
    @SuppressWarnings("methodlength")
    private void init() {
        String f1 = "eggs";
        String f2 = "beef";
        String f3 = "chicken";
        String f4 = "lettuce";
        String f5 = "spinach";
        String f6 = "carrots";
        String f7 = "lamb";
        String f8 = "noodles";
        String f9 = "radish";
        String f10 = "tomato";
        String f11 = "potato";
        String f12 = "salmon";
        String f13 = "cheese";
        String f14 = "bacon";
        String f15 = "escargot";
        Collections.addAll(foodItemNames, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15);

        String c1 = "pan";
        String c2 = "oven";
        String c3 = "knife";
        String c4 = "cutting board";
        String c5 = "vegetable peeler";
        String c6 = "grater";
        String c7 = "bowl";
        String c8 = "rolling pin";
        String c9 = "spatula";
        String c10 = "wok";
        String c11 = "pot";
        Collections.addAll(cookingUtensilNames, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11);

        FoodItem noodles = new FoodItem("noodles");
        FoodItem beef = new FoodItem("beef");
        FoodItem eggs = new FoodItem("eggs");
        CookingUtensil pan = new CookingUtensil("pan");
        CookingUtensil bowl = new CookingUtensil("bowl");
        CookingUtensil pot = new CookingUtensil("pot");

        List<Item> ingredient1 = new ArrayList<>();
        Collections.addAll(ingredient1, noodles, pan, bowl);

        List<String> instruct1 = new ArrayList<>();
        instruct1.add("1. Fill a bowl up with water.");
        instruct1.add("2. Boil the water.");
        instruct1.add("3. Place noodles inside the bowl carefully.");

        Recipe r1 = new Recipe("spaghetti", ingredient1, instruct1);

        List<Item> ingredient2 = new ArrayList<>();
        Collections.addAll(ingredient2, noodles, beef, eggs, pot, bowl);

        List<String> instruct2 = new ArrayList<>();
        instruct2.add("1. Fill pot up with water.");
        instruct2.add("2. Dump beef, egg and noodle in. Voila!");

        Recipe r2 = new Recipe("beef udon", ingredient2, instruct2);

        // ADD NEW RECIPES HERE
        Collections.addAll(recipes, r1, r2);
    }

    // EFFECTS: returns instance of database.
    public static Database getInstance() {
        return INSTANCE;
    }


}
