package WHS_planner.UI;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


class Home extends BorderPane {


    Home(Pane calendar, Pane newsUI, ProgressBar schedule) {
        this.setPrefWidth(1100);
        this.setMinWidth(1100);
        //TODO ^

        //Calendar
        this.setCenter(calendar);


        //News
        ScrollPane newsScroll = new ScrollPane();
        newsScroll.setContent(newsUI);
        newsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        newsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        newsScroll.setFitToWidth(true);
        newsScroll.setStyle("-fx-focus-color: transparent;");
        newsScroll.getStyleClass().setAll("scroll-bar");
        newsScroll.getStylesheets().add("/UI/NewsUI.css");
        newsScroll.setPrefWidth(280);
        newsScroll.setMaxWidth(280);
        newsScroll.setPrefHeight(this.getPrefHeight());
        this.setRight(newsScroll);

        //Progress bar
        BorderPane barPane = new BorderPane();
        barPane.setCenter(schedule);
        setBottom(barPane);
        barPane.setMaxHeight(30);
        barPane.setPadding(new Insets(5, 0, 5, 0));

    }





}
