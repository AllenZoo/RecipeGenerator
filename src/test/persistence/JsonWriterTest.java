package persistence;

import model.CookingUtensil;
import model.FoodItem;
import model.UserInfo;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{

    @Test
    public void testWriterInvalidFile() {
        try {
            UserInfo userInfo = new UserInfo("User 1");
            JsonWriter writer = new JsonWriter("./data/::pizza.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyUserInfo() {
        try {
            UserInfo userInfo = new UserInfo("User 1");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUserInfo.json");
            writer.open();
            writer.write(userInfo);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUserInfo.json");
            userInfo = reader.read();
            assertEquals("User 1", userInfo.getName());
            assertTrue(userInfo.getFoodItems().isEmpty());
            assertTrue(userInfo.getCookingUtensils().isEmpty());
        } catch (IOException e) {
            fail("IOException thrown");
        }
    }

    @Test
    public void testWriterGeneralUserInfo() {
        try {
            UserInfo userInfo = new UserInfo("User 1");
            userInfo.addFoodItem(new FoodItem("chicken", 1));
            userInfo.addFoodItem(new FoodItem("lettuce", 2));
            userInfo.addCookingUtensil(new CookingUtensil("spatula", true));
            userInfo.addCookingUtensil(new CookingUtensil("pot", false));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUserInfo.json");
            writer.open();
            writer.write(userInfo);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUserInfo.json");
            userInfo = reader.read();
            assertEquals("User 1", userInfo.getName());
            assertEquals(2, userInfo.getFoodItems().size());
            assertEquals(2, userInfo.getCookingUtensils().size());
            checkFoodItem("chicken", 1, (FoodItem) userInfo.getFoodItems().get(0));
            checkFoodItem("lettuce", 2, (FoodItem) userInfo.getFoodItems().get(1));
            checkCookingUtensil("spatula", true, (CookingUtensil) userInfo.getCookingUtensils().get(0));
            checkCookingUtensil("pot", false, (CookingUtensil) userInfo.getCookingUtensils().get(1));

        } catch (IOException e) {
            fail("IOException thrown");
        }
    }


}
