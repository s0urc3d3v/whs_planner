package WHS_planner.Schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;


/**
 * Created by william_robison on 9/26/16.
 */
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

    public static void getClasses() throws IOException {
        int count = 2;
        Element schedElement = null;
        Element rawClass = null;
        Elements classesRaw = new Elements();

        File input = new File("raw.html");
        Document doc = Jsoup.parse(input,"UTF-8","");
        Elements tables  = doc.select("table");

        for (int i = 0; i < tables.size(); i++) { // Finds the Schedule table & assigns it to schedElement
            if(tables.get(i).id().equalsIgnoreCase("Student's Schedule")){
                schedElement = tables.get(i);
            }
        }
            while (!schedElement.select(":has(table)").isEmpty()) {
                if(count < 59){
                rawClass = schedElement.select("table table").get(count);
                count++;
                classesRaw.add(rawClass);}
                else {break;}
            }

        System.out.println(classesRaw.size());
        for (int i = 0; i < 57; i++) {
//            System.out.println(polishClass(classesRaw.get(i)));
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(classesRaw.get(i));
            System.out.println();
            System.out.println();
            System.out.println();
        }

        ScheduleBlock[] schedule = new ScheduleBlock[57];

//        for (int i = 0; i < 57 ; i++) {
//            schedule[i] = new ScheduleBlock()
//        }


    }



    private static String[] polishClass(Element el){ // returns "Class:Teacher:Roome:Period"
        //Write code for advisory
        String strEl = el.toString();
        String[] polishedStr = new String[4];
        String utilStr = "";
        String[] free = {"free","free","free","free"};


        if(strEl.length() < 200){
            return free;
        }

        //Find td's

        //removes first garbage
        strEl = strEl.substring(0,strEl.indexOf("<td")) + strEl.substring(strEl.indexOf("</td>") + 5);

        //Finds Teacher
        for (int i = 0; i < 4; i++) {
            if(i == 3){
                utilStr = strEl.substring(strEl.indexOf("<td") + 1, strEl.indexOf("</td>") + 3);
                polishedStr[i] = utilStr.substring(utilStr.indexOf(":") + 2, utilStr.indexOf(" </"));
                strEl = strEl.substring(0, strEl.indexOf("<td")) + strEl.substring(strEl.indexOf("</td>") + 5);
            }else {
                utilStr = strEl.substring(strEl.indexOf("<td") + 1, strEl.indexOf("</td>") + 3);
                polishedStr[i] = utilStr.substring(utilStr.indexOf("> ") + 2, utilStr.indexOf(" </")) + ":";
                strEl = strEl.substring(0, strEl.indexOf("<td")) + strEl.substring(strEl.indexOf("</td>") + 5);
            }
        }


        //

            return polishedStr;
        }
    }

