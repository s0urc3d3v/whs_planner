package WHS_planner.News.read;

/**
 * Created by george_jiang on 9/20/16.
 */
//import java.lang.Object;

import org.jsoup.Jsoup;
import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;

public class HTMLParser {
    String y;

    public HTMLParser()
    {

    }
    public String Escape(String x)
    {
        y = Jsoup.parse(x).text();
        return y;
    }



}
