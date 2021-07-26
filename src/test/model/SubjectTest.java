package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubjectTest {
    private FlashCard flashCard1;
    private FlashCard flashCard2;
    private FlashCard flashCard3;
    private FlashCard flashCard4;
    private FlashCard flashCard5;
    private FlashCard flashCard6;
    private Subject subject;
    private Subject mtlist;

    @BeforeEach
    public void runBefore() {
        flashCard1 = new FlashCard();
        flashCard2 = new FlashCard();
        flashCard3 = new FlashCard();
        flashCard4 = new FlashCard();
        flashCard5 = new FlashCard();
        flashCard6 = new FlashCard();
        subject = new Subject();
        flashCard1.setName("Aung");
        flashCard2.setName("Khant");
        flashCard3.setName("Min");
        flashCard4.setName("What");
        flashCard5.setName("Whatman");
        flashCard6.setName("Coumpo");
        flashCard1.createQuestion("How are you?");
        flashCard1.createAnswer("I'm good.");
        flashCard2.createQuestion("What are you?");
        flashCard2.createAnswer("What do you mean?");
        flashCard3.createQuestion("When are you born?");
        flashCard3.createAnswer("June");
        flashCard4.createQuestion("Why are you here?");
        flashCard4.createAnswer("What?");
        flashCard5.createQuestion("Whose are you?");
        flashCard5.createAnswer("Mom");
        flashCard6.createQuestion("Your name is what?");
        flashCard6.createAnswer("Nothing");
    }

    private void addFlashCards() {
        assertTrue(subject.addFlashCard(flashCard1));
        assertTrue(subject.addFlashCard(flashCard2));
        assertTrue(subject.addFlashCard(flashCard3));
        assertTrue(subject.addFlashCard(flashCard4));
        assertTrue(subject.addFlashCard(flashCard5));
        assertTrue(subject.addFlashCard(flashCard6));
    }

    @Test
    void testConstructor() {
        assertEquals(0, subject.subjectSize());
    }

    @Test
    void addFlashCardTest() {
        addFlashCards();
        assertEquals(6, subject.subjectSize());
        assertFalse(subject.addFlashCard(flashCard5));
        assertEquals(6, subject.subjectSize());
        assertFalse(subject.addFlashCard(flashCard6));
        assertFalse(subject.addFlashCard(flashCard1));
        assertEquals(6, subject.subjectSize());
    }

    @Test
    void removeFlashCardTest() {
        assertTrue(subject.addFlashCard(flashCard1));
        assertTrue(subject.removeFlashCard(flashCard1));
        assertFalse(subject.removeFlashCard(flashCard1));
        assertTrue(subject.addFlashCard(flashCard2));
        assertTrue(subject.addFlashCard(flashCard3));
        assertTrue(subject.addFlashCard(flashCard4));
        assertTrue(subject.addFlashCard(flashCard5));
        assertTrue(subject.addFlashCard(flashCard6));
        assertTrue(subject.removeFlashCard(flashCard2));
        assertFalse(subject.removeFlashCard(flashCard2));

    }

    @Test
    void subjectSizeTest() {
        assertTrue(subject.addFlashCard(flashCard1));
        assertEquals(1, subject.subjectSize());
        assertFalse(subject.addFlashCard(flashCard1));
        assertTrue(subject.addFlashCard(flashCard2));
        assertTrue(subject.addFlashCard(flashCard3));
        assertTrue(subject.addFlashCard(flashCard4));
        assertTrue(subject.addFlashCard(flashCard5));
        assertTrue(subject.addFlashCard(flashCard6));
        assertEquals(6, subject.subjectSize());
    }

    @Test
    void notFindFlashCardTest() {
        assertEquals("Aung", flashCard1.getName());
        assertEquals("Khant", flashCard2.getName());
        addFlashCards();
        assertEquals(mtlist, subject.searchFlashCard("Kind"));
    }

    @Test
    void foundFlashCardTest() {
        addFlashCards();
        LinkedList<FlashCard> whatSubject = new LinkedList<>();
        whatSubject.add(flashCard2);
        whatSubject.add(flashCard4);
        whatSubject.add(flashCard5);
        whatSubject.add(flashCard6);
        assertEquals(4, whatSubject.size());
        LinkedList<FlashCard> a = subject.searchFlashCard("what");
        assertEquals(4, a.size());
        for (int i = 0; i < whatSubject.size()  ; i++){
            assertEquals(whatSubject.get(i), a.get(i));
        }
        LinkedList<FlashCard> b = subject.searchFlashCard("What");
        assertEquals(4, b.size());
        for (int i = 0; i < whatSubject.size()  ; i++) {
            assertEquals(whatSubject.get(i), b.get(i));
        }
    }

    @Test
    void clearSubjectTest() {
        assertEquals(0, subject.subjectSize());
        addFlashCards();
        assertEquals(6, subject.subjectSize());
        subject.clearSubject();
        assertEquals(0, subject.subjectSize());
    }

    @Test
    void getSubjectNameTest() {
        subject.setSubjectName("Aung");
        assertEquals("Aung", subject.getSubjectName());
    }
}
