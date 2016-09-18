package WHS_planner.Calendar;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CalendarUtility {

    public Pane[][] generateCalendar(int startDay, int numberOfDays) throws IOException {
        Pane[][] calendar = new Pane[5][7];

        int dayInMonth = 1;

        for (int row = 0; row < 5 ; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 0&&dayInMonth == 1) {
                    col = startDay;
                }
                Pane calendarBoxPane = (Pane)(FXMLLoader.load(getClass().getResource("/calendarBox.fxml")));
                calendar[row][col] = calendarBoxPane;
                calendar[row][col].setId(dayInMonth+"");
                Label label = (Label)calendar[row][col].getChildren().get(1);
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
