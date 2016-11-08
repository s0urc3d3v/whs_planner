package WHS_planner.Schedule;

import WHS_planner.Core.IO;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParseCalendar
{
    private String[] classdays;
    
    private Map<String, Integer> tracker;

    public ParseCalendar()
    {
        
    }
    
    public void setData() throws Exception
    {
        classdays = parseData();
    }

    public void writeData()
    {
        IO io = new IO("DayArray.json");
        io.writeArray("calendarData", classdays);
        //io.unload();

        //IO io2 = new IO("dateArray.json");

        Set keys = tracker.keySet();
        String[] datess = new String[keys.size()];
        Object[] dates = keys.toArray();


        Integer[] days = new Integer[keys.size()];

        for (int i = 0; i < keys.size(); i++)
        {
            datess[i] = dates[i].toString();
            days[i] = tracker.get(dates[i]);
        }


        io.writeArray("dates", datess);
        //io2.unload();


        //IO io3 = new IO("MapArray.json");
        io.writeArray("days", days);
        io.unload();
    }

    public void readData()
    {
        IO io = new IO("DayArray.json");
        Object[] objects = io.readArray("@calendarData");
        for (int i = 0; i < objects.length; i++)
        {
            classdays[i] = objects[i].toString();
        }

        objects = io.readArray("@dates");
        Object[] objects2 = io.readArray("@days");
        for (int i = 0; i < objects.length; i++)
        {
            tracker.put(objects[i].toString(), (Integer) objects2[i]);
        }

        io.unload();



    }

    private String[] parseData() throws Exception
    {
        String[] days = new String[365];

        tracker = new HashMap<String, Integer>();
        
        File parent = new File("tmp");

        for(File file : parent.listFiles())
        {
            if(file.isDirectory())
            {
                String month = file.getName();

                int mon;

                switch(month)
                {
                    case "January": mon = 1;
                        break;
                    case "February": mon = 2;
                        break;
                    case "March": mon = 3;
                        break;
                    case "April": mon = 4;
                        break;
                    case "May": mon = 5;
                        break;
                    case "June": mon = 6;
                        break;
                    case "July": mon = 7;
                        break;
                    case "August": mon = 8;
                        break;
                    case "September": mon = 9;
                        break;
                    case "October": mon = 10;
                        break;
                    case "November": mon = 11;
                        break;
                    case "December": mon = 12;
                        break;
                    default: mon = -1;
                        break;
                }

                for(File file2 : file.listFiles())
                {
                    String date = file2.getName();

                    Document doc = Jsoup.parse(file2, "UTF-8", "");

                    Elements elements;
                    elements = doc.select(".DATA");

                    for(Element element : elements)
                    {

                        String letter = element.text();

                        String day;

                        if(element.text().contains("Today is the"))
                        {
                            day = letter.substring(13, 16);

                            if(!StringUtil.isNumeric(day.substring(2)))
                            {
                                day = day.substring(0,2);
                            }

                            if(!StringUtil.isNumeric(day.substring(1)))
                            {
                                day = day.substring(0,1);
                            }

                            int x = Integer.parseInt(day);


                            int i = letter.indexOf("cycle")-2;
                            letter = letter.substring(i, i+1);

                            days[x-1] = letter;


                            String full = Integer.toString(mon)+"/"+date;
                            tracker.put(full, x);

                        }
                    }
                }
            }

            else
            {
                System.out.println("errors");
            }
        }

        for (int i = 0; i < days.length; i++)
        {
            if(days[i] == null)
            {

                days[i] = "There is No School Today";
            }
        }

        return days;
    }
    
    public String[] getArray()
    {
        return classdays;
    }

    public String getDay(String s)
    {
        if(tracker.get(s) != null)
        {
            int i = tracker.get(s);
            return classdays[i-1];
        }
        else
        {
            System.out.println("day not found");
            return "No School Today";
        }
    }
}
