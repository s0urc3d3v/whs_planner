package WHS_planner.UI;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


class Home extends BorderPane {

    private Pane cal;
    private Pane news;
    private Pane bar;


    Home(Pane calendar, Pane newsUI, Pane schedule) {
        cal = calendar;
        news = newsUI;
        bar = schedule;
        initialize();
    }

    private void initialize() {
        this.setPrefSize(1280, 720);

        this.setCenter(cal);


        setBottom(new Label("Progress bar here!"));
        news.setPrefWidth(270);
        news.setPrefHeight(this.getPrefHeight());
        this.setRight(news);


    }


}
