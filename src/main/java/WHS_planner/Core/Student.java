package WHS_planner.Core;

/**
 * Created by spam on 08.10.2016.
 */
public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private int grade;

    public Student(String firstName, String lastName, String email, int grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
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
}
