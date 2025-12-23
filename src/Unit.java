public class Unit {
    public String unitName;
    public int lectureHours;
    public int practicalHours;
    public int selfStudyHours;

    /**
     * Constructor for Unit class
     * @param unitName
     * @param lectureHours
     * @param practicalHours
     */
    public Unit(String unitName, int lectureHours, int practicalHours) {
        this.unitName = unitName;
        this.lectureHours = lectureHours;
        this.practicalHours = practicalHours;
        this.selfStudyHours = 10 - (lectureHours + practicalHours);
    }
}
