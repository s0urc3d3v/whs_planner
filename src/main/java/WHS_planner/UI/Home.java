package WHS_planner.UI;

import WHS_planner.Calendar.CalendarYear;
import WHS_planner.Schedule.ParseCalendar;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ProgressBar;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

class Home extends Pane {

    private JFXCheckBox checkBox;
    private HBox outsidePane = new HBox();
    private VBox insidePane = new VBox();
//    private JFXProgressBar progressBar = new JFXProgressBar();
    private ProgressBar progressBar = new ProgressBar();
    private Timer progressbarTimer;
    private Tooltip tooltip = new Tooltip();

    private GlobalTime globalTime;
    private ParseCalendar pc = new ParseCalendar();
    private java.util.Calendar javaCalendar = java.util.Calendar.getInstance();
    private String currentClass;

    Home(CalendarYear calendar, Pane newsUI) {
        globalTime = new GlobalTime(calendar.getSchedule().getCheck());
        File day = new File("Documents" + File.separator + "DayArray.json");
        if(day.exists()) {
            pc.readData();
        }
        //pc.readData();

//        progressBar.prefWidthProperty().bind(insidePane.widthProperty());
        progressBar.setCursor(Cursor.HAND);
        progressBar.setTooltip(tooltip);
        progressBar.getStylesheets().add("UI" + File.separator + "Main.css");
        progressBar.getStyleClass().setAll("progress-bar");
//        progressBar.setScaleY(3);
        hackTooltipStartTiming(tooltip);
        //Force initial timer update
//        progressBar.setProgress(100);
//        progressBar.setProgress(0);
//                    String currentClass = calendar.getSchedule().getToday(globalTime.getLetterDay())[classIndex].getClassName();

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

        progressBar.prefWidthProperty().bind(insidePane.widthProperty());
//        VBox parent = (VBox)progressBar.getParent();
//        progressBar.setPrefWidth(parent.getWidth());
        Platform.runLater(() -> {
//            VBox parent = (VBox)progressBar.getParent();
//            progressBar.setPrefWidth(parent.getWidth());
            String today = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            double d = 1.0 - progressVal();
            progressBar.setProgress(d);
            int classIndex = globalTime.getClassIndex();
            if(day.exists() && day.length() > 0 && calendar.getSchedule().isLoggedIn()) {
                System.out.println("class index: "+ classIndex);
                System.out.println(pc.getDay(today));
                if (classIndex == -1 || pc.getDay(today).length() != 1) {
                    System.out.println("tooltip is null!");
                    progressBar.setTooltip(null);
                } else if (classIndex == -4) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Passing time" + "\nTime left: \n" + timeLeft() + " min");
                } else if (classIndex == -5) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Break" + "\nTime left: \n" + timeLeft() + " min");
                } else if (classIndex == -2) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Advisory" + "\nTime left: \n" + timeLeft() + " min");
                }
                else {
                    progressBar.setTooltip(tooltip);
//                System.out.println(globalTime.getLetterDay());
//                System.out.println(calendar.getSchedule().getToday(globalTime.getLetterDay()));
//                    System.out.println("IT DOESNT EVEN GO PAST THIS LINE OF CODE");
                    try {
                        currentClass = calendar.getSchedule().getToday(globalTime.getLetterDay())[classIndex].getClassName();
//                    System.out.println("Letter day: " + globalTime.getLetterDay());
//                    System.out.println("Class Index: " + classIndex);
//                    currentClass = calendar.getSchedule().getToday("G")[2].getClassName();
                        //gets schedule from calendar. gets array of user's blocks using letterday from globaltime. gets class name from current index
//                        System.out.println(currentClass);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
//                    System.out.println("it got past the line of code...");
                    if(currentClass != null) {
                        tooltip.setText(currentClass + "\nTime left: \n" + timeLeft() + " min");
//                        System.out.println("scenario 1, not null");
                    } else {
                        tooltip.setText("Time left: \n" + timeLeft() + " min");
//                        System.out.println("scnenario 2, null");
                    }
//                String currentClass = calendar.getSchedule().getToday(today)[classIndex].getClassName();
                }
            } else{
                tooltip.setText("Time left: \n" + timeLeft() + " min");
            }

            checkForSpecialDayTooltip(tooltip);
        });

        //Timer updates (60 sec)
        progressbarTimer = new Timer(60000, e -> Platform.runLater(() -> {
            String today = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            double d = 1.0 - progressVal();
            progressBar.setProgress(d);
            int classIndex = globalTime.getClassIndex();
            if (day.exists()&& day.length() > 0 && calendar.getSchedule().isLoggedIn()) {
                if (classIndex == -1 || pc.getDay(today).length() != 1) {
                    progressBar.setTooltip(null);
                } else if (classIndex == -4) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Passing time" + "\nTime left: \n" + timeLeft() + " min");
                } else if (classIndex == -5) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Break" + "\nTime left: \n" + timeLeft() + " min");
                } else if (classIndex == -2) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Advisory" + "\nTime left: \n" + timeLeft() + " min");
                } else {
                    progressBar.setTooltip(tooltip);
                    try {
                        currentClass = calendar.getSchedule().getToday(globalTime.getLetterDay())[classIndex].getClassName();
                    } catch (NullPointerException exception) {
                        exception.printStackTrace();
                    }
                    if (currentClass != null) {
                        tooltip.setText(currentClass + "\nTime left: \n" + timeLeft() + " min");
                    } else {
                        tooltip.setText("Time left: \n" + timeLeft() + " min");
                    }
                }
            } else {
                tooltip.setText("Time left: \n" + timeLeft() + " min");
            }

            checkForSpecialDayTooltip(tooltip);
//            VBox parent = (VBox)progressBar.getParent();
//            progressBar.setPrefWidth(parent.getWidth());
        }));
        progressbarTimer.start();
        //Set checkbox to instantly refresh progressBar
        checkBox = calendar.getSchedule().getCheck();
        globalTime = new GlobalTime(checkBox);
        checkBox.setOnAction(e -> Platform.runLater(() -> {
            String today = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            double d = 1.0 - progressVal();
            progressBar.setProgress(d);
            int classIndex = globalTime.getClassIndex();
            if (day.exists()&& day.length() > 0 && calendar.getSchedule().isLoggedIn()) {
                if (classIndex == -1 || pc.getDay(today).length() != 1) {
                    progressBar.setTooltip(null);
                } else if (classIndex == -4) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Passing time" + "\nTime left: \n" + timeLeft() + " min");
                } else if (classIndex == -5) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Break" + "\nTime left: \n" + timeLeft() + " min");
                } else if (classIndex == -2) {
                    progressBar.setTooltip(tooltip);
                    tooltip.setText("Advisory" + "\nTime left: \n" + timeLeft() + " min");
                } else {
                    progressBar.setTooltip(tooltip);
                    try {
                        currentClass = calendar.getSchedule().getToday(globalTime.getLetterDay())[classIndex].getClassName();
                    } catch (NullPointerException exception) {
                        exception.printStackTrace();
                    }
                    if (currentClass != null) {
                        tooltip.setText(currentClass + "\nTime left: \n" + timeLeft() + " min");
                    } else {
                        tooltip.setText("Time left: \n" + timeLeft() + " min");
                    }
                }
            } else {
                tooltip.setText("Time left: \n" + timeLeft() + " min");
            }

            checkForSpecialDayTooltip(tooltip);

//            VBox parent = (VBox)progressBar.getParent();
//            progressBar.setPrefWidth(parent.getWidth());
        }));
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
        if(n == Calendar.SATURDAY || n == Calendar.SUNDAY) {
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
                mod = (535 - num) / 40.0;
            } else if (num >= 535 && num < 620) {
                mod = (620 - num) / 85.0;
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
                mod = (501 - num) / 56.0;
            } else if (num >= 501 && num < 558) {
                mod = (558 - num) / 62.0;
            } else if (num >= 558 && num < 593) {
                //Class meeting
                mod = (593 - num) / 30.0;
            } else if (num >= 593 && num < 650) {
                mod = (650 - num) / 57.0;
            } else if (num >= 650 && num < 741) {
                mod = (741 - num) / 91.0;
            } else if (num >= 741 && num <= 798) {
                mod = (798 - num) / 57.0;
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num) / 57.0;
            } else {
                mod = 1;
            }
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
        System.out.println(num);
        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) {
            if (num >= 450 && num < 495) {
                mod = (495 - num);
            } else if (num >= 495 && num < 535) {
                mod = (535 - num);
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
        } else {
            System.out.println("yes, its a normal day");
            if (num >= 450 && num < 506) {
                mod = (506 - num);
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
        System.out.println("minleft: " + mod);
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

    private void checkForSpecialDayTooltip(Tooltip temp){

        Calendar now = Calendar.getInstance();

        ArrayList<String> bellTimesFile = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get("Documents" + File.separator + "BellTimes.txt"))) {
            stream.forEachOrdered(bellTimesFile::add);
        }catch (Exception e){
            e.printStackTrace();
        }

        while(bellTimesFile.size()>4) {
            if (now.get(Calendar.MONTH) == Integer.parseInt(bellTimesFile.get(0)) - 1 && now.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(bellTimesFile.get(1)) && now.get(Calendar.YEAR) == Integer.parseInt(bellTimesFile.get(2))) {
                temp.setText("Today is a special schedule, check 'Bell Schedule' for the updated info!");
                break;
            }else{
                int numberOfRows = Integer.parseInt(bellTimesFile.get(3));
                for (int i = 0; i < numberOfRows*2+4; i++) {
                    bellTimesFile.remove(0);
                }
            }
        }
    }
}
