package WHS_planner.CoreUI;

import WHS_planner.Calendar.Calendar;
import WHS_planner.Calendar.CalendarUtility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;


public class NavigationBar extends Application{

    public void start(Stage stage) throws Exception{


        String sceneFile = "/CoreUI/NavigationBar.fxml";
        Parent root = null;
        URL    url  = null;

        try
        {
            FXMLLoader loader = new FXMLLoader();
            url  = getClass().getResource("/CoreUI/NavigationBar.fxml");
            root = loader.load(url);
            System.out.println( "  fxmlResource = " + sceneFile );
        }
        catch ( Exception ex )
        {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            throw ex;
        }

//        Parent root = FXMLLoader.load(getClass().getResource("/loginPage.fxml")); //root is the fxml "loginPage" in the src dir

        Scene scene = new Scene(root);

        //Set the stylesheet
        scene.getStylesheets().add("/Calendar/MainUI.css");

        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args) {
        launch(args);
    }
}
