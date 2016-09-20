package WHS_planner.Calendar;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Created by geoffrey_wang on 9/17/16.
 */
public class MainUI extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        InputStream font = MainUI.class.getResourceAsStream("/FontAwesome/fontawesome.ttf");
        Font.loadFont(font, 10);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));

        CalendarUtility util = new CalendarUtility();

        Pane[][] panes = null;
        try {
            panes = util.generateCalendar(3, 30);
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
        scene.getStylesheets().add("/Calendar/MainUI.css");

        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.show();
    }
}
