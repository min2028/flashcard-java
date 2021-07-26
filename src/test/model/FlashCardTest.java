package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlashCardTest {
    private FlashCard flashCard;

    @BeforeEach
    public void runBefore(){
        flashCard = new FlashCard();
    }

    @Test
    void getDayMonthYearTest() {
        flashCard.setDayMonthYear(9,10, 11);
        assertEquals(10, flashCard.getDayMonthYear("month"));
        assertEquals(9, flashCard.getDayMonthYear("day"));
        assertEquals(11, flashCard.getDayMonthYear("year"));
        assertEquals(0, flashCard.getDayMonthYear("mdy"));
        flashCard.setDayMonthYear(9,10, 11);
        assertEquals(10, flashCard.getDayMonthYear("Month"));
        assertEquals(9, flashCard.getDayMonthYear("Day"));
        assertEquals(11, flashCard.getDayMonthYear("Year"));
        flashCard.setDayMonthYear(10,11, 1200);
        assertEquals(11, flashCard.getDayMonthYear("MONTH"));
        assertEquals(10, flashCard.getDayMonthYear("DAY"));
        assertEquals(1200, flashCard.getDayMonthYear("YEAR"));
    }

    @Test
    void getDateTest() {
        flashCard.setDayMonthYear(9,10, 2001);
        assertEquals ("9/10/2001", flashCard.getDate());
        flashCard.setDayMonthYear(31,12, 10);
        assertEquals ("31/12/10", flashCard.getDate());
    }

    @Test
    void getFlashCardNameTest() {
        flashCard.setName("Aung");
        assertEquals("Aung", flashCard.getName());
    }
}