package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DividerTest {
    private Divider divider;
    private Divider divider2;
    private Subject subject1;
    private Subject subject2;
    private Subject subject3;


    @BeforeEach
    public void runBefore() {
        divider = new Divider();
        divider2 = new Divider();
        subject1 = new Subject();
        subject2 = new Subject();
        subject3 = new Subject();
    }

    @Test
    void testConstructor() {
        assertEquals(0, divider.size());
    }

    @Test
    void addSubjectTest() {
        assertTrue(divider.add(subject1));
        assertEquals(1, divider.size());
        assertFalse(divider.add(subject1));
        assertEquals(1, divider.size());
        divider.add(subject1);
        assertTrue(divider.add(subject2));
        assertTrue(divider.add(subject3));
        assertEquals(3, divider.size());
    }

    @Test
    void dividerSizeTest() {
        divider.add(subject1);
        assertEquals(1, divider.size());
        assertEquals(0, divider2.size());
        divider.add(subject2);
        divider.add(subject3);
        assertEquals(3, divider.size());
    }

    @Test
    void removeSubjectTest() {
        assertFalse(divider.remove(subject1));
        divider.add(subject1);
        assertTrue(divider.remove(subject1));
        assertFalse(divider.remove(subject1));

    }

    @Test
    void getDividerNameTest() {
        divider.setDividerName("Aung");
        assertEquals("Aung", divider.getDividerName());
        divider2.setDividerName("Khant Min");
        assertEquals("Khant Min", divider2.getDividerName());
    }

    @Test
    void getSubjectTest() {
        divider.add(subject1);
        divider.add(subject2);
        divider.add(subject3);
        assertEquals(subject1, divider.get(0));
        assertEquals(subject2, divider.get(1));
    }

    @Test
    void getSubjectsTest() {
        addSubjectTest();
        for (int i = 0; i < divider.size(); i++) {
            assertEquals(divider.get(i), divider.getList().get(i));
        }
    }
}
