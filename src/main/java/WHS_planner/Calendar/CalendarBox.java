package WHS_planner.Calendar;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class CalendarBox extends Pane{

    public static final int CALENDAR_BOX_MIN_HEIGHT = 80, CALENDAR_BOX_MIN_WIDTH = 110; //Constant that defines the min size of a CalendarBox
    public static final int HOMEWORK = 0,TESTS = 1; //List IDs (Default)
    private static final int NUMBER_OF_TASKLISTS = 2; //The default number of lists in a box
    private static final String[] ICONS_UNICODE = new String[]{"\uf0f6","\uf00c"}; //File Icon, Check Icon (Font UNICODE)


    private int date; //The date of the box
    private int week; //The week this box is in

    private ArrayList<ArrayList<Task>> tasks; //List of the lists of tasks
    private Pane mainPane; //The main pane
    private Map<String,Object> map; //A map of all the objects in the FXML
    private HBox taskBox;

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
    }

    /*-----METHODS-----*/

    //Initializes this box
    public void initFXMLBox(){
        String dateString = date + ""; //Creates a string version of the date value
        getDateLabel().setText(dateString); //Set the dateLabel text = to the date

        //Set the buttonClicked action
        getButtonNode().setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                update();
                Calendar calendar = (Calendar)this.getParent().getParent().getParent();
                calendar.update(week,date);
            }
        }));

        //Set the size of the mainPane
        mainPane.prefWidthProperty().bind(this.widthProperty());
        mainPane.prefHeightProperty().bind(this.heightProperty());

        //Set the button size to be equal to the mainPane's size
        getButtonNode().prefWidthProperty().bind(mainPane.widthProperty());
        getButtonNode().prefHeightProperty().bind(mainPane.heightProperty());

        //Set the size of a VBox to be equal to the mainPane's size
        VBox vbox = (VBox)map.get("vbox");
        vbox.prefWidthProperty().bind(mainPane.widthProperty());
        vbox.prefHeightProperty().bind(mainPane.heightProperty());

        //Set the iconContainer accordingly
        HBox iconContainer = (HBox) map.get("iconContainer");
        iconContainer.prefWidthProperty().bind(mainPane.widthProperty());
        iconContainer.prefHeightProperty().bind(vbox.heightProperty());

        this.getStyleClass().add("box"); //Set the CSS style class to be box
        this.getChildren().setAll(mainPane); //Set this pane to contain the mainPane
        this.setId("calendar-box"); //Set the id of this box to be "calendar-box"

        //Initiate update sequence
        update();
    }

    //Updates the iconContainer
    public void update(){
        ArrayList<Node> icons = new ArrayList<Node>(); //Create a list for all the icons

        //Loop through all the task lists and add icons according to the content
        for (int listID = 0; listID < tasks.size(); listID++) {
            if(getTaskCount(listID) != 0) { //Only add an icon if there is more than one task
                //Create the ICON
                Label icon = new Label();
                icon.getStyleClass().add("icon");
                icon.setId("icon");
                icon.setText(ICONS_UNICODE[listID]);

                //Create the badge on the Label
                JFXBadge badge = new JFXBadge(icon, Pos.TOP_RIGHT);
                badge.getStyleClass().add("icon-badge");
//                badge.getChildren().get(0).getStyleClass().setAll("testsefd");
                badge.setText("" + getTaskCount(listID)); //Set the badge number
                icons.add(badge);
            }
        }

        HBox iconContainer = (HBox) map.get("iconContainer"); //Get the iconContainer from the FXML
        iconContainer.getChildren().setAll(icons); //Add all the "icons" into "iconContainer"
    }

    //If button is not in the right month, set them to be unclickable and remove the date
    public void setInactive(){
        getButtonNode().setDisable(true);
        getDateLabel().setText("");
    }

    //Create a taskBox
    public Node getTaskBox(ReadOnlyDoubleProperty widthProperty){
        //If there is no taskBox create one
        if(taskBox == null) {
            FXMLLoader loader = new FXMLLoader(); //Create a new FXML Loader
            loader.setLocation(getClass().getResource("/Calendar/taskBox.fxml")); //Set location of taskbox FXML file

            taskBox = new HBox(); //Creates a return taskbox

            try {
                taskBox = loader.load(); //Load from FXML
                taskBox.prefWidthProperty().bind(widthProperty); //Set the width of the taskbox to be the same as the width passed in

                //Get the JFXTextField and set the width to grow
                JFXTextField textBox = (JFXTextField) taskBox.getChildren().get(0);
                taskBox.setHgrow(textBox, Priority.ALWAYS);

                //Set pressing enter to clear the box text
                textBox.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        String textBoxText = textBox.getText();
                        if (textBoxText.trim().length() > 0){
                            if(textBoxText.contains("test")) {
                                addTask(TESTS, new Task("English","", textBoxText));
                            }else{
                                addTask(HOMEWORK, new Task("English","", textBoxText));
                            }
                            update();
                        }
                        textBox.clear();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return taskBox; //return the created taskbox
    }

    /*-----ID RELATED-----*/
    //Get the date of this box
    public int getDate() {
        return date;
    }

    //Get the week this box is in (row)
    public int getWeek() {
        return week;
    }

    /*-----TASK RELATED-----*/
    //Used to get the number of tasks in a certain list
    public int getTaskCount(int listID){
        return tasks.get(listID).size();
    }

    //Adds a task in a certain list based on the listID
    public void addTask(int listID, Task task){
        tasks.get(listID).add(task);
    }

    //Removes a task in a certain list based on the listID
    public void removeTask(int listID, Task task){
        tasks.get(listID).remove(task);
    }

    /*-----NODE RELATED-----*/
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
}
