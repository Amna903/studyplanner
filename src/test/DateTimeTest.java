package test;

import main.DateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeTest {

    @Test
    public void testValidDateTime() {
        DateTime dt = new DateTime("Monday", 9, 12);
        assertEquals("Monday", dt.getDay());
        assertEquals(9, dt.getStartHour());
        assertEquals(12, dt.getEndHour());
        assertEquals("Monday 09:00 AM-12:00 PM", dt.toString());
    }

    @Test
    public void testInvalidDay() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DateTime("Funday", 9, 12);
        });
        assertEquals("Invalid day: Funday", exception.getMessage());
    }

    @Test
    public void testInvalidStartHour() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DateTime("Monday", -1, 12);
        });
        assertTrue(exception.getMessage().contains("Invalid time range"));
    }

    @Test
    public void testInvalidEndHour() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DateTime("Monday", 10, 25);
        });
        assertTrue(exception.getMessage().contains("Invalid time range"));
    }

    @Test
    public void testStartHourAfterEndHour() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DateTime("Monday", 15, 10);
        });
        assertTrue(exception.getMessage().contains("Invalid time range"));
    }

    @Test
    public void testFormatTimeAMPM() {
        assertEquals("12:00 AM", DateTime.formatTime(0));
        assertEquals("01:00 AM", DateTime.formatTime(1));
        assertEquals("12:00 PM", DateTime.formatTime(12));
        assertEquals("03:00 PM", DateTime.formatTime(15));
        assertEquals("11:00 PM", DateTime.formatTime(23));
    }
}
