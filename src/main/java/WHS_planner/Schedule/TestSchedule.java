package WHS_planner.Schedule;

import WHS_planner.Main;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class TestSchedule extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage PrimaryStage) throws Exception
    {


        File keys = new File("Keys");

        if(!keys.exists())
        {
            keys.mkdir();
        }

        Schedule schedule = new Schedule();
        PrimaryStage.setResizable(true);
        PrimaryStage.setMinHeight(520);
        PrimaryStage.setMinWidth(573);
        PrimaryStage.setScene(Schedule.schedule);
        PrimaryStage.show();
    }
}
