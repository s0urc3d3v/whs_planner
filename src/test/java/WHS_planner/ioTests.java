package WHS_planner;

import WHS_planner.Core.ErrorHandler;
import WHS_planner.Core.IO;
import WHS_planner.Util.Course;
import WHS_planner.Util.Student;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

/**
 * Created by spam on 11/11/2016.
 */
public class ioTests extends TestCase {
    public void testReadMeetingDataJsonObject(){
        IO io = new IO("testDatabase" + File.separator + "test.json");

        //Student requesting meeting
        Student requestingStudent = new Student("John", "Smith", "john_smith@test.com", 11, "Mrs Johnson");

        //Student requested for meeting
        Student requestedStudent = new Student("Bob", "Robert", "john_robert@test.com", 11, "Mrs, Johnson");

        Course course = new Course("English", 5, "Mrs. Johnson", Course.level.COLLEGE);
        try {
            io.writeJsonMeetingData(requestingStudent, requestedStudent, 8,9,10,11,12, course);
        } catch (IOException e) {
            ErrorHandler.HandleIOError(e);
        }
    }
    public void testWriteJsonDataMeeting(){

    }
}
