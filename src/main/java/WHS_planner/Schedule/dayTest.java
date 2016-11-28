package WHS_planner.Schedule;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by john_broderick on 11/28/16.
 */
public class dayTest extends Application
{

    @Override
    public void start(Stage PrimaryStage) throws Exception
    {
        Schedule schedule = new Schedule();
        PrimaryStage.setResizable(true);
        PrimaryStage.setMinHeight(520);
        PrimaryStage.setMinWidth(573);
        PrimaryStage.setScene(Schedule.day);
        PrimaryStage.show();
    }
}
