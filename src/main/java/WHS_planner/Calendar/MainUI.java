package WHS_planner.Calendar;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by geoffrey_wang on 9/17/16.
 */
public class MainUI extends Application{

    //Days of the week
    String[] daysOfTheWeek = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    @Override
    public void start(Stage stage) throws Exception {
//------------------------Calendar Pane Implementation---------------------------------------
        Calendar cal = new Calendar(CalendarUtility.TUESDAY, 30);
        cal.setId("MainCalendar");

        Scene scene = new Scene(cal);
//-------------------------------------------------------------------------------------------

        cal.setStyle("-fx-background-color:#FFECB3;");
        //Set the stylesheet
        scene.getStylesheets().add("/Calendar/MainUI.css");

        //Final Stage initiation
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.show();
    }
}
