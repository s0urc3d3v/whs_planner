package WHS_planner.Calendar;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by geoffrey_wang on 9/17/16.
 */
public class MainUI extends Application{

    //Days of the week
    String[] daysOfTheWeek = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    @Override
    public void start(Stage stage) throws Exception {
        CalendarUtility util = new CalendarUtility();

        //Loads the ttf font file into the program
        InputStream font = MainUI.class.getResourceAsStream("/FontAwesome/fontawesome.ttf");
        Font.loadFont(font, 10);

        //Generate the Calendar
        CalendarBox[][] calendar = null;
        try {
            calendar = util.generateCalendar(CalendarUtility.TUESDAY, 31);
        }catch (Exception e){
            e.printStackTrace();
        }

//-------------------------------VBOX Implementation-----------------------------------------
//        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(14,0,0,14));//TODO Fix without hardcoding
//        ArrayList<GridPane> rows = new ArrayList<GridPane>();
//
//        for (int r = 0; r < calendar.length ; r++) {
//            GridPane row = new GridPane();
//            row.setAlignment(Pos.CENTER);
//            for (int c = 0; c < calendar[r].length; c++) {
//                if (calendar[r][c] != null) {
//                    row.add(calendar[r][c], c, 0);
//                }else{
//                    row.add(new CalendarBox(0,0,null),c,0);//TODO Fix without hardcoding
//                }
//            }
//            rows.add(row);
//        }
//
//        vbox.getChildren().setAll(rows);
//
//        //Set the scene to the grid container
//        Scene scene = new Scene(vbox);
//-------------------------------------------------------------------------------------------

//--------------------------Grid Implementation----------------------------------------------

        //Set up the grid container
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));

        //Add the week day to the calendar
        for (int dayIndex = 0; dayIndex < daysOfTheWeek.length; dayIndex++) {
            Label dayLabel = new Label(daysOfTheWeek[dayIndex]);
            dayLabel.getStyleClass().add("week-day");
            gridPane.add(new Label(daysOfTheWeek[dayIndex]),dayIndex,0);
        }

        //Add the generated Calendar to the grid container
        for (int r = 0; r < 5 ; r++) {
            for (int c = 0; c < 7; c++) {
                if (calendar[r][c] != null) {
                    gridPane.add(calendar[r][c], c, r+1);
                }
            }
        }

        //Set the scene to the grid container
        Scene scene = new Scene(gridPane);
//-------------------------------------------------------------------------------------------


        //Set the stylesheet
        scene.getStylesheets().add("/Calendar/MainUI.css");

        //Final Stage initiation
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.show();
    }
}
