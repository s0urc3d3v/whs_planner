package WHS_planner.UI;

import WHS_planner.Calendar.CalendarYear;
import WHS_planner.News.ui.NewsUI;
import WHS_planner.Schedule.Schedule;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Stream;


public class MainPane extends StackPane {

//    private final int HOME = 0;
//    private final int SCHEDULE = 1;
//    private final int CALENDAR = 2;
//    private final int NEWS = 3;
//    private final int MEETING = 4;
    private JFXCheckBox bell2Check = new JFXCheckBox();
    private Pane navBar;
    private Pane content;
    private ArrayList<Pane> contentPanes;
    private JFXDrawer drawer;
    private VBox mainPane;
//    private VBox cardView = new NewsUI().getCardView();
    private Schedule schedule;
    private CalendarYear calendar;

//    private Home homePane;

    public MainPane(){
        navBar = loadNavBar(); //Loads the navBar from the FXML
        navBar.getStyleClass().setAll("navBar");
        navBar.getChildren().get(0).getStyleClass().setAll("jfx-hamburger-icon");
        navBar.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 15, 0, 1, 2, 0);");
        content = new Pane(); //Creates an empty main content pane
        contentPanes = new ArrayList<>(); //Makes an empty list for all the content panes
        generatePanes(); //Loads in all the different panes
        mainPane = createPane();
        mainPane.prefWidthProperty().bind(this.widthProperty());
        mainPane.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().setAll(mainPane); //Set the main pane as the pane generated
    }

    /**
     * Loads in the NavBar from the FXML file.
     * @return NavBarPane
     */
    private Pane loadNavBar() {
        String location = "/UI/NavBar.fxml"; //Location of the FXML in the resources folder //TODO make absolute


        //Tries to load in the FXML and if it fails it returns an error message
        try {
            return FXMLLoader.load(getClass().getResource(location));
        } catch (IOException e) {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * location: " + location);
            System.out.println("  * " + e);
            System.out.println("----------------------------------------\n");
        }

        return null; //Return nothing if it errors out
    }

    /**
     * Generates the Pane from all the data given
     * @return vBox
     */
    private VBox createPane() {
        VBox vBox = new VBox(); //Create a vBox for the base pane

        //Make a stack pane with the drawer and content in it
        StackPane stackPane = new StackPane(content,createDrawer((JFXHamburger)navBar.getChildren().get(0),1440,48));

        initiateDropDown((Button)navBar.getChildren().get(1));

        //Set the content the base pane to have the nav bar on top and content under it
        vBox.getChildren().setAll(navBar,stackPane);
        VBox.setVgrow(stackPane, Priority.ALWAYS);
        VBox.setVgrow(navBar, Priority.NEVER);

        return vBox;
    }

//    public JFXCheckBox getCheck() {
//        return bell2Check;
//    }

    private void initiateDropDown(Button bigButton) {
        /*

           START Jesus code

         */
        bigButton.setText("\uf142");
        bigButton.setCursor(Cursor.HAND);
        bigButton.setStyle("-fx-font-family: 'FontAwesome Regular'; -fx-font-size: 28px; -fx-text-fill: #FFFFFF;");
        Pane parent = (Pane)(bigButton.getParent());
        bigButton.prefHeightProperty().bind(parent.heightProperty());
        final StackPane backmanISGay = this;
        bigButton.setOnMouseClicked(event -> {
            VBox info = new VBox();
            JFXButton button0 = new JFXButton();
            button0.setText("      Show Bell Schedule");
            button0.setOnMouseClicked(event1 -> {
                info.setMinSize(0, 0);
                info.getChildren().clear();
                info.getChildren().add(getBellSchedulePane());
                info.getStyleClass().setAll("large-text");
            });
//            JFXButton button0 = new JFXButton();
//            button0.setText("      Delete Calendar Data");
//            button0.setOnMouseClicked(event0 -> {
//                System.out.println("delete calendar data pressed");
//                calendar.deleteCalendarData();
//            });
            JFXButton button1 = new JFXButton();
            button1.setText("      Reset Schedule");
            button1.setOnMouseClicked(event1 -> {
                try {
                    schedule.getScheduleControl().updateSchedule();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            JFXButton button2 = new JFXButton();
            button2.setText("      Logout of iPass");
            button2.setOnMouseClicked(event12 -> {
                try {
                    schedule.getScheduleControl().logout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            JFXButton button3 = new JFXButton();
            button3.setText("      Send Feedback");
            button3.setOnMouseClicked(event13 -> {
                try {
                    Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", "https://goo.gl/forms/KSCFGXldhE4EBytp1"});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            JFXButton button4 = new JFXButton();
            button4.setText("      About");
            button4.setOnMouseClicked(event14 -> {
                info.getChildren().clear();
                Label versionLabel = new Label("Version:");
                versionLabel.setUnderline(true);
                Label peopleLabel = new Label("Collaborators:");
                peopleLabel.setUnderline(true);

                info.getChildren().add(new Label("Created in HACS under the guidance of Mr.Hopps!\n "));

                info.getChildren().add(versionLabel);
                info.getChildren().add(new Label("1.0.3\n "));
                info.getChildren().add(peopleLabel);
                String[] names = new String[]{
                        "Tyler Brient - UI Master, Bug Squasher",
                        "George Jiang - UX, News, Bug Finder",
                        "Andrew Eggleston - Yelled at Tyler",
                        "Geoffrey Wang - UI Master, Calendar, T-Rex :)",
                        "Matthew Elbing - Backend, Project Lead",
                        "Jack Bachman - Backend, Github",
                        "John Broderick - Schedule, Bug Creator",
                        "Will Robison - HTML, Piano Tiles 2",
                        "Tzur Almog - Calendar",
                };
                for (String name : names) {
                    info.getChildren().add(new Label(name));
                }
                info.getStyleClass().setAll("large-text");
                info.setPadding(new Insets(10));
            });

//            HBox button5 = new HBox();
            bell2Check.setCursor(Cursor.HAND);
            bell2Check.setText("Bell 2");
//            JFXCheckBox bell2Check = new JFXCheckBox();
//            Label bell2Label = new Label();
//            bell2Label.setText("Bell 2");
//            button5.getChildren().addAll(bell2Check/*, bell2Label*/);
//            button5.setPrefSize(200, 50);
//            button5.setAlignment(Pos.CENTER_LEFT);
//            button5.setPadding(new Insets(15, 0, 10, 20)); //top right bottom left
            bell2Check.setTranslateX(10);
//            bell2Label.setTranslateX(10);
            bell2Check.setAlignment(Pos.CENTER_LEFT);

//            info.getChildren().add(button0);

            info.getChildren().addAll(button0, button1, button2, button3, button4, bell2Check);

            info.setAlignment(Pos.TOP_LEFT);
            info.getStylesheets().addAll("UI" + File.separator + "dropDown.css");
            button0.setCursor(Cursor.HAND);
            button1.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            button3.setCursor(Cursor.HAND);
            button4.setCursor(Cursor.HAND);
//            button5.setCursor(Cursor.HAND);
            button0.getStyleClass().setAll("list-button");
            button1.getStyleClass().setAll("list-button");
            button2.getStyleClass().setAll("list-button");
            button3.getStyleClass().setAll("list-button");
            button4.getStyleClass().setAll("list-button");
//            button5.getStyleClass().setAll("label-button");
            bell2Check.getStyleClass().setAll("label-button");
            bell2Check.setPrefSize(200,50);
//            System.out.println(bell2Check.getLabelPadding());
            info.setSpacing(0);
            info.setMinSize(200, 300);
            JFXDialog dialog = new JFXDialog(backmanISGay, info, JFXDialog.DialogTransition.CENTER, true);
            dialog.show();

            /*

                 END Jesus code

             */
        });


    }

    /**
     * Generates a drawer initiated by the hamburger, defined by the width and height
     * @param hamburger
     * @param width
     * @param buttonHeight
     * @return drawer
     */
    private JFXDrawer createDrawer(JFXHamburger hamburger, double width, double buttonHeight) {
        //Put the buttons generated into a vBox
//        ScheduleBlock[] scheduleBlocks = new ScheduleBlock[7]; //TODO: Get from schedule
//        VBox tabsVBox = new VBox();
//        for (int i = 0; i < scheduleBlocks.length; i++) {
//            Label l = new Label();
//            l.setPadding(new Insets(20, 5, 20, 5));
//            l.setText(scheduleBlocks[i].getClassName());
//            tabsVBox.getChildren().add(l);
//        }
//        generateButtons(new String[]{"Home", "Schedule"}, width, buttonHeight);

        //Set drawer preferences
        drawer = new JFXDrawer();
        drawer.setDefaultDrawerSize(width);
        drawer.setSidePane(contentPanes.get(1));
//        drawer.setSidePane(tabsVBox);
        drawer.setPickOnBounds(false);
        drawer.setMouseTransparent(true);                                                //vertical, higher = lower
        drawer.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 15, 0, 1, 5, 0);");

        //Hamburger animation
        hamburger.setAnimation(new HamburgerBackArrowBasicTransition(hamburger));
        hamburger.getAnimation().setRate(-1);

        //Hamburger function to open an close the drawer
        hamburger.setOnMouseClicked(event -> {
            if (drawer.isShown()) {
                drawer.setMouseTransparent(true);
                hamburger.getAnimation().setRate(-1); //Switches the transition between forward and backwards.
                drawer.close();
            } else {
                drawer.setMouseTransparent(false);
                hamburger.getAnimation().setRate(1);
                drawer.open();
            }
            hamburger.getAnimation().play(); //Plays the transition
        });

        //More functions to open and close the drawer

        return drawer;
    }

    private void generatePanes() {
        schedule = new Schedule(bell2Check);
        calendar = new CalendarYear(schedule);
        //calender has schedule and bell2check

//        NewsUI news = null;
//        try {
          NewsUI news = new NewsUI();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        //Pane meeting = new MeetingPane();
        Home homePane = new Home(calendar, news.getCardView());
        addPane(homePane);
        addPane((Pane) schedule.getPane());
        //addPane(meeting);

        content.getChildren().add(contentPanes.get(0)); //Sets home tab as default

    }


    public void resetSchedule() throws Exception {
        remPane((Pane)schedule.getPane());
        schedule = new Schedule(bell2Check);
        addPane((Pane) schedule.getPane(), 1);
        drawer.getContent().clear();
        drawer.setSidePane(contentPanes.get(1));
        calendar.setSchedule(schedule);

    }

    private Node[] generateButtons(String[] text, double width, double buttonHeight) {
        JFXButton[] buttonArray = new JFXButton[text.length];
        for (int i = 0; i < text.length; i++) {
            JFXButton button = new JFXButton(text[i].toUpperCase());
            button.setPrefSize(width, buttonHeight);
            buttonArray[i] = button;
            setMouseClickedEvent(button, i);
        }
        return buttonArray;
    }

    private void setMouseClickedEvent(JFXButton button, final int id) {
        button.setOnMouseClicked(event -> {
            if(!content.getChildren().contains(contentPanes.get(id))) {
                content.getChildren().clear();
                content.getChildren().add(contentPanes.get(id));
            }
            JFXDrawer drawer = (JFXDrawer)button.getParent().getParent().getParent();
            JFXHamburger hamburger =  (JFXHamburger)navBar.getChildren().get(0);

            closeDrawer(drawer, hamburger);
        });
    }

    private void addPane(Pane pane) {
        pane.prefHeightProperty().bind(content.heightProperty());
        pane.prefWidthProperty().bind(content.widthProperty());
        contentPanes.add(pane);
    }

    private void addPane(Pane pane, int i) {
        pane.prefHeightProperty().bind(content.heightProperty());
        pane.prefWidthProperty().bind(content.widthProperty());
        contentPanes.add(i, pane);
    }

    private void remPane(Pane pane)
    {
        contentPanes.remove(pane);
    }

    private void closeDrawer(JFXDrawer drawer, JFXHamburger hamburg) {
        drawer.setMouseTransparent(true);
        hamburg.getAnimation().setRate(-1); //Switches the transition between forward and backwards.
        hamburg.getAnimation().play(); //Plays the transition
        drawer.close();
    }

    public Schedule getSchedule()
    {
        return schedule;
    }

    public void saveCalendar(){
        calendar.saveCalendar();
    }

    private Pane getBellSchedulePane(){
        Calendar now = Calendar.getInstance();
//        //Testing
//        now.set(Calendar.DATE, 1);
//        now.set(Calendar.MONTH, 1);
//        now.set(Calendar.YEAR, 2017);

        String[] times;
        String[] blocks;

        if(now.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY){
            times = new String[]{"7:30-8:10","8:15-8:55","9:05-9:30","9:35-10:15", "10:20-10:50","10:42-11:12","11:05-11:35","11:40-12:20","12:25-1:05"};
            blocks = new String[]{"Block 1: ","Block 2: ","Advisory: ","Block 3: ","1st Lunch: ","2nd Lunch: ","3rd Lunch: ","Block 5: ","Block 6: "};

        } else if (bell2Check.isSelected()) {
            times = new String[]{"7:30-8:21", "8:26-9:18", "9:58-10:50", "10:55-11:25", "11:22-11:52", "11:51-12:21", "12:26-1:18", "1:23-2:15"};
            blocks = new String[]{"Block 1: ", "Block 2: ", "Class Meeting: ", "Block 3: ", "1st Lunch: ", "2nd Lunch: ", "3rd Lunch: ", "Block 5: ", "Block 6: "};
        } else {
            times = new String[]{"7:30-8:26","8:31-9:28","9:38-10:35","10:40-11:10","11:10-11:40","11:41-12:11","12:16-1:13","1:18-2:15"};
            blocks = new String[]{"Block 1: ","Block 2: ","Block 3: ","1st Lunch: ","2nd Lunch: ","3rd Lunch: ","Block 5: ","Block 6: "};
        }

        ArrayList<String> bellTimesFile = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get("Documents" + File.separator + "BellTimes.txt"))) {
            stream.forEachOrdered(bellTimesFile::add);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            while(bellTimesFile.size()>4) {
                if (now.get(Calendar.MONTH) == Integer.parseInt(bellTimesFile.get(0)) - 1 && now.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(bellTimesFile.get(1)) && now.get(Calendar.YEAR) == Integer.parseInt(bellTimesFile.get(2))) {
                    int numberOfRows = Integer.parseInt(bellTimesFile.get(3));
                    blocks = new String[numberOfRows];
                    times = new String[numberOfRows];
                    for (int i = 0; i < numberOfRows; i++) {
                        blocks[i] = bellTimesFile.get(i + 4);
                    }
                    for (int i = 0; i < numberOfRows; i++) {
                        times[i] = bellTimesFile.get(i + 4 + numberOfRows);
                    }
                    break;
                }else{
                    int numberOfRows = Integer.parseInt(bellTimesFile.get(3));
                    for (int i = 0; i < numberOfRows*2+4; i++) {
                        bellTimesFile.remove(0);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        GridPane returnPane = new GridPane();
        for (int i = 0; i < times.length; i++) {
            returnPane.add(new Label(blocks[i]),0,i);
            returnPane.add(new Label(times[i]),1,i);
        }
        returnPane.setHgap(10);
        returnPane.setPadding(new Insets(10));
        return returnPane;
    }
}