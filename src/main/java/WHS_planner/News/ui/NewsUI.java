package WHS_planner.News.ui;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class NewsUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        JFXMasonryPane root = new JFXMasonryPane();


        Scene scene = new Scene(root);
        scene.getStylesheets().add(File.separator + "News" + File.separator + "ButtonStyle.css");
        stage.setScene(scene);
        stage.show();
    }
}
