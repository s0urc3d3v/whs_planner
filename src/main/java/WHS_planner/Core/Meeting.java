package WHS_planner.Core;

import WHS_planner.Util.Course;

import java.io.File;

/**
 * Created by matthewelbing on 07.10.16.
 */
public class Meeting {
    private String requestingStudent;
    private String studentRequested;
    private long hour;
    private long minute;
    private Course course;
    private IO io;

    public Meeting(String requestingStudent, String studentRequested, long hour, long minute, Course course) {
        String filename = "src" + File.pathSeparator + "main" + File.pathSeparator + "resources" + File.pathSeparator + "Core" + File.pathSeparator + "meeting.json";
        io = new IO(filename);
        this.requestingStudent = requestingStudent;
        this.studentRequested = studentRequested;
        this.hour = hour;
        this.minute = minute;
        this.course = course;
    }
    public void create(){
        io.writeMeetingJsonData(requestingStudent, studentRequested, hour, minute, course);
    }
}
