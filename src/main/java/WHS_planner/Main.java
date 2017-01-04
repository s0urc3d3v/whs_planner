package WHS_planner;


//import WHS_planner.Core.MeetingFileHandler;

import WHS_planner.Calendar.CalendarBox;
import WHS_planner.UI.MainPane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.Random;

public class Main extends Application {
    //ON first run move jfoenix to a place it can be referenced on a remote system
    private static String readKey = null;
    private static MainPane mainPane;

    public static String getXorKey()
    {
        if (readKey != null)
        {
            return readKey;
        }
        return null;
    }

    /**
     * The main method of the program.
     * It initializes and runs the application!
     */
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("log4j.properties");

       File keys = new File("Keys");

       if(!keys.exists())
       {
           keys.mkdir();
       }

       File encKey = new File("Keys" + File.separator + "xor.key");
       if (!encKey.exists()) {
           Random r = new Random();
           int key = r.nextInt();
           readKey = String.valueOf(key);
           try {
               BufferedWriter writer = new BufferedWriter(new FileWriter("Keys" + File.separator + "xor.key"));
               writer.write(String.valueOf(key));
               writer.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       else {
           try {
               BufferedReader reader = new BufferedReader(new FileReader("keys" + File.separator + "xor.key"));
               readKey = reader.readLine();
               reader.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
        try {
            launch(args);
        }
        catch (Exception e){
           e.printStackTrace();
        }
       }

    public static Object getMainPane() {
        return mainPane;
    }

    /**
     * This method is where JavaFX creates the UI and displays the window.
     */
    @Override
    public void start(Stage stage) throws Exception {

        mainPane = new MainPane(); //Create the mainPane (pane with all the content)

        Scene scene = new Scene(mainPane); //Put the mainPane into a scene

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.Q)
                {
                    stop();
                }
            }
        });


        //Binds the size of the mainPane to be equal to the scene
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        mainPane.prefHeightProperty().bind(scene.heightProperty());

        //Original (without HOME)
//        stage.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT*5+198); //Set the minimum height of the window
//        stage.setMinWidth(CalendarBox.CALENDAR_BOX_MIN_WIDTH*7+90); //Set the minimum width of the window

        //WITH HOME
        stage.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT*5+198); //Set the minimum height of the window
        stage.setMinWidth(CalendarBox.CALENDAR_BOX_MIN_WIDTH*7+90+280); //Set the minimum width of the window


        stage.setTitle("WHS Planner"); //Set the title of the window
        stage.setScene(scene); //Set the window (stage) to display things in the scene

        scene.getStylesheets().add("/Calendar/MainUI.css");
        scene.getStylesheets().add("/UI/Main.css");

        stage.show(); //Display the window

    }

    /**
     * Exits the program fully when it is closed. Put save functions here!
     */
    @Override
    public void stop(){
        mainPane.saveCalendar();
        System.exit(0);
    }
}