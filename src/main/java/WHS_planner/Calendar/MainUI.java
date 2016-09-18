package WHS_planner.Calendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by geoffrey_wang on 9/17/16.
 */
public class MainUI extends Application{
    @Override
    public void start(Stage stage) throws Exception {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));

        CalendarUtility util = new CalendarUtility();

//        Pane pane = (Pane)(FXMLLoader.load(getClass().getResource("calendarBox.fxml")));
Pane[][] panes = null;
        try {
            panes = util.generateCalendar(3, 30);

//            FXMLLoader.<Pane>load(getClass().getResource("calendarBox.fxml"));

        }catch (Exception e){
            e.printStackTrace();
        }


        for (int r = 0; r < 5 ; r++) {
            for (int c = 0; c < 7; c++) {
                if (panes[r][c] != null) {
                    gridPane.add(panes[r][c], c, r);
                }
            }
        }

        Scene scene = new Scene(gridPane);

        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.show();
    }
}
