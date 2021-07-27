package model;

import java.util.LinkedList;

public class DividerList {
    private Divider divider;
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

    // EFFECTS: return the number of dividers in the dividerList.
    public int dividerSize() {
        return dividerList.size();
    }
}

