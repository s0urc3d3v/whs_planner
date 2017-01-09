package WHS_planner.CoreUI;

import WHS_planner.Calendar.CalendarBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


public class NavigationBar extends Application {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stageNew) throws Exception {

        stage = stageNew;


        String sceneFile = "CoreUI" + File.separator + "NavigationBar.fxml";
        Parent root = null;
        URL url = null;

        try {
            url = getClass().getResource("CoreUI" + File.separator + "NavigationBar.fxml");
            root = FXMLLoader.load(url);
            System.out.println("  fxmlResource = " + sceneFile);
        } catch (Exception ex) {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * url: " + url);
            System.out.println("  * " + ex);
            System.out.println("    ----------------------------------------\n");
            throw ex;
        }

        Scene scene = new Scene(root);

        //Set the stylesheet
        scene.getStylesheets().add("Calendar" + File.separator + "MainUI.css");

        stage.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT *5+48+10*8+30+30+10);
        stage.setMinWidth(CalendarBox.CALENDAR_BOX_MIN_WIDTH *7+90);
        stage.setScene(scene);
        stage.show();
    }
}
