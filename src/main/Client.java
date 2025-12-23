package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/** CLI for managing study schedule */
public class Client {
    private static final String FILE_NAME = "schedule.csv";
    private static final String[] DAYS = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    public static void main(String[] args) throws Exception {
        Schedule schedule = new Schedule();
        Scanner scanner = new Scanner(System.in);

        
        try {
            schedule.loadFromFile(FILE_NAME);
            schedule.validateSchedule();
            checkIncompleteSchedules(schedule, scanner);
        } catch (Exception e) {
            System.out.println("Error loading schedule: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            displayMenu();
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();  
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            try {
                running = processMenuChoice(schedule, scanner, choice);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    /** main menu */
 private static void displayMenu() {
    System.out.println("\n====> Study Schedule Planner <====");
    System.out.println("1. Add Unit");
    System.out.println("2. Schedule Activities");
    System.out.println("3. View Schedule");
    System.out.println("4. Validate Schedule");
    System.out.println("5. Save and Exit");
    System.out.println("6. Delete Unit");
    System.out.println("7. Delete Activity");
System.out.println("8. Update Activity");

    System.out.print("Choose an option: ");
}


    /** menu choice */
    private static boolean processMenuChoice(Schedule schedule, Scanner scanner, int choice) throws Exception {
        switch (choice) {
            case 1:
                addUnit(schedule, scanner);
                break;
            case 2:
                scheduleActivities(schedule, scanner);
                break;
            case 3:
                viewSchedule(schedule);
                break;
            case 4:
                schedule.validateSchedule();
                break;
            case 5:
                schedule.saveToFile(FILE_NAME);
                System.out.println("Schedule saved successfully. Goodbye!");
                return false;
            case 6:
            deleteUnit(schedule, scanner);
            break;
            case 7:
    deleteActivity(schedule, scanner);
    break;
case 8:
    updateActivity(schedule, scanner);
    break;

            default:
                System.out.println("Invalid option! Try again.");
        }
        return true;
    }

    /** checks for incomplete schedules */
    private static void checkIncompleteSchedules(Schedule schedule, Scanner scanner) throws Exception {
        for (Unit unit : schedule.getUnits()) {
            int totalHours = 0;
            boolean hasLecture = false, hasPractical = false, hasSelfStudy = false;
            for (ScheduleEntry entry : unit.getActivities()) {
                totalHours += entry.getDateTime().getEndHour() - entry.getDateTime().getStartHour();
                String type = entry.getActivityType();
                if (type.equals("Lecture")) hasLecture = true;
                else if (type.equals("Practical")) hasPractical = true;
                else if (type.equals("Self Study")) hasSelfStudy = true;
            }
            if (totalHours < 10 || !hasLecture || !hasPractical || !hasSelfStudy) {
                System.out.println("\n⚠️ Unit " + unit.getName() + " is incomplete (" + totalHours + "/10 hours).");
                System.out.print("Schedule now? (y/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    scheduleActivities(schedule, scanner, unit);
                }
            }
        }
    }

    /**Adds a new unit */
    private static void addUnit(Schedule schedule, Scanner scanner) {
        System.out.print("Enter unit name: ");
        String name = scanner.nextLine();

        System.out.print("Enter lecture hours (2 or 3): ");
        int lectureHours = safeIntInput(scanner);
        if (lectureHours < 2 || lectureHours > 3) {
            System.out.println("Lecture hours must be 2 or 3.");
            return;
        }

        System.out.print("Enter practical hours (1–3): ");
        int practicalHours = safeIntInput(scanner);
        if (practicalHours < 1 || practicalHours > 3) {
            System.out.println("Practical hours must be between 1 and 3.");
            return;
        }

        try {
            Unit unit = new Unit(name, lectureHours, practicalHours);
            int initialSize = schedule.getUnits().size();
            schedule.addUnit(unit);
            if (schedule.getUnits().size() > initialSize) {
                System.out.println("✅ Unit added: " + unit.getName());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

private static void updateActivity(Schedule schedule, Scanner scanner) throws Exception {
    System.out.print("Enter unit name: ");
    String unitName = scanner.nextLine().trim();
    Unit unit = findUnit(schedule, unitName);

    if (unit == null) {
        System.out.println("Unit not found!");
        return;
    }

    boolean done = false;
    while (!done) {
        System.out.println("\n=== Update/Add Activities for " + unit.getName() + " ===");
        System.out.println("1. Add missing activities");
        System.out.println("2. Update existing activity");
        System.out.println("3. Go back");
        System.out.print("Choose an option: ");
        int option = safeIntInput(scanner);

        switch (option) {
            case 1:
                // Add missing activities
                if (!unit.hasActivityType("Lecture") ) {
                    addMissingActivity(schedule, scanner, unit, "Lecture", 2, 3);
                }
                if (!unit.hasActivityType("Practical") ) {
                    addMissingActivity(schedule, scanner, unit, "Practical", 1, 3);
                }
                if (!unit.hasActivityType("Self Study") && unit.getSelfStudyHours() > 0) {
                    addMissingSelfStudy(schedule, scanner, unit);
                }
                break;

            case 2:
                // Update existing activity
                if (unit.getActivities().isEmpty()) {
                    System.out.println("No activities to update.");
                    break;
                }
                System.out.println("\nCurrent activities:");
                for (int i = 0; i < unit.getActivities().size(); i++) {
                    System.out.println((i + 1) + ". " + unit.getActivities().get(i));
                }
                System.out.print("Enter number to update (or 0 to cancel): ");
                int idx = safeIntInput(scanner) - 1;
                if (idx == -1) break;
                if (idx < 0 || idx >= unit.getActivities().size()) {
                    System.out.println("Invalid choice.");
                    break;
                }
                updateExistingActivity(schedule, scanner, unit, idx);
                break;

            case 3:
                done = true;
                break;

            default:
                System.out.println("Invalid option!");
        }
    }
    schedule.saveToFile(FILE_NAME);
}

private static void addMissingActivity(Schedule schedule, Scanner scanner, Unit unit,
                                       String type, int minHours, int maxHours) throws Exception {
    int hours = -1;
    do {
        System.out.print("Enter " + type + " hours (" + minHours + "-" + maxHours + "): ");
        hours = safeIntInput(scanner);
    } while (hours < minHours || hours > maxHours);

    // Check weekly limit before scheduling
    if (exceedsWeeklyLimit(unit, hours, null)) {
        System.out.println("⛔ Cannot schedule " + type + " for " + hours +
                           " hours — weekly 10-hour limit exceeded!");
        return;
    }

    boolean scheduled = false;
    while (!scheduled) {
        System.out.print("Enter day for " + type + ": ");
        String day = capitalize(scanner.nextLine().trim());
        if (day.isEmpty()) continue;

        List<int[]> freeSlots = schedule.getFreeSlotsForDay(day);
        if (freeSlots.isEmpty()) {
            System.out.println("No free slots available on " + day + ".");
            System.out.print("Do you want to update existing activities? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                updateActivity(schedule, scanner);
            } else {
                break;
            }
        } else {
            System.out.println("Available slots:");
            for (int i = 0; i < freeSlots.size(); i++) {
                int[] slot = freeSlots.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, DateTime.formatTime(slot[0]), DateTime.formatTime(slot[1]));
            }
            System.out.print("Choose a slot: ");
            int slotChoice = safeIntInput(scanner) - 1;
            if (slotChoice < 0 || slotChoice >= freeSlots.size()) {
                System.out.println("Invalid choice, try again.");
                continue;
            }

            int start = freeSlots.get(slotChoice)[0];
            int end = start + hours;
            DateTime dt = new DateTime(day, start, end);
            ScheduleEntry entry = new ScheduleEntry(unit.getName(), type, dt);

            if (!schedule.hasConflict(entry)) {
                unit.addActivity(entry);
                System.out.println("✅ " + type + " scheduled: " + dt);
                scheduled = true;
            } else {
                System.out.println("⛔ Conflict detected! Try another slot or update existing activity.");
                System.out.print("Update existing activity? (y/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    updateActivity(schedule, scanner);
                }
            }
        }
    }

    schedule.saveToFile(FILE_NAME);
}

private static void addMissingSelfStudy(Schedule schedule, Scanner scanner, Unit unit) throws Exception {
    System.out.print("Add missing Self-Study? (y/n): ");
    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;

    System.out.print("Enter preferred days (comma-separated, e.g., Monday,Wednesday): ");
    String[] days = scanner.nextLine().split(",");
    distributeSelfStudy(schedule, unit, days, unit.getSelfStudyHours());
}

private static void updateExistingActivity(Schedule schedule, Scanner scanner, Unit unit, int index) throws Exception {
    ScheduleEntry old = unit.getActivities().get(index);
    System.out.println("Updating: " + old);

    System.out.print("Enter new day (or leave empty to keep " + old.getDateTime().getDay() + "): ");
    String day = scanner.nextLine().trim();
    if (day.isEmpty()) day = old.getDateTime().getDay();

    System.out.print("Enter new start time (HH:MM AM/PM) or leave empty: ");
    String start = scanner.nextLine().trim();
    int startHour = start.isEmpty() ? old.getDateTime().getStartHour() : parseHour(start);

    int duration = old.getDateTime().getEndHour() - old.getDateTime().getStartHour();
    if (old.getActivityType().equalsIgnoreCase("Lecture")) {
        System.out.print("Enter lecture hours (2 or 3) or leave empty: ");
        int h = safeIntInput(scanner);
        if (h >= 2 && h <= 3) duration = h;
    } else if (old.getActivityType().equalsIgnoreCase("Practical")) {
        System.out.print("Enter practical hours (1-3) or leave empty: ");
        int h = safeIntInput(scanner);
        if (h >= 1 && h <= 3) duration = h;
    }

    // Check weekly limit before updating
    if (exceedsWeeklyLimit(unit, duration, old)) {
        System.out.println("⛔ Cannot update — weekly 10-hour limit exceeded!");
        return;
    }

    int endHour = startHour + duration;

    unit.getActivities().remove(index); // temporarily remove old activity
    ScheduleEntry updated = new ScheduleEntry(unit.getName(), old.getActivityType(),
            new DateTime(day, startHour, endHour));

    if (!schedule.hasConflict(updated)) {
        unit.getActivities().add(index, updated);
        System.out.println("✅ Activity updated successfully!");
    } else {
        unit.getActivities().add(index, old); // restore old
        System.out.println("⛔ Conflict detected! Update cancelled.");
    }

    schedule.saveToFile(FILE_NAME);
}

private static boolean exceedsWeeklyLimit(Unit unit, int additionalHours, ScheduleEntry excludeEntry) {
    int totalHours = 0;
    for (ScheduleEntry entry : unit.getActivities()) {
        if (entry != excludeEntry) {
            totalHours += entry.getDateTime().getEndHour() - entry.getDateTime().getStartHour();
        }
    }
    return (totalHours + additionalHours) > 10;
}

    /**schedules activities */
    private static void scheduleActivities(Schedule schedule, Scanner scanner) throws Exception {
        System.out.print("Enter unit name: ");
        String unitName = scanner.nextLine();
        Unit unit = findUnit(schedule, unitName);
        if (unit == null) {
            System.out.println("Unit not found!");
            return;
        }
        scheduleActivities(schedule, scanner, unit);
    }

   /** schedule a single activity */
private static void scheduleActivity(Schedule schedule, Scanner scanner, Unit unit, String activityType, int hours) throws Exception {
    boolean scheduled = false;
    while (!scheduled) {
        System.out.print("Enter day for " + activityType + ": ");
        String day = capitalize(scanner.nextLine().trim());
        if (day.isEmpty()) continue;

        List<int[]> freeSlots = schedule.getFreeSlotsForDay(day);
        if (freeSlots.isEmpty()) {
            System.out.println("No free slots available on " + day + ".");
            System.out.print("Try another day? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                break;
            }
            continue;
        }

        // Generate sub-slots of required duration
        List<int[]> subSlots = new ArrayList<>();
        for (int[] slot : freeSlots) {
            for (int start = slot[0]; start + hours <= slot[1]; start++) {
                int end = start + hours;
                subSlots.add(new int[]{start, end});
            }
        }

        if (subSlots.isEmpty()) {
            System.out.println("No slots of " + hours + " hours available on " + day + ".");
            System.out.print("Try another day? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                break;
            }
            continue;
        }

        // Show sub-slots to user
        System.out.println("Available slots:");
        for (int i = 0; i < subSlots.size(); i++) {
            int[] slot = subSlots.get(i);
            System.out.printf("%d. %s - %s%n", i + 1, DateTime.formatTime(slot[0]), DateTime.formatTime(slot[1]));
        }
        System.out.print("Choose a slot: ");
        int slotChoice = safeIntInput(scanner) - 1;
        if (slotChoice < 0 || slotChoice >= subSlots.size()) {
            System.out.println("Invalid choice, try again.");
            continue;
        }

        int start = subSlots.get(slotChoice)[0];
        int end = start + hours;
        DateTime dt = new DateTime(day, start, end);
        ScheduleEntry entry = new ScheduleEntry(unit.getName(), activityType, dt);

        if (!schedule.hasConflict(entry)) {
            unit.addActivity(entry);
            System.out.println("✅ " + activityType + " scheduled: " + dt);
            scheduled = true;
        } else {
            System.out.println("⛔ Conflict detected! Try another slot.");
            System.out.print("Try another slot? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                break;
            }
        }
    }
}

/** schedules activities for a given unit */
private static void scheduleActivities(Schedule schedule, Scanner scanner, Unit unit) throws Exception {
    boolean done = false;
    while (!done) {
        System.out.println("\n=== Schedule Activities for " + unit.getName() + " ===");
        System.out.println("Current status:");
        System.out.println("- Lecture: " + (unit.hasActivityType("Lecture") ? "Scheduled" : "Not scheduled") + " (" + unit.getLectureHours() + " hours required)");
        System.out.println("- Practical: " + (unit.hasActivityType("Practical") ? "Scheduled" : "Not scheduled") + " (" + unit.getPracticalHours() + " hours required)");
        System.out.println("- Self Study: " + (unit.hasActivityType("Self Study") ? "Scheduled" : "Not scheduled") + " (" + unit.getSelfStudyHours() + " hours required)");
        
        System.out.println("\nOptions:");
        System.out.println("1. Schedule Lecture");
        System.out.println("2. Schedule Practical");
        System.out.println("3. Schedule Self Study");
        System.out.println("4. Done (go back)");
        System.out.print("Choose an option: ");
        int choice = safeIntInput(scanner);

        switch (choice) {
            case 1:
                if (unit.getLectureHours() > 0) {
                    if (unit.hasActivityType("Lecture")) {
                        System.out.print("Lecture is already scheduled. Reschedule? (y/n): ");
                        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) break;
                        unit.removeActivitiesByType("Lecture");
                    }
                    System.out.println("\nScheduling Lecture (" + unit.getLectureHours() + " hours)");
                    scheduleActivity(schedule, scanner, unit, "Lecture", unit.getLectureHours());
                } else {
                    System.out.println("No lecture hours required for this unit.");
                }
                break;
            case 2:
                if (unit.getPracticalHours() > 0) {
                    if (unit.hasActivityType("Practical")) {
                        System.out.print("Practical is already scheduled. Reschedule? (y/n): ");
                        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) break;
                        unit.removeActivitiesByType("Practical");
                    }
                    System.out.println("\nScheduling Practical (" + unit.getPracticalHours() + " hours)");
                    scheduleActivity(schedule, scanner, unit, "Practical", unit.getPracticalHours());
                } else {
                    System.out.println("No practical hours required for this unit.");
                }
                break;
            case 3:
                if (unit.getSelfStudyHours() > 0) {
                    if (unit.hasActivityType("Self Study")) {
                        System.out.print("Self Study is already scheduled. Reschedule? (y/n): ");
                        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) break;
                        unit.removeActivitiesByType("Self Study");
                    }
                    System.out.println("\nScheduling Self-Study (" + unit.getSelfStudyHours() + " hours)");
                    System.out.print("Enter preferred days (comma-separated, e.g., Monday,Wednesday): ");
                    String[] preferredDays = scanner.nextLine().split(",");
                    distributeSelfStudy(schedule, unit, preferredDays, unit.getSelfStudyHours());
                } else {
                    System.out.println("No self-study hours required for this unit.");
                }
                break;
            case 4:
                done = true;
                break;
            default:
                System.out.println("Invalid option! Try again.");
        }
    }
    schedule.saveToFile(FILE_NAME); // Save after changes
}
    /** distributes self study hours across preferred days */
private static void distributeSelfStudy(Schedule schedule, Unit unit, String[] preferredDays, int totalHours) throws Exception {
    int hoursPerDay = totalHours / preferredDays.length;
    int remainingHours = totalHours % preferredDays.length;
    Scanner scanner = new Scanner(System.in);

for (String dayRaw : preferredDays) {
    String day = capitalize(dayRaw.trim());
    if (day.isEmpty()) continue;

    int currentHours = hoursPerDay + (remainingHours-- > 0 ? 1 : 0);

    System.out.println("\nChecking free slots for " + day + "...");
    List<int[]> freeSlots = schedule.getFreeSlotsForDay(day);

    if (freeSlots.isEmpty()) {
        System.out.println("No free slots on " + day);
        continue;
    }

   // Generate sub-slots of required duration
List<int[]> subSlots = new ArrayList<>();
for (int[] slot : freeSlots) {
    for (int start = slot[0]; start + currentHours <= slot[1]; start++) {
        int end = start + currentHours;
        subSlots.add(new int[]{start, end});
    }
}

// Show sub-slots to user
for (int i = 0; i < subSlots.size(); i++) {
    int[] s = subSlots.get(i);
    System.out.printf("%d. %s - %s\n", i + 1,
            DateTime.formatTime(s[0]),
            DateTime.formatTime(s[1]));
}

// Ask user to pick one
System.out.print("Choose a slot for " + currentHours + " self-study hour(s): ");
String line = scanner.nextLine().trim();
if (line.isEmpty()) {
    System.out.println("No choice entered, skipping " + day);
    continue;
}

int choice;
try {
    choice = Integer.parseInt(line) - 1;
} catch (NumberFormatException e) {
    System.out.println("Invalid number, skipping " + day);
    continue;
}

if (choice < 0 || choice >= subSlots.size()) {
    System.out.println("Invalid choice. Skipping " + day);
    continue;
}

int[] chosenSlot = subSlots.get(choice);
DateTime dateTime = new DateTime(day, chosenSlot[0], chosenSlot[1]);
ScheduleEntry entry = new ScheduleEntry(unit.getName(), "Self Study", dateTime);
if (!schedule.hasConflict(entry)) {
    unit.addActivity(entry);
    System.out.println("✅ Added: " + dateTime);
} else {
    System.out.println("⛔ Conflict detected for that slot!");
}

 
}
}


 
    public static int parseHour(String timeStr) {
        String[] parts = timeStr.trim().split(" ");
        int hour = Integer.parseInt(parts[0].split(":")[0]);
        if (parts.length > 1 && parts[1].equalsIgnoreCase("PM") && hour != 12) {
            hour += 12;
        } else if (parts.length > 1 && parts[1].equalsIgnoreCase("AM") && hour == 12) {
            hour = 0;
        }
        return hour;
    }

    public static Unit findUnit(Schedule schedule, String name) {
        for (Unit unit : schedule.getUnits()) {
            if (unit.getName().equalsIgnoreCase(name.trim())) {
                return unit;
            }
        }
        return null;
    }

    private static void viewSchedule(Schedule schedule) {
        System.out.println("\n==== Current Schedule ====");
        for (Unit unit : schedule.getUnits()) {
            System.out.println("📘 " + unit.getName());
            for (ScheduleEntry entry : unit.getActivities()) {
                System.out.println("  - " + entry.getActivityType() + ": " + entry.getDateTime() + "\n");
            }
        }
    }
 
private static void deleteActivity(Schedule schedule, Scanner scanner) throws Exception {
    System.out.print("Enter unit name: ");
    String name = scanner.nextLine().trim();
    Unit unit = findUnit(schedule, name);

    if (unit == null) {
        System.out.println("Unit not found!");
        return;
    }

    if (unit.getActivities().isEmpty()) {
        System.out.println("This unit has no scheduled activities.");
        return;
    }

    System.out.println("\nActivities for " + unit.getName() + ":");
    for (int i = 0; i < unit.getActivities().size(); i++) {
        ScheduleEntry entry = unit.getActivities().get(i);
        System.out.printf("%d. %s - %s%n", i + 1, entry.getActivityType(), entry.getDateTime());
    }

    System.out.print("\nEnter the number of the activity to delete: ");
    int index = safeIntInput(scanner) - 1;

    if (index < 0 || index >= unit.getActivities().size()) {
        System.out.println("Invalid choice!");
        return;
    }

    ScheduleEntry removed = unit.getActivities().remove(index);
    System.out.println("✅ Removed: " + removed.getActivityType() + " - " + removed.getDateTime());

     schedule.saveToFile(FILE_NAME);
    System.out.println("Schedule updated and saved.");
}

private static void deleteUnit(Schedule schedule, Scanner scanner) throws Exception {
    System.out.print("Enter unit name to delete: ");
    String name = scanner.nextLine().trim();
    Unit unit = findUnit(schedule, name);

    if (unit == null) {
        System.out.println("Unit not found!");
        return;
    }

    System.out.print("Are you sure you want to delete '" + unit.getName() + "'? (y/n): ");
    String confirm = scanner.nextLine().trim();
    if (!confirm.equalsIgnoreCase("y")) {
        System.out.println("Deletion cancelled.");
        return;
    }

     schedule.getUnits().remove(unit);
    schedule.saveToFile(FILE_NAME);
    System.out.println("Unit '" + unit.getName() + "' deleted and schedule saved.");
}

    private static int safeIntInput(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    public static String capitalize(String s) {
        if (s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
