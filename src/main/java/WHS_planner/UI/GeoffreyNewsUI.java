package WHS_planner.UI;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jsoup.Jsoup;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class GeoffreyNewsUI extends Pane {

    private static final double BOX_WIDTH = 250;
    private static final double IMAGE_WIDTH = 200;
    private static final double IMAGE_HEIGHT = 200;
    private static final double BOX_HEIGHT = 300;
    private RSSFeedParser parser = new RSSFeedParser("http://waylandstudentpress.com/feed/");
    private Feed feed = parser.readFeed();
    //List of articles to add to display
    private List<FeedMessage> feedArray = feed.getMessages();
    //List of articles CURRENTLY ON DISPLAY
    private List<FeedMessage> onScreenMessages = new ArrayList<>();
    private URL url;

    private ScrollPane mainPane = new ScrollPane();
    private JFXMasonryPane masonryPane = new JFXMasonryPane();

    GeoffreyNewsUI() {
        mainPane.setContent(masonryPane);
        mainPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainPane.setFitToWidth(true);
//        mainPane.setStyle("-fx-focus-color: transparent;");
        mainPane.setStyle("-fx-background-color: #FFFFFF;");
        mainPane.getStyleClass().setAll("scroll-bar");

        masonryPane.setHSpacing(10);
        masonryPane.setVSpacing(10);

        masonryPane.setCellHeight(BOX_HEIGHT+30);
        masonryPane.setCellWidth(BOX_WIDTH);
        masonryPane.prefHeightProperty().bind(mainPane.heightProperty());
        init();

        this.getChildren().setAll(mainPane);
        mainPane.prefWidthProperty().bind(this.widthProperty());
        mainPane.prefHeightProperty().bind(this.heightProperty());
        mainPane.getStylesheets().add("/UI/NewsUI.css");
    }

    private void openLink(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", parser.readFeed().getMessages().get(index).getLink()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void init() {
        masonryPane.getChildren().clear();

//        System.out.println("Number of feeds: " + feedArray.size());

        //Loop through all articles
        for (int i = 0; i < feedArray.size(); i++) {

            //Add Hyperlink
            final int eye = i;
            Hyperlink hpl = new Hyperlink(escapeHTML(feedArray.get(i).getTitle()));
            hpl.setOnAction((event) -> openLink(eye));
            hpl.setWrapText(true);
            hpl.setMaxWidth(BOX_WIDTH);
            hpl.setPadding(new Insets(0, 0, 0, 4));

            //Add label
            Label description = new Label(escapeHTML(feedArray.get(i).getDescription()));
            description.setWrapText(true);
            description.setMaxWidth(BOX_WIDTH);
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
                        addCard(description,hpl);
                        continue;
                    }

                    WritableImage wr = convertImg(bf);
                    ImageView img = new ImageView(wr);
                    if(wr.getHeight()<wr.getWidth()){
                        img.setFitWidth(IMAGE_WIDTH);
                        img.setFitHeight(wr.getHeight() / (wr.getWidth() / (IMAGE_WIDTH)));
                    } else {
                        img.setFitHeight(IMAGE_HEIGHT);
                        img.setFitWidth(wr.getWidth() / (wr.getHeight() / (IMAGE_HEIGHT)));
                    }
                    img.setImage(wr);

                    //Add article to list
                    addCard(description, hpl, img);

                } else {

                    addCard(description, hpl);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            onScreenMessages.add(feedArray.get(i));

        }
    }

    private void addCard(Label description, Hyperlink hyperlink) {
        addCard(description,hyperlink,null);
    }

    private void addCard(Label description, Hyperlink hyperlink, ImageView image) {
        VBox vBox;
        if (image == null){
            vBox = new VBox(hyperlink, description);
        }else{
            vBox = new VBox(image, hyperlink, description);
        }
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(BOX_WIDTH);
        vBox.setPrefHeight(BOX_HEIGHT);
        vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 15, 0, 1, 2, 0);" + "-fx-background-color: white;" + "-fx-padding: 10;");
        masonryPane.getChildren().add(vBox);

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
