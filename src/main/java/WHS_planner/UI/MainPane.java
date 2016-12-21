package WHS_planner.UI;

import WHS_planner.Calendar.Calendar;
import WHS_planner.News.ui.NewsUI;
import WHS_planner.Schedule.Schedule;
import WHS_planner.Schedule.ScheduleBlock;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;


public class MainPane extends Pane {

    private Pane navBar;
    private Pane content;
    private ArrayList<Pane> contentPanes;

    private VBox mainPane;


    private Schedule schedule;

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
        StackPane stackPane = new StackPane(content,createDrawer((JFXHamburger)navBar.getChildren().get(0),175,48));

        //Set the content the base pane to have the nav bar on top and content under it
        vBox.getChildren().setAll(navBar,stackPane);
        VBox.setVgrow(stackPane, Priority.ALWAYS);
        VBox.setVgrow(navBar, Priority.NEVER);

        return vBox;
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
        JFXDrawer drawer = new JFXDrawer();
        drawer.setDefaultDrawerSize(width);
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
        drawer.setOnMouseClicked(event -> {
            System.out.println(event.getSceneX());
            if (drawer.isShown()) {
                if (event.getSceneX() >= 175) {
                    drawer.setMouseTransparent(false);
                    hamburger.getAnimation().setRate(1); //Switches the transition between forward and backwards.
                    hamburger.getAnimation().play(); //Plays the transition
                }
            }else{
                drawer.setMouseTransparent(true);
                hamburger.getAnimation().setRate(-1);
                hamburger.getAnimation().play(); //Plays the transition
            }
        });

        return drawer;
    }

    private void generatePanes() {
        schedule = new Schedule();
        BorderPane calendar = new Calendar();
        NewsUI news = new NewsUI();
        //Pane meeting = new MeetingPane();
        Home homePane = new Home(calendar, news.getCardView(), schedule.getProgressBar());

        addPane(homePane);
        addPane((Pane) schedule.getPane());
        //addPane(meeting);


        content.getChildren().add(contentPanes.get(0)); //Sets home tab as default

    }


    public void resetSchedule() throws Exception
    {
        remPane((Pane)schedule.getPane());
        schedule = new Schedule();
        addPane((Pane) schedule.getPane(), 1);
        content.getChildren().clear();
        content.getChildren().add(contentPanes.get(1));
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

    
}
