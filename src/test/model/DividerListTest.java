package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DividerListTest {

    private Divider divider1;
    private Divider divider2;
    private Divider divider3;
    private Divider divider4;
    private Divider divider5;
    private DividerList dividerList;

    @BeforeEach
    public void runBefore() {
        divider1 = new Divider();
        divider2 = new Divider();
        divider3 = new Divider();
        divider4 = new Divider();
        divider5 = new Divider();
        dividerList = new DividerList();
    }

    private void addDivider() {
        assertTrue(dividerList.addDivider(divider1));
        assertTrue(dividerList.addDivider(divider2));
        assertTrue(dividerList.addDivider(divider3));
        assertTrue(dividerList.addDivider(divider4));
        assertTrue(dividerList.addDivider(divider5));
    }

    @Test
    void testConstructor() {
        assertEquals(0, dividerList.dividerListSize());
    }

    @Test
    void addDividerTest() {
        assertTrue(dividerList.addDivider(divider1));
        assertEquals(1, dividerList.dividerListSize());
        assertFalse(dividerList.addDivider(divider1));
        assertEquals(1, dividerList.dividerListSize());
        assertTrue(dividerList.addDivider(divider3));
        assertTrue(dividerList.addDivider(divider2));
        assertFalse(dividerList.addDivider(divider3));
        assertTrue(dividerList.addDivider(divider4));
        assertTrue(dividerList.addDivider(divider5));
        assertEquals(5, dividerList.dividerListSize());
    }

    @Test
    void removeDividerTest() {
        assertFalse(dividerList.removeDivider(divider1));
        assertEquals(0, dividerList.dividerListSize());
        assertTrue(dividerList.addDivider(divider1));
        assertEquals(1, dividerList.dividerListSize());
        assertTrue(dividerList.removeDivider(divider1));
        assertEquals(0, dividerList.dividerListSize());
        addDivider();
        assertTrue(dividerList.removeDivider(divider3));
        assertEquals(4, dividerList.dividerListSize());
        assertFalse(dividerList.removeDivider(divider3));
        assertEquals(4, dividerList.dividerListSize());
    }

    @Test
    void dividerSizeTest() {
        assertEquals(0, dividerList.dividerListSize());
        addDivider();
        assertEquals(5, dividerList.dividerListSize());
    }

    @Test
    void getDividerTest() {
        addDivider();
        assertEquals(divider1, dividerList.getDivider(0));
        assertEquals(divider2, dividerList.getDivider(1));

    }
}
