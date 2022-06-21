package persistence;

import org.json.JSONObject;

// Represents a writable object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
