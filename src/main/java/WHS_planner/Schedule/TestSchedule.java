package WHS_planner.Schedule;

import WHS_planner.Main;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestSchedule extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage PrimaryStage) throws Exception
    {

        Schedule schedule = new Schedule();
        PrimaryStage.setResizable(true);
        PrimaryStage.setMinHeight(520);
        PrimaryStage.setMinWidth(573);
        PrimaryStage.setScene(Schedule.schedule);
        PrimaryStage.show();
    }
}
