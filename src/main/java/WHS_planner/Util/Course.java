package WHS_planner.Util;

/**
 * Created by matthewelbing on 07.10.16.
 */
public class Course {
    private String name;
    private int period;
    private String teacher;
    public enum level{
        INTRO, COLLEGE, HONORS, AP
    }
    private level courseLevel;
    public Course(String name, int period, String teacher, level courseLevel) {
        this.name = name;
        this.period = period;
        this.teacher = teacher;
        this.courseLevel = courseLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", period=" + period +
                ", teacher='" + teacher + '\'' +
                ", courseLevel=" + courseLevel +
                '}';
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public level getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(level courseLevel) {
        this.courseLevel = courseLevel;
    }

}
