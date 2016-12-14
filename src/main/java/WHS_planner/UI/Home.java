package WHS_planner.UI;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.io.File;


class Home extends Pane {

    private HBox outsidePane = new HBox();
    private VBox insidePane = new VBox();

    Home(BorderPane calendar, Pane newsUI, ProgressBar progressBar) {


        //News
        ScrollPane newsScroll = new ScrollPane();
        newsScroll.setContent(newsUI);
        newsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        newsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        newsScroll.setFitToWidth(true);
        newsScroll.setStyle("-fx-focus-color: transparent;");
        newsScroll.getStyleClass().setAll("scroll-bar");
        newsScroll.getStylesheets().add("News" + File.separator + "NewsUI.css");
        newsScroll.setMinWidth(280);
        newsScroll.setMaxWidth(280);
        newsScroll.setPrefHeight(this.getPrefHeight());

        //Progress bar
        BorderPane barPane = new BorderPane();
        barPane.setCenter(progressBar);
        barPane.setMaxHeight(30);
        barPane.setPadding(new Insets(5, 0, 5, 0));

        //Calendar + add stuff to H/VBoxes
        insidePane.getChildren().addAll(calendar,barPane);
        outsidePane.getChildren().addAll(insidePane,newsScroll);

        //Resizing stuff
        calendar.prefHeightProperty().bind(insidePane.heightProperty());
        calendar.prefWidthProperty().bind(insidePane.widthProperty());

        barPane.prefHeightProperty().bind(insidePane.heightProperty());
        barPane.prefWidthProperty().bind(insidePane.widthProperty());

        VBox.setVgrow(calendar, Priority.ALWAYS);
        VBox.setVgrow(barPane, Priority.NEVER);

        insidePane.prefHeightProperty().bind(outsidePane.heightProperty());
        insidePane.prefWidthProperty().bind(outsidePane.widthProperty());

        newsScroll.prefHeightProperty().bind(outsidePane.heightProperty());
        newsScroll.prefWidthProperty().bind(outsidePane.widthProperty());

        HBox.setHgrow(newsScroll, Priority.NEVER);
        HBox.setHgrow(insidePane, Priority.ALWAYS);

        outsidePane.prefHeightProperty().bind(this.heightProperty());
        outsidePane.prefWidthProperty().bind(this.widthProperty());

        this.getChildren().setAll(outsidePane);
    }
}
