package WHS_planner.News.ui;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

public class NewsUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception{

        String sceneFile = "/resources/News/news1_5.fxml";
        Parent root;
        URL url  = null;

        RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
        Feed feed = parser.readFeed();
        List<FeedMessage> feedArray = feed.getMessages();

        try {
            url = getClass().getResource("/News/news1_5.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            root = FXMLLoader.load(url);
            System.out.println( "  fxmlResource = " + sceneFile );
        }
        catch ( Exception ex )
        {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            throw ex;
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
