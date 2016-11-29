package WHS_planner.Core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matthewelbing on 27.09.16.
 */
public class SportsHandler {

    private String sportSite = "http://miaa.net/athletics/calendar/schedule.php?school_id=1424&team_type_ids=0&PRINT_PREVIEW=1";

    public SportsHandler() {}

    private boolean siteUpdateNeeded() {
        //Eventually we will need to check if we have written the sports to a JSON file.
        return true;
    }

    private void writeToJSON(HashMap<String, Integer> sports) {}

    public HashMap<String, Integer> getSports() {

        Document doc = null;
        try {
            doc = getWebsite(sportSite);
        } catch (IOException e) {
            e.printStackTrace();
            //Problem with grabbing the page
            return new HashMap<>();
        }
        HashMap<String, Integer> sports = new HashMap<String, Integer>();
        //Grab all the select elements under the change team dropdown.
        for(Element element: doc.getElementById("change_team_sb_0").children()) {
            //Store all the sports names in a list with their indexes.
            sports.put(element.text(), Integer.valueOf(element.val()));
        }
        //Write this to a JSON File for later.
        writeToJSON(sports);
        return sports;
    }

    private Document getWebsite(String siteURL) throws IOException {
        String site = "";
        URL url = null;
        try {
            url = new URL(siteURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while((line=bufferedReader.readLine()) != null) {
            site += line;
        }
        return Jsoup.parse(site);
    }

    public List<String> getEvents(int sportIndex) {
        List<String> events = new ArrayList<>();
        try {
            Document eventPage = getWebsite(getEventSiteLink(sportIndex));
            List<Element> elements = eventPage.getElementsByTag("tbody").get(1).children();
            for(int i = 3; i < elements.size(); i++) {
                if(elements.get(i).children().size() >= 4) {
                    //Adds each element as a date : the location
                    events.add(Arrays.toString(Arrays.copyOfRange(elements.get(i).children().get(1).text().split(" "),0,2)).replaceAll("\\[|\\]|,","") + " : " + elements.get(i).children().get(3).text());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    public String getEventSiteLink(int sportIndex) {
        String teamTag = "team_type_ids=";
        int teamIndexStringPosition = sportSite.indexOf(teamTag) + teamTag.length();
        return sportSite.substring(0, teamIndexStringPosition) + String.valueOf(sportIndex) + sportSite.substring(teamIndexStringPosition + 1);
    }
}
