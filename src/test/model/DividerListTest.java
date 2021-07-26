package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

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
    @Test
    void addDividerTest() {
        assertTrue(dividerList.addDivider(divider1));
        assertEquals(1, dividerList.dividerSize());
        assertFalse(dividerList.addDivider(divider1));
        assertEquals(1, dividerList.dividerSize());
        assertTrue(dividerList.addDivider(divider3));
        assertTrue(dividerList.addDivider(divider2));
        assertFalse(dividerList.addDivider(divider3));
        assertTrue(dividerList.addDivider(divider4));
        assertTrue(dividerList.addDivider(divider5));
        assertEquals(5, dividerList.dividerSize());
    }

    @Test
    void removeDividerTest() {
        assertFalse(dividerList.removeDivider(divider1));
        assertEquals(0, dividerList.dividerSize());
        assertTrue(dividerList.addDivider(divider1));
        assertEquals(1, dividerList.dividerSize());
        assertTrue(dividerList.removeDivider(divider1));
        assertEquals(0, dividerList.dividerSize());
        addDivider();
        assertTrue(dividerList.removeDivider(divider3));
        assertEquals(4, dividerList.dividerSize());
        assertFalse(dividerList.removeDivider(divider3));
        assertEquals(4, dividerList.dividerSize());
    }

    private void addDivider() {
        assertTrue(dividerList.addDivider(divider1));
        assertTrue(dividerList.addDivider(divider2));
        assertTrue(dividerList.addDivider(divider3));
        assertTrue(dividerList.addDivider(divider4));
        assertTrue(dividerList.addDivider(divider5));
    }

    @Test
    void dividerSizeTest() {
        assertEquals(0, dividerList.dividerSize());
        addDivider();
        assertEquals(5, dividerList.dividerSize());
    }
}
