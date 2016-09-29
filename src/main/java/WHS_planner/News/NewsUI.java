package WHS_planner.News;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by andrew_eggleston on 9/26/16.
 */
public class NewsUI extends Application {
    public void start(Stage stage) throws Exception{


        String sceneFile = "/resources/News/News1_2.fxml";
        Parent root = null;
        URL url  = null;

        try
        {
            url = getClass().getResource("/News/News1_2.fxml");
            root = FXMLLoader.load(url);
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

        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args) {
        launch(args);
    }
}
