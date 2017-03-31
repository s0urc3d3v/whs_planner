package WHS_planner.Schedule;

import WHS_planner.Core.IO;
import WHS_planner.Main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ScheduleParserV3 {

    private ScheduleBlock[] blocks;

    private String name, teacher, room, block;


    public ScheduleParserV3() {
        blocks = new ScheduleBlock[56];
    }

    public void getClasses() throws IOException {
        String htm = cleanHTML(new File(Main.SAVE_FOLDER+ File.separator +"output.html"));
        Document d = Jsoup.parse(htm);
        Elements tables = d.getElementsByTag("table");
        Element schedule = tables.get(3);
        Elements tbodies = schedule.getElementsByTag("tbody");
        tbodies.remove(0);

        int blocknum = 0;

        for (int i = 0; i < tbodies.size(); i++) {
            Element e = tbodies.get(i);
            Elements tds = e.getElementsByTag("td");

            if(tds.size() % 5 == 0) {
                int times = tds.size()/5;
                for (int j = 0; j < times; j++) {

                    if(j == 0) {
                        name = tds.get(j*5).text().trim();
                        teacher = tds.get((j*5)+1).text().trim();
                        room = tds.get((j*5)+2).text().trim();
                        block = tds.get((j*5)+3).text().trim();
                    }
                    else {
                        name += " or " + tds.get((j*5)).text().trim();
                        teacher += " or " + tds.get((j*5)+1).text().trim();
                        room += " or " + tds.get((j*5)+2).text().trim();
                        block += " or " + tds.get((j*5)+3).text().trim();
                    }

                }

                blocks[blocknum] = new ScheduleBlock(name, teacher, room, block);
                blocknum++;
            }
            else if(tds.size() == 1) {
                name = "Free";
                teacher = "Free";
                room = "Free";
                block = "Free";
                blocks[blocknum] = new ScheduleBlock(name, teacher, room, block);
                blocknum++;
            }
            else {
                System.out.println("Probably have a problem");
            }

        }

        File schedf = new File(Main.SAVE_FOLDER+ File.separator +"Schedule.json");

        if(!schedf.exists())
        {
            schedf.createNewFile();
        }

        IO io = new IO(Main.SAVE_FOLDER+ File.separator +"Schedule.json");
        io.writeScheduleArray(blocks);
        io.unload();
    }


    public ScheduleBlock[] getblocks() {
        return blocks;
    }

    private void printNode(Elements elements) {
        for(Element e : elements) {
            System.out.println(e);
        }
    }

    private String cleanHTML(File html) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(html));
        String line;
        StringBuilder res = new StringBuilder();
        while((line = br.readLine()) != null) {
            res.append(line);
        }
        String s = res.toString();
        s = s.replace("\n", "");
        s = s.replace("\t", "");
        return Jsoup.clean(s, Whitelist.relaxed());
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
