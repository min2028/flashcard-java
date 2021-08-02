package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

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
        assertEquals(0, divider.dividerSize());
    }

    @Test
    void addSubjectTest() {
        assertTrue(divider.addSubject(subject1));
        assertEquals(1, divider.dividerSize());
        assertFalse(divider.addSubject(subject1));
        assertEquals(1, divider.dividerSize());
        divider.addSubject(subject1);
        assertTrue(divider.addSubject(subject2));
        assertTrue(divider.addSubject(subject3));
        assertEquals(3, divider.dividerSize());
    }

    @Test
    void dividerSizeTest() {
        divider.addSubject(subject1);
        assertEquals(1, divider.dividerSize());
        assertEquals(0, divider2.dividerSize());
        divider.addSubject(subject2);
        divider.addSubject(subject3);
        assertEquals(3, divider.dividerSize());
    }

    @Test
    void removeSubjectTest() {
        assertFalse(divider.removeSubject(subject1));
        divider.addSubject(subject1);
        assertTrue(divider.removeSubject(subject1));
        assertFalse(divider.removeSubject(subject1));

    }

    @Test
    void clearDividerTest() {
        assertEquals(0, divider.dividerSize());
        assertTrue(divider.addSubject(subject1));
        assertTrue(divider.addSubject(subject2));
        assertTrue(divider.addSubject(subject3));
        assertEquals(3, divider.dividerSize());
        divider.clearDivider();
        assertEquals(0, divider.dividerSize());
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
        divider.addSubject(subject1);
        divider.addSubject(subject2);
        divider.addSubject(subject3);
        assertEquals(subject1, divider.getSubject(0));
        assertEquals(subject2, divider.getSubject(1));
    }
}
