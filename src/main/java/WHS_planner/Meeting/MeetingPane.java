package WHS_planner.Meeting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;


public class MeetingPane extends Pane {
    private static MeetingController controller = new MeetingController();

    public MeetingPane(){
        String sceneFile = "/Meeting/meeting.fxml";
        Parent root = null;
        URL url = null;

        try {
            url = getClass().getResource("/Meeting/meeting.fxml");
            root = FXMLLoader.load(url);
            System.out.println("  fxmlResource = " + sceneFile);
        } catch (Exception ex) {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * url: " + url);
            System.out.println("  * " + ex);
            System.out.println("    ----------------------------------------\n");
        }

        this.getChildren().setAll(root);
    }
}
