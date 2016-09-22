package WHS_planner.Calendar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by geoffrey_wang on 9/21/16.
 */
public class Calendar extends Pane {

    //Days of the week
    private String[] daysOfTheWeek = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private CalendarBox[][] calendar;
    private int startDay;
    private int numberOfDays;

    private VBox mainPane;

    public Calendar(int startDay, int numberOfDays){

        this.startDay = startDay;
        this.numberOfDays = numberOfDays;

        CalendarUtility util = new CalendarUtility();

        //Loads the ttf font file into the program
        InputStream font = MainUI.class.getResourceAsStream("/FontAwesome/fontawesome.ttf");
        Font.loadFont(font,10);

        try {
            calendar = util.fillInCalendar(startDay, numberOfDays,new UIController());
        }catch (Exception e){
            e.printStackTrace();
        }

        mainPane = new VBox();
        mainPane.setId("vbox");//Replace this ID
        mainPane.setPadding(new Insets(5,5,5,5));
        ArrayList<Node> rows = new ArrayList<>();
        mainPane.setAlignment(Pos.CENTER);

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

        mainPane.getChildren().setAll(rows);

        this.getChildren().setAll(mainPane);
    }

}
