package WHS_planner.Core;

import WHS_planner.Util.Course;
import WHS_planner.Util.Student;

import java.io.IOException;

/**
 * Created by matthewelbing on 07.10.16.
 */
public class Meeting {
    private Student requestingStudent;
    private Student studentRequested;
    private long month;
    private long day;
    private long year;

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    private long hour;
    private long minute;

    public Student getRequestingStudent() {
        return requestingStudent;
    }

    public void setRequestingStudent(Student requestingStudent) {
        this.requestingStudent = requestingStudent;
    }

    public Student getStudentRequested() {
        return studentRequested;
    }

    public void setStudentRequested(Student studentRequested) {
        this.studentRequested = studentRequested;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public IO getIo() {
        return io;
    }

    public void setIo(IO io) {
        this.io = io;
    }

    private Course course;
    private IO io;

    public Meeting(Student requestingStudent, Student studentRequested, long month, long day, long year, long hour, long minute, Course course, String path) {
        String filename = path;
        io = new IO(filename);
        this.requestingStudent = requestingStudent;
        this.studentRequested = studentRequested;
        this.month = month;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.course = course;

    }

    @Override
    public String toString() {
        return "Meeting{" +
                "requestingStudent=" + requestingStudent +
                ", studentRequested=" + studentRequested +
                ", month=" + month +
                ", day=" + day +
                ", year=" + year +
                ", hour=" + hour +
                ", minute=" + minute +
                ", course=" + course +
                ", io=" + io +
                '}';
    }

    public void create(){
        try {
            io.writeJsonMeetingData(requestingStudent, studentRequested, month, day, year, hour, minute, course);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
