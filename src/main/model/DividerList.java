package model;

import com.sun.org.apache.xpath.internal.operations.Div;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Represents a list of dividers
public class DividerList extends Writable implements Compartable {
    private LinkedList<Divider> dividerList;

    // EFFECTS: create a DividerList with no dividers in it
    public DividerList() {
        dividerList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add divider to the dividerlist and return true if the divider is not already there and \
    // added successfully, false otherwise
    @Override
    public boolean add(Object divider) {
        if (divider instanceof Divider) {
            if (!dividerList.contains(divider)) {
                return dividerList.add((Divider) divider);
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: remove the divider from the dividerlist, true if successful, false otherwise
    @Override
    public boolean remove(Object divider) {
        if (divider instanceof Divider) {
            if (dividerList.size() > 0) {
                return dividerList.remove(divider);
            }
        }
        return false;
    }

    // EFFECTS: return the number of dividers in the dividerList.
    @Override
    public int size() {
        return dividerList.size();
    }

    // EFFECTS: get the divider at the given index
    public Divider get(int index) {
        return dividerList.get(index);
    }

    // EFFECTS: returns an unmodifiable list of dividers in this dividerList
    public List<Divider> getList() {
        return Collections.unmodifiableList(dividerList);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("dividers", dividerListToJson());
        return json;
    }

    public JSONArray dividerListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Divider d : dividerList) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }

}

