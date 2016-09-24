package WHS_planner;

import WHS_planner.Core.ReadSchedule;
import junit.framework.TestCase;

/**
 * Created by matthewelbing on 24.09.16.
 */
public class ReadScheduleTest extends TestCase{
    public void testAuthAndFindTableWithIpass(){
        try {
            ReadSchedule.authAndFindTableWithIpass("user", "pass");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
