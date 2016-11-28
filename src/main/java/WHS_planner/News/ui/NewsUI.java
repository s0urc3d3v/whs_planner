package WHS_planner.News.ui;

import WHS_planner.News.html.HTMLScanner;
import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;
import WHS_planner.News.read.RSSFeedParser;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
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

    //TODO WIDTHLENGTH OF IS HARDCODED AHHH
    private double widthLength = 200;

    private URL url;
    private Scene scene;

    private BorderPane roooot = new BorderPane();
    private ScrollPane rooot = new ScrollPane();
    private JFXMasonryPane root = new JFXMasonryPane();

    public NewsUI() {
        roooot.setPrefSize(1280, 720);
        rooot.setPrefSize(1280, 720);

        roooot.setCenter(rooot);
        rooot.setContent(root);
        rooot.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rooot.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rooot.setFitToWidth(true);

//        root.setPrefSize(1280, 720);
        root.setPrefSize(rooot.getPrefWidth(), rooot.getPrefHeight());
        root.setHSpacing(10);
        root.setVSpacing(10);

//        root.setVSpacing(10);
        root.setCellHeight(200);
        //TODO^ ????????????????????????????


        root.setCellWidth(widthLength);
        init();
        System.out.println("init called");

        scene = new Scene(roooot);
        scene.getStylesheets().add(File.separator + "News" + File.separator + "ButtonStyle.css");
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        roooot.setPrefSize(1280, 720);
        rooot.setPrefSize(1280, 720);

        roooot.setCenter(rooot);
        rooot.setContent(root);
        rooot.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rooot.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rooot.setFitToWidth(true);

//        root.setPrefSize(1280, 720);
        root.setPrefSize(rooot.getPrefWidth(), rooot.getPrefHeight());
        root.setHSpacing(10);
        root.setVSpacing(10);

//        root.setVSpacing(10);
        root.setCellHeight(200);
        //TODO^ ????????????????????????????


        root.setCellWidth(widthLength);
        init();
        System.out.println("init called");

        scene = new Scene(roooot);
        scene.getStylesheets().add(File.separator + "News" + File.separator + "ButtonStyle.css");
        scene.getStylesheets().add(File.separator + "News" + File.separator + "BoxShadow.css");
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

        Long refreshStartTime = System.currentTimeMillis();

        //get new articles
        feedArray = parser.getNewArticles(onScreenMessages);

        //Loop through all NEW articles
        if (feedArray != null) {

            for (int i = 0; i < feedArray.size(); i++) {

                //Add Hyperlink
                final int eye = i;
                Hyperlink hpl = new Hyperlink(escapeHTML(feedArray.get(i).getTitle()));
                hpl.setOnAction((event) -> openLink(eye));
                hpl.setWrapText(true);
                hpl.setMaxWidth(widthLength);
                hpl.setPadding(new Insets(0, 3, 0, 4));

                //Add label
                Label description = new Label(escapeHTML(feedArray.get(i).getDescription()));
                description.setWrapText(true);
                description.setMaxWidth(widthLength);
                description.setPadding(new Insets(0, 3, 0, 6));

                //Add Image
                try {
                    //Timing Test
                    Long startTime = System.currentTimeMillis();

                    String urlString = HTMLScanner.scanDescription(feedArray.get(i).getDescription());
                    System.out.println(urlString);
                    if (urlString != null) {
                        System.out.println("There's an image.");
                        url = new URL(urlString);
                        BufferedImage bf;

                        try {
                            bf = ImageIO.read(url);
                        } catch (/*IOException ex*/ Exception ex) {
//                            ex.printStackTrace();
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
                    //print
                    System.out.println(System.currentTimeMillis() - startTime);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                onScreenMessages.add(feedArray.get(i));

            }
        }
        System.out.println("REFRESH FINISH: " + (System.currentTimeMillis() - refreshStartTime) + " ms");
        System.out.println();
        System.out.println();
    }


    public void init() {
        //TODO Scrolling and Spacing
        root.setStyle("-fx-background-color: #FFFFFF;");
//        root.setStyle("-fx-background-color: #e1e0df;");

        root.getChildren().clear();

        //Add button
        JFXButton refreshButton = new JFXButton("Refresh");
        refreshButton.getStyleClass().add("button-raised");
        refreshButton.setOnAction((event) -> updateFrame());
        VBox r = new VBox(refreshButton);
        root.getChildren().add(r);

        System.out.println(feedArray.size() + "GEORGEISSTUPID");
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
                //Timing Test
                Long startTime = System.currentTimeMillis();

                String urlString = HTMLScanner.scanDescription(feedArray.get(i).getDescription());
                System.out.println(urlString);
                if (urlString != null) {
                    System.out.println("There's an image.");
                    url = new URL(urlString);
                    BufferedImage bf;

                    try {
                        bf = ImageIO.read(url);
                    } catch (/*IOException ex*/ Exception ex) {
//                            ex.printStackTrace();
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
                //print
                System.out.println(System.currentTimeMillis() - startTime);
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
        double height = hpl.getHeight() + desc.getHeight();

        v.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 15, 0, 1, 2, 0);" + "-fx-background-color: white;");
//        root.setVSpacing(10);
//        root.setCellHeight(200);
        root.getChildren().add(v);
    }

    private void addCard(ImageView img, Hyperlink hpl, Label desc) {
        VBox v = new VBox(img, hpl, desc);
        System.out.println("mIT ADDED TYLER");
        v.setPrefWidth(widthLength);
        v.setMaxWidth(widthLength);
        double height = img.getFitHeight() + hpl.getHeight() + desc.getHeight();
//        v.setPrefHeight(img.getFitHeight()+hpl.getHeight()+desc.getHeight());
//        v.setMaxHeight(img.getFitHeight()+hpl.getHeight()+desc.getHeight());
        v.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 15, 0, 1, 2, 0);" + "-fx-background-color: white;");
//        root.setVSpacing(10);
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


}
