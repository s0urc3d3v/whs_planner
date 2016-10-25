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
import javafx.geometry.Insets;
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
    private RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
    private Feed feed = parser.readFeed();
    private List<FeedMessage> feedArray = feed.getMessages();
    private ObservableList<VBox> articleList = FXCollections.observableArrayList();

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
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
        refreshButton.getStyleClass().add("button-raised");

        refreshButton.setOnAction((event) -> updateFrame());
        VBox r = new VBox(refreshButton);
        articleList.add(r);
        for (int i = 0; i < feedArray.size(); i++) {
            final int eye = i;
            Hyperlink hpl = new Hyperlink(feedArray.get(i).getTitle());
            hpl.setOnAction((event) -> openLink(eye));
            hpl.setPadding(new Insets(0, 0, 0, -1));
            Label author = new Label(feedArray.get(i).getAuthor());
            author.setWrapText(true);
            Label description = new Label(feedArray.get(i).getDescription());
            description.setWrapText(true);
            VBox v = new VBox(hpl, /*author,*/ description);
            v.setMaxWidth(articleListView.getPrefWidth() - 45);
            articleList.add(v);
        }
        articleListView.setItems(articleList);
    }
}

//test