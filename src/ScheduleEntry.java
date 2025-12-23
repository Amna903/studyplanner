public class ScheduleEntry {
    public String unitName;
    public String activityType; // "Lecture", "Practical", or "Self-Study"
    public DayTime startTime;
    public DayTime endTime;

    /**
     * Constructor for ScheduleEntry class
     * @param unitName
     * @param activityType
     * @param startTime
     * @param endTime
     */
    public ScheduleEntry(String unitName, String activityType, DayTime startTime, DayTime endTime) {
        this.unitName = unitName;
        this.activityType = activityType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Converts the ScheduleEntry object to a CSV-formatted string
    @Override
    public String toString() {
        return unitName + "," + activityType + "," + startTime.dayOfWeek + "," +
               startTime.toString() + "," + endTime.toString();
    }
}
