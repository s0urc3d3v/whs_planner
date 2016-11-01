package WHS_planner.News.ui;

import WHS_planner.News.html.HTMLScanner;
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
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SimpleController implements Initializable {
    @FXML
    private JFXListView<VBox> articleListView;
    private RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
    private Feed feed = parser.readFeed();
    private List<FeedMessage> feedArray = feed.getMessages();
    private List<FeedMessage> newArticles = feed.getMessages();
    private double widthLength;
    private HTMLScanner HTMLScanner = new HTMLScanner();
    private ObservableList<VBox> articleList = FXCollections.observableArrayList();
    private URL url;
    private URL kms;
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {


        init();
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

        //Get new articles
        feedArray = parser.getNewArticles(feedArray);

        //Loop through all NEW articles backwards (to add them in the correct order)
        for (int i = feedArray.size(); i > 0; i--) {

            //Add Hyperlink
            final int eye = i;
            Hyperlink hpl = new Hyperlink(feedArray.get(i).getTitle());
            hpl.setOnAction((event) -> openLink(eye));
            hpl.setPadding(new Insets(0, 0, 0, -1));
            //Add label
            Label description = new Label(feedArray.get(i).getDescription());
            description.setWrapText(true);

            //Add Image
            try {
                Long startTime = System.currentTimeMillis();
                String urlString = HTMLScanner.scanHTML(HTMLScanner.scanURL(new URL(feedArray.get(i).getLink())));
                if (urlString != null) {
                    url = new URL(urlString);
                    BufferedImage bf = null;
                    try {
                        bf = ImageIO.read(url);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    WritableImage wr = convertImg(bf);
                    ImageView img = new ImageView(wr);
                    widthLength = articleListView.getPrefWidth() / 2;
                    img.setFitWidth(widthLength);
                    img.setFitHeight(wr.getHeight() / (wr.getWidth() / widthLength));
                    img.setImage(wr);


                    //Add article to list
                    VBox v = new VBox(img, hpl, /*author,*/ description);
                    v.setMaxWidth(articleListView.getPrefWidth());
                    articleList.add(0, v);
                } else {

                    //Add article to list
                    VBox v = new VBox(hpl, /*author,*/ description);
                    v.setMaxWidth(articleListView.getPrefWidth());
                    articleList.add(0, v);
                }
                System.out.println(System.currentTimeMillis() - startTime);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        articleListView.setItems(articleList);
    }


    @FXML
    private void init() {

        articleList.clear();

        //Add button
        JFXButton refreshButton = new JFXButton("Refresh");
        refreshButton.getStyleClass().add("button-raised");
        refreshButton.setOnAction((event) -> updateFrame());
        VBox r = new VBox(refreshButton);
        articleList.add(r);

        //Loop through all articles
        for (int i = 0; i < feedArray.size(); i++) {

            //Add Hyperlink
            final int eye = i;
            Hyperlink hpl = new Hyperlink(feedArray.get(i).getTitle());
            hpl.setOnAction((event) -> openLink(eye));
            hpl.setPadding(new Insets(0, 0, 0, -1));

            //Add label
            Label description = new Label(feedArray.get(i).getDescription());
            description.setWrapText(true);

            //Add Image
            try {
                Long startTime = System.currentTimeMillis();
                String urlString = HTMLScanner.scanHTML(HTMLScanner.scanURL(new URL(feedArray.get(i).getLink())));
                if (urlString != null) {
                    url = new URL(urlString);
                    BufferedImage bf = null;
                    try {
                        bf = ImageIO.read(url);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    WritableImage wr = convertImg(bf);
                    ImageView img = new ImageView(wr);
                    widthLength = articleListView.getPrefWidth() / 2;
                    img.setFitWidth(widthLength);
                    img.setFitHeight(wr.getHeight() / (wr.getWidth() / widthLength));
                    img.setImage(wr);

                    //Add article to list
                    VBox v = new VBox(img, hpl, /*author,*/ description);
                    v.setMaxWidth(articleListView.getPrefWidth());
                    articleList.add(v);
                } else {

                    //Add article to list
                    VBox v = new VBox(hpl, /*author,*/ description);
                    v.setMaxWidth(articleListView.getPrefWidth());
                    articleList.add(v);
                }
                System.out.println(System.currentTimeMillis() - startTime);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        articleListView.setItems(articleList);
    }


    private WritableImage convertImg(BufferedImage bf) {
        WritableImage wr = null;
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }
        return wr;
    }
}

