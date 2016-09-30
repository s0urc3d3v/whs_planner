package WHS_planner.News;

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

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;

/**
 * Created by andrew_eggleston on 9/26/16.
 */
public class SimpleController implements Initializable {

    @FXML
    private Hyperlink Article_Title_Sample2;

    @FXML
    private Button nextPageButton;

    @FXML
    private Button prevPageButton;

    @FXML
    private Text Author_Sample;

    @FXML
    private Text Description;

    @FXML
    private Label label;

    private RSSFeedParser parser;
    private Feed feed;
    private List<FeedMessage> feedArray;
    private int currentArticle; //0 represents the first and most recent article

//    @FXML
//    private Text text;


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Article_Title_Sample2 = new Hyperlink();
        prevPageButton = new Button();
        nextPageButton = new Button();
        Author_Sample = new Text();
        Description = new Text();
        label = new Label("ASDF");



        parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
        feed = parser.readFeed();
        feedArray = feed.getMessages();
        currentArticle = 0;

        FeedMessage currentMessage = feedArray.get(currentArticle);

        Article_Title_Sample2.setText(currentMessage.getTitle());
        Author_Sample.setText(currentMessage.getAuthor());
        Description.setText(currentMessage.getDescription());

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


    public void setArticle(int articleNumber){
        currentArticle = articleNumber;
        updateFrame();
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

    @FXML
    public void updateFrame(){
        FeedMessage currentMessage = feedArray.get(currentArticle);

        Article_Title_Sample2.setText(currentMessage.getTitle());
        Author_Sample.setText(currentMessage.getAuthor());
        Description.setText(currentMessage.getDescription());

        System.out.println(Article_Title_Sample2.getText());
        System.out.println(Author_Sample.getText());
        System.out.println(Description.getText());

        if(currentArticle == 0){
            prevPageButton.setVisible(false);
            nextPageButton.setVisible(true);
        }
        else if(currentArticle == feedArray.size()-1){
            prevPageButton.setVisible(true);
            nextPageButton.setVisible(false);
        }
        else{
            prevPageButton.setVisible(true);
            nextPageButton.setVisible(true);
        }
    }
}

