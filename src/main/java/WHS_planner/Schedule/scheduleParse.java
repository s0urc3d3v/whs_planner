package WHS_planner.Schedule;

import WHS_planner.Core.IO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;

public class scheduleParse
{


    public static void main(String[] args)
    {
        try
        {
            getClasses();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void getClasses() throws IOException
    {

        ScheduleBlock[] schedule;
        int count = 2;
        Element schedElement = null;
        Element rawClass;
        Elements classesRaw = new Elements();

        File input = new File("raw.html");
        Document doc = Jsoup.parse(input,"UTF-8","");
        Elements tables  = doc.select("table");

        for (int i = 0; i < tables.size(); i++)
        {
            // Finds the Schedule table & assigns it to schedElement
            if(tables.get(i).id().equalsIgnoreCase("Student's Schedule"))
            {
                schedElement = tables.get(i);
            }
        }

        while (!schedElement.select(":has(table)").isEmpty())
        {
            if(count < 59)
            {
                rawClass = schedElement.select("table table").get(count);
                count++;
                classesRaw.add(rawClass);
            }

            else
            {
                break;
            }
        }

        schedule = new ScheduleBlock[56];
        String[] holder = new String[4];

        for (int i = 0; i < 56; i++)
        {
            try
            {
                holder = polishClass(classesRaw.get(i));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            schedule[i] = new ScheduleBlock(holder[0],holder[1],holder[2],holder[3]);
        }

        IO io = new IO("Schedule");
        io.writeScheduleArray(schedule);
        io.unload();
    //change
    }



    private static String[] polishClass(Element el) throws Exception
    {
        // returns "Class:Teacher:Room:Period"
        //Write code for advisory
        String strEl = el.toString();
        String[] polishedStr = new String[4];
        String utilStr = "";
        String[] free = {"free","free","free","free"};


        if(strEl.length() < 200)
        {
            return free;
        }

        //Find td's

        //removes first garbage
        strEl = strEl.substring(0,strEl.indexOf("<td")) + strEl.substring(strEl.indexOf("</td>") + 5);

        //Finds Teacher
        for (int i = 0; i < 4; i++)
        {
            if(i == 3)
            {
                utilStr = strEl.substring(strEl.indexOf("<td") + 1, strEl.indexOf("</td>") + 3);
                System.out.println(utilStr);
                polishedStr[i] = utilStr.substring(utilStr.indexOf(":") + 2, utilStr.indexOf(" </"));
                strEl = strEl.substring(0, strEl.indexOf("<td")) + strEl.substring(strEl.indexOf("</td>") + 5);
            }

            else
            {
                utilStr = strEl.substring(strEl.indexOf("<td") + 1, strEl.indexOf("</td>") + 3);
                System.out.println(utilStr);
                polishedStr[i] = utilStr.substring(utilStr.indexOf("> ")+2, utilStr.indexOf(" </")) + ":";
                strEl = strEl.substring(0, strEl.indexOf("<td")) + strEl.substring(strEl.indexOf("</td>") + 5);
            }
        }

            return polishedStr;
        }
    }
