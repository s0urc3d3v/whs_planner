package WHS_planner.UI;

import WHS_planner.Calendar.CalendarYear;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class Home extends Pane {

    private JFXCheckBox checkBox;
    private HBox outsidePane = new HBox();
    private VBox insidePane = new VBox();
    private JFXProgressBar progressBar = new JFXProgressBar();
    private Timer progressbartimer;
    private Tooltip tooltip = new Tooltip();

    Home(CalendarYear calendar, Pane newsUI) {

        //Force initial timer update
        progressBar.setProgress(100);
        progressBar.setProgress(0);
        Platform.runLater(() -> {
            double d = 1.0 - progressVal();
            progressBar.setProgress(d);
            tooltip.setText("Time left: \n" + timeLeft() + " min");
        });
        //Timer updates (60 sec)
        progressbartimer = new Timer(60000, e -> Platform.runLater(() -> {
            double d = 1.0 - progressVal();
            progressBar.setProgress(d);
            //set Tooltip text
            tooltip.setText("Time left: \n" + timeLeft() + " min");
        }));
        progressbartimer.start();
        //Set checkbox to instantly refresh progressBar
        checkBox = calendar.getSchedule().getCheck();
        checkBox.setOnAction(e -> Platform.runLater(() -> {
            double d = 1.0 - progressVal();
            progressBar.setProgress(d);
            tooltip.setText("Time left: \n" + timeLeft() + " min");
        }));

        //Initialize NEWS
        ScrollPane newsScroll = new ScrollPane();
        newsScroll.setContent(newsUI);
        newsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        newsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //NEWS Style
        newsScroll.setStyle("-fx-background-color: transparent;");
        newsScroll.getStylesheets().add("News" + File.separator + "NewsUI.css");
        newsScroll.getStyleClass().setAll("scroll-bar");
        //NEWS Scaling
        newsScroll.setFitToWidth(true);
        newsScroll.setMinWidth(280);
        newsScroll.setMaxWidth(280);
        newsScroll.setPrefHeight(this.getPrefHeight());

        //Progress bar
        progressBar.prefWidthProperty().bind(insidePane.widthProperty());
        progressBar.setCursor(Cursor.HAND);
        progressBar.setTooltip(tooltip);
        progressBar.setScaleY(3);
        hackTooltipStartTiming(tooltip);

        insidePane.setPadding(new Insets(0, 0, 5, 5)); //top, right, bottom, left

        //Add Nodes to H/VBoxes
        insidePane.getChildren().addAll(calendar, progressBar);
        outsidePane.getChildren().addAll(insidePane,newsScroll);

        //Resizing stuff
        calendar.prefHeightProperty().bind(insidePane.heightProperty());
        calendar.prefWidthProperty().bind(insidePane.widthProperty());
        VBox.setVgrow(calendar, Priority.ALWAYS);
        VBox.setVgrow(progressBar, Priority.NEVER);
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

    private static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);
            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);
            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double progressVal() {

        int n = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(n == Calendar.SATURDAY || n == Calendar.SUNDAY)
        {
            return 1;
        }

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateS = df.format(date);
        int num = parseDate(dateS);
        double mod;
        //wednesday
        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) {
            if (num >= 450 && num < 495) {
                mod = (495 - num) / 45.0;
            } else if (num >= 495 && num < 535) {
                mod = (575 - num) / 40.0;
            } else if (num >= 535 && num <= 620) {
                mod = (620 - num) / 40.0;
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
        }
        //Bell2
        else if (checkBox.isSelected()) {
            if (num >= 450 && num < 501) {
                mod = (501 - num) / 56;
            } else if (num >= 501 && num < 558) {
                mod = (558 - num) / 62;
            } else if (num >= 558 && num < 593) {
                //Class meeting
                mod = (593 - num) / 30;
            } else if (num >= 593 && num < 650) {
                mod = (650 - num) / 57;
            } else if (num >= 650 && num < 741) {
                mod = (741 - num) / 91;
            } else if (num >= 741 && num <= 798) {
                mod = (798 - num) / 57;
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num) / 57;
            } else {
                mod = 1;
            }

            return 1;
        }
        //other days
        else {
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

    private int timeLeft() {
        int n = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(n == Calendar.SATURDAY || n == Calendar.SUNDAY)
        {
            return 0;
        }
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateS = df.format(date);
        int num = parseDate(dateS);
        double mod;
        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) {
            if (num >= 450 && num < 495) {
                mod = (495 - num);
            } else if (num >= 495 && num < 535) {
                mod = (575 - num);
            } else if (num >= 535 && num <= 620) {
                mod = (620 - num);
            } else if (num >= 575 && num < 620) {
                mod = (620 - num);
            } else if (num >= 620 && num < 700) {
                mod = (700 - num);
            } else if (num >= 700 && num < 745) {
                mod = (745 - num);
            } else if (num >= 745 && num <= 785) {
                mod = (785 - num);
            } else {
                mod = 1;
            }
        } else if (checkBox.isSelected()) {
            if (num >= 450 && num < 501) {
                mod = (501 - num);
            } else if (num >= 501 && num < 558) {
                mod = (558 - num);
            } else if (num >= 558 && num < 593) {
                //Class meeting
                mod = (593 - num);
            } else if (num >= 593 && num < 650) {
                mod = (650 - num);
            } else if (num >= 650 && num < 741) {
                mod = (741 - num);
            } else if (num >= 741 && num <= 798) {
                mod = (798 - num);
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num);
            } else {
                mod = 1;
            }

            return 1;
        } else {
            if (num >= 450 && num < 512) {
                mod = (512 - num);
            } else if (num >= 512 && num < 579) {
                mod = (579 - num);
            } else if (num >= 579 && num < 641) {
                mod = (641 - num);
            } else if (num >= 641 && num < 736) {
                mod = (736 - num);
            } else if (num >= 736 && num < 798) {
                mod = (798 - num);
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num);
            } else {
                mod = 0;
            }
        }
        return (int) mod;
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
