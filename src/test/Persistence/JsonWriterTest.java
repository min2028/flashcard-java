package Persistence;

// Modelled from JsonSerializationDemo

import model.Divider;
import model.DividerList;
import model.FlashCard;
import model.Subject;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    private Divider newDividerAndSubject() {
        Divider divider = new Divider();
        divider.setDividerName("first divider");
        Subject subject = new Subject();
        subject.setSubjectName("first subject");
        Subject subject1 = new Subject();
        subject1.setSubjectName("second subject");
        divider.add(subject);
        divider.add(subject1);
        return divider;
    }

    @Test
    void testWriterInvalidFile() {
        try {
            DividerList dividerList = new DividerList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDividerList() {
        try {
            DividerList dividerList = new DividerList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDividerList.json");
            writer.open();
            writer.write(dividerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDividerList.json");
            dividerList = reader.read();
            assertEquals(0, dividerList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyDivider() {
        try {
            Divider divider = new Divider();
            divider.setDividerName("first divider");
            Divider divider1 = new Divider();
            divider1.setDividerName("second divider");
            DividerList dividerList = new DividerList();
            dividerList.add(divider);
            dividerList.add(divider1);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDivider.json");
            writer.open();
            writer.write(dividerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDivider.json");
            dividerList = reader.read();
            assertEquals("first divider", dividerList.get(0).getDividerName());
            assertEquals("second divider", dividerList.get(1).getDividerName());
            assertEquals(2, dividerList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptySubject() {
        try {
            Divider divider = newDividerAndSubject();
            DividerList dividerList = new DividerList();
            dividerList.add(divider);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySubject.json");
            writer.open();
            writer.write(dividerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySubject.json");
            dividerList = reader.read();

            assertEquals(1, dividerList.size());
            assertEquals("first divider", dividerList.get(0).getDividerName());
            assertEquals("first subject",  dividerList.get(0).get(0).getSubjectName());
            assertEquals(2,  dividerList.get(0).size());
            assertEquals(0,  dividerList.get(0).get(0).size());
            assertEquals(0,  dividerList.get(0).get(1).size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralDividerList() {
        try {
            Divider divider = new Divider();
            divider.setDividerName("first divider");
            Divider divider1 = new Divider();
            divider1.setDividerName("second divider");
            Subject subject = new Subject();
            subject.setSubjectName("first subject");
            Subject subject1 = new Subject();
            subject1.setSubjectName("second subject");
            FlashCard flashCard = new FlashCard();
            flashCard.setDayMonthYear(1, 1, 1);
            flashCard.setName("first flashcard");
            flashCard.createQuestion("first question");
            flashCard.createAnswer("first answer");
            FlashCard flashCard1 = new FlashCard();
            flashCard1.setName("second flashcard");
            flashCard1.createQuestion("second question");
            flashCard1.createAnswer("second answer");
            flashCard1.setDayMonthYear(2, 2, 2);
            subject.add(flashCard);
            subject1.add(flashCard1);
            divider.add(subject);
            divider.add(subject1);
            DividerList dividerList = new DividerList();
            dividerList.add(divider);
            dividerList.add(divider1);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDividerList.json");
            writer.open();
            writer.write(dividerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDividerList.json");
            dividerList = reader.read();

            assertEquals(2, dividerList.size());
            assertEquals(2, dividerList.get(0).size());
            assertEquals(0, dividerList.get(1).size());
            assertEquals(1, dividerList.get(0).get(0).size());
            assertEquals(1, dividerList.get(0).get(1).size());
            assertEquals("first flashcard", dividerList.get(0).get(0).get(0).getName());
            assertEquals("first question", dividerList.get(0).get(0).get(0).getQuestion());
            assertEquals("first answer", dividerList.get(0).get(0).get(0).getAnswer());
            assertEquals(1, dividerList.get(0).get(0).get(0).getDayMonthYear("day"));
            assertEquals(1, dividerList.get(0).get(0).get(0).getDayMonthYear("month"));
            assertEquals(1, dividerList.get(0).get(0).get(0).getDayMonthYear("year"));
            assertEquals("second flashcard", dividerList.get(0).get(1).get(0).getName());
            assertEquals("second question", dividerList.get(0).get(1).get(0).getQuestion());
            assertEquals("second answer", dividerList.get(0).get(1).get(0).getAnswer());
            assertEquals(2, dividerList.get(0).get(1).get(0).getDayMonthYear("day"));
            assertEquals(2, dividerList.get(0).get(1).get(0).getDayMonthYear("month"));
            assertEquals(2, dividerList.get(0).get(1).get(0).getDayMonthYear("year"));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
