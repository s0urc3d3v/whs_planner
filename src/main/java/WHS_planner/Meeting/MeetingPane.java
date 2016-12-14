package WHS_planner.Meeting;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.net.URL;


public class MeetingPane extends Pane {
    private static MeetingController controller = new MeetingController();

    public MeetingPane(){
        String sceneFile = "Meeting/meeting.fxml";
        Parent root = null;
        URL url = null;

        try {
            url = getClass().getResource("/Meeting/meeting.fxml");
            root = FXMLLoader.load(url);
//            System.out.println("  fxmlResource = " + sceneFile);
        } catch (Exception ex) {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * url: " + url);
            System.out.println("  * " + ex);
            System.out.println("    ----------------------------------------\n");
        }

        Pane pane = (Pane) root;
        pane.prefHeightProperty().bind(this.prefHeightProperty());
        pane.prefWidthProperty().bind(this.prefWidthProperty());
        this.getChildren().setAll(root);
    }
}
