package WHS_planner.Calendar;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Created by geoffrey_wang on 11/11/16.
 */

//JAR FILE IS IN THE CALENDAR RESOURCES FOLDER
//    *********************************************************************
public class JFXMasonryPaneTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        JFXMasonryPane root = new JFXMasonryPane();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            Label label = new Label(i+"");
            label.setStyle("-fx-background-color:rgb("+ random.nextInt(3)*100 + "," + random.nextInt(3)*100 + "," + random.nextInt(3)*100 + ");");
//            label.setPrefSize(random.nextInt(3)*100,random.nextInt(3)*100);
            label.setPrefSize(100,100);
            root.getChildren().add(label);
        }

        Scene scene = new Scene(root,300,250);

        //Final Stage initiation
        stage.setTitle("JFXMasonryPaneTest");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
