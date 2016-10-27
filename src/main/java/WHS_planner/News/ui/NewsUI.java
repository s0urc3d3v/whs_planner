package WHS_planner.News.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class NewsUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage stage) throws Exception{


        String sceneFile = File.separator + "resources" + File.separator + "News" + File.separator + "news1_6.fxml";
        Parent root;
        URL url  = null;
        try {
            url = getClass().getResource(File.separator + "News" + File.separator + "news1_6.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            root = FXMLLoader.load(url);
            System.out.println( "  fxmlResource = " + sceneFile );
        } catch (Exception ex) {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            throw ex;
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(File.separator + "News" + File.separator + "ButtonStyle.css");
        stage.setScene(scene);
        stage.show();
    }
}
