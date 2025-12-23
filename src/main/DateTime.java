 package main;

public class DateTime {
    private String day;  
    private int startHour;  
    private int endHour; 
    public DateTime(String day, int startHour, int endHour) {
        validateDay(day);
        validateTime(startHour, endHour);
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

 
    private void validateDay(String day) {
        String[] validDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String validDay : validDays) {
            if (validDay.equalsIgnoreCase(day)) {
                this.day = validDay;
                return;
            }
        }
        throw new IllegalArgumentException("Invalid day: " + day);
    }

 
    private void validateTime(int startHour, int endHour) {
        if (startHour < 0 || startHour > 23 || endHour < 1 || endHour > 24 || startHour >= endHour) {
            throw new IllegalArgumentException("Invalid time range: " + startHour + " to " + endHour);
        }
    }

    // getters
    public String getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

   public static String formatTime(int hour) {
        String period = hour < 12 ? "AM" : "PM";
        int displayHour = hour == 0 ? 12 : (hour > 12 ? hour - 12 : hour);
        return String.format("%02d:00 %s", displayHour, period);
    }

    @Override
    public String toString() {
        return day + " " + formatTime(startHour) + "-" + formatTime(endHour);
    }
}