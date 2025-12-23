package main;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Schedule {
    private static final int MAX_UNITS = 4; // max units per semester
    private ArrayList<Unit> units;

    public Schedule() {
        units = new ArrayList<>();
    }

    /** Adds unit, checking the max limit */
    public void addUnit(Unit unit) {
        if (units.size() >= MAX_UNITS) {
            System.out.println("Warning: Cannot add unit " + unit.getName() + ". Maximum " + MAX_UNITS + " units reached.");
            return;
        }
        units.add(unit);
    }
 
   public boolean hasConflict(ScheduleEntry newEntry) {
    for (Unit unit : units) {
        for (ScheduleEntry entry : unit.getActivities()) {
            
            if (entry.getDateTime().getDay().equalsIgnoreCase(newEntry.getDateTime().getDay())) {

                    //convert hours to minutes
                int newStart = newEntry.getDateTime().getStartHour() * 60;
                int newEnd = newEntry.getDateTime().getEndHour() * 60;
                int existingStart = entry.getDateTime().getStartHour() * 60;
                int existingEnd = entry.getDateTime().getEndHour() * 60;

         
                boolean overlap = !(newEnd <= existingStart || newStart >= existingEnd);
                if (overlap) {
                      return true;
                }
            }
        }
    }
    return false;
}

public List<int[]> getFreeSlotsForDay(String day) {
    List<int[]> freeSlots = new ArrayList<>();

    // Working hours 9 AM – 5 PM
    int start = 9, end = 17;
    List<DateTime> busy = getBusyTimesForDay(day);

    // Sort by start time
    busy.sort(Comparator.comparingInt(DateTime::getStartHour));

    int current = start;
    for (DateTime d : busy) {
        if (d.getStartHour() > current) {
            freeSlots.add(new int[]{current, d.getStartHour()});
        }
        current = Math.max(current, d.getEndHour());
    }

    if (current < end) {
        freeSlots.add(new int[]{current, end});
    }

    return freeSlots;
}
public void addEntry(ScheduleEntry entry) {
    Unit unit = findOrCreateUnit(entry.getUnitName());
    if (!hasConflict(entry)) {
        unit.addActivity(entry);
    } else {
        System.out.println("Warning: Conflict detected for " + entry + ", skipped.");
    }
}

private List<DateTime> getBusyTimesForDay(String day) {
    List<DateTime> list = new ArrayList<>();
    for (Unit unit : units) {  // iterate through all units
        for (ScheduleEntry entry : unit.getActivities()) {  // then each activity
            if (entry.getDateTime().getDay().equalsIgnoreCase(day)) {
                list.add(entry.getDateTime());
            }
        }
    }
    return list;
}


    /**loads schedule   */
  public void loadFromFile(String filename) throws Exception {
    units.clear();  
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty() && !line.equals("Unit Name,Activity Type,Day,Start Time,End Time")) {
                parseCsvLine(line);
            }
        }
    }
 
    for (Unit unit : units) {
        int lectureH = 0, practicalH = 0;
        for (ScheduleEntry entry : unit.getActivities()) {
            int duration = entry.getDateTime().getEndHour() - entry.getDateTime().getStartHour();
            switch (entry.getActivityType()) {
                case "Lecture" -> lectureH += duration;
                case "Practical" -> practicalH += duration;
            }
        }
      
        unit.setHours(lectureH, practicalH);
    }
}

 
    private void parseCsvLine(String line) throws Exception {
 
        Pattern customPattern = Pattern.compile("(\\w+),0,0 - (\\w+-?\\w*) - (\\w+) \\((\\d{2}:\\d{2}) to (\\d{2}:\\d{2})\\)");
        Matcher customMatcher = customPattern.matcher(line);
        if (customMatcher.find()) {
            String unitName = customMatcher.group(1);
            String activityType = customMatcher.group(2).replace("-", " ");
            String day = customMatcher.group(3);
            String startTime = customMatcher.group(4);
            String endTime = customMatcher.group(5);
            int startHour = parseHour24(startTime);
            int endHour = parseHour24(endTime);
            DateTime dateTime = new DateTime(day, startHour, endHour);
            ScheduleEntry entry = new ScheduleEntry(unitName, activityType, dateTime);
            Unit unit = findOrCreateUnit(unitName);
            if (!hasConflict(entry)) {
                unit.addActivity(entry);
            } else {
                System.out.println("Warning: Conflict detected for " + entry + ", skipped.");
            }
            return;
        }

 
        String[] parts = line.split(",");
        if (parts.length == 5) {
            String unitName = parts[0];
            String activityType = parts[1].replace("-", " ");
            String day = parts[2];
            String startTime = parts[3];
            String endTime = parts[4];
            int startHour = parseHour12(startTime);
            int endHour = parseHour12(endTime);
            DateTime dateTime = new DateTime(day, startHour, endHour);
            ScheduleEntry entry = new ScheduleEntry(unitName, activityType, dateTime);
            Unit unit = findOrCreateUnit(unitName);
            if (!hasConflict(entry)) {
                unit.addActivity(entry);
            } else {
                System.out.println("Warning: Conflict detected for " + entry + ", skipped.");
            }
            return;
        }

        System.out.println("Warning: Invalid CSV line format, skipped: " + line);
    }
 
    private int parseHour24(String timeStr) {
        return Integer.parseInt(timeStr.split(":")[0]);
    }
 
    private int parseHour12(String timeStr) {
        String[] parts = timeStr.trim().split(" ");
        int hour = Integer.parseInt(parts[0].split(":")[0]);
        if (parts.length > 1 && parts[1].equalsIgnoreCase("PM") && hour != 12) {
            hour += 12;
        } else if (parts.length > 1 && parts[1].equalsIgnoreCase("AM") && hour == 12) {
            hour = 0;
        }
        return hour;
    }

    /**saves schedule*/
    public void saveToFile(String filename) throws Exception {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("Unit Name,Activity Type,Day,Start Time,End Time");
            for (Unit unit : units) {
                for (ScheduleEntry entry : unit.getActivities()) {
                    pw.println(entry.toCsv());
                }
            }
        }
    }

        private Unit findOrCreateUnit(String name) {
        for (Unit unit : units) {
            if (unit.getName().equals(name)) {
                return unit;
            }
        }
        Unit newUnit = new Unit(name, 2, 1);  
        addUnit(newUnit);
        return newUnit;
    }

     public void validateSchedule() {
        validateScheduleRecursive(units, 0);
    }
 
    private void validateScheduleRecursive(ArrayList<Unit> units, int index) {
        if (index >= units.size()) {
            return; // Base case
        }
        Unit unit = units.get(index);
        int totalHours = 0;
        boolean hasLecture = false, hasPractical = false, hasSelfStudy = false;
        for (ScheduleEntry entry : unit.getActivities()) {
            totalHours += entry.getDateTime().getEndHour() - entry.getDateTime().getStartHour();
            String type = entry.getActivityType();
            if (type.equals("Lecture")) hasLecture = true;
            else if (type.equals("Practical")) hasPractical = true;
            else if (type.equals("Self Study")) hasSelfStudy = true;
        }
        if (totalHours != 10) {
            System.out.println("Warning: Unit " + unit.getName() + " has " + totalHours + " hours, expected 10.");
        }
        if (!hasLecture || !hasPractical || !hasSelfStudy) {
            System.out.println("Warning: Unit " + unit.getName() + " missing required activities (Lecture, Practical, Self Study).");
        }
        validateScheduleRecursive(units, index + 1); //recursive call
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
}