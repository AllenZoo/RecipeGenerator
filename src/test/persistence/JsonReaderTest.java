package persistence;

import model.CookingUtensil;
import model.FoodItem;
import model.UserInfo;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/pizza.json");
        try {
            UserInfo userInfo = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void  testReaderEmptyUserInfo() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUserInfo.json");
        try {
            UserInfo userInfo = reader.read();
            assertEquals("User 1", userInfo.getName());
            assertTrue(userInfo.getFoodItems().isEmpty());
            assertTrue(userInfo.getCookingUtensils().isEmpty());
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }

    @Test
    public void testReaderGeneralUserInfo() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUserInfo.json");
        try {
            UserInfo userInfo  = reader.read();
            assertEquals("User 1", userInfo.getName());
            assertEquals(2, userInfo.getFoodItems().size());
            assertEquals(2, userInfo.getCookingUtensils().size());
            checkFoodItem("eggs", 1, (FoodItem) userInfo.getFoodItems().get(0));
            checkFoodItem("beef", 2, (FoodItem) userInfo.getFoodItems().get(1));
            checkCookingUtensil("pan", false, (CookingUtensil) userInfo.getCookingUtensils().get(0));
            checkCookingUtensil("bowl", true, (CookingUtensil) userInfo.getCookingUtensils().get(1));
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }
}
