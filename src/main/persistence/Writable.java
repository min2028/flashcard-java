package persistence;

import org.json.JSONObject;

// From JsonSerializationDemo

public abstract class Writable {
    // EFFECTS: returns this as JSON object
    protected abstract JSONObject toJson();
}
