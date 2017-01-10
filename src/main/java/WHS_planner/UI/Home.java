package WHS_planner.UI;

import WHS_planner.Calendar.CalendarYear;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


class Home extends Pane implements ActionListener {

    private HBox outsidePane = new HBox();
    private VBox insidePane = new VBox();


    private JFXProgressBar progressBar = new JFXProgressBar();
    //    private ProgressBar progressBar = new ProgressBar();
    private Timer progressbartimer;


    Home(CalendarYear calendar, Pane newsUI/*, ProgressBar progressBar*/) {
        progressbartimer = new Timer(1000, this);
        progressbartimer.start();

        //News
        ScrollPane newsScroll = new ScrollPane();
        newsScroll.setContent(newsUI);
        newsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        newsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        newsScroll.setFitToWidth(true);
        newsScroll.setStyle("-fx-focus-color: transparent;");


        newsScroll.getStylesheets().add("News" + File.separator + "NewsUI.css");
        newsScroll.getStyleClass().setAll("scroll-bar");
        newsScroll.setMinWidth(280);
        newsScroll.setMaxWidth(280);
        newsScroll.setPrefHeight(this.getPrefHeight());

        //Progress bar
        BorderPane barPane = new BorderPane();
//        progressBar.getStylesheets().add("Schedule"+File.separator + "progressbar.css");
//        progressBar.getStyleClass().setAll("progress-bar");

        progressBar.setProgress(0);
        progressBar.getStylesheets().add("News" + File.separator + "NewsUI.css");
        progressBar.getStyleClass().setAll("orange-bar");

        progressBar.setStyle("-fx-accent: orange");
        progressBar.setStyle("-fx-accent: orange; ");
        System.out.println(progressBar.getStyle());

//        progressBar.setStyle("-fx-color: #FF9800");

        barPane.setCenter(progressBar);

        barPane.setMaxHeight(30);
//        barPane.setMaxWidth(600);
//        barPane.setPadding(new Insets(0, 40, 5, 40));
//        barPane.setPadding(new Insets(0,15,0,15));
        progressBar.setPadding(new Insets(0, 15, 0, 15));
        progressBar.prefWidthProperty().bind(barPane.widthProperty());


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

    private double progressVal() {
        Date date = new Date();

        DateFormat df = new SimpleDateFormat("HH:mm");

        String dateS = df.format(date);

        int num = parseDate(dateS);

        double mod;

        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) {
            if (num >= 450 && num < 495) {
                mod = (495 - num) / 45.0;
            } else if (num >= 495 && num < 575) {
                mod = (575 - num) / 80.0;
            } else if (num >= 575 && num < 620) {
                mod = (620 - num) / 45.0;
            } else if (num >= 620 && num < 700) {
                mod = (700 - num) / 80.0;
            } else if (num >= 700 && num < 745) {
                mod = (745 - num) / 45.0;
            } else if (num >= 745 && num <= 785) {
                mod = (785 - num) / 40.0;
            } else {
                mod = 1;
            }
        } else {
            if (num >= 450 && num < 512) {
                mod = (512 - num) / 62.0;
            } else if (num >= 512 && num < 579) {
                mod = (579 - num) / 67.0;
            } else if (num >= 579 && num < 641) {
                mod = (641 - num) / 62.0;
            } else if (num >= 641 && num < 736) {
                mod = (736 - num) / 95.0;
            } else if (num >= 736 && num < 798) {
                mod = (798 - num) / 62.0;
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num) / 57.0;
            } else {
                mod = 1;
            }
        }

        return mod;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Platform.runLater(() -> {
            double d = progressVal();
            d = 1.0 - d;
            progressBar.setProgress(d);
        });

    }

    private int parseDate(String date) {
        String hour = date.substring(0, date.indexOf(":"));
        String minute = date.substring(date.indexOf(":") + 1);

        int hr = Integer.parseInt(hour);
        int min = Integer.parseInt(minute);

        min += (hr * 60);

        return min;
    }


}
