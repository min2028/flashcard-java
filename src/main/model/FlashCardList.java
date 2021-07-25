package model;

import java.awt.datatransfer.FlavorEvent;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;





// TODO Specification
public class FlashCardList {
    private LinkedList<FlashCard> flashCardList;
    private FlashCard flashCard;

    // TODO: Specification
    public FlashCardList() {
        flashCardList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a FlashCard to the FlashCardList
    public void addFlashCard(FlashCard flashCard) {
        flashCardList.add(flashCard);
    }

    // REQUIRES: there is at least one FlashCard in the FlashCardList
    // MODIFIES: this
    // EFFECTS: remove the flashCard from the FlashCardList
    public void removeFlashCard(FlashCard flashCard) {
        flashCardList.remove(flashCard);
    }

    // EFFECTS: return the number of FlashCards in the FlashCardList
    public int numofFlashCard() {
        return flashCardList.size();
    }

    // REQUIRES: Need to have at least two FlashCards in the FlashCardlist
    // MODIFIES: this
    // EFFECTS: sort the FlashCardList in ascending date order
    public LinkedList<FlashCard> sortbyDateAscending() {
        return null;
    }

}
