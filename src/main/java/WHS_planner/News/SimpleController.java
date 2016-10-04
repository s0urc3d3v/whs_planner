package WHS_planner.News;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by andrew_eggleston on 9/26/16.
 */
public class SimpleController implements Initializable {

    @FXML
    private Hyperlink Title1;

    @FXML
    private Button refreshButton;

    @FXML
    private Text Author1;

    @FXML
    private Text Description1;

    @FXML
    private Label label;

    private RSSFeedParser parser;
    private Feed feed;
    private List<FeedMessage> feedArray;
    private int currentArticle; //0 represents the first and most recent article

//    @FXML
//    private Text text;


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Title1 = new Hyperlink();
        refreshButton = new Button();
        Author1 = new Text();
        Description1 = new Text();
        label = new Label("ASDF");



        parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
        feed = parser.readFeed();
        feedArray = feed.getMessages();
        currentArticle = 0;

        FeedMessage currentMessage = feedArray.get(currentArticle);

        Title1.setText(currentMessage.getTitle());
        Author1.setText(currentMessage.getAuthor());
        Description1.setText(currentMessage.getDescription());

        label.setText("DSHGWDUYGDIDYUW");
//        try {
//            System.out.println(text.getText());
//        } catch(Exception e){
//
//        }
//        text = new Text("ASDF");
//        System.out.println(text.getText());
//        text.setText("ASDF");
//        System.out.println(text.getText());

    }



    //LINE TO OPEN URL: Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", "http://waylandstudentpress.com/"});


    @FXML
    public void openLink(){
        try {
            Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", feedArray.get(currentArticle).getLink()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void nextArticle(){
        if(currentArticle+1<=feedArray.size()-1) {
            currentArticle++;
            updateFrame();
        }
    }

    @FXML
    public void prevArticle(){
        if(currentArticle-1>=0) {
            currentArticle--;
            updateFrame();
        }
    }

    public int getArticle(){
        return currentArticle;
    }

    public void setArticle(int articleNumber) {
        currentArticle = articleNumber;
        updateFrame();
    }

    @FXML
    public void updateFrame(){
        FeedMessage currentMessage = feedArray.get(currentArticle);

        Title1.setText(currentMessage.getTitle());
        Author1.setText(currentMessage.getAuthor());
        Description1.setText(currentMessage.getDescription());

        System.out.println(Title1.getText());
        System.out.println(Author1.getText());
        System.out.println(Description1.getText());

    }
}

