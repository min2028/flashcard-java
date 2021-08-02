package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;


// Represent a list of FlashCards
public class Subject implements Writable {
    private final LinkedList<FlashCard> subject;
    private String name;

    // EFFECTS: Create an empty Subject
    public Subject() {
        subject = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a FlashCard to the Subject if it is not already there
    // and returns true if added successfully, false otherwise
    public boolean addFlashCard(FlashCard flashCard) {
        if (!subject.contains(flashCard)) {
            return subject.add(flashCard);
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: remove the flashCard from the Subject and returns true, false otherwise
    public boolean removeFlashCard(FlashCard flashCard) {
        if (subject.size() > 0) {
            return subject.remove(flashCard);
        }
        return false;
    }

    // EFFECTS: return the number of FlashCards in the Subject
    public int subjectSize() {
        return subject.size();
    }

    public void setSubjectName(String name) {
        this.name = name;
    }

    public String getSubjectName() {
        return name;
    }

    // EFFECTS: return a new linked list from flashcards
    // whose name or question or answer contains searchSubstring
    public LinkedList<FlashCard> searchFlashCard(String searchSubstring) {
        LinkedList<FlashCard> emptyList = new LinkedList<FlashCard>();
        LinkedList<FlashCard> foundCards = new LinkedList<FlashCard>();
        for (FlashCard fc: subject) {
            if (fc.getName().toLowerCase().contains(searchSubstring.toLowerCase())
                    || fc.getQuestion().toLowerCase().contains(searchSubstring.toLowerCase())
                    || fc.getAnswer().toLowerCase().contains(searchSubstring.toLowerCase())) {
                foundCards.add(fc);
            }
        }
        if (foundCards.size() > 0) {
            return foundCards;
        }
        return emptyList;
    }

    // EFFECTS: getting flashcard at the given index
    public FlashCard getFlashCard(int i) {
        return subject.get(i);
    }

    // MODIFIES: this
    // EFFECTS: clears all the flashcards from the subject
    public void clearSubject() {
        subject.clear();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("subject name", name);
        json.put("flashcards", subjectToJson());
        return json;
    }

    public JSONArray subjectToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FlashCard fc: subject) {
            jsonArray.put(fc.toJson());
        }
        return jsonArray;
    }

}
