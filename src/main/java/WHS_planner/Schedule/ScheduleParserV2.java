package WHS_planner.Schedule;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * I don't want to touch schedule parser
 *
 * it was written so long ago and relies on jsoup
 *
 * Just gonna make a new object
 *
 * */

public class ScheduleParserV2
{
    private ScheduleBlock[] blocks;

    public ScheduleParserV2()
    {
        blocks = new ScheduleBlock[56];
    }

    public void getClasses() throws IOException
    {
        File html = new File("output.html");
        BufferedReader br = new BufferedReader(new FileReader(html));

        String line;

        while((line = br.readLine()) != null)
        {
            if(!line.startsWith("<") && !line.startsWith(" "))
            {
                System.out.println(line);
            }
        }

    }

    public void grabwebpage(String u, String p)
    {
        try
        {
            GrabDay gd = new GrabDay(u, p);
            gd.grabSchedule("output.html");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
