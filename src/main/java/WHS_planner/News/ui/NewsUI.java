package WHS_planner.News.ui;

import WHS_planner.News.html.HTMLScanner;
import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsUI extends Application {

    private RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
    private Feed feed = parser.readFeed();

    //List of articles to add to display
    private List<FeedMessage> feedArray = feed.getMessages();

    //List of articles CURRENTLY ON DISPLAY
    private List<FeedMessage> onScreenMessages = new ArrayList<>();

    private HTMLScanner HTMLScanner = new HTMLScanner();

    private double widthLength;

    private URL url;

    //    private Group roooot = new Group();
//    private ScrollPane rooot = new ScrollPane();
    private JFXMasonryPane root = new JFXMasonryPane();


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
//        rooot.setContent(root);
//        roooot.getChildren().add(rooot);

        root.setPrefSize(1440, 900);
        init();


        Scene scene = new Scene(root);
        scene.getStylesheets().add(File.separator + "News" + File.separator + "ButtonStyle.css");
        stage.setScene(scene);
        stage.show();
    }

    private void openLink(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", parser.readFeed().getMessages().get(index).getLink()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void updateFrame() {

        //TODO: text wrap, scroll bar
        Long refreshStartTime = System.currentTimeMillis();

        //Get new articles
        feedArray = parser.getNewArticles(onScreenMessages);


        //Loop through all NEW articles
        for (int i = 0; i < feedArray.size(); i++) {

            //Add Hyperlink
            final int eye = i;
            Hyperlink hpl = new Hyperlink(escapeHTML(feedArray.get(i).getTitle()));
            hpl.setOnAction((event) -> openLink(eye));
            hpl.setPadding(new Insets(0, 0, 0, -1));
            //Add label
            Label description = new Label(escapeHTML(feedArray.get(i).getDescription()));
            description.setWrapText(true);

            //Add Image
            try {
                //Timing test
                Long startTime = System.currentTimeMillis();

                String urlString = HTMLScanner.scanDescription(feedArray.get(i).getDescription());
                if (urlString != null) { //Image
                    url = new URL(urlString);
                    BufferedImage bf = null;
                    try {
                        bf = ImageIO.read(url);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    WritableImage wr = convertImg(bf);
                    ImageView img = new ImageView(wr);

//                    widthLength = articleListView.getPrefWidth() / 2;
//                    img.setFitWidth(widthLength);
//                    img.setFitHeight(wr.getHeight() / (wr.getWidth() / widthLength));
                    img.setImage(wr);

                    //Add article to list, with image
                    VBox v = new VBox(img, hpl, /*author,*/ description);
//                    v.setMaxWidth(articleListView.getPrefWidth());
                    root.getChildren().add(1, v);
                } else { //No Image!

                    //Add article to list
                    VBox v = new VBox(hpl, /*author,*/ description);
//                    v.setMaxWidth(articleListView.getPrefWidth());
                    root.getChildren().add(1, v);
                }
                //print
                System.out.println(System.currentTimeMillis() - startTime);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            onScreenMessages.add(feedArray.get(i));

        }
        System.out.println("REFRESH FINISH: " + (System.currentTimeMillis() - refreshStartTime) + " ms");
        System.out.println();
        System.out.println();
    }

    @FXML
    public void init() {
        root.setStyle("-fx-background-color: #dbdbdb;");

        root.getChildren().clear();

        //Add button
//        JFXButton refreshButton = new JFXButton("Refresh");
//        refreshButton.getStyleClass().add("button-raised");
//        refreshButton.setOnAction((event) -> updateFrame());
//        VBox r = new VBox(refreshButton);
//        root.getChildren().add(r);

        //Loop through all articles
        for (int i = 0; i < feedArray.size(); i++) {

            //Add Hyperlink
            final int eye = i;
            Hyperlink hpl = new Hyperlink(escapeHTML(feedArray.get(i).getTitle()));
            hpl.setOnAction((event) -> openLink(eye));
            hpl.setWrapText(true);
//            hpl.setPadding(new Insets(0, 0, 0, -1));

            //Add label
            Label description = new Label(escapeHTML(feedArray.get(i).getDescription()));
            description.setWrapText(true);
            description.setMaxWidth(widthLength);

            //Add Image
            try {

                //Timing Test
                Long startTime = System.currentTimeMillis();

                String urlString = HTMLScanner.scanDescription(feedArray.get(i).getDescription());
                System.out.println(urlString);
                if (urlString != null) {
                    System.out.println("There's an image.");
                    url = new URL(urlString);
                    BufferedImage bf = null;
                    try {
                        bf = ImageIO.read(url);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    WritableImage wr = convertImg(bf);
                    ImageView img = new ImageView(wr);
//                    widthLength = articleListView.getPrefWidth() / 2;
                    widthLength = 200;
                    img.setFitWidth(widthLength);
                    img.setFitHeight(wr.getHeight() / (wr.getWidth() / widthLength));
                    img.setImage(wr);

                    //Add article to list
                    VBox v = new VBox(img, hpl, /*author,*/ description);
//                    v.setPrefSize(600,400);
//                    v.setMinSize(500,300);
                    v.setPrefWidth(widthLength);
                    v.setMaxWidth(widthLength);
//                    v.setStyle("-fx-background-color: #FFFFFF;");
                    v.getStyle();

//                    v.setMaxWidth(articleListView.getPrefWidth());

//                    Pane pane = (Pane) (description.getParent());
//                    pane.setStyle("-fx-background-color:#FFFFFF;");
                    v.setStyle("-fx-background-color: #FFFFFF;");

                    System.out.println(v.getStyle());
                    root.getChildren().add(v);
//                    root.getChildren().add(new Group(v));


                } else {

                    System.out.println("----ADDED WITHOUT IMAGE-----");
                    //Add article to list
                    VBox v = new VBox(hpl, /*author,*/ description);
                    v.setPrefSize(400, 400);
                    v.setStyle("-fx-background-color: #FFFFFF;");
//                    v.setMaxWidth(articleListView.getPrefWidth());
                    root.getChildren().add(v);
                }
                //print
                System.out.println(System.currentTimeMillis() - startTime);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            onScreenMessages.add(feedArray.get(i));

        }
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

    private String escapeHTML(String string) {
        return Jsoup.parse(string).text();
    }

}
