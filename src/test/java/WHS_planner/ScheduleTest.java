package WHS_planner;

import WHS_planner.Schedule.GrabDay;
import WHS_planner.Schedule.Schedule;
import junit.framework.TestCase;

public class ScheduleTest extends TestCase
{


    public void testSchedule() throws Exception
    {
        System.out.println("memes");
    }


    public void testGettingLogin()
    {
        try
        {
            String user = "";
            String pass = "";

            GrabDay gd = new GrabDay(user, pass);
            System.out.println(gd.testConn());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
