package model;

import java.util.LinkedList;

// Represents a divider with its name and Subject list in it
public class Divider {
    private String name;
    private LinkedList<Subject> divider = new LinkedList<>();

    // MODIFIES: this
    // EFFECTS: add a subject to the divider if it not already there and
    // returns true if added successfully, false otherwise
    public boolean addSubject(Subject subject) {
        if (!divider.contains(subject)) {
            return divider.add(subject);
        }
        return false;
    }

    // EFFECTS: return the number of subjects of the divider
    public int dividerSize() {
        return divider.size();
    }

    // EFFECTS: get the subject at the given index
    public Subject getSubject(int i) {
        return divider.get(i);
    }

    // REQUIRES: There is at least one subject in the divider
    // MODIFIES: this
    // EFFECTS: remove a given subject from the divider and returns true, false otherwise
    public boolean removeSubject(Subject subject) {
        return divider.remove(subject);
    }

    // MODIFIES: this
    // EFFECTS: clears all the subjects from the divider
    public void clearDivider() {
        divider.clear();
    }

    public void setDividerName(String name) {
        this.name = name;
    }

    public String getDividerName() {
        return name;
    }
}

