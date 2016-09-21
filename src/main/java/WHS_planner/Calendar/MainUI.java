package WHS_planner.Calendar;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
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
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5,5,5,5));
        ArrayList<GridPane> rows = new ArrayList<GridPane>();

        //Add the week days and first row to the calendar
        GridPane firstRow = new GridPane();
        firstRow.setAlignment(Pos.CENTER);
        firstRow.setHgap(10);
        firstRow.setVgap(10);
        firstRow.setPadding(new Insets(5,5,5,5));

        for (int dayIndex = 0; dayIndex < daysOfTheWeek.length; dayIndex++) {
            Label dayLabel = new Label(daysOfTheWeek[dayIndex]);
            dayLabel.getStyleClass().add("week-day");
            firstRow.add(new Label(daysOfTheWeek[dayIndex]),dayIndex,0);
        }
        for (int c = 0; c < calendar[0].length; c++) {
            if (calendar[0][c] != null) {
                firstRow.add(calendar[0][c], c, 1);
            }else{
                firstRow.add(new CalendarBox(0,0,null),c,1);
            }
        }
        rows.add(firstRow);


        //Fill in rest of the calendar
        for (int r = 1; r < calendar.length ; r++) {
            GridPane row = new GridPane();
            row.setAlignment(Pos.CENTER);
            row.setHgap(10);
            row.setPadding(new Insets(5,5,5,5));
            for (int c = 0; c < calendar[r].length; c++) {
                if (calendar[r][c] != null) {
                    row.add(calendar[r][c], c, 0);
                }else{
                    row.add(new CalendarBox(0,0,null),c,0);
                }
            }
            rows.add(row);
        }

        vbox.getChildren().setAll(rows);

        //Set the scene to the grid container
        Scene scene = new Scene(vbox);
//-------------------------------------------------------------------------------------------

        String useless = "THIS IS JUST A PLACEHOLDER TO SEPARATE COMMENTS!";//TODO REMOVE!!

//--------------------------Grid Implementation----------------------------------------------
//
//        //Set up the grid container
//        GridPane gridPane = new GridPane();
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//        gridPane.setPadding(new Insets(10,10,10,10));
//
//        //Add the week day to the calendar
//        for (int dayIndex = 0; dayIndex < daysOfTheWeek.length; dayIndex++) {
//            Label dayLabel = new Label(daysOfTheWeek[dayIndex]);
//            dayLabel.getStyleClass().add("week-day");
//            gridPane.add(new Label(daysOfTheWeek[dayIndex]),dayIndex,0);
//        }
//
//        //Add the generated Calendar to the grid container
//        for (int r = 0; r < 5 ; r++) {
//            for (int c = 0; c < 7; c++) {
//                if (calendar[r][c] != null) {
//                    gridPane.add(calendar[r][c], c, r+1);
//                }
//            }
//        }
//
//        //Set the scene to the grid container
//        Scene scene = new Scene(gridPane);
//-------------------------------------------------------------------------------------------


        //Set the stylesheet
        scene.getStylesheets().add("/Calendar/MainUI.css");

        //Final Stage initiation
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.show();
    }
}
