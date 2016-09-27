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

        File input = new File("raw.html");
        Document doc = Jsoup.parse(input,"UTF-8","");
        Elements tables  = doc.select("table");

        for (int i = 0; i < tables.size(); i++) {
            if(tables.get(i).id().equalsIgnoreCase("Student's Schedule")){
                schedElement = tables.get(i);
                System.out.println(i);
            }
        }

//        schedElement = schedElement.select("table table").first();
        while(! schedElement.select(":has(table)").isEmpty()){
            schedElement = schedElement.select("table table").first();
        }

//        System.out.println(wowo);
        System.out.println(schedElement);
        System.out.println(count);
        String wow2 = schedElement.toString();
//        System.out.println(wow2);

    }

    private static String[] findClass(int period, String el){

        return null;
    }

}
