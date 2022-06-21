package model;
import exceptions.InvalidItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    Item item;
    Item item2;

    @BeforeEach
    void runBefore() {
        item = new FoodItem("cheese");
        item2 = new CookingUtensil("pann");
    }

    @Test
    public void testValidItem(){
        try {
            item.isValid();

        } catch (InvalidItemException e) {
            fail("Did not expect exception");
        }

    }

    @Test
    public void testInvalidItem(){
        try {
            item2.isValid();
            fail("expected exception");
        } catch (InvalidItemException e) {
            // expected
        }
    }

    @Test
    public void testItemToString() {
        assertEquals("cheese", item.toString());
    }
}
