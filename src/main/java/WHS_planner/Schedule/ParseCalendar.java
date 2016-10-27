package WHS_planner.Schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class ParseCalendar
{
    private String[] classdays;
    
    
    public ParseCalendar()
    {
        
    }
    
    public void setData() throws Exception
    {
        classdays = parseData();
    }

    private String[] parseData() throws Exception
    {
        String[] days = new String[365];
        
        File parent = new File("tmp");

        for(File file : parent.listFiles())
        {
            if(file.isDirectory())
            {
                for(File file2 : file.listFiles())
                {
                    Document doc = Jsoup.parse(file2, "UTF-8", "");

                    Elements elements;
                    elements = doc.select(".DATA");

                    for(Element element : elements)
                    {
                        if(element.text().contains("Today is the"))
                        {
                            String s = element.text();
                            int i = s.indexOf("cycle")-2;
                            s = s.substring(i, i+1);
                            System.out.println(s);
                        }
                    }
                }
            }

            else
            {
                System.out.println("errors");
            }
        }

        return days;
    }
    
    public String[] getArray()
    {
        return classdays;
    }
}
