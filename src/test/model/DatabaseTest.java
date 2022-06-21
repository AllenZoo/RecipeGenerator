package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    private Database database;
    private int initNumRecipes;

    @BeforeEach
    public void runBefore(){
        database = Database.getInstance();
        initNumRecipes = database.getRecipes().size();
    }

    @Test
    public void testConstructor(){
        assertFalse(database.getFoodItemNames().isEmpty());
        assertFalse(database.getCookingUtensilNames().isEmpty());
        assertFalse(database.getRecipes().isEmpty());
    }

    @Test
    public void testAddRecipe(){
        FoodItem bread = new FoodItem("bread");
        CookingUtensil toaster = new CookingUtensil("toaster");
        List<Item> ingredients = new ArrayList<>();
        ingredients.add(bread);
        ingredients.add(toaster);

        List<String> instructions = new ArrayList<>();
        instructions.add("1. Put bread in toaster");

        Recipe r1 = new Recipe("toast", ingredients, instructions);
        database.addRecipe(r1);

        assertEquals(initNumRecipes + 1, database.getRecipes().size());
        //assertTrue(database.getRecipes().contains("toast"));

        FoodItem ham = new FoodItem("ham");
        ingredients = new ArrayList<>();
        ingredients.add(bread);
        ingredients.add(ham);
        ingredients.add(toaster);

        Recipe r2 = new Recipe("hamsandwich", ingredients, instructions);
        database.addRecipe(r2);

        assertEquals(initNumRecipes + 2, database.getRecipes().size());
        //assertTrue(database.getRecipes().contains("toast"));
        //assertTrue(database.getRecipes().contains("hamsandwich"));
    }
}
