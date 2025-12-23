import java.io.*;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException {
        // Create a new Schedule object
        Schedule schedule = new Schedule();
        Scanner scanner = new Scanner(System.in);

        // Add entries to the schedule
        schedule.addEntry(createScheduleEntry("COMP1010", "Lecture", "Monday", 9, 0, 2));
        schedule.addEntry(createScheduleEntry("MATH1007", "Practical", "Tuesday", 11, 0, 1));
        schedule.addEntry(createScheduleEntry("STAT1170", "Self-Study", "Wednesday", 14, 0, 4));
        schedule.addEntry(createScheduleEntry("ENGG1000", "Lecture", "Thursday", 15, 0, 2));
        schedule.addEntry(createScheduleEntry("COMP1729", "Practical", "Friday", 10, 0, 1));
        schedule.addEntry(createScheduleEntry("MATH6174", "Self-Study", "Saturday", 9, 0, 3));


        // Save the schedule to a CSV file
        System.out.print("Enter the file name to save the schedule (e.g., schedule.csv): ");
        String fileName = scanner.nextLine();
        schedule.saveToCSV(fileName);

        // Load the schedule from the CSV file
        System.out.println("\nLoading the schedule from the file...");
        Schedule loadedSchedule = new Schedule();
        loadedSchedule.loadFromCSV(fileName);

        // Display the loaded schedule
        System.out.println("\nLoaded Schedule:");
        for (ScheduleEntry entry : loadedSchedule.entries) {
            System.out.println(entry);
        }
        
        scanner.close();
    }

    // Helper method to create a ScheduleEntry
    public static ScheduleEntry createScheduleEntry(String unitName, String activityType, 
                                                     String dayOfWeek, int startHour, 
                                                     int startMinute, int durationHours) {
        DayTime startTime = new DayTime(startHour, startMinute, dayOfWeek);
        DayTime endTime = startTime.addHours(durationHours);
        return new ScheduleEntry(unitName, activityType, startTime, endTime);
    }
}