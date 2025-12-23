package test;

import main.Unit;
import main.ScheduleEntry;
import main.DateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    @Test
    public void testConstructorAndGetters() {
        Unit unit = new Unit("Math", 3, 2);
        assertEquals("Math", unit.getName());
        assertEquals(3, unit.getLectureHours());
        assertEquals(2, unit.getPracticalHours());
        assertEquals(5, unit.getSelfStudyHours()); // 10 - (3+2)
        assertNotNull(unit.getActivities());
        assertEquals(0, unit.getActivities().size());
    }

    @Test
    public void testInvalidLectureHours() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Unit("Physics", 4, 2);
        });
        assertTrue(ex.getMessage().contains("Lecture hours must be 2 or 3"));
    }

    @Test
    public void testInvalidPracticalHours() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Unit("Chemistry", 2, 5);
        });
        assertTrue(ex.getMessage().contains("Practical hours must be 1, 2, or 3"));
    }

    @Test
    public void testAddActivityAndHasActivityType() {
        Unit unit = new Unit("Math", 3, 2);
        DateTime dt = new DateTime("Monday", 9, 11);
        ScheduleEntry lecture = new ScheduleEntry("Math", "Lecture", dt);

        assertFalse(unit.hasActivityType("Lecture"));
        unit.addActivity(lecture);
        assertTrue(unit.hasActivityType("Lecture"));
    }

    @Test
    public void testSetHoursUpdatesSelfStudy() {
        Unit unit = new Unit("Math", 2, 2);
        assertEquals(6, unit.getSelfStudyHours());

        unit.setHours(3, 1);
        assertEquals(6, unit.getSelfStudyHours()); // 10 - (3+1)
        assertEquals(3, unit.getLectureHours());
        assertEquals(1, unit.getPracticalHours());
    }

    @Test
    public void testToString() {
        Unit unit = new Unit("Math", 3, 2);
        String expected = "Math (Lecture: 3h, Practical: 2h, Self-Study: 5h)";
        assertEquals(expected, unit.toString());
    }
}
