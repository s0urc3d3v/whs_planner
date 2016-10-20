package WHS_planner.News.ui;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class SimpleController implements Initializable {

    @FXML
    private JFXListView<VBox> articleListView;

    @FXML
    private JFXButton oldRefreshButton;

    private RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
    private Feed feed = parser.readFeed();
    private List<FeedMessage> feedArray = feed.getMessages();

    private ObservableList<VBox> articleList;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
//        oldRefreshButton = new JFXButton();
        articleList = FXCollections.observableArrayList();
        JFXButton refreshButton = new JFXButton("Refresh");


        refreshButton.setOnMousePressed(event -> updateFrame());
        //TODO ^

        VBox r = new VBox(refreshButton);
        articleList.add(r);
        articleListView.setItems(articleList);
        updateFrame();
    }

    @FXML
    public void openLink(int index) {
        //TODO get index
        try {
            Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", feedArray.get(index).getLink()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void updateFrame() {
        System.out.println("Update frame called!");
        for (FeedMessage message : feed.getMessages()) {
            VBox v = new VBox(new Hyperlink(message.getTitle()), new Label(message.getAuthor()), new Label(message.getDescription()));
            //TODO variable tied to the hyperlink, so on action pressed it can send array index to method
            articleList.add(v);
        }
        articleListView.setItems(articleList);
        //TODO make it so it replaces articles instead of adding



    }
}