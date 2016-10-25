package WHS_planner.Meeting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class MeetingTest extends Application{

    public void start(Stage stage) throws Exception{


        String sceneFile = "/Meeting/meeting.fxml";
        Parent root = null;
        URL    url  = null;

        try
        {
            FXMLLoader loader = new FXMLLoader();
            url  = getClass().getResource("/Meeting/meeting.fxml");
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

//        Region rect = (Region)root.getChildrenUnmodifiable().get(0);
//
//        rect.prefWidthProperty().bind(scene.widthProperty());
//        rect.prefHeightProperty().bind(scene.heightProperty());

        //Set the stylesheet
        scene.getStylesheets().add("/Calendar/MainUI.css");

        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args) {
        launch(args);
    }
}
