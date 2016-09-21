package WHS_planner.Calendar;

import com.jfoenix.controls.JFXBadge;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class CalendarBox {
    private int date;
    private int dayOfTheWeek;
    private ArrayList<Task> homework;
    private ArrayList<Task> tests;
    private Pane calendarBoxPane;

    public CalendarBox(int date, int dayOfTheWeek, UIController controller){
        this.date = date;
        this.dayOfTheWeek = dayOfTheWeek;
        this.homework = new ArrayList<Task>();
        this.tests = new ArrayList<Task>();

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new UIController());
        loader.setResources(ResourceBundle.getBundle("FontAwesome.fontawesome"));
        loader.setLocation(getClass().getResource("/Calendar/calendarBoxV2.fxml"));

        try {//TODO Replace this with errorhandler
            calendarBoxPane = loader.load();
        }catch(Exception e){
            e.printStackTrace();
        }
        String dateString = date+"";
        calendarBoxPane.setId(dateString);
        Label label = (Label)calendarBoxPane.lookup("#date");
        label.setText(dateString);
        homework.add(null);//TODO remove these
        tests.add(null);//TODO remove these
        tests.add(null);
        update();
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
            icon.setText("\uf00c"); //File Icon

            JFXBadge badge = new JFXBadge(icon, Pos.TOP_RIGHT);
            badge.getStyleClass().add("icon-badge");
            badge.setText(""+getHomeworkCount());
            icons.add(badge);
        }

        if(getTestsCount() != 0){
            Label icon = new Label();
            icon.getStyleClass().add("icon");
            icon.setId("test-icon");
            icon.setText("\uf0f6"); //Check Icon

            JFXBadge badge = new JFXBadge(icon, Pos.TOP_RIGHT);
            badge.getStyleClass().add("icon-badge");
            badge.setText(""+getTestsCount());
            icons.add(badge);
        }
        iconContainer.getChildren().setAll(icons);
    }

    public void addHomework(Task task){
        homework.add(task);
    }

    public void addTest(Task task){
        tests.add(task);
    }
}
