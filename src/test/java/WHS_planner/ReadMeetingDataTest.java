package WHS_planner;

import WHS_planner.Core.IO;
import junit.framework.TestCase;

import java.io.File;

/**
 * Created by spam on 08.10.2016.
 */
public class ReadMeetingDataTest extends TestCase {
    public void testReadMeetingData(){
        IO io = new IO("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "meeting.json.whsplannermeeting");
        io.readMeetingJsonData();
    }
}
