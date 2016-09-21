package WHS_planner.Calendar;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ResourceBundle;

public class CalendarUtility {

    FXMLLoader loader;

    //Generates a calendar array with the correct amount of days and starting on the start day
    public Pane[][] generateCalendar(int startDay, int numberOfDays) throws IOException {

        //Intitialize a general calendar array size
        Pane[][] calendar = new Pane[5][7];

        int dayInMonth = 1;

        for (int row = 0; row < calendar.length ; row++) {
            for (int col = 0; col < calendar[row].length; col++) {
                //Check if loop is on the first index
                if (row == 0&&dayInMonth == 1) {
                    col = startDay;
                }

                //Initialize flame dragon
                loader = new FXMLLoader();
                loader.setController(new UIController());
                loader.setResources(ResourceBundle.getBundle("FontAwesome.fontawesome"));
                loader.setLocation(getClass().getResource("/Calendar/calendarBox.fxml"));

                //Load in a calendarBox
                Pane calendarBoxPane = loader.load();

                calendar[row][col] = calendarBoxPane;
                calendar[row][col].setId(dayInMonth+"");
                Label label = (Label)calendar[row][col].getChildren().get(0);
                label.setText(calendar[row][col].getId());
                dayInMonth ++;
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
