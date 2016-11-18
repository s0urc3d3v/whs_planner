package WHS_planner.CoreUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by matthewelbing on 24.10.16.
 */
public class MeetingUiFinal extends Application {
    @FXML
    Pane pane;

    @FXML
    Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        pane =  FXMLLoader.load(MeetingUiFinal.class.getResource("CoreUI" + File.separator + "MeetingOutline.fxml"));
        scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Meeting");
        primaryStage.show();
    }
    public Node getPane(){
        return scene.getRoot();
    }
}

