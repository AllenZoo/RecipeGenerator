package persistence;

import model.CookingUtensil;
import model.FoodItem;
import model.Item;
import model.UserInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public UserInfo read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private UserInfo parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        UserInfo userInfo = new UserInfo(name);
        addItems(userInfo, jsonObject);
        return userInfo;
    }

    // MODIFIES: userInfo
    // EFFECTS: parses items from JSON object and adds them to the user
    private void addItems(UserInfo userInfo, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("food items");
        JSONArray jsonArray2 = jsonObject.getJSONArray("cooking utensils");

        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addFoodItem(userInfo, nextItem);
        }

        for (Object json: jsonArray2) {
            JSONObject nextItem = (JSONObject) json;
            addCookingItem(userInfo, nextItem);
        }

    }

    // MODIFIES: userInfo
    // EFFECTS: parses food items from JSON object and adds them to the user
    private void addFoodItem(UserInfo userInfo, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int portions = jsonObject.getInt("portions");
        FoodItem foodItem = new FoodItem(name, portions);
        userInfo.addFoodItem(foodItem);
    }

    // MODIFIES: userInfo
    // EFFECTS: parses cooking utensils from JSON object and adds them to the user
    private void addCookingItem(UserInfo userInfo, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        boolean isDamaged = jsonObject.getBoolean("isDamaged");
        CookingUtensil cookingUtensil = new CookingUtensil(name, isDamaged);
        userInfo.addCookingUtensil(cookingUtensil);
    }
}
