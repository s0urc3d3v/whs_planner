package WHS_planner.UI;

import WHS_planner.Calendar.Calendar;
import WHS_planner.News.ui.NewsUI;
import WHS_planner.Schedule.Schedule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by geoffrey_wang on 11/22/16.
 */
public class MainPane extends Pane {

    private Pane navBar;
    private Pane content;
    private ArrayList<Pane> contentPanes;

    public MainPane(){
        navBar = loadNavBar(); //Loads the navBar from the FXML
        content = new Pane(); //Creates an empty main content pane
        contentPanes = new ArrayList<>(); //Makes an empty list for all the content panes
        generatePanes(); //Loads in all the different panes
        VBox mainPane = createPane();
        mainPane.prefWidthProperty().bind(this.widthProperty());
        mainPane.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().setAll(mainPane); //Set the main pane as the pane generated
    }

    /**
     * Loads in the NavBar from the FXML file.
     * @return NavBarPane
     */
    public Pane loadNavBar(){
        String location = "/UI/NavBar.fxml"; //Location of the FXML in the resources folder

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
    public VBox createPane(){
        VBox vBox = new VBox(); //Create a vBox for the base pane

        //Make a stack pane with the drawer and content in it
        StackPane stackPane = new StackPane(content,createDrawer((JFXHamburger)navBar.getChildren().get(0),200,48));

        //Set the content the base pane to have the nav bar on top and content under it
        vBox.getChildren().setAll(navBar,stackPane);
        vBox.setVgrow(stackPane, Priority.ALWAYS);
        vBox.setVgrow(navBar, Priority.NEVER);

        return vBox;
    }

    /**
     * Generates a drawer initiated by the hamburger, defined by the width and height
     * @param hamburger
     * @param width
     * @param buttonHeight
     * @return drawer
     */
    public JFXDrawer createDrawer(JFXHamburger hamburger, double width, double buttonHeight){
        //Put the buttons generated into a vBox
        VBox tabsVBox = new VBox(generateButtons(new String[]{"Calendar", "Schedule", "Today", "News"}, width, buttonHeight));

        //Set drawer preferences
        JFXDrawer drawer = new JFXDrawer();
        drawer.setSidePane(tabsVBox);
        drawer.setPickOnBounds(false);
        drawer.setMouseTransparent(true);

        //Hamburger animation
        HamburgerBackArrowBasicTransition burgerTransition = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTransition.setRate(-1);

        //Hamburger function to open an close the drawer
        hamburger.setOnMouseClicked(event -> {
            if (drawer.isShown()) {
                drawer.setMouseTransparent(true);
                burgerTransition.setRate(-1); //Switches the transition between forward and backwards.
                drawer.close();
            } else {
                drawer.setMouseTransparent(false);
                burgerTransition.setRate(1);
                drawer.open();
            }

            burgerTransition.play(); //Plays the transition
        });

        //More functions to oepn and close the drawer
        drawer.setOnMouseClicked(event -> {
            if (drawer.isShown()) {
                drawer.setMouseTransparent(false);
                burgerTransition.setRate(1); //Switches the transition between forward and backwards.
                burgerTransition.play(); //Plays the transition
            }else{
                drawer.setMouseTransparent(true);
                burgerTransition.setRate(-1);
                burgerTransition.play(); //Plays the transition

            }
        });

        return drawer;
    }

    public void generatePanes(){
        Schedule schedule = new Schedule();

        addPane(new Calendar(1,30));
        addPane((Pane)schedule.getPane());
        addPane((Pane)schedule.getdaypane());
        addPane(new NewsUI().getROOOOOOOT());
    }

    public Node[] generateButtons(String[] text, double width, double buttonHeight){
        JFXButton[] buttonArray = new JFXButton[text.length];
        for (int i = 0; i < text.length; i++) {
            JFXButton button = new JFXButton(text[i].toUpperCase());
            button.setPrefSize(width, buttonHeight);
            buttonArray[i] = button;

            setMouseClickedEvent(button, i);

        }
        return buttonArray;
    }

    public void setMouseClickedEvent(JFXButton button, final int id){
        button.setOnMouseClicked(event -> {
            System.out.println(contentPanes.get(id));
            if(!content.getChildren().contains(contentPanes.get(id))) {
                content.getChildren().clear();
                content.getChildren().add(contentPanes.get(id));
            }
        });
    }

    public void addPane(Pane pane){
        pane.prefHeightProperty().bind(content.heightProperty());
        pane.prefWidthProperty().bind(content.widthProperty());
        contentPanes.add(pane);
    }
}
