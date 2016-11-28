package WHS_planner;

import WHS_planner.Core.IO;
import WHS_planner.Core.OnStart;
import WHS_planner.CoreUI.NavigationBar;
import WHS_planner.Util.MemoryCredentials;
import javafx.application.Application;
import javafx.stage.Stage;
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
       MemoryCredentials memoryCredentials = new MemoryCredentials(null, null, null);
       PropertyConfigurator.configure("log4j.properties");

       launch(args);
        IO io = new IO("src" + File.separator + "main" + File.separator + "resources"+ File.separator + "Core" + File.separator + "util.json");
        io.setFirstRunVar();
        OnStart onStart = new OnStart();
        onStart.getCreditials(memoryCredentials);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        bar = new NavigationBar();
        bar.start(primaryStage);
    }
}


