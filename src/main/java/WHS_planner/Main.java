package WHS_planner;

import WHS_planner.CoreUI.NavigationBar;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class Main extends Application
{
    public static NavigationBar bar;

    public static void main(String[] args)
    {
       /*if (System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("os x")){
        }
        MeetingFileHandler.configFileHandler(); //This must be at the top of Main() for meeting import to work
         */
       PropertyConfigurator.configure("log4j.properties");

       File keys = new File("Keys");

       if(!keys.exists())
       {
           keys.mkdir();
       }


       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        bar = new NavigationBar();
        bar.start(primaryStage);
    }
}


