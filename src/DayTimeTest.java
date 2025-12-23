import static org.junit.Assert.*;

import org.junit.Test;

public class DayTimeTest {
    @Test
    public void testDayTime() {
        DayTime dt = new DayTime(20, 40, "Monday");
        assertEquals(20, dt.hour);
        assertEquals(40, dt.minute);
        assertEquals("Monday", dt.dayOfWeek);

        dt = new DayTime(15, 30, "Friday");
        assertEquals(15, dt.hour);
        assertEquals(30, dt.minute);
        assertEquals("Friday", dt.dayOfWeek);
    } 
    
    @Test
    public void testAddHours() {
        DayTime dt = new DayTime(20, 40, "Monday");
        DayTime dt2 = dt.addHours(5);
        
        assertEquals(20, dt.hour); //dt should remain unchanged
        assertEquals(40, dt.minute);
        assertEquals("Monday", dt.dayOfWeek);

        assertEquals(1, dt2.hour);
        assertEquals(40, dt2.minute);
        assertEquals("Tuesday", dt2.dayOfWeek);
    } 
}
