package WHS_planner.UI;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.ArrayList;


public class Home extends ContentPane {

    HBox outsidePane = new HBox();
    VBox insidePane = new VBox();

    public Home(ContentPane calendar, Pane newsUI, ProgressBar progressBar) {



        //News
        ScrollPane newsScroll = new ScrollPane();
        newsScroll.setContent(newsUI);
        newsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        newsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        newsScroll.setFitToWidth(true);
        newsScroll.setStyle("-fx-focus-color: transparent;");
        newsScroll.getStyleClass().setAll("scroll-bar");
        newsScroll.getStylesheets().add("/UI/NewsUI.css");
        newsScroll.setMinWidth(280);
        newsScroll.setMaxWidth(280);
        newsScroll.setPrefHeight(this.getPrefHeight());

        //Progress bar
        BorderPane barPane = new BorderPane();
        barPane.setCenter(progressBar);
        barPane.setMaxHeight(30);
        barPane.setPadding(new Insets(5, 0, 5, 0));

        //Calendar
        insidePane.getChildren().addAll(calendar,barPane);
        outsidePane.getChildren().addAll(insidePane,newsScroll);

        calendar.prefHeightProperty().bind(insidePane.heightProperty());
        barPane.prefHeightProperty().bind(insidePane.heightProperty());
        insidePane.setVgrow(calendar, Priority.ALWAYS);
        insidePane.setVgrow(barPane, Priority.NEVER);

        calendar.prefWidthProperty().bind(insidePane.widthProperty());
        barPane.prefWidthProperty().bind(insidePane.widthProperty());

        insidePane.prefHeightProperty().bind(outsidePane.heightProperty());
        insidePane.prefWidthProperty().bind(outsidePane.widthProperty());

        newsScroll.prefHeightProperty().bind(outsidePane.heightProperty());
        newsScroll.prefWidthProperty().bind(outsidePane.widthProperty());

        outsidePane.setHgrow(newsScroll, Priority.NEVER);
        outsidePane.setHgrow(insidePane, Priority.ALWAYS);

        outsidePane.prefHeightProperty().bind(this.heightProperty());
        outsidePane.prefWidthProperty().bind(this.widthProperty());

        this.getChildren().setAll(outsidePane);
    }


    @Override
    public String getName() {
        return "Home";
    }
}
