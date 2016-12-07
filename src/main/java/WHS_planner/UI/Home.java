package WHS_planner.UI;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


class Home extends BorderPane {

    private Pane newsUI;


    private Pane cal;
    private ScrollPane news;
    private Pane bar;
    private ScrollPane newsScroll;


    Home(Pane calendar, Pane newsUI, Pane schedule) {
        cal = calendar;


        this.newsUI = newsUI;
        newsScroll = new ScrollPane();
        newsScroll.setContent(newsUI);
        newsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        newsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        newsScroll.setFitToWidth(true);
        newsScroll.setStyle("-fx-background-color: #FFFFFF;");
        newsScroll.getStyleClass().setAll("scroll-bar");

//        newsScroll.prefWidthProperty().bind(this.widthProperty());
//        newsScroll.prefHeightProperty().bind(this.heightProperty());

        newsScroll.getStylesheets().add("/UI/NewsUI.css");


        news = newsScroll;
        bar = schedule;
        initialize();
    }

    private void initialize() {
//        this.set
        this.setPrefSize(640, 480);
        //TODO ^
        this.setCenter(cal);


        setBottom(bar);
        bar.setMaxHeight(30);
        bar.setPadding(new Insets(5, 0, 5, 0));
        //Horizontal insets don't work...
        news.setPrefWidth(280);
        news.setMaxWidth(280);
        news.setPrefHeight(this.getPrefHeight());
        this.setRight(news);
    }




}
