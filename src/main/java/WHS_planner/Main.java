package WHS_planner;


//import WHS_planner.Core.MeetingFileHandler;

import WHS_planner.Calendar.CalendarBox;
import WHS_planner.UI.MainPane;
import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

public class
Main extends Application {
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
        PrintStream console = System.err;

        File file = new File("err.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);

        System.setProperty("http.agent", "Chrome");
//        PropertyConfigurator.configure("log4j.properties");

       File keys = new File("Keys");

       if(!keys.exists())
       {
           keys.mkdir();
       }
        File documents = new File("Documents");

        if(!documents.exists())
        {
            documents.mkdir();
        }

        for (int i = 0; i < 12; i++)
        {
            try
            {
                File f = new File("Documents"+File.separator+i+"CalendarHolder.json");
                f.createNewFile();
            }
            catch(Exception e)
            {
                System.out.println("Error creating holder files...");
            }
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

        File saveFile = new File("Documents" + File.separator + "BellTimes.txt");
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            URL website = new URL("https://dl.dropboxusercontent.com/s/a8e3qfwgbfbi0qc/BellTimes.txt?dl=0");
            InputStream in = website.openStream();
            if (!(in == null)) {
                Files.copy(in, Paths.get("Documents" + File.separator + "BellTimes.txt"), StandardCopyOption.REPLACE_EXISTING);
                in.close();
            }
        }catch(Exception e){
            e.printStackTrace();
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
//        mainPane.setCache(true);
//        mainPane.setCacheShape(true);
//        mainPane.setCacheHint(CacheHint.SPEED);


        //Binds the size of the mainPane to be equal to the scene
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        mainPane.prefHeightProperty().bind(scene.heightProperty());

        //Original (without HOME)
//        stage.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT*5+198); //Set the minimum height of the window
//        stage.setMinWidth(CalendarBox.CALENDAR_BOX_MIN_WIDTH*7+90); //Set the minimum width of the window

        //WITH HOME
        stage.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT * 5 + 198 + 110); //Set the minimum height of the window
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
        new File("Keys/ipass.key").delete();

        System.exit(0);
    }
}