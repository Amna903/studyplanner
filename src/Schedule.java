import java.io.*;
import java.util.*;

public class Schedule {
    public ArrayList<ScheduleEntry> entries;

    /**
     * Constructor for Schedule class
     */
    public Schedule() {
        this.entries = new ArrayList<>();
    }

    /**
     * Adds a ScheduleEntry to the schedule
     * @param entry
     */
    public void addEntry(ScheduleEntry entry) {
        this.entries.add(entry);
    }

    /**
     * Saves the schedule to a CSV file
     * @param fileName
     */
    public void saveToCSV(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (ScheduleEntry entry : entries) {
            writer.write(entry.toString());
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Loads the schedule from a CSV file
     * @param fileName
     */
    public void loadFromCSV(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String unitName = parts[0];
            String activityType = parts[1];
            String dayOfWeek = parts[2];
            int startHour = Integer.parseInt(parts[3].split(":")[0]);
            int startMinute = Integer.parseInt(parts[3].split(":")[1]);
            DayTime startTime = new DayTime(startHour, startMinute, dayOfWeek);

            int endHour = Integer.parseInt(parts[4].split(":")[0]);
            int endMinute = Integer.parseInt(parts[4].split(":")[1]);
            DayTime endTime = new DayTime(endHour, endMinute, dayOfWeek);

            ScheduleEntry entry = new ScheduleEntry(unitName, activityType, startTime, endTime);
            this.entries.add(entry);
        } 
        reader.close();
    }
}
