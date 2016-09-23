package WHS_planner;

import WHS_planner.Core.RSS;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import junit.framework.TestCase;

import java.net.URL;

/**
 * Created by john_bachman on 9/23/16.
 */
public class RSSTest extends TestCase {
    public void testRSS() throws Exception {
        RSS rss = new RSS(new URL("http://waylandstudentpress.com/feed/"));
        SyndFeed feed = rss.getRssFeed();
        SyndEntry entry = (SyndEntry) feed.getEntries().get(0);
        System.out.println(entry.getContents());
    }
}
