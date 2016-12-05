package WHS_planner.News.ui;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.jsoup.Jsoup;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsUI {

    private RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
    private Feed feed = parser.readFeed();
    List<FeedMessage> feedArray = feed.getMessages();
    //List of articles to be added to display
    private List<FeedMessage> meme = feed.getMessages();
    //List of articles CURRENTLY ON DISPLAY
    private List<FeedMessage> onScreenMessages = new ArrayList<>();
    //width of cards in masonry pane
    private double widthLength = 200;
    private URL url;
    /**/private BorderPane roooot = new BorderPane();
    /**/private ScrollPane rooot = new ScrollPane();
    private JFXMasonryPane root = new JFXMasonryPane();


    public NewsUI() {


        roooot.setPrefSize(1280, 720);

        roooot.setCenter(rooot);
        rooot.setContent(root);
        rooot.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rooot.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rooot.setFitToWidth(true);

        rooot.prefHeightProperty().bind(roooot.heightProperty());
        rooot.prefWidthProperty().bind(roooot.widthProperty());

        root.setPrefSize(rooot.getPrefWidth(), rooot.getPrefHeight());
        root.setHSpacing(10);
        root.setVSpacing(10);

        root.setCellHeight(150);

        root.setCellWidth(widthLength);

        init();

    }



    private void openLink(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", parser.readFeed().getMessages().get(index).getLink()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {

        root.getChildren().clear();

        root.setStyle("-fx-background-color: #FFFFFF;");

        //Loop through all articles
        for (int i = 0; i < feedArray.size(); i++) {

            //Add Hyperlink
            final int eye = i;
            Hyperlink hpl = new Hyperlink(escapeHTML(feedArray.get(i).getTitle()));
            hpl.setOnAction((event) -> openLink(eye));
            hpl.setWrapText(true);
            hpl.setMaxWidth(widthLength);
            hpl.setPadding(new Insets(0, 0, 0, 4));

            //Add label
            Label description = new Label(escapeHTML(feedArray.get(i).getDescription()));
            description.setWrapText(true);
            description.setMaxWidth(widthLength);
            description.setPadding(new Insets(0, 0, 0, 6));

            //Add Image
            try {

                String urlString = scanDescription(feedArray.get(i).getDescription());
                if (urlString != null) {
                    url = new URL(urlString);
                    BufferedImage bf;

                    try {
                        bf = ImageIO.read(url);
                    } catch (Exception ex) {
                        System.out.println("Error with image.");
                        addCard(hpl, description);
                        continue;
                    }

                    WritableImage wr = convertImg(bf);
                    ImageView img = new ImageView(wr);
                    img.setFitWidth(widthLength);
                    img.setFitHeight(wr.getHeight() / (wr.getWidth() / widthLength));
                    img.setImage(wr);

                    //Add article to list
                    addCard(img, hpl, description);

                } else {

                    addCard(hpl, description);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            onScreenMessages.add(feedArray.get(i));

        }
        System.out.println("News loaded. f  e  e  l  s  g  o  o  d  m  a  n .");

    }


    private void addCard(Hyperlink hpl, Label desc) {
        System.out.println("Added without image.");
        VBox v = new VBox(hpl, /*author,*/ desc);
        v.setPrefWidth(widthLength);
        v.setMaxWidth(widthLength);

        v.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 15, 0, 1, 2, 0);" + "-fx-background-color: white;");
        root.getChildren().add(v);
    }

    private void addCard(ImageView img, Hyperlink hpl, Label desc) {
        VBox v = new VBox(img, hpl, desc);
        v.setPrefWidth(widthLength);
        v.setMaxWidth(widthLength);
        v.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 15, 0, 1, 2, 0);" + "-fx-background-color: white;");
        root.getChildren().add(v);
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

    public BorderPane getROOOOOOOT() {
        return roooot;
    }

    private String scanDescription(String content) {
        String link;
        if (content.contains("src")) {
            content = content.substring(content.indexOf("src=") + 5, content.length());
            link = content.substring(0, content.indexOf("\""));
            return link;
        } else {
            return null;
        }
    }

}
