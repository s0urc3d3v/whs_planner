package WHS_planner.Calendar;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by geoffrey_wang on 9/17/16.
 */
public class MainUI extends Application{

    private Calendar cal;
    @Override
    public void start(Stage stage) throws Exception {
//------------------------Calendar Pane Implementation---------------------------------------
        try {
            cal = new Calendar(0,new JFXButton("TESTSETFD"));
        }catch (Exception e){
            e.printStackTrace();
        }


        cal.setId("MainCalendar");

        Scene scene = new Scene(cal);
//-------------------------------------------------------------------------------------------

        cal.setStyle("-fx-background-color:#FFECB3;");
        //Set the stylesheet
        scene.getStylesheets().add("Calendar" + File.separator + "MainUI.css");

        //Final Stage initiation
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        cal.saveCalendar();
    }
}