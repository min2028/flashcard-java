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
        assertTrue(dividerList.add(divider1));
        assertTrue(dividerList.add(divider2));
        assertTrue(dividerList.add(divider3));
        assertTrue(dividerList.add(divider4));
        assertTrue(dividerList.add(divider5));
    }

    @Test
    void testConstructor() {
        assertEquals(0, dividerList.size());
    }

    @Test
    void addDividerTest() {
        assertTrue(dividerList.add(divider1));
        assertEquals(1, dividerList.size());
        assertFalse(dividerList.add(divider1));
        assertEquals(1, dividerList.size());
        assertTrue(dividerList.add(divider3));
        assertTrue(dividerList.add(divider2));
        assertFalse(dividerList.add(divider3));
        assertTrue(dividerList.add(divider4));
        assertTrue(dividerList.add(divider5));
        assertEquals(5, dividerList.size());
    }

    @Test
    void removeDividerTest() {
        assertFalse(dividerList.remove(divider1));
        assertEquals(0, dividerList.size());
        assertTrue(dividerList.add(divider1));
        assertEquals(1, dividerList.size());
        assertTrue(dividerList.remove(divider1));
        assertEquals(0, dividerList.size());
        addDivider();
        assertTrue(dividerList.remove(divider3));
        assertEquals(4, dividerList.size());
        assertFalse(dividerList.remove(divider3));
        assertEquals(4, dividerList.size());
    }

    @Test
    void dividerSizeTest() {
        assertEquals(0, dividerList.size());
        addDivider();
        assertEquals(5, dividerList.size());
    }

    @Test
    void getDividerTest() {
        addDivider();
        assertEquals(divider1, dividerList.get(0));
        assertEquals(divider2, dividerList.get(1));
    }

    @Test
    void getDividersTest() {
        addDivider();
        for (int i = 0; i < dividerList.size(); i++) {
            assertEquals(dividerList.get(i), dividerList.getList().get(i));
        }
    }
}
