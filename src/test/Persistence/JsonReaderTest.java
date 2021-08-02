package Persistence;

import model.Divider;
import model.DividerList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Modelled from JsonSerializationDemo

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            DividerList dividerList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDividerList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDividerList.json");
        try {
            DividerList dividerList = reader.read();
            assertEquals(0, dividerList.dividerListSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyDivider() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySubject.json");
        try {
            DividerList dividerList = reader.read();
            assertEquals(1, dividerList.dividerListSize());
            assertEquals("first divider", dividerList.getDivider(0).getDividerName());
            assertEquals(0, dividerList.getDivider(0).getSubject(0).subjectSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptySubject() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySubject.json");
        try{
            DividerList dividerList = reader.read();
            assertEquals(1, dividerList.dividerListSize());
            assertEquals(2, dividerList.getDivider(0).dividerSize());
            assertEquals("first divider", dividerList.getDivider(0).getDividerName());
            assertEquals("first subject", dividerList.getDivider(0).getSubject(0).getSubjectName());
            assertEquals("second subject", dividerList.getDivider(0).getSubject(1).getSubjectName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDividerList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDividerList.json");
        try {
            DividerList dividerList = reader.read();
            assertEquals(3, dividerList.dividerListSize());
            assertEquals(1, dividerList.getDivider(0).dividerSize());
            assertEquals(2, dividerList.getDivider(1).dividerSize());
            assertEquals(0, dividerList.getDivider(2).dividerSize());
            assertEquals("first divider", dividerList.getDivider(0).getDividerName());
            assertEquals("second divider", dividerList.getDivider(1).getDividerName());
            assertEquals("third divider", dividerList.getDivider(2).getDividerName());
            assertEquals(1, dividerList.getDivider(0).getSubject(0).subjectSize());
            assertEquals(2, dividerList.getDivider(1).getSubject(0).subjectSize());
            assertEquals(0, dividerList.getDivider(1).getSubject(1).subjectSize());
            assertEquals("first flashcard", dividerList.getDivider(0).getSubject(0).
                    getFlashCard(0).getName());
            assertEquals("first question", dividerList.getDivider(0).getSubject(0).
                    getFlashCard(0).getQuestion());
            assertEquals("first answer", dividerList.getDivider(0).getSubject(0).
                    getFlashCard(0).getAnswer());
            assertEquals(1, dividerList.getDivider(0).getSubject(0).
                    getFlashCard(0).getDayMonthYear("day"));
            assertEquals(1, dividerList.getDivider(0).getSubject(0).
                    getFlashCard(0).getDayMonthYear("month"));
            assertEquals(1, dividerList.getDivider(0).getSubject(0).
                    getFlashCard(0).getDayMonthYear("year"));
            assertEquals("third flashcard", dividerList.getDivider(1).getSubject(0).
                    getFlashCard(1).getName());
            assertEquals("third question", dividerList.getDivider(1).getSubject(0).
                    getFlashCard(1).getQuestion());
            assertEquals("third answer", dividerList.getDivider(1).getSubject(0).
                    getFlashCard(1).getAnswer());
            assertEquals(3, dividerList.getDivider(1).getSubject(0).
                    getFlashCard(1).getDayMonthYear("day"));
            assertEquals(3, dividerList.getDivider(1).getSubject(0).
                    getFlashCard(1).getDayMonthYear("month"));
            assertEquals(3, dividerList.getDivider(1).getSubject(0).
                    getFlashCard(1).getDayMonthYear("year"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}

