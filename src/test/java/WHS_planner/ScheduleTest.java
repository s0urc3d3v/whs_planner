package WHS_planner;

import WHS_planner.Schedule.GrabDay;
import junit.framework.TestCase;

import java.io.File;

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

    public void testGettfiles()
    {
        File f = new File("Keys/ipass.key");


        System.out.println(f.getName());
    }


}
