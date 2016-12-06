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
    }

    private void initialize(Pane cal, Pane news, Pane bar) {


    }


}
