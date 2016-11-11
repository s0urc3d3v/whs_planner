package WHS_planner.Util;

/**
 * Created by spam on 08.10.2016.
 */
public class Student {
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    private String firstName;
    private String lastName;
    private String email;
    private int grade;
    private String teacher;

    public Student(String firstName, String lastName, String email, int grade, String teacher) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", grade=" + grade +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getGrade() {
        return grade;
    }

    public String getFullName(){
        return getFirstName() + "_" + getLastName();
    }
}
