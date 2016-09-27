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
public class wankers {



    public static void main(String[] args) {
        try {
            getClasses();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getClasses() throws IOException {
        int[] wanks = new int[12];
        int count = 0;
        Element schedElement = null;

        File input = new File("/Users/william_robison/IdeaProjects/whs_planner/raw.html");
        Document doc = Jsoup.parse(input,"UTF-8","");
        Elements links  = doc.select("table");

        for (int i = 0; i < links.size(); i++) {
            if(links.get(i).id().equalsIgnoreCase("Student's Schedule")){
                schedElement = links.get(i);
                System.out.println(i);
            }
        }

        for (int i = 0; i < 12; i++) {
            System.out.println(links.get(wanks[i]));
        }
        System.out.println(count);
        String wow2 = schedElement.toString();
        System.out.println(wow2);

    }
}
