package WHS_planner.Core;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.URL;

/**
 * Created by matthewelbing on 17.09.16.
 */
public class RSS {
    private SyndFeed feed;

    public RSS(URL feedUrl){
        try {
            SyndFeedInput feedInput = new SyndFeedInput();
            feed = feedInput.build(new XmlReader(feedUrl));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorHandler.handleGenericError("Error with getting RSS Feed", new Exception()); //TODO Create error message and screen
        }
    }

    public SyndFeed getRssFeed(){
        return feed;
    }
}
