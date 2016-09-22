package WHS_planner.Core;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.net.URL;

/**
 * Created by matthewelbing on 17.09.16.
 */
public class RSS {
    private SyndFeed feed;
    private URL feedUrl = null;
    public RSS(){
        try {
            JSON json = new JSON();
            SyndFeedInput feedInput = new SyndFeedInput();
            feedUrl = (URL) json.readObject("feedurl");
            feed = feedInput.build(new XmlReader(feedUrl));

        } catch (Exception e) {
            e.printStackTrace();
            ErrorHandler.handleGenericError("error", new Exception()); //TODO Create error message and screen
        }
    }
    public SyndFeed getRssFeed(){
        return feed;
    }
}
