package WHS_planner;


//import WHS_planner.Core.MeetingFileHandler;
import WHS_planner.CoreUI.NavigationBar;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
       /*if (System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("os x")){
        }
        MeetingFileHandler.configFileHandler(); //This must be at the top of Main() for meeting import to work
        launch(args); */
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        NavigationBar bar = new NavigationBar();
        bar.start(primaryStage);
    }
}
