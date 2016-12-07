package WHS_planner.UI;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


class Home extends BorderPane {

    private Pane cal;
    private Pane news;
    private Pane bar;
    private AnchorPane anchor;


    Home(Pane calendar, Pane newsUI, Pane schedule) {
        cal = calendar;
        news = newsUI;
        bar = schedule;
        initialize();
    }

    private void initialize() {
        this.setPrefSize(1280, 720);
        ///TODO lmao
        this.setCenter(cal);


        setBottom(bar);
        bar.setMaxHeight(20);
        bar.setPadding(new Insets(100, 10, 100, 10));
        //Horizontal insets don't work...
        news.setPrefWidth(280);
        news.setMaxWidth(280);
        news.setPrefHeight(this.getPrefHeight());
        this.setRight(news);


    }


}
