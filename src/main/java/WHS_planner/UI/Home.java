package WHS_planner.UI;

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
//        this.set
//        this.setPrefSize(640, 480);
//        //TODO ^
//        this.setCenter(cal);
//
//
//        setBottom(bar);
//        bar.setMaxHeight(30);
//        bar.setPadding(new Insets(5,0,5,0));
//        //Horizontal insets don't work...
//        news.setPrefWidth(280);
//        news.setMaxWidth(280);
//        news.setPrefHeight(this.getPrefHeight());
//        this.setRight(news);


    }


}
