package WHS_planner.Meeting;

import WHS_planner.Core.IO;
import WHS_planner.Core.Meeting;
import junit.framework.TestCase;

import java.io.File;

/**
 * Created by spam on 08.10.2016.
 */
public class ReadMeetingDataTest extends TestCase {
    public void testReadMeetingData(){
        IO io = new IO("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "meeting.json.whsplannermeeting");
        Meeting m = io.readMeetingJsonData();
        System.out.println(m.toString());
    }
}
