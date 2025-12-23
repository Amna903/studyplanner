package test;

import main.DateTime;
import main.Schedule;
import main.ScheduleEntry;
import main.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    private Schedule schedule;
    private Unit unit1, unit2;

    @BeforeEach
    public void setup() {
        schedule = new Schedule();

    
        unit1 = new Unit("COMP1010", 2, 1);  
        unit2 = new Unit("MATH1010", 3, 1);  
 
        schedule.addUnit(unit1);
        schedule.addUnit(unit2);
 
        unit1.addActivity(new ScheduleEntry("COMP1010", "Lecture", new DateTime("Monday", 9, 11)));
        unit1.addActivity(new ScheduleEntry("COMP1010", "Practical", new DateTime("Monday", 11, 12)));
        unit1.addActivity(new ScheduleEntry("COMP1010", "Self Study", new DateTime("Monday", 12, 13)));

        unit2.addActivity(new ScheduleEntry("MATH1010", "Lecture", new DateTime("Tuesday", 10, 13)));
        unit2.addActivity(new ScheduleEntry("MATH1010", "Practical", new DateTime("Tuesday", 13, 14)));
        unit2.addActivity(new ScheduleEntry("MATH1010", "Self Study", new DateTime("Tuesday", 14, 15)));
    }

    @Test
    public void testHasConflict() {
        ScheduleEntry conflict = new ScheduleEntry("COMP1010", "Lecture", new DateTime("Monday", 10, 12));
        ScheduleEntry noConflict = new ScheduleEntry("COMP1010", "Lecture", new DateTime("Monday", 13, 15));

        assertTrue(schedule.hasConflict(conflict));
        assertFalse(schedule.hasConflict(noConflict));
    }

  
  @Test
public void testGetFreeSlotsForDay() {
    List<int[]> freeSlots = schedule.getFreeSlotsForDay("Monday");
    assertNotNull(freeSlots);
    assertTrue(freeSlots.size() > 0);
 
    assertEquals(13, freeSlots.get(0)[0]);
}


    @Test
    public void testAddUnitMaxLimit() {
        Unit u3 = new Unit("PHY1010", 2, 1);
        Unit u4 = new Unit("CHEM1010", 2, 1);
        Unit u5 = new Unit("BIO1010", 2, 1);

        schedule.addUnit(u3);
        schedule.addUnit(u4);
        schedule.addUnit(u5);  
        assertEquals(4, schedule.getUnits().size());  
    }

    @Test
    public void testFindOrCreateUnit() {
         
        Unit found = schedule.getUnits().get(0);  
        assertNotNull(found);
        assertEquals("COMP1010", found.getName());
    }
}
