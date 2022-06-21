package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFoodItem(String name, int portions, FoodItem item) {
        assertEquals(name, item.getName());
        assertEquals(portions, item.getPortions());
    }

    protected void checkCookingUtensil(String name, boolean isDamaged, CookingUtensil item) {
        assertEquals(name, item.getName());
        assertEquals(isDamaged, item.getIsDamaged());
    }
}
