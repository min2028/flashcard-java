package Persistence;

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
            assertEquals(0, dividerList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyDivider() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySubject.json");
        try {
            DividerList dividerList = reader.read();
            assertEquals(1, dividerList.size());
            assertEquals("first divider", dividerList.get(0).getDividerName());
            assertEquals(0, dividerList.get(0).get(0).size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptySubject() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySubject.json");
        try{
            DividerList dividerList = reader.read();
            assertEquals(1, dividerList.size());
            assertEquals(2, dividerList.get(0).size());
            assertEquals("first divider", dividerList.get(0).getDividerName());
            assertEquals("first subject", dividerList.get(0).get(0).getSubjectName());
            assertEquals("second subject", dividerList.get(0).get(1).getSubjectName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDividerList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDividerList.json");
        try {
            DividerList dividerList = reader.read();
            assertEquals(3, dividerList.size());
            assertEquals(1, dividerList.get(0).size());
            assertEquals(2, dividerList.get(1).size());
            assertEquals(0, dividerList.get(2).size());
            assertEquals("first divider", dividerList.get(0).getDividerName());
            assertEquals("second divider", dividerList.get(1).getDividerName());
            assertEquals("third divider", dividerList.get(2).getDividerName());
            assertEquals(1, dividerList.get(0).get(0).size());
            assertEquals(2, dividerList.get(1).get(0).size());
            assertEquals(0, dividerList.get(1).get(1).size());
            assertEquals("first flashcard", dividerList.get(0).get(0).
                    get(0).getName());
            assertEquals("first question", dividerList.get(0).get(0).
                    get(0).getQuestion());
            assertEquals("first answer", dividerList.get(0).get(0).
                    get(0).getAnswer());
            assertEquals(1, dividerList.get(0).get(0).
                    get(0).getDayMonthYear("day"));
            assertEquals(1, dividerList.get(0).get(0).
                    get(0).getDayMonthYear("month"));
            assertEquals(1, dividerList.get(0).get(0).
                    get(0).getDayMonthYear("year"));
            assertEquals("third flashcard", dividerList.get(1).get(0).
                    get(1).getName());
            assertEquals("third question", dividerList.get(1).get(0).
                    get(1).getQuestion());
            assertEquals("third answer", dividerList.get(1).get(0).
                    get(1).getAnswer());
            assertEquals(3, dividerList.get(1).get(0).
                    get(1).getDayMonthYear("day"));
            assertEquals(3, dividerList.get(1).get(0).
                    get(1).getDayMonthYear("month"));
            assertEquals(3, dividerList.get(1).get(0).
                    get(1).getDayMonthYear("year"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}

