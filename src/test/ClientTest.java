package test;

import main.Client;
import main.Schedule;
import main.Unit;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    private Schedule schedule;

    @BeforeEach
    public void setup() {
        schedule = new Schedule();
    }

    @Test
    public void testCapitalize() {
        assertEquals("Monday", Client.capitalize("monday"));
        assertEquals("Monday", Client.capitalize("MONDAY"));
        assertEquals("M", Client.capitalize("m"));
        assertEquals("", Client.capitalize(""));
    }

    @Test
    public void testParseHourAMPM() {
        assertEquals(0, Client.parseHour("12:00 AM"));
        assertEquals(12, Client.parseHour("12:00 PM"));
        assertEquals(9, Client.parseHour("09:00 AM"));
        assertEquals(21, Client.parseHour("09:00 PM"));
        assertEquals(23, Client.parseHour("11:00 PM"));
    }

    @Test
    public void testFindUnit() {
        Unit unit1 = new Unit("Math", 2, 1);
        Unit unit2 = new Unit("Physics", 3, 2);
        schedule.addUnit(unit1);
        schedule.addUnit(unit2);

        assertEquals(unit1, Client.findUnit(schedule, "Math"));
        assertEquals(unit2, Client.findUnit(schedule, "PHYSICS"));
        assertNull(Client.findUnit(schedule, "Chemistry"));
    }

    @Test
    public void testAddAndDeleteUnit() {
        int initialSize = schedule.getUnits().size();

        Unit unit = new Unit("Biology", 2, 1);
        schedule.addUnit(unit);

        assertEquals(initialSize + 1, schedule.getUnits().size());
        assertTrue(schedule.getUnits().contains(unit));

        // simulate delete
        schedule.getUnits().remove(unit);
        assertEquals(initialSize, schedule.getUnits().size());
        assertFalse(schedule.getUnits().contains(unit));
    }

    @Test
    public void testAddUnitWithInvalidHours() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Unit("Chemistry", 5, 0); // invalid hours
        });
        assertNotNull(ex.getMessage());
    }

    // Note: Most interactive methods in Client cannot be tested without mocking System.in/out
}
