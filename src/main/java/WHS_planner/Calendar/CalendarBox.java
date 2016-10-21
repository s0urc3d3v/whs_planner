package WHS_planner.Calendar;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class CalendarBox extends Pane{

    public static final int CALENDAR_BOX_HEIGHT = 80, CALENDAR_BOX_WIDTH = 110;
    public static final int HOMEWORK = 0,TESTS = 1;
    private static final int NUMBER_OF_TASKLISTS = 2;

    private int date; //The date of the box
    private int week; //The week this box is in

    private ArrayList<ArrayList<Task>> tasks; //List of the lists of tasks
    private Pane mainPane; //The main pane
    private Map<String,Object> map; //A map of all the objects in the FXML

    public CalendarBox(int date, int week, boolean active){
        this.date = date; //This box's date
        this.week = week; //The week (row) this box is in
        this.tasks = new ArrayList<>(); //Used to hold lists of tasks (Ex. List of homeworks, list of tests, etc)

        //Creates and fills in tasks with correct amount of lists according to NUMBER_OF_TASKLISTS
        for (int taskListIndex = 0; taskListIndex < NUMBER_OF_TASKLISTS; taskListIndex++) {
            tasks.add(new ArrayList<>()); //Create a new list
        }

        FXMLLoader loader = new FXMLLoader(); //Create a new loader
        loader.setResources(ResourceBundle.getBundle("FontAwesome.fontawesome")); //Load the Font Awesome font into the loader
        loader.setController(new UIController()); //Set the UI controller of the loader
        loader.setLocation(getClass().getResource("/Calendar/calendarBoxV2.fxml")); //Set the path of the FXML file

        //Load in the FXML and set the map to be the list of all the objects in the FXML
        try {
            mainPane = loader.load();
            map = loader.getNamespace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Set up the calendar box
        initFXMLBox();

        //Make the button inactive if required
        if(!active){
            setInactive();
        }

        this.getStyleClass().add("box"); //Set the CSS style class to be box
    }

    //Used to get the number of tasks in a certain list
    public int getTaskCount(int listID){
        return tasks.get(listID).size();
    }


    public void update(){
        HBox iconContainer = (HBox) map.get("iconContainer"); //Get the iconContainer from the FXML

        ArrayList<Node> icons = new ArrayList<Node>(); //The nodes

        String[] iconsUnicode = new String[]{"\uf0f6","\uf00c"}; //File Icon, Check Icon

        for (int listID = 0; listID < tasks.size(); listID++) {
            if(getTaskCount(listID) != 0) {
                Label icon = new Label();
                icon.getStyleClass().add("icon");
                icon.setId("homework-icon");
                icon.setText(iconsUnicode[listID]); //File Icon

                JFXBadge badge = new JFXBadge(icon, Pos.TOP_RIGHT);
                badge.getStyleClass().add("icon-badge");
                badge.setText("" + getTaskCount(listID));
                icons.add(badge);
            }
        }

        iconContainer.getChildren().setAll(icons);
        this.getChildren().setAll(mainPane);
    }

    //Adds a task in a certain list based on the listID
    public void addTask(int listID, Task task){
        tasks.get(listID).add(task);
    }

    //Removes a task in a certain list based on the listID
    public void removeTask(int listID, Task task){
        tasks.get(listID).remove(task);
    }

    public int getWeek() {
        return week;
    }//Get the week this box is in (row)

    public int getDate() {
        return date;
    }//Get the date of this box

    //Get the button
    public JFXButton getButtonNode(){
        JFXButton button = (JFXButton)map.get("button");
        return button;
    }

    //Get the date Label
    public Label getDateLabel(){
        Label date = (Label)map.get("dateLabel");
        return date;
    }

    public void setInactive(){
        getButtonNode().setDisable(true);
        getDateLabel().setText("");
    }

    public void initFXMLBox(){
        String dateString = date + ""; //Creates a string version of the date value
        this.setId("calendar-box"); //Set the id of this box to be "calendar-box"
        getDateLabel().setText(dateString); //Set the dateLabel text = to the date

        //Set the size of the mainPane
        mainPane.prefWidthProperty().bind(this.widthProperty());
        mainPane.prefHeightProperty().bind(this.heightProperty());

        //Set the button size to be equal to the mainPane's size
        getButtonNode().prefWidthProperty().bind(mainPane.widthProperty());
        getButtonNode().prefHeightProperty().bind(mainPane.heightProperty());

        VBox vbox = (VBox)map.get("vbox");
        vbox.prefWidthProperty().bind(mainPane.widthProperty());
        vbox.prefHeightProperty().bind(mainPane.heightProperty());

        HBox iconContainer = (HBox) map.get("iconContainer");
        iconContainer.prefWidthProperty().bind(mainPane.widthProperty());
        iconContainer.prefHeightProperty().bind(vbox.heightProperty());

        //Initiate update sequence
        update();
    }
}
