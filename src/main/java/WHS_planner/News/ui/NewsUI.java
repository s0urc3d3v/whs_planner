package WHS_planner.News.ui;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import WHS_planner.Trex.TrexPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jsoup.Jsoup;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewsUI extends Pane {

    private static final double BOX_WIDTH = 250;
    private static final double IMAGE_WIDTH = 250;
//    private static final double IMAGE_HEIGHT = 200;

    private RSSFeedParser parser = new RSSFeedParser("https://waylandstudentpress.com/feed/");
    private Feed feed = parser.readFeed();
    private List<FeedMessage> feedArray = feed.getMessages();
    private VBox cardView = new VBox();

    public NewsUI() {

        cardView.getStylesheets().add("News" + File.separator + "NewsUI.css");

        //Checks if feed sends back a connection error. If it doesn't, initialize cards as normal.
        if (feed.getTitle().equals("badNet")) {
            Hyperlink badNetLink = new Hyperlink("https://www.google.com/");
            badNetLink.setText("Test Connection (google.com)");
            addCard(badNetLink, new Label("Error with Connection!"));
            addCard(new TrexPane());
        } else {
            init();
        }
    }

    private void openLink(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", parser.readFeed().getMessages().get(index).getLink()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        cardView.getChildren().clear();

        //Loop through all articles
        for (int i = 0; i < feedArray.size(); i++) {

            //Add Title (Hyperlink)
            final int eye = i;
            Hyperlink hpl = new Hyperlink(escapeHTML(feedArray.get(i).getTitle()));
            hpl.setOnAction((event) -> openLink(eye));
            hpl.setWrapText(true);
            hpl.setMaxWidth(BOX_WIDTH);
            hpl.setPadding(new Insets(0, 0, 0, 4));

            //Add Description (Label)
            Label description = new Label(escapeHTML(feedArray.get(i).getDescription()));
            description.setWrapText(true);
            description.setMaxWidth(BOX_WIDTH);
            description.setPadding(new Insets(0, 0, 0, 6));

            //Add Image
            try {
                String urlString = scanDescription(feedArray.get(i).getDescription());
                if (urlString != null) {
                    URL url = new URL(urlString);
                    BufferedImage bf;
                    try {
                        bf = ImageIO.read(url);
                    } catch (Exception ex) {
                        addCard(description, hpl, null);
                        continue;
                    }
                    WritableImage wr = convertImg(bf);
                    ImageView img = new ImageView(wr);

                    img.setFitWidth(IMAGE_WIDTH);
                    img.setFitHeight(wr.getHeight() / (wr.getWidth() / (IMAGE_WIDTH)));
//                    if (wr.getHeight() < wr.getWidth()) {
//                        img.setFitWidth(IMAGE_WIDTH);
//                        img.setFitHeight(wr.getHeight() / (wr.getWidth() / (IMAGE_WIDTH)));
//                    } else {
//                        img.setFitHeight(IMAGE_HEIGHT);
//                        img.setFitWidth(wr.getWidth() / (wr.getHeight() / (IMAGE_HEIGHT)));
//                    }
                    img.setImage(wr);

                    //Add article to list WITH image
                    addCard(description, hpl, img);
                } else {
                    //Add article to list WITHOUT image
                    addCard(description, hpl, null);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    //Normal news article
    private void addCard(Label description, Hyperlink hyperlink, ImageView image) {
        hyperlink.getStyleClass().add("roboto");
        description.getStyleClass().add("roboto");
        VBox textVBox;
        VBox vBox;


        if (image == null) {
            textVBox = new VBox(hyperlink, description);
            textVBox.getStyleClass().setAll("text-padding");
            vBox = new VBox(textVBox);
//            vBox = new VBox(hyperlink, description);
        } else {

            textVBox = new VBox(hyperlink, description);
            textVBox.getStyleClass().setAll("text-padding");
            vBox = new VBox(image,textVBox);
//            vBox = new VBox(image, hyperlink, description);
        }
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(BOX_WIDTH);
        vBox.getStyleClass().setAll("news-card");
        VBox.setMargin(vBox, new Insets(10, 10, 10, 10));

        cardView.getChildren().add(vBox);
    }

    private void addCard(Pane pane) {
        HBox hBox;
        hBox = new HBox(pane);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(BOX_WIDTH);
        hBox.getStyleClass().setAll("news-card");
        VBox.setMargin(hBox, new Insets(10, 10, 10, 10));

        cardView.getChildren().add(hBox);
    }

    //This one adds the label first - for offline
    private void addCard(Hyperlink hyperlink, Label description)
    {
        hyperlink.getStyleClass().add("roboto");
        description.getStyleClass().add("roboto");
        VBox vBox = new VBox(description, hyperlink);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(BOX_WIDTH);
        vBox.getStyleClass().setAll("news-card");
        VBox.setMargin(vBox, new Insets(10, 10, 10, 10));

        cardView.getChildren().add(vBox);
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

    public VBox getCardView() {
        return cardView;
    }


}
