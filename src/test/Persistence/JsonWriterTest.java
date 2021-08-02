package Persistence;

// Modelled from JsonSerializationDemo

import com.sun.org.apache.xpath.internal.operations.Div;
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
        divider.addSubject(subject);
        divider.addSubject(subject1);
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
            assertEquals(0, dividerList.dividerListSize());
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
            dividerList.addDivider(divider);
            dividerList.addDivider(divider1);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDivider.json");
            writer.open();
            writer.write(dividerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDivider.json");
            dividerList = reader.read();
            assertEquals("first divider", dividerList.getDivider(0).getDividerName());
            assertEquals("second divider", dividerList.getDivider(1).getDividerName());
            assertEquals(2, dividerList.dividerListSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptySubject() {
        try {
            Divider divider = newDividerAndSubject();
            DividerList dividerList = new DividerList();
            dividerList.addDivider(divider);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySubject.json");
            writer.open();
            writer.write(dividerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySubject.json");
            dividerList = reader.read();

            assertEquals(1, dividerList.dividerListSize());
            assertEquals("first divider", dividerList.getDivider(0).getDividerName());
            assertEquals("first subject",  dividerList.getDivider(0).getSubject(0).getSubjectName());
            assertEquals(2,  dividerList.getDivider(0).dividerSize());
            assertEquals(0,  dividerList.getDivider(0).getSubject(0).subjectSize());
            assertEquals(0,  dividerList.getDivider(0).getSubject(1).subjectSize());
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
            subject.addFlashCard(flashCard);
            subject1.addFlashCard(flashCard1);
            divider.addSubject(subject);
            divider.addSubject(subject1);
            DividerList dividerList = new DividerList();
            dividerList.addDivider(divider);
            dividerList.addDivider(divider1);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDividerList.json");
            writer.open();
            writer.write(dividerList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDividerList.json");
            dividerList = reader.read();

            assertEquals(2, dividerList.dividerListSize());
            assertEquals(2, dividerList.getDivider(0).dividerSize());
            assertEquals(0, dividerList.getDivider(1).dividerSize());
            assertEquals(1, dividerList.getDivider(0).getSubject(0).subjectSize());
            assertEquals(1, dividerList.getDivider(0).getSubject(1).subjectSize());
            assertEquals("first flashcard", dividerList.getDivider(0).getSubject(0).getFlashCard(0).getName());
            assertEquals("first question", dividerList.getDivider(0).getSubject(0).getFlashCard(0).getQuestion());
            assertEquals("first answer", dividerList.getDivider(0).getSubject(0).getFlashCard(0).getAnswer());
            assertEquals(1, dividerList.getDivider(0).getSubject(0).getFlashCard(0).getDayMonthYear("day"));
            assertEquals(1, dividerList.getDivider(0).getSubject(0).getFlashCard(0).getDayMonthYear("month"));
            assertEquals(1, dividerList.getDivider(0).getSubject(0).getFlashCard(0).getDayMonthYear("year"));
            assertEquals("second flashcard", dividerList.getDivider(0).getSubject(1).getFlashCard(0).getName());
            assertEquals("second question", dividerList.getDivider(0).getSubject(1).getFlashCard(0).getQuestion());
            assertEquals("second answer", dividerList.getDivider(0).getSubject(1).getFlashCard(0).getAnswer());
            assertEquals(2, dividerList.getDivider(0).getSubject(1).getFlashCard(0).getDayMonthYear("day"));
            assertEquals(2, dividerList.getDivider(0).getSubject(1).getFlashCard(0).getDayMonthYear("month"));
            assertEquals(2, dividerList.getDivider(0).getSubject(1).getFlashCard(0).getDayMonthYear("year"));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
