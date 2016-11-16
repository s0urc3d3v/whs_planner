package WHS_planner.Meeting;

import WHS_planner.Core.GmailApiAccess;
import WHS_planner.Core.Meeting;
import WHS_planner.Meeting.ReadMeetingDataTest;
import WHS_planner.Util.Course;
import WHS_planner.Util.Student;
import junit.framework.TestCase;

import java.io.File;


 /* Created by spam on 08.10.2016*/

public class MeetingCreateTests extends TestCase { //THIS WORKS!
    public void testMeetingCreation(){
        Course english = new Course("English", 4, "Teacher", Course.level.COLLEGE);
        Student requestingStudent = new Student("John", "Smith", "test@test.com", 12, "teacher1");
        Student studentRequested = new Student("Smith", "John", "matthewelbing@gmail.com", 12, "teacher2");
        String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "meeting.json.whsplannermeeting";
        Meeting m = new Meeting(requestingStudent, studentRequested, 11, 13, 10, 12, 16, english, path);
        m.create();

        /*try {
            GmailApiAccess.sendEmail(studentRequested.getEmail(), requestingStudent.getEmail(), "WHS_planner meeting", "test");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
