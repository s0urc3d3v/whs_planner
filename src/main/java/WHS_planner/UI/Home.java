package WHS_planner.UI;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


class Home extends BorderPane {

    private Pane newsUI;


    private Pane cal;
    private ScrollPane news;
    private Pane bar;


    Home(Pane calendar, Pane newsUI, ProgressBar schedule) {
        cal = calendar;


        this.newsUI = newsUI;
        ScrollPane newsScroll = new ScrollPane();
        newsScroll.setContent(newsUI);
        newsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        newsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        newsScroll.setFitToWidth(true);
//        newsScroll.setStyle("-fx-background-color: #FFFFFF;");
        newsScroll.setStyle("-fx-focus-color: transparent;");

        newsScroll.getStyleClass().setAll("scroll-bar");
        newsScroll.getStylesheets().add("/UI/NewsUI.css");
        news = newsScroll;


        BorderPane barPane = new BorderPane();
        barPane.setCenter(schedule);
        bar = barPane;
        initialize();
    }

    private void initialize() {
//        this.setPrefSize(640, 480);
        this.setPrefWidth(1100);
        //TODO ^
        this.setCenter(cal);


        news.setPrefWidth(280);
        news.setMaxWidth(280);
        news.setPrefHeight(this.getPrefHeight());
        this.setRight(news);


        setBottom(bar);
        bar.setMaxHeight(30);
        bar.setPadding(new Insets(5, 0, 5, 0));
    }




}
