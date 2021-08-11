package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Represents a divider with its name and Subject list in it
public class Divider extends Writable implements Compartable {
    private String name;
    LinkedList<Subject> divider;

    // EFFECTS: Create an emtpy divider
    public Divider() {
        divider = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a subject to the divider if it not already there and
    // returns true if added successfully, false otherwise
    @Override
    public boolean add(Object subject) {
        if (subject instanceof Subject) {
            if (!divider.contains(subject)) {
                return divider.add((Subject) subject);
            }
        }
        return false;
    }

    // REQUIRES: There is at least one subject in the divider
    // MODIFIES: this
    // EFFECTS: remove a given subject from the divider and returns true, false otherwise
    @Override
    public boolean remove(Object subject) {
        return divider.remove((Subject) subject);
    }

    // EFFECTS: return the number of subjects of the divider
    @Override
    public int size() {
        return divider.size();
    }

    // EFFECTS: get the subject at the given index
    public Subject get(int index) {
        return divider.get(index);
    }

    // EFFECTS: returns an unmodifiable list of subjects in this divider
    public List<Subject> getList() {
        return Collections.unmodifiableList(divider);
    }

    public void setDividerName(String name) {
        this.name = name;
    }

    public String getDividerName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("divider name", name);
        json.put("subjects", dividerToJson());
        return json;
    }

    public JSONArray dividerToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subject sbj: divider) {
            jsonArray.put(sbj.toJson());
        }
        return jsonArray;
    }
}

