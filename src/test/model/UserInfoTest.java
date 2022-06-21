package model;
import exceptions.CookingUtensilNotInListException;
import exceptions.FoodItemNotInListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserInfoTest {
    UserInfo userInfo;

    @BeforeEach
    public void runBefore() {
        userInfo = new UserInfo("Bob");
    }

    @Test
    public void testConstructor() {
        assertEquals("Bob", userInfo.getName());
        assertTrue(userInfo.getCookingUtensils().isEmpty());
        assertTrue(userInfo.getFoodItems().isEmpty());
        assertTrue(userInfo.getCustomRecipes().isEmpty());
    }

    @Test
    public void testAddFoodItem(){
        FoodItem rice = new FoodItem("Rice");
        userInfo.addFoodItem(rice);

        assertEquals(1, userInfo.getFoodItems().size());
        assertEquals(rice, userInfo.getFoodItems().get(0));

        FoodItem egg = new FoodItem("Egg");
        userInfo.addFoodItem(egg);
        assertEquals(2, userInfo.getFoodItems().size());
        assertEquals(rice, userInfo.getFoodItems().get(0));
        assertEquals(egg, userInfo.getFoodItems().get(1));
    }

    @Test
    public void testAddMultipleOfSameFood() {
        FoodItem rice = new FoodItem("Rice", 1);
        FoodItem rice2 = new FoodItem("Rice" , 2);

        userInfo.addFoodItem(rice);
        FoodItem foodItem = (FoodItem) userInfo.getFoodItems().get(0);

        assertEquals(1, userInfo.getFoodItems().size());
        assertEquals(1, foodItem.getPortions());

        userInfo.addFoodItem(rice);
        assertEquals(1, userInfo.getFoodItems().size());
        assertEquals(2, foodItem.getPortions());

        userInfo.addFoodItem(rice2);
        assertEquals(1, userInfo.getFoodItems().size());
        assertEquals(4, foodItem.getPortions());
    }

    @Test
    public void testAddCookingUtensil(){
        CookingUtensil pan = new CookingUtensil("pan");
        userInfo.addCookingUtensil(pan);

        assertEquals(1, userInfo.getCookingUtensils().size());
        assertEquals(pan, userInfo.getCookingUtensils().get(0));

        CookingUtensil spatula = new CookingUtensil("spatula");
        userInfo.addCookingUtensil(spatula);
        assertEquals(2, userInfo.getCookingUtensils().size());
        assertEquals(pan, userInfo.getCookingUtensils().get(0));
        assertEquals(spatula, userInfo.getCookingUtensils().get(1));
    }

    @Test
    public void testAddCustomRecipe() {
        FoodItem beef = new FoodItem("beef");
        FoodItem carrot = new FoodItem("carrot");
        FoodItem potato = new FoodItem("potato");
        CookingUtensil pot = new CookingUtensil("pot");

        List<Item> ingredients = new ArrayList<>();
        Collections.addAll(ingredients, beef, carrot, potato, pot);

        List<String> instructions = new ArrayList<>();
        Collections.addAll(instructions, "1. Mix beef, carrot, and potato inside the pot filled with water");

        Recipe beefStew = new Recipe("beef stew", ingredients, instructions);
        userInfo.addCustomRecipe(beefStew);

        assertEquals(1, userInfo.getCustomRecipes().size());
        assertEquals("beef stew", userInfo.getCustomRecipes().get(0).getName());

        Recipe beefStewBob = new Recipe("beef stew bob", ingredients, instructions);
        userInfo.addCustomRecipe(beefStewBob);

        assertEquals(2, userInfo.getCustomRecipes().size());
        assertEquals("beef stew", userInfo.getCustomRecipes().get(0).getName());
        assertEquals("beef stew bob", userInfo.getCustomRecipes().get(1).getName());
    }

    @Test
    public void testRemoveFoodItem(){
        FoodItem chicken = new FoodItem("chicken");
        FoodItem beef = new FoodItem("beef");
        FoodItem ricee = new FoodItem("ricee");

        userInfo.addFoodItem(chicken);

        try {
            userInfo.removeFoodItem(chicken);
            assertEquals(0, userInfo.getFoodItems().size());
        } catch (FoodItemNotInListException e) {
            fail("FoodItemNotInListException thrown");
        }


        userInfo.addFoodItem(beef);
        userInfo.addFoodItem(chicken);
        try {
            userInfo.removeFoodItem(beef);
            assertEquals(1, userInfo.getFoodItems().size());
            assertEquals(chicken, userInfo.getFoodItems().get(0));
        } catch (FoodItemNotInListException e) {
            fail("FoodItemNotInListException thrown");
        }


        try {
            userInfo.removeFoodItem(ricee);
            fail("Expected FoodItemNotInListException thrown");

        } catch (FoodItemNotInListException e) {
            // expected
        }

        assertEquals(1, userInfo.getFoodItems().size());
        assertEquals(chicken, userInfo.getFoodItems().get(0));

    }

    @Test
    public void testRemovePortionButNotWholeItem() {
        FoodItem chicken = new FoodItem("chicken", 5);
        FoodItem chickenToRemove = new FoodItem("chicken", 3);
        userInfo.addFoodItem(chicken);

        try {
            userInfo.removeFoodItem(chickenToRemove);
            assertEquals(2, chicken.getPortions());
        } catch (FoodItemNotInListException e) {
            fail();
        }
    }

    @Test
    public void testRemovePortionWholeItem() {
        FoodItem chicken = new FoodItem("chicken", 3);
        FoodItem chickenToRemove = new FoodItem("chicken", 5);
        userInfo.addFoodItem(chicken);

        try {
            userInfo.removeFoodItem(chickenToRemove);
            assertEquals(0, userInfo.getFoodItems().size());
        } catch (FoodItemNotInListException e) {
            fail();
        }
    }

    @Test
    public void testRemoveCookingUtensil(){
        CookingUtensil pan = new CookingUtensil("pan");
        CookingUtensil bowl = new CookingUtensil("bowl");
        CookingUtensil spatula = new CookingUtensil("spatula");

        userInfo.addCookingUtensil(pan);
        assertEquals(1, userInfo.getCookingUtensils().size());

        try {
            userInfo.removeCookingUtensil(pan);
            assertEquals(0, userInfo.getCookingUtensils().size());
        } catch (CookingUtensilNotInListException e) {
            fail("did not expect exception");
        }

        try {
            userInfo.removeCookingUtensil(pan);
            fail();
            assertEquals(0, userInfo.getCookingUtensils().size());
        } catch (CookingUtensilNotInListException e) {
            // expected
        }

        userInfo.addCookingUtensil(bowl);
        userInfo.addCookingUtensil(spatula);
        assertEquals(2, userInfo.getCookingUtensils().size());

        try {
            userInfo.removeCookingUtensil(pan);
            fail("Expected exception");
            assertEquals(2, userInfo.getCookingUtensils().size());
        } catch (CookingUtensilNotInListException e) {
            // expected
        }

        assertEquals(bowl, userInfo.getCookingUtensils().get(0));
        assertEquals(spatula, userInfo.getCookingUtensils().get(1));

        try {
            userInfo.removeCookingUtensil(bowl);
            assertEquals(1, userInfo.getCookingUtensils().size());
            assertEquals(spatula, userInfo.getCookingUtensils().get(0));
        } catch (CookingUtensilNotInListException e) {
            fail("Exception not expected");
        }

    }

    @Test
    public void testRemoveCookingUtensilFail() {
        CookingUtensil pan = new CookingUtensil("pan", true);
        CookingUtensil pan2 = new CookingUtensil("pan", false);

        userInfo.addCookingUtensil(pan);

        try {
            userInfo.removeCookingUtensil(pan2);
            fail();
        } catch (CookingUtensilNotInListException e) {
            // expected
        }
    }

    @Test
    public void testRemoveCookingUtensilPass() {
        CookingUtensil pan = new CookingUtensil("pan", true);
        CookingUtensil pan2 = new CookingUtensil("pan", true);

        userInfo.addCookingUtensil(pan);

        try {
            userInfo.removeCookingUtensil(pan2);
            assertEquals(0, userInfo.getCookingUtensils().size());
        } catch (CookingUtensilNotInListException e) {
            fail();
        }
    }

    @Test
    public void testGenerateRecipe(){
        FoodItem noodle = new FoodItem("noodles");
        CookingUtensil pan = new CookingUtensil("pan");
        CookingUtensil bowl = new CookingUtensil("bowl");

        userInfo.addFoodItem(noodle);
        userInfo.addCookingUtensil(pan);
        userInfo.addCookingUtensil(bowl);


        List<Recipe> recipes = userInfo.generateRecipes();
        assertEquals(1, recipes.size());
        assertEquals("spaghetti", recipes.get(0).getName());

    }

    @Test
    public void testGenerateRecipeInviableCase() {
        FoodItem noodle = new FoodItem("noodles");
        CookingUtensil pan = new CookingUtensil("pan");

        userInfo.addFoodItem(noodle);
        userInfo.addCookingUtensil(pan);


        List<Recipe> recipes = userInfo.generateRecipes();
        assertEquals(0, recipes.size());
        assertTrue(recipes.isEmpty());

    }



}
