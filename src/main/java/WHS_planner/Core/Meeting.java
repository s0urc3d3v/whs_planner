package WHS_planner.Core;

import WHS_planner.Util.Course;

import java.io.File;
import java.io.IOException;

/**
 * Created by matthewelbing on 07.10.16.
 */
public class Meeting {
    private Student requestingStudent;
    private Student studentRequested;
    private long hour;
    private long minute;
    private Course course;
    private IO io;

    public Meeting(Student requestingStudent, Student studentRequested, long hour, long minute, Course course) {
        String filename = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "meeting.json.whsplannermeeting";
        io = new IO(filename);
        this.requestingStudent = requestingStudent;
        this.studentRequested = studentRequested;
        this.hour = hour;
        this.minute = minute;
        this.course = course;
    }
    public void create(){
        try {
            io.writeMeeting(requestingStudent, studentRequested, hour, minute, course);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
