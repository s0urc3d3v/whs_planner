package WHS_planner.News.ui;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import javafx.application.Application;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

public class NewsUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception{

        String sceneFile = "/resources/News/news1_3.fxml";
        Parent root;
        URL url  = null;

        RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
        Feed feed = parser.readFeed();
        List<FeedMessage> feedArray = feed.getMessages();

        try {
            url = getClass().getResource("/News/news1_3.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            root = fxmlLoader.load();
            ObservableMap<String, Object> hashMap = fxmlLoader.getNamespace();
            Hyperlink Title1 = (Hyperlink)hashMap.get("Title1");
            Title1.setText(feedArray.get(0).getTitle());
            Label Author1 = (Label)hashMap.get("AuthorOne");
            Author1.setText(feedArray.get(0).getAuthor());
            Label Description1 = (Label)hashMap.get("DescriptionOne");
            Description1.setText(feedArray.get(0).getDescription());

            Hyperlink Title2 = (Hyperlink)hashMap.get("Title2");
            Title2.setText(feedArray.get(1).getTitle());
            Label Author2 = (Label)hashMap.get("AuthorTwo");
            Author2.setText(feedArray.get(1).getAuthor());
            Label Description2 = (Label)hashMap.get("DescriptionTwo");
            Description2.setText(feedArray.get(1).getDescription());

            Hyperlink Title3 = (Hyperlink)hashMap.get("Title3");
            Title3.setText(feedArray.get(2).getTitle());
            Label Author3 = (Label)hashMap.get("AuthorThree");
            Author3.setText(feedArray.get(2).getAuthor());
            Label Description3 = (Label)hashMap.get("DescriptionThree");
            Description3.setText(feedArray.get(2).getDescription());

            Hyperlink Title4 = (Hyperlink)hashMap.get("Title4");
            Title4.setText(feedArray.get(3).getTitle());
            Label Author4 = (Label)hashMap.get("AuthorFour");
            Author4.setText(feedArray.get(3).getAuthor());
            Label Description4 = (Label)hashMap.get("DescriptionFour");
            Description4.setText(feedArray.get(3).getDescription());

            Hyperlink Title5 = (Hyperlink)hashMap.get("Title5");
            Title5.setText(feedArray.get(4).getTitle());
            Label Author5 = (Label)hashMap.get("AuthorFive");
            Author5.setText(feedArray.get(4).getAuthor());
            Label Description5 = (Label)hashMap.get("DescriptionFive");
            Description5.setText(feedArray.get(4).getDescription());

            Hyperlink Title6 = (Hyperlink)hashMap.get("Title6");
            Title6.setText(feedArray.get(5).getTitle());
            Label Author6 = (Label)hashMap.get("AuthorSix");
            Author6.setText(feedArray.get(5).getAuthor());
            Label Description6 = (Label)hashMap.get("DescriptionSix");
            Description6.setText(feedArray.get(5).getDescription());

            Hyperlink Title7 = (Hyperlink)hashMap.get("Title7");
            Title7.setText(feedArray.get(6).getTitle());
            Label Author7 = (Label)hashMap.get("AuthorSeven");
            Author7.setText(feedArray.get(6).getAuthor());
            Label Description7 = (Label)hashMap.get("DescriptionSeven");
            Description7.setText(feedArray.get(6).getDescription());

            Hyperlink Title8 = (Hyperlink)hashMap.get("Title8");
            Title8.setText(feedArray.get(7).getTitle());
            Label Author8 = (Label)hashMap.get("AuthorEight");
            Author8.setText(feedArray.get(7).getAuthor());
            Label Description8 = (Label)hashMap.get("DescriptionEight");
            Description8.setText(feedArray.get(7).getDescription());

            Hyperlink Title9 = (Hyperlink)hashMap.get("Title9");
            Title9.setText(feedArray.get(8).getTitle());
            Label Author9 = (Label)hashMap.get("AuthorNine");
            Author9.setText(feedArray.get(8).getAuthor());
            Label Description9 = (Label)hashMap.get("DescriptionNine");
            Description9.setText(feedArray.get(8).getDescription());

            Hyperlink Title10 = (Hyperlink)hashMap.get("Title10");
            Title10.setText(feedArray.get(9).getTitle());
            Label Author10 = (Label)hashMap.get("AuthorTen");
            Author10.setText(feedArray.get(9).getAuthor());
            Label Description10 = (Label)hashMap.get("DescriptionTen");
            Description10.setText(feedArray.get(9).getDescription());

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

        //TODO Issues: RSS reader isn't getting author, WSPN feed uses dc:creator instead of author
        //TODO title seems shortened sometimes
        //TODO Each of the articles shown shouldn't be hard coded, use vbox and jfxlistview instead


        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
