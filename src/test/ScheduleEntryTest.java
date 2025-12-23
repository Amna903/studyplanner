package test;

import main.DateTime;
import main.ScheduleEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScheduleEntryTest {

    @Test
    public void testConstructorAndGetters() {
        DateTime dt = new DateTime("Tuesday", 14, 16);
        ScheduleEntry entry = new ScheduleEntry("Physics", "Practical", dt);

        assertEquals("Physics", entry.getUnitName());
        assertEquals("Practical", entry.getActivityType());
        assertEquals(dt, entry.getDateTime());
    }

    @Test
    public void testInvalidActivityType() {
        DateTime dt = new DateTime("Monday", 9, 11);
        assertThrows(IllegalArgumentException.class, () -> {
            new ScheduleEntry("Math", "Homework", dt);
        });
    }

    @Test
    public void testActivityTypeNormalization() {
        DateTime dt = new DateTime("Monday", 9, 11);
        ScheduleEntry entry = new ScheduleEntry("Math", "Self-Study", dt);
        assertEquals("Self Study", entry.getActivityType());
    }

    @Test
    public void testToCsv() {
        DateTime dt = new DateTime("Tuesday", 14, 16);
        ScheduleEntry entry = new ScheduleEntry("Physics", "Practical", dt);

         String expected = "Physics,Practical,Tuesday,02:00 PM,04:00 PM";
        assertEquals(expected, entry.toCsv());
    }

    @Test
    public void testToStringFormat() {
        DateTime dt = new DateTime("Wednesday", 10, 12);
        ScheduleEntry entry = new ScheduleEntry("Biology", "Self Study", dt);

         String expected = "Biology - Self Study: Wednesday 10:00 AM - 12:00 PM";
        assertEquals(expected, entry.toString());
    }
}
