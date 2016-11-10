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
