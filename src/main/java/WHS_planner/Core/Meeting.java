package WHS_planner.Core;

import WHS_planner.Util.Course;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by matthewelbing on 07.10.16.
 */
public class Meeting {
    private Student requestingStudent;
    private Student studentRequested;
    private int month;
    private int day;
    private long year;
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

    public Meeting(Student requestingStudent, Student studentRequested, int month, int day, long year, long hour, long minute, Course course) {
        String filename = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "meeting.json.whsplannermeeting";
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
    public void create(){
        try {
            io.writeMeeting(requestingStudent, studentRequested, month, day, year, hour, minute, course);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
