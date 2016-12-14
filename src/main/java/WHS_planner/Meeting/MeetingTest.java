package WHS_planner.Meeting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


public class MeetingTest extends Application{
    private static MeetingController controller = new MeetingController();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception{


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
            throw ex;
        }

//        Parent root = FXMLLoader.load(getClass().getResource("/loginPage.fxml")); //root is the fxml "loginPage" in the src dir

        Scene scene = new Scene(root);

//        Region rect = (Region)root.getChildrenUnmodifiable().get(0);
//
//        rect.prefWidthProperty().bind(scene.widthProperty());
//        rect.prefHeightProperty().bind(scene.heightProperty());

        //Set the stylesheet
        scene.getStylesheets().add("Calendar" + File.separator + "MainUI.css");

        stage.setScene(scene);
        stage.show();
    }
}
