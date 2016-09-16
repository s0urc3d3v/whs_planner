package WHS_planner.Calendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UITestingStuff extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(2);
        grid.setHgap(2);
        grid.setGridLinesVisible(true);
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 7; c++) {
                grid.add(new Rectangle(120,90,Color.ANTIQUEWHITE),c,r);
                String date = (c+1)*(r+1)+"";

                grid.add(new Label(date),c,r);
            }
        }

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}