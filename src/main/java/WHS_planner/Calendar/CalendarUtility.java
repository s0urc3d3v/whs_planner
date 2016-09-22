package WHS_planner.Calendar;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ResourceBundle;

public class CalendarUtility {

    public static final int SUNDAY = 0, MONDAY = 1,TUESDAY = 2, WEDNESDAY = 3, THURSDAY = 4, FRIDAY = 5, SATURDAY = 6;
    FXMLLoader loader;

    //Generates a calendar array with the correct amount of days and starting on the start day
    public CalendarBox[][] fillInCalendar(int startDay, int numberOfDays, UIController controller) throws IOException {

        //Intitialize a general calendar array size
        CalendarBox[][] calendar = new CalendarBox[5][7];

        int dayInMonth = 1;

        for (int row = 0; row < calendar.length ; row++) {
            for (int col = 0; col < calendar[row].length; col++) {
                //Check if loop is on the first index
                if (row == 0&&dayInMonth == 1) {
                    col = startDay;
                }

//                //Initialize flame dragon
//                loader = new FXMLLoader();
//                loader.setController(new UIController());
//                loader.setResources(ResourceBundle.getBundle("FontAwesome.fontawesome"));
//                loader.setLocation(getClass().getResource("/Calendar/calendarBoxV2.fxml"));
//
//                //Load in a calendarBox
//                Pane calendarBoxPane = loader.load();
//
//                calendar[row][col] = calendarBoxPane;
//                calendar[row][col].setId(dayInMonth+"");
////                Label label = (Label)calendar[row][col].getChildren().get(1);
//                Label label = (Label)calendar[row][col].lookup("#date");
//                label.setText(calendar[row][col].getId());
//                dayInMonth ++;

                CalendarBox box = new CalendarBox(dayInMonth,row,controller);
                calendar[row][col] = box;
                dayInMonth++;

                if(dayInMonth > numberOfDays){
                    break;
                }
            }
            if(dayInMonth > numberOfDays){
                break;
            }
        }
        return calendar;
    }
}
