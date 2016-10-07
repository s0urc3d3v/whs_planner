package WHS_planner.Core;

import WHS_planner.Util.Course;

/**
 * Created by matthewelbing on 07.10.16.
 */
public class createMeeting {
    private String requestingStudent;
    private String studentRequested;
    private long hour;
    private long minute;
    private Course course;
    private IO io;

    public createMeeting(String requestingStudent, String studentRequested, long hour, long minute, Course course) {
        io = new IO("/src/main/resources/Core/meeting.json");
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
