package WHS_planner.Calendar;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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

    }
}
