package WHS_planner;

import WHS_planner.Core.ReadSchedule;
import WHS_planner.Schedule.ScheduleParser;
import junit.framework.TestCase;

/**
 * Created by matthewelbing on 24.09.16.
 */
public class ReadScheduleTest extends TestCase{
    public void testAuthAndFindTableWithIpass(){
        try {
            ReadSchedule r = new ReadSchedule();
            r.authAndFindTableWithIpass("user", "pass"); //replace user and pass with credentials
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testParseSchedule() throws Exception {
        ScheduleParser scheduleParser = new ScheduleParser();
        scheduleParser.getClasses();
    }
}
