package WHS_planner;

import WHS_planner.Schedule.GrabDay;
import WHS_planner.Schedule.ParseCalendar;
import junit.framework.TestCase;

import java.io.*;
import java.util.Calendar;

/**
 * Created by John on 10/24/2016.
 */
public class grabTest extends TestCase
{
    public void testGrabCalendar()
    {
        try
        {
            File f = new File("Keys/ipass.key");

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String user = br.readLine();
            String pass = br.readLine();

            br.close();
            fr.close();

            System.out.println("User: "+user+" : Password: "+pass);

            GrabDay gd = new GrabDay(user, pass);
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
            File f = new File("Keys/ipass.key");

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String user = br.readLine();
            String pass = br.readLine();

            br.close();
            fr.close();

            GrabDay gd = new GrabDay(user, pass);

            gd.grabSchedule("output.html");
        }
        catch(Exception e)
        {

        }

    }

    public void testWrite()
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

    public void testRead()
    {
        try
        {
            ParseCalendar pc = new ParseCalendar();
            pc.readData();

            String s = (Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            s = pc.getDay(s);
            System.out.println(s);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
