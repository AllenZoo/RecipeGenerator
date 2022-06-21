package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    private Recipe r;

    @BeforeEach
    public void runBefore(){
        FoodItem noodle = new FoodItem("noodles");
        CookingUtensil pan = new CookingUtensil("pan");
        CookingUtensil bowl = new CookingUtensil("bowl");
        List<Item> ingredients = new ArrayList<>();
        ingredients.add(noodle);
        ingredients.add(pan);
        ingredients.add(bowl);

        List<String> instructions = new ArrayList<>();
        instructions.add("Fill a bowl up with water.");
        instructions.add("Boil the water.");
        instructions.add("Place the noodles inside the bowl carefully.");

        r = new Recipe("Spaghetti", ingredients, instructions);
    }

    @Test
    public void testConstructor(){
        assertEquals("Spaghetti", r.getName());
        assertEquals(3, r.getRecipeItems().size());
        assertEquals(3, r.getInstructions().size());
        assertEquals("noodles", r.getRecipeItems().get(0).getName());
        assertEquals("pan", r.getRecipeItems().get(1).getName());
        assertEquals("bowl", r.getRecipeItems().get(2).getName());
        assertEquals("Fill a bowl up with water.", r.getInstructions().get(0));
        assertEquals("Boil the water.", r.getInstructions().get(1));
        assertEquals("Place the noodles inside the bowl carefully.", r.getInstructions().get(2));
    }

    @Test
    public void testPrint() {
        assertEquals("Recipe Name: *Spaghetti* \n" +
                "Recipe items(3): *noodles**pan**bowl*\n" +
                "Recipe Instructions: \n" +
                "*1* Fill a bowl up with water.\n" +
                "*2* Boil the water.\n" +
                "*3* Place the noodles inside the bowl carefully.\n", r.toString());
    }

    @Test
    public void testInstructionToOneString() {
        assertEquals("Fill a bowl up with water.\n" +
                "Boil the water.\n" +
                "Place the noodles inside the bowl carefully.\n", r.instructionToOneString());
    }

    @Test
    public void testRecipeItemsToString() {
        assertEquals("*noodles**pan**bowl*", r.recipeItemsToString());
    }

}
