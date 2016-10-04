package WHS_planner.Calendar;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class CalendarBox extends Pane{

    private int date; //The date of the box
    private int week; //The week this box is in

    private ArrayList<Task> homework;
    private ArrayList<Task> tests;
    private Pane calendarBoxPane;

    public CalendarBox(int date, int week, UIController controller){
        this.date = date;
        this.week = week;
        this.homework = new ArrayList<Task>();
        this.tests = new ArrayList<Task>();

        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("FontAwesome.fontawesome"));

        if(date != 0) {
            loader.setController(new UIController());
            loader.setLocation(getClass().getResource("/Calendar/calendarBoxV2.fxml"));

            try {//TODO Replace this with errorhandler
                calendarBoxPane = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String dateString = date + "";
            this.setId("calendar-box");
            Label label = (Label) calendarBoxPane.lookup("#date");
            calendarBoxPane.setId(dateString);
            label.setText(dateString);
            tests.add(null);//TODO remove these
            tests.add(null);//TODO remove these
            update();
        }else{
            loader.setLocation(getClass().getResource("/Calendar/calendarBoxV2-empty.fxml"));
            try {//TODO Replace this with errorhandler
                calendarBoxPane = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.getChildren().setAll(calendarBoxPane);
        }

        this.getStyleClass().add("box");
    }

    public int getHomeworkCount(){
        return homework.size();
    }

    public int getTestsCount(){
        return tests.size();
    }

    public Pane getPane(){
        update();
        return calendarBoxPane;
    }

    public void update(){
        HBox iconContainer = (HBox)calendarBoxPane.lookup("#iconContainer");

        ArrayList<Node> icons = new ArrayList<Node>();

        if(getHomeworkCount() != 0){
            Label icon = new Label();
            icon.getStyleClass().add("icon");
            icon.setId("homework-icon");
            icon.setText("\uf0f6"); //File Icon

            JFXBadge badge = new JFXBadge(icon, Pos.TOP_RIGHT);
            badge.getStyleClass().add("icon-badge");
            badge.setText(""+getHomeworkCount());
            icons.add(badge);
        }

        if(getTestsCount() != 0){
            Label icon = new Label();
            icon.getStyleClass().add("icon");
            icon.setId("test-icon");
            icon.setText("\uf00c"); //Check Icon

            JFXBadge badge = new JFXBadge(icon, Pos.TOP_RIGHT);
            badge.getStyleClass().add("icon-badge");
            badge.setText(""+getTestsCount());
            icons.add(badge);
        }
        iconContainer.getChildren().setAll(icons);
        this.getChildren().setAll(calendarBoxPane);
    }

    public void addHomework(Task task){
        homework.add(task);
    }

    public void addTest(Task task){
        tests.add(task);
    }

    //TODO Remove this testing method
    public void removeHomework(){
        if(getHomeworkCount() != 0) {
            homework.remove(0);
        }
    }

    //TODO Remove this testing method
    public void removeTest(){
        if(getTestsCount() != 0) {
            tests.remove(0);
        }
    }

    public int getWeek() {
        return week;
    }

    public int getDate() {
        return date;
    }

    public JFXButton getButton(){//TODO MAKE IT NOT HARD CODE
        AnchorPane anchorPane = (AnchorPane)calendarBoxPane.getChildren().get(0);
        JFXButton button = (JFXButton)anchorPane.getChildren().get(0);
        return button;
    }
}
