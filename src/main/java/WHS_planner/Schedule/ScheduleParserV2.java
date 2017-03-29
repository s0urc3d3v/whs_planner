package WHS_planner.Schedule;


import WHS_planner.Core.IO;
import WHS_planner.Main;

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
 * Here be dragons
 *
 * Don't touch
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
        File html = new File(Main.SAVE_FOLDER+ File.separator +"output.html");
        BufferedReader br = new BufferedReader(new FileReader(html));

        String line;

        String name = "";
        String teacher = "";
        String room = "";
        String period = "";

        int iterator = 0;

        int blocknumber = 0;

        boolean start = false;

        boolean newBlock = false;

        boolean weirdname = false;

        while((line = br.readLine()) != null)
        {

            if(!line.startsWith("<") && !line.startsWith(" "))
            {
                if(line.contains("Period"))
                {
                    start = true;
                }
                else if(start)
                {
                    newBlock = false;
                    if(line.contains("<br/>"))
                    {
                        line = line.replace("<br/>", "");
                        line = line.trim();

                        if(iterator == 0)
                        {
                            name = line;
                        }
                        else if(iterator == 1)
                        {
                            teacher = line;
                        }
                        else if(iterator == 2)
                        {
                            room = line;
                        }
                        else
                        {
                            period = line;
                        }

                        iterator--;
                        weirdname = true;
                    }
                    else if(iterator == 0)
                    {
                        line = line.replace("</td>", "");
                        line = line.trim();
                        if(weirdname)
                        {
                            name += " & " + line;
                            weirdname = false;
                        }
                        else
                        {
                            name = line;
                        }
                    }
                    else if(iterator == 1)
                    {
                        line  = line.replace("</td>", "");
                        line = line.trim();

                        if(weirdname)
                        {
                            teacher += " & "+line;
                            weirdname = false;
                        }
                        else
                        {
                            teacher = line;
                        }
                    }
                    else if(iterator == 2)
                    {
                        line = line.replace("</td>", "");
                        line = line.trim();

                        if(weirdname)
                        {
                            room += " & "+line;
                            weirdname = false;
                        }
                        else
                        {
                            room = line;
                        }
                    }
                    else if(iterator == 3)
                    {
                        line = line.replace("</td>", "");
                        line = line.trim();
                        line = line.replace("Block:", "");
                        period = line;
                    }
                    else if(iterator == 4)
                    {
                            blocks[blocknumber] = new ScheduleBlock(name, teacher, room, period);
                            blocknumber++;

                        iterator = -1;
                    }
                    iterator++;
                }

            }
            else if(line.contains("<td class=\"MsoNormal\"") && start)
            {
                if(newBlock)
                {
                    blocks[blocknumber] = new ScheduleBlock("Free", "Free", "Free", "Free");
                    blocknumber++;
                }
                newBlock = true;
            }
            else if(line.contains("<table border=\"1\" width=\"100%\" cellpadding=\"0\" cellspacing=\"1\">") && start)
            {
                break;
            }
        }


        br.close();
        File schedf = new File(Main.SAVE_FOLDER+ File.separator +"Schedule.json");

        if(!schedf.exists())
        {
            schedf.createNewFile();
        }

        IO io = new IO(Main.SAVE_FOLDER+ File.separator +"Schedule.json");
        io.writeScheduleArray(blocks);
        io.unload();

    }

    public void grabwebpage(String u, String p)
    {
        try
        {
            GrabDay gd = new GrabDay(u, p);
            gd.grabSchedule(Main.SAVE_FOLDER+ File.separator +"output.html");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
