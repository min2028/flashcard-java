package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


// Represent a list of FlashCards
public class Subject extends Writable implements Compartable {
    private final LinkedList<FlashCard> subject;
    private String name;

    // EFFECTS: Create an empty Subject
    public Subject() {
        subject = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a FlashCard to the Subject if it is not already there
    // and returns true if added successfully, false otherwise
    @Override
    public boolean add(Object flashCard) {
        if (!subject.contains(flashCard)) {
            return subject.add((FlashCard) flashCard);
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: remove the flashCard from the Subject and returns true, false otherwise
    @Override
    public boolean remove(Object flashCard) {
        if (subject.size() > 0) {
            return subject.remove((FlashCard) flashCard);
        }
        return false;
    }

    // EFFECTS: return the number of FlashCards in the Subject
    @Override
    public int size() {
        return subject.size();
    }

    // EFFECTS: getting flashcard at the given index
    public FlashCard get(int index) {
        return subject.get(index);
    }

    // EFFECTS: returns an unmodifiable list of flashcards in this subject
    public List<FlashCard> getList() {
        return Collections.unmodifiableList(subject);
    }

    public void setSubjectName(String name) {
        this.name = name;
    }

    public String getSubjectName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("subject name", name);
        json.put("flashcards", subjectToJson());
        return json;
    }

    // EFFECTS: returns flashCards in this subject as a JSON array
    public JSONArray subjectToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FlashCard fc : subject) {
            jsonArray.put(fc.toJson());
        }
        return jsonArray;
    }

}
