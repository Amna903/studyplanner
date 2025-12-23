/**
represents a single scheduled activity
 */package main;

public class ScheduleEntry {
    private String unitName;
    private String activityType; 
    private DateTime dateTime;

    /**constructor  */
    public ScheduleEntry(String unitName, String activityType, DateTime dateTime) {
        validateActivityType(activityType);
        this.unitName = unitName;
        this.activityType = activityType.replace("-", " ");  
        this.dateTime = dateTime;
    }

  
    private void validateActivityType(String type) {
        String normalized = type.replace("-", " ");
        if (!normalized.equals("Lecture") && !normalized.equals("Practical") && !normalized.equals("Self Study")) {
            throw new IllegalArgumentException("Invalid activity type: " + type);
        }
    }

    // getters
    public String getUnitName() {
        return unitName;
    }

    public String getActivityType() {
        return activityType;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    /** CSV format for output */
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s",
                unitName, activityType, dateTime.getDay(),
                dateTime.formatTime(dateTime.getStartHour()),
                dateTime.formatTime(dateTime.getEndHour()));
    }
    @Override
public String toString() {
    return String.format("%s - %s: %s %s - %s",
            unitName,
            activityType,
            dateTime.getDay(),
            dateTime.formatTime(dateTime.getStartHour()),
            dateTime.formatTime(dateTime.getEndHour()));
}

}