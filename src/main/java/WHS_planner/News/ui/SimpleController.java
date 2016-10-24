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

    private final int ZERO = 0;
    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private final int FOUR = 4;
    private final int FIVE = 5;
    private final int SIX = 6;
    private final int SEVEN = 7;
    private final int EIGHT = 8;
    private final int NINE = 9;
    private final int TEN = 10;

    @FXML
    private JFXListView<VBox> articleListView;

    private RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
    private Feed feed = parser.readFeed();
    private List<FeedMessage> feedArray = feed.getMessages();

    private ObservableList<VBox> articleList;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        articleList = FXCollections.observableArrayList();

//        articleListView.setItems(articleList);
        updateFrame();
    }

    @FXML
    public void openLink(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", feedArray.get(index).getLink()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateFrame() {
        articleList.clear();

        JFXButton refreshButton = new JFXButton("Refresh");
        refreshButton.setOnAction((event) -> updateFrame());
        VBox r = new VBox(refreshButton);
        articleList.add(r);

        for (int i = 0; i < feedArray.size(); i++) {
            final int vapeNaysh = i;
            Hyperlink hpl = new Hyperlink(feedArray.get(i).getTitle());
            hpl.setOnAction((event) -> openLink(vapeNaysh));

            Label author = new Label(feedArray.get(i).getAuthor());
            author.setWrapText(true);
            Label description = new Label(feedArray.get(i).getDescription());
            description.setWrapText(true);
            VBox v = new VBox(hpl, author, description);
            v.setMaxWidth(560);
            //TODO dont hard code this
            articleList.add(v);
        }
        articleListView.setItems(articleList);
    }
}