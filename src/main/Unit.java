package main;
import java.util.ArrayList;

/** represents a unit with lectures, practicals and self-study hours
 */
public class Unit {
    private String name;
    private int lectureHours;  
    private int practicalHours;  
    private int selfStudyHours; //10 - (lecture + practical)
    private ArrayList<ScheduleEntry> activities;

    /**constructor   */
    public Unit(String name, int lectureHours, int practicalHours) {
        validateHours(lectureHours, practicalHours);
        this.name = name;
        this.lectureHours = lectureHours;
        this.practicalHours = practicalHours;
        this.selfStudyHours = 10 - (lectureHours + practicalHours);
        this.activities = new ArrayList<>();
    }

  
    private void validateHours(int lectureHours, int practicalHours) {
        if (lectureHours != 2 && lectureHours != 3) {
            throw new IllegalArgumentException("Lecture hours must be 2 or 3, got: " + lectureHours);
        }
        if (practicalHours < 1 || practicalHours > 3) {
            throw new IllegalArgumentException("Practical hours must be 1, 2, or 3, got: " + practicalHours);
        }
    }

    // getters
    public String getName() {
        return name;
    }

    public int getLectureHours() {
        return lectureHours;
    }

    public int getPracticalHours() {
        return practicalHours;
    }

    public int getSelfStudyHours() {
        return selfStudyHours;
    }

    public ArrayList<ScheduleEntry> getActivities() {
        return activities;
    }

   
    public void addActivity(ScheduleEntry entry) {
        activities.add(entry);
    }
public void setHours(int lectureHours, int practicalHours) {
    this.lectureHours = lectureHours;
    this.practicalHours = practicalHours;
    this.selfStudyHours = 10 - (lectureHours + practicalHours);
}
public boolean hasActivityType(String type) {
    for (ScheduleEntry entry : activities) {
        if (entry.getActivityType().equalsIgnoreCase(type)) return true;
    }
    return false;
}
 
public void removeActivitiesByType(String type) {
    activities.removeIf(entry -> entry.getActivityType().equalsIgnoreCase(type));
}
    @Override
    public String toString() {
        return name + " (Lecture: " + lectureHours + "h, Practical: " + practicalHours + "h, Self-Study: " + selfStudyHours + "h)";
    }
}