package WHS_planner.News.tests;

import WHS_planner.News.html.HTMLScanner;
import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class ScanTest {


    public static void main(String[] Args) throws MalformedURLException {

        RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
        Feed feed = parser.readFeed();
        List<FeedMessage> feedArray = feed.getMessages();

        URL testArticle = new URL("http://waylandstudentpress.com/50598/articles/maggie-fiske-i-felt-like-the-way-i-felt-in-my-bedroom-dancing-by-myself/");
        HTMLScanner html_Body = new HTMLScanner();

        //Prints image link from the article
//        String imgLink = html_Body.scanHTML(html_Body.scanURL(testArticle));
//        System.out.println(imgLink);

        //Prints image link from desc, new method
        for (FeedMessage message : feed.getMessages()) {
            String linkOMG = html_Body.scanDescription(message.getDescription());
            System.out.println(linkOMG);


        }







    }

}
