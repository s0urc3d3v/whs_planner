package WHS_planner.UI;

import WHS_planner.Calendar.CalendarYear;
import WHS_planner.News.ui.NewsUI;
import WHS_planner.Schedule.Schedule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainPane extends StackPane {

    private final int HOME = 0;
    private final int SCHEDULE = 1;
    private final int CALENDAR = 2;
    private final int NEWS = 3;
    private final int MEETING = 4;

    private Pane navBar;
    private Pane content;
    private ArrayList<Pane> contentPanes;
    private JFXDrawer drawer;

    private VBox mainPane;
    private VBox cardView = new NewsUI().getCardView();

    private Schedule schedule;
    private CalendarYear calendar;

    private Home homePane;

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

    private void initiateDropDown(Button bigButton) {
        /*

           START Jesus code

         */
        bigButton.setText("\uf142");
        bigButton.setStyle("-fx-font-family: 'FontAwesome Regular'; -fx-font-size: 28px; -fx-text-fill: #FFFFFF;");
        Pane parent = (Pane)(bigButton.getParent());
        bigButton.prefHeightProperty().bind(parent.heightProperty());
        final StackPane backmanISGay = this;
        bigButton.setOnMouseClicked(event -> {
            VBox info = new VBox();

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
                    schedule.getControl().updateSchedule();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            JFXButton button2 = new JFXButton();
            button2.setText("      Logout of iPass");
            button2.setOnMouseClicked(event12 -> {
                try {
                    schedule.getControl().logout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            JFXButton button3 = new JFXButton();
            button3.setText("      Send Feedback");
            button3.setOnMouseClicked(event13 -> {
                try {
                    Runtime.getRuntime().exec(new String[]{"open", "-a", "Google Chrome", "https://goo.gl/forms/K5ieqVSterU8Jxmt1"});
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
                info.getChildren().add(new Label("Dev Alpha 1/12/17\n "));
                info.getChildren().add(peopleLabel);
                String[] names = new String[]{"Tyler Brient", "George Jiang", "Andrew Eggleston", "Geoffrey Wang", "Matthew Elbing", "Jack Bachman", "John Broderick", "Will Robinson", "Tzur Almog", "Alex Bell"};
                for (String name : names) {
                    info.getChildren().add(new Label(name));
                }
                info.setPadding(new Insets(10));
            });
//            info.getChildren().add(button0);
            info.getChildren().add(button1);
            info.getChildren().add(button2);
            info.getChildren().add(button3);
            info.getChildren().add(button4);
            info.setAlignment(Pos.TOP_LEFT);
            info.getStylesheets().addAll("UI" + File.separator + "dropDown.css");
//            button0.getStyleClass().setAll("list-button");
            button1.getStyleClass().setAll("list-button");
            button2.getStyleClass().setAll("list-button");
            button3.getStyleClass().setAll("list-button");
            button4.getStyleClass().setAll("list-button");
            info.setSpacing(0);
            info.setMinSize(200, 200 /*50 pixels multiplied by number of buttons*/);
            JFXDialog dialog = new JFXDialog(backmanISGay, info, JFXDialog.DialogTransition.CENTER, true);
            dialog.show();
            /*

                 END Jesus code

             */
        });
    }

    /**
     * Generates a drawer initiated by the hamburger, defined by the width and height
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
        drawer.setMouseTransparent(true);

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
        schedule = new Schedule();
        calendar = new CalendarYear();
        homePane = new Home(calendar, cardView);
        addPane(homePane);
        addPane((Pane) schedule.getPane());
        content.getChildren().add(contentPanes.get(0)); //Sets home tab as default
    }

    public void resetSchedule() throws Exception
    {
        remPane((Pane)schedule.getPane());
        schedule = new Schedule();
        addPane((Pane) schedule.getPane(), 1);
        drawer.getContent().clear();
        drawer.setSidePane(contentPanes.get(1));
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
}
