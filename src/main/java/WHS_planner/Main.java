package WHS_planner;


//import WHS_planner.Core.MeetingFileHandler;
import WHS_planner.Calendar.CalendarBox;
import WHS_planner.UI.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

public class Main extends Application {


    /**
     * The main method of the program.
     * It initializes and runs the application!
     */
    public static void main(String[] args) {

        /*----------INITIALIZATION----------*/
        /*
        if (System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("os x")){
        }
        MeetingFileHandler.configFileHandler(); //This must be at the top of Main() for meeting import to work
        */

        PropertyConfigurator.configure("log4j.properties");

        /*----------START OF PROGRAM----------*/
        launch(args); //Runs the program
    }

    /**
     * This method is where JavaFX creates the UI and displays the window.
     */
    @Override
    public void start(Stage stage) throws Exception {

        MainPane mainPane = new MainPane(); //Create the mainPane (pane with all the content)

        Scene scene = new Scene(mainPane); //Put the mainPane into a scene

        //Binds the size of the mainPane to be equal to the scene
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        mainPane.prefHeightProperty().bind(scene.heightProperty());

        stage.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT*5+198); //Set the minimum height of the window
        stage.setMinWidth(CalendarBox.CALENDAR_BOX_MIN_WIDTH*7+90); //Set the minimum width of the window
        stage.setTitle("WHS Planner"); //Set the title of the window
        stage.setScene(scene); //Set the window (stage) to display things in the scene

        scene.getStylesheets().add("/Calendar/MainUI.css");

        stage.show(); //Display the window
    }
}