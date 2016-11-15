/*package WHS_planner;

import WHS_planner.Core.Meeting;
import WHS_planner.Core.Student;
import WHS_planner.Util.Course;
import junit.framework.TestCase;

/**
 * Created by spam on 08.10.2016.

public class MeetingTest extends TestCase {
    public void testMeetingCreation(){
        Course english = new Course("English", 4, "Teacher", Course.level.COLLEGE);
        Student requestingStudent = new Student("John", "Smith", "test@test.com", 12, "teacher1");
        Student studentRequested = new Student("Smith", "John", "matthewelbing@gmail.com", 12, "teacher2");
        Meeting m = new Meeting(requestingStudent, studentRequested, 11, 13, 10, 12, 16, english);
        m.create();
        ReadMeetingDataTest readMeetingDataTest = new ReadMeetingDataTest();
        readMeetingDataTest.testReadMeetingData();
        try {
            GmailApiAccess.sendEmail(studentRequested.getEmail(), requestingStudent.getEmail(), "WHS_planner meeting", "test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/