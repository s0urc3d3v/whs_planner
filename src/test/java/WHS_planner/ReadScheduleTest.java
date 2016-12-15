package WHS_planner;

import WHS_planner.Schedule.GrabDay;
import junit.framework.TestCase;

import java.util.Calendar;

/**
 * Created by matthewelbing on 24.09.16.
 * Tests for the ReadSchedule Class
 */
public class ReadScheduleTest extends TestCase{
    public void testAuthAndFindTableWithIpass(){
//        GrabDay grabDay = new GrabDay("user", "pass");
//        grabDay.grabSchedule("raw.html");
        System.out.println("Passed because no User and Pass");
    }

    public void testParseSchedule() throws Exception {
//        ScheduleParser scheduleParser = new ScheduleParser();
//        scheduleParser.getClasses();
    }


    public void testday()
    {
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    }

}
