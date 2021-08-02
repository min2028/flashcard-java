package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// Represents a list of dividers
public class DividerList implements Writable {
    private LinkedList<Divider> dividerList = new LinkedList<>();

    // MODIFIES: this
    // EFFECTS: add divider to the dividerlist and return true if the divider is not already there and \
    // added successfully, false otherwise
    public boolean addDivider(Divider divider) {
        if (!dividerList.contains(divider)) {
            return dividerList.add(divider);
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: remove the divider from the dividerlist, true if successful, false otherwise
    public boolean removeDivider(Divider divider) {
        if (dividerList.size() > 0) {
            return dividerList.remove(divider);
        }
        return false;
    }

    // EFFECTS: get the divider at the given index
    public Divider getDivider(int i) {
        return dividerList.get(i);
    }

    // EFFECTS: return the number of dividers in the dividerList.
    public int dividerListSize() {
        return dividerList.size();
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("dividers", dividerListToJson());
        return json;
    }

    public JSONArray dividerListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Divider d: dividerList) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}

