package WHS_planner;

import WHS_planner.Schedule.ParseCalendar;
import WHS_planner.Schedule.ScheduleParser;
import WHS_planner.Schedule.grabDay;
import junit.framework.TestCase;

import java.io.*;

/**
 * Created by John on 10/24/2016.
 */
public class grabTest extends TestCase
{
    public void testGrabCalendar()
    {
        try
        {
            File f = new File("user.key");

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String user = br.readLine();
            String pass = br.readLine();

            br.close();
            fr.close();

            System.out.println("User: "+user+" : Password: "+pass);

            grabDay gd = new grabDay(user, pass);
            gd.grabData();

            ParseCalendar pc = new ParseCalendar();

            pc.setData();

            System.out.println(pc.getDay("10/25"));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void testGrabSchedule()
    {
        try
        {
            File f = new File("user.key");

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String user = br.readLine();
            String pass = br.readLine();

            br.close();
            fr.close();

            grabDay gd = new grabDay(user, pass);

            gd.grabSchedule();
        }
        catch(Exception e)
        {

        }

    }

    public void testWriteMap()
    {
        ParseCalendar pc = new ParseCalendar();
        try
        {
            pc.setData();
            pc.writeData();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
