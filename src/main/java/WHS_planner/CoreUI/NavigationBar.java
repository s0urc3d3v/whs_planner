package WHS_planner.CoreUI;

import WHS_planner.Calendar.Calendar;
import WHS_planner.Calendar.CalendarBox;
import WHS_planner.Calendar.CalendarUtility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;


public class NavigationBar extends Application {

    private static Stage stage;

    public void start(Stage stageNew) throws Exception {

        stage = stageNew;


        String sceneFile = "/CoreUI/NavigationBar.fxml";
        Parent root = null;
        URL url = null;

        try {
            url = getClass().getResource("/CoreUI/NavigationBar.fxml");
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
        scene.getStylesheets().add("/Calendar/MainUI.css");

        stage.setMinHeight(CalendarBox.CALENDAR_BOX_HEIGHT*5+48+10*8+30+30+10);
        stage.setMinWidth(CalendarBox.CALENDAR_BOX_WIDTH*7+90);
        stage.setScene(scene);
        stage.show();
    }



    public static Stage getStage() {
        return stage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
