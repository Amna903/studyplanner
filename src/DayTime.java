import java.util.*;

public class DayTime {
    public int hour;
    public int minute;
    public String dayOfWeek;

    // List of valid days of the week
    public static final String[] VALID_DAYS = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    public static final ArrayList<String> VALID_DAYS_LIST = new ArrayList<>(Arrays.asList(VALID_DAYS));

    /**
     * Constructor for DayTime class
     * @param hour
     * @param minute
     * @param dayOfWeek
     */
    public DayTime(int hour, int minute, String dayOfWeek) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            this.dayOfWeek = "Monday";
        }
        else if (!isValidDay(dayOfWeek)) {
            this.dayOfWeek = "Monday";
        }
        else {
           this.hour = hour;
            this.minute = minute;
            this.dayOfWeek = dayOfWeek;
        }
    }

    // Converts the DayTime object to a string in the format Day HH:MM
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Adds hours to the current time and returns a new DayTime object
     * @param hours
     * @return DayTime object with the new time
     */
    public DayTime addHours(int hours) {
        int newHour = this.hour + hours;
        String newDay = this.dayOfWeek;

        /**
         * If the new hour exceeds 23, we need to wrap around to the next day
         * and adjust the hour accordingly
         */
        if (newHour >= 24) {
            newHour -= 24; // wrap around if hour exceeds 23
            newDay = getNextDayOfWeek(this.dayOfWeek); // move to the next day
        }

        return new DayTime(newHour, this.minute, newDay);
    }

    /**
     * Parses a string in the format "Day HH:MM" to a DayTime object
     * @param time
     * @return DayTime object parsed from the string
     */
    public static DayTime fromString(String time) {
        String[] parts = time.split(" ");
        String day = parts[0];
        String[] timeParts = parts[1].split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        return new DayTime(hour, minute, day);
    }

    /**
     * Checks if the given day is valid
     * @param day
     * @return true if the day is valid, false otherwise
     */
    public static boolean isValidDay(String day) {
        return VALID_DAYS_LIST.contains(day);
    }

    /**
     * Returns the next day of the week
     * @param currentDay
     * @return the next day of the week
     */
    public static String getNextDayOfWeek(String currentDay) {
        if(!isValidDay(currentDay)) {
            return null;
        }
        int currentIndex = VALID_DAYS_LIST.indexOf(currentDay);
        int nextIndex = (currentIndex + 1) % VALID_DAYS_LIST.size();
        return VALID_DAYS_LIST.get(nextIndex);
    }
}
