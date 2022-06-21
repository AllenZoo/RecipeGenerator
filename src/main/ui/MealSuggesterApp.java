package ui;

import exceptions.*;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// An application that suggests meals to make given what's available.
public class MealSuggesterApp {
    private static final String JSON_STORE = "./data/appData.json";

    private UserInfo user;
    private Scanner input;
    private Database database;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the meal suggester app.
    public MealSuggesterApp() {
        //runMealSuggester();
        init();
    }

    // EFFECTS: generates and returns a string of the current list of recipes that are possible to make given the
    //          user's current available food items and cooking utensils.
    public String displayGeneratedRecipes() {
        List<Recipe> recipes = user.generateRecipes();
        String str = "";
        if (recipes.size() > 0) {
            str += "Using the current available food items and cooking utensils here are some recipes we "
                    + "found that you can try making: ";
            for (Recipe recipe : recipes) {
                str += "*" + recipe.getName() + "*";
            }
        } else {
            str += "No recipes possible yet with current amount of items";
        }
        return str;
    }

    // EFFECTS:  saves user info to a file.
    public void saveUserInfo() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved " + user.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user info from file.
    public void loadUserInfo() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded " + user.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays the current list of user available food items.
    public String displayFoodItems() {
        String list = "";
        System.out.println("\nCurrent foods in list of available food items: ");
        list += "Current foods in list of available food items: ";
        if (user.getFoodItems().isEmpty()) {
            list += "*No items in food list*";
            System.out.println("*No items in food list*");
        }
        for (Item item : user.getFoodItems()) {
            FoodItem foodItem = (FoodItem) item;
            System.out.print("*" + item.getName() + "#" + foodItem.getPortions() + "* ");
            list += "*" + item.getName() + "#" + foodItem.getPortions();
        }
        return list;
    }

    // EFFECTS: displays the current list of user available cooking utensils.
    public String displayCookingUtensils() {
        String list = "";
        System.out.println("\nCurrent cooking utensils in list of available utensils: ");
        if (user.getCookingUtensils().isEmpty()) {
            list += "*No items in cooking utensils list*";
            System.out.println("*No items in cooking utensils list*");
        }
        for (Item item : user.getCookingUtensils()) {
            list += "*" + item.getName() + "*";
            System.out.print("*" + item.getName() + "* ");
        }
        return list;
    }

    // getter
    public UserInfo getUserInfo() {
        return user;
    }

    // MODIFIES: this
    // EFFECTS: initializes the application
    private void init() {
        user = new UserInfo("User 1");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        database = new Database();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMealSuggester() {
        boolean run = true;
        String command = null;

        init();

        while (run) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                run = false;
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            System.out.println("Select which category to add an item to");
            displayAddMenu();
            selectAddList();
        } else if (command.equals("r")) {
            System.out.println("Select which category to remove an item from");
            displayRemoveMenu();
            selectRemoveList();

        } else if (command.equals("g")) {
            System.out.println("Possible meals to make: ");
            displayGeneratedRecipes();
        } else if (command.equals("v")) {
            System.out.println("Displaying current items in lists");
            displayLists();
        } else if (command.equals("s")) {
            System.out.println("saving lists...");
            saveUserInfo();
        } else if (command.equals("l")) {
            System.out.println("loaded lists");
            loadUserInfo();
        } else {
            System.out.println("Invalid input...");
        }
    }

    // EFFECTS: displays the menu of main functions that the user can select from
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add item");
        System.out.println("\tr -> remove item");
        System.out.println("\tg -> generate meals");
        System.out.println("\tv -> view lists");
        System.out.println("\ts -> save lists");
        System.out.println("\tl -> load lists");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays the menu of lists that the user can select to add on to.
    private void displayAddMenu() {
        System.out.println("\nAdd an item to:");
        System.out.println("\t fi -> available food items:");
        System.out.println("\t cu -> available cooking utensils items:");
        System.out.println("\t cr -> custom recipes:");
    }

    // EFFECTS: displays the menu of lists that the user can select to remove from.
    private void displayRemoveMenu() {
        System.out.println("\nRemove an item from:");
        System.out.println("\t fi -> available food items:");
        System.out.println("\t cu -> available cooking utensils items:");
        System.out.println("\t cr -> custom recipes:");
    }

    // EFFECTS: displays the current value of the lists of food items and cooking utensils of the
    //          user.
    private void displayLists() {
        displayFoodItems();
        displayCookingUtensils();
        displayCustomRecipes();
    }

    // EFFECTS: selects a list to add an item to.
    private void selectAddList() {
        String command = input.next();
        if (command.equals("fi")) {
            addAvailableFoodItem();
        } else if (command.equals("cu")) {
            addAvailableCookingUtensil();
        } else if (command.equals("cr")) {
            addCustomRecipe();
        } else {
            System.out.println("invalid input");
        }
    }

    // EFFECTS: selects a list to remove an item from.
    private void selectRemoveList() {
        String command = input.next();
        if (command.equals("fi")) {
            removeAvailableFoodItem();
        } else if (command.equals("cu")) {
            removeAvailableCookingUtensil();
        } else if (command.equals("cr")) {
            removeCustomRecipe();
        } else {
            System.out.println("invalid input");
        }

    }

    // MODIFIES: this
    // EFFECTS: adds the user inputted food item into the food item list if the name of the item appears in the
    //          database.
    private void addAvailableFoodItem() {
        System.out.println("Please fill in the specifications for the food items you wish to add ");
        displayFoodItems();

        try {
            FoodItem foodItem = inputFoodItem();
            System.out.println("Added " + foodItem.getName() + " to the list of available foods");
            user.addFoodItem(foodItem);
        } catch (InvalidFoodItemException e) {
            System.out.println("Cannot add non-existing item to list.");
        } catch (NegativePortionException e) {
            System.out.println("Cannot add a food item with negative portions");
        }
        displayFoodItems();
    }

    // MODIFIES: this
    // EFFECTS: adds the user inputted cooking utensil into cooking utensil list if the name of the item appears in the
    //          database.
    private void addAvailableCookingUtensil() {
        System.out.println("Please fill in the specifications for the cooking utensils you wish to add ");
        displayCookingUtensils();

        CookingUtensil cookingUtensil = inputCookingUtensil();

        // CHECK IF THE NAME OF COOKING UTENSIL IS CONTAINED WITHIN DATABASE
        if (cookingUtensil != null) {
            System.out.println("Added " + cookingUtensil.getName() + " to the list of available cooking utensils");
            user.addCookingUtensil(cookingUtensil);
        } else {
            System.out.println("Cannot add non-existing item to list.");
        }
        displayCookingUtensils();
    }

    // MODIFIES: this
    // EFFECTS: adds the user inputted recipe into the custom recipes list.
    private void addCustomRecipe() {
        System.out.println("Please fill in the specifications for the custom recipe you wish to add. ");
        Recipe recipe = null;
        try {
            recipe = inputCustomRecipe();
        } catch (InvalidFoodItemException e) {
            System.out.println("Can't add a recipe with an invalid food item");
        } catch (NegativePortionException e) {
            System.out.println("Can't add a recipe with a negative food item portion");
        }
        user.addCustomRecipe(recipe);
    }

    // MODIFIES: this
    // EFFECTS: removes the user inputted food item from the food item list of the user if the inputted item
    //          matches an item in the list of the user.
    private void removeAvailableFoodItem() {
        System.out.println("Please fill in the specifications for the food items you wish to remove ");
        displayFoodItems();

        try {
            FoodItem foodItem = inputFoodName();
            user.removeFoodItem(foodItem);
        } catch (InvalidFoodItemException e) {
            System.out.println("Invalid food inputted");
        } catch (FoodItemNotInListException e) {
            System.out.println("Cannot remove item that is not contained in list");
        } catch (NegativePortionException e) {
            System.out.println("Cannot add negative food items");
        }
        displayFoodItems();
    }

    // MODIFIES: this
    // EFFECTS: removes the user inputted cooking utensil from the cooking utensil list of the user if the
    //          inputted item matches an item in the list of the user.
    private void removeAvailableCookingUtensil() {
        System.out.println("Please fill in the specifications for the cooking utensil you wish to remove ");
        displayCookingUtensils();

        CookingUtensil cookingUtensil = inputCookingUtensil();

        // CHECK IF THE NAME OF FOOD ITEM IS CONTAINED WITHIN DATABASE
        if (cookingUtensil != null) {
            try {
                user.removeCookingUtensil(cookingUtensil);
            } catch (CookingUtensilNotInListException e) {
                System.out.println("Cannot remove cooking utensil not in list");
            }
        } else {
            System.out.println("Cannot remove null item");
        }
        displayFoodItems();
    }

    // MODIFIES: this
    // EFFECTS: removes the user inputted custom recipe from the custom recipe list of the user if the
    //          inputted item matches an item in the list of the user.
    private void removeCustomRecipe(){
        //stub
    }

    // EFFECTS: prompts the user to input food name and returns the food item with a portion of 1 if it's valid
    private FoodItem inputFoodName() throws InvalidFoodItemException, NegativePortionException {
        FoodItem foodItem;
        String nameOfFood = "";  // force entry into loop
        while (nameOfFood.equals("")) {
            System.out.println("\nName of food item: ");
            nameOfFood = input.next();
            nameOfFood = nameOfFood.toLowerCase();
            foodItem = new FoodItem(nameOfFood);
            if (!foodItem.isValid()) {
                throw new InvalidFoodItemException();
            }
        }
        return new FoodItem(nameOfFood);
    }

    // EFFECTS: prompts the user to input the food item name and portion size. If item name is not contained within
    //          database, InvalidFoodItemException will be thrown.
    //          NegativePortionException will be thrown when the inputted portion is <= 0. returns a new food item
    //          using the input.
    private FoodItem inputFoodItem() throws InvalidFoodItemException, NegativePortionException {
        FoodItem foodItem = inputFoodName();;
        String nameOfFood = foodItem.getName();
        int portion = -1;

        while (portion == -1) {
            System.out.println("Portions: ");
            try {
                portion = input.nextInt();
                if (portion <= 0) {
                    throw new NegativePortionException();
                }
            } catch (RuntimeException e) {
                System.out.println("Please specify the portion size");
                portion = -1;
            }
        }
        return new FoodItem(nameOfFood, portion);
    }


    // EFFECTS: prompts the user to input a cooking utensil.
    private CookingUtensil inputCookingUtensil() {
        String nameOfCU = "";  // force entry into loop

        while (nameOfCU.equals("")) {
            System.out.println("\nName of cooking utensil item: ");
            nameOfCU = input.next();
            nameOfCU = nameOfCU.toLowerCase();
        }

        // CHECKING IF THE NAME OF FOOD ITEM IS CONTAINED WITHIN DATABASE
        if (database.getCookingUtensilNames().contains(nameOfCU)) {
            return new CookingUtensil(nameOfCU);
        } else {
            System.out.println("This cooking utensil does not exist... "
                    + "(or has not been added into our database, sorry :( )");
            return null;
        }
    }

    // EFFECTS: prompts the user to input a name
    private String inputName() {
        String name = "";

        while (name.equals("")) {
            System.out.println("Name: ");
            name = input.next();
            name = name.toLowerCase();
        }

        return name;
    }

    // EFFECTS: prompts the user to input a string
    private String inputString() {
        String string = "";

        while (string.equals("")) {
            string = input.next();
            string = string.toLowerCase();
        }

        return  string;
    }

    // EFFECTS: prompts the user to input a custom recipe.
    private Recipe inputCustomRecipe() throws InvalidFoodItemException, NegativePortionException {
        String name = "";
        List<Item> items = new ArrayList<>();
        List<String> instructions = new ArrayList<>();

        while (name.equals("")) {
            System.out.println("\nName of custom recipe: ");
            name = input.next();
            name = name.toLowerCase();
        }

        System.out.println("Input items: ");
        if (items.isEmpty()) {
            items = inputItems();
        }

        if (instructions.isEmpty()) {
            instructions = inputInstructions();
        }
        System.out.println("item size: " + items.size());
        return new Recipe(name, items, instructions);
    }

    // EFFECTS: prompts the user to input either a food item or cooking utensil.
    private Item inputItem() throws InvalidFoodItemException, NegativePortionException {
        System.out.println("Input food item or cooking utensil? (f/c)");
        Item item = new FoodItem("empty", -1);
        String itemType = inputString();

        if (itemType.equals("f")) {
            item = inputFoodItem();
        } else if (itemType.equals("c")) {
            item = inputCookingUtensil();
        } else {
            System.out.println("Invalid item type, please type either 'f' or 'c'");
            item = new FoodItem("empty", -1);
        }
        return item;
    }

    // EFFECTS: prompts the user to input item of itemType.
    private Item inputItem(String itemType) throws InvalidFoodItemException, NegativePortionException {
        Item item = new FoodItem("empty", -1);

        if (itemType.equals("f")) {
            item = inputFoodItem();
        } else if (itemType.equals("c")) {
            item = inputCookingUtensil();
        } else {
            System.out.println("Invalid item type, please type either 'f' or 'c'");
            item = new FoodItem("empty", -1);
        }
        return item;
    }

    // EFFECTS: prompts the user to input multiple items.
    private List<Item> inputItems() throws InvalidFoodItemException, NegativePortionException  {
        List<Item> items = new ArrayList<>();
        Item item;
        boolean keepGoing = true;

        while (keepGoing) {
            item = inputItem();

            if (!item.getName().equals("empty")) {
                items.add(item);
                System.out.println("Add more? (f/c), type 'n' to stop.");

                String cmd = input.next();
                cmd.toLowerCase();

                if (cmd.equals("n")) {
                    keepGoing = false;
                } else {
                    inputItem(cmd);
                }
            }
        }
        return items;
    }



    // EFFECTS: prompts the user to write down instructions and returns them.
    private List<String> inputInstructions() {
        List<String> instructions = new ArrayList<>();
        boolean keepGoing = true;

        System.out.println("Please write down your instructions for your custom recipe: ");
        while (instructions.size() == 0) {
            String instruction = input.next();
            while (keepGoing) {
                if (!instruction.equals("")) {
                    instructions.add(instruction);

                    System.out.println("Keep typing to add more, type 'n' to stop.");
                    instruction = input.next();

                    if (instruction.equals("n")) {
                        keepGoing = false;
                    }
                } else {
                    System.out.println("Please input an instruction!");
                }
            }

        }
        return instructions;
    }

    // EFFECTS: prints out the custom recipe formatted.
    private void displayCustomRecipes() {
        System.out.println("\nCurrent custom recipes: ");
        List<Recipe> customRecipes = user.getCustomRecipes();
        if (customRecipes.isEmpty()) {
            System.out.println("*No custom recipes added yet*");
        } else {
            for (Recipe recipe: customRecipes) {
                System.out.println(recipe.toString());
            }
        }
    }

}
