package WHS_planner.Schedule;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;


public class Schedule extends Application
{
    Label[][] classes = new Label[6][9];

    Pane rootlayout;

    public static void main(String[] args)
    {
        launch(args);
    }


    public void start(Stage PrimaryStage)
    {
        PrimaryStage.setTitle("Schedule");

        try
        {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Schedule.class.getResource("schedule.fxml"));


            //error location not set fix it
            rootlayout = loader.load();

            Scene scene = new Scene(rootlayout);

            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }




}
