package WHS_planner;

import WHS_planner.Core.ReadSchedule;
import junit.framework.TestCase;

/**
 * Created by matthewelbing on 24.09.16.
 */
public class ReadScheduleTest extends TestCase{
    public void testAuthAndFindTableWithIpass(){
        try {
            ReadSchedule r = new ReadSchedule();
            r.authAndFindTableWithIpass("matthew_elbing", "wps501181"); //replace user and pass with credentials
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
