package WHS_planner.CoreUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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

        //this handles any keys pressed, and if the Q key is pressed, the program will close cleanly

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.Q))
                {
                    System.exit(0);
                }
            }
        });

//        Region rect = (Region)root.getChildrenUnmodifiable().get(0);
//
//        rect.prefWidthProperty().bind(scene.widthProperty());
//        rect.prefHeightProperty().bind(scene.heightProperty());

        //Set the stylesheet
        scene.getStylesheets().add("/Calendar/MainUI.css");

        stage.setMinHeight(75*5+48+10*8+25+30);
        stage.setMinWidth(780);
        stage.setScene(scene);
        stage.show();
    }



    public static Stage getStage() {
        return stage;
    }


}
