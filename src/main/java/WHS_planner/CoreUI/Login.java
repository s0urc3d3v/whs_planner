package WHS_planner.CoreUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * Created by andrew_eggleston on 9/16/16.
 */
public class Login extends Application{

    public void start(Stage stage) throws Exception{


        String sceneFile = "/res/loginPage.fxml";
        Parent root = null;
        URL    url  = null;

        try
        {
            url  = getClass().getResource("/loginPage.fxml");
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

    private static void getAllFiles(File curDir) {

        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                System.out.println(f.getName());
            if(f.isFile()){
                System.out.println(f.getName());
            }
        }

    }



    public static void main(String[] args) {
        launch(args);
//        File curDir = new File(".");
//        getAllFiles(curDir);
    }
}
