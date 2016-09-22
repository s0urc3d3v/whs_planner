package WHS_planner.Schedule;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Schedule extends Application
{
    javafx.scene.control.Label[][] classes = new javafx.scene.control.Label[6][9];
    Pane rootLayout;

    public static void main(String[] args)
    {
        launch(args);
    }


    public void start(Stage PrimaryStage)
    {
        PrimaryStage.setTitle("src/main/resources/Schedule");

        classes[0][0] = new javafx.scene.control.Label();
        classes[0][0].setText("memes");
        try
        {
            FXMLLoader loader = new FXMLLoader();


            loader.setLocation(getClass().getResource("/src/main/resources/Schedule/scheduletest.fxml"));

            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);

            scene.widthProperty().addListener(new ChangeListener<Number>() {
                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {

                }
            });
            scene.heightProperty().addListener(new ChangeListener<Number>() {
                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {

                }
            });

            PrimaryStage.setResizable(true);
            PrimaryStage.setMinHeight(520);
            PrimaryStage.setMinWidth(573);
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }




}
