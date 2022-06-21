package model;
import exceptions.InvalidFoodItemException;
import exceptions.NegativePortionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodItemTest {
    private FoodItem foodItem;
    private FoodItem foodItem2;

    @BeforeEach
    public void runBefore() {
        foodItem = new FoodItem("lettuce");
        foodItem2 = new FoodItem("salmon", 3);
    }

    @Test
    public void testConstructor(){
        assertEquals("lettuce", foodItem.getName());
        assertEquals(1, foodItem.getPortions());
    }

    @Test
    public void testConstructorTwoParam() {
        assertEquals("salmon", foodItem2.getName());
        assertEquals(3, foodItem2.getPortions());
    }

    @Test
    public void testValidFoodItem() {
        try {
            foodItem2.isValid();
        } catch (InvalidFoodItemException e){
            fail("did not expect exception");
        } catch (NegativePortionException e) {
            fail("did not expect exception");
        }
    }

    @Test
    public void testInvalidFoodItem() {
        FoodItem foodItem = new FoodItem("eggg", 3);
        try {
            foodItem.isValid();
            fail("expected exception");
        } catch (InvalidFoodItemException e){
            // expected
        } catch (NegativePortionException e) {
            fail("Expected InvalidFoodItemException - caught NegativePortionException");
        }
    }

    @Test
    public void testNegativePortionFoodItem() {
        FoodItem foodItem = new FoodItem("beef", -1);
        try {
            foodItem.isValid();
            fail("expected exceptions");
        } catch (InvalidFoodItemException e) {
            fail("Expected Negative Portion Exception - caught InvalidFoodItemException");
        } catch (NegativePortionException e) {
            // expected
        }
    }

    @Test
    public void testSetter() {
        foodItem.setPortions(10);
        assertEquals(10, foodItem.getPortions());
    }


}
