package WHS_planner.News.tests;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;

import java.util.List;

public class ReadTest {
    public static void main(String[] args) {
        RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
        Feed feed = parser.readFeed();
//        System.out.println(feed);


        List<FeedMessage> feedArray = feed.getMessages();
//        for (int i = 0; i < feedArray.size(); i++) {
//            System.out.print("Article " + i + ": " + feedArray.get(i).getTitle());
//            System.out.print(" DESCRIPTION: " + feedArray.get(i).getDescription());
//            System.out.println();
//        }

        for (FeedMessage message : feed.getMessages()) {
            System.out.println(message.getTitle());
            System.out.print(" " + message.getDescription());
            System.out.println(message.getAuthor());
        }

    }
}