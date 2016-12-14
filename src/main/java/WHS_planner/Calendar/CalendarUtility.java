package WHS_planner.Calendar;

import WHS_planner.Core.IO;
import WHS_planner.Core.JSON;
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

                CalendarBox box = new CalendarBox(dayInMonth,row,true);
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

    // tzurs code
    public  CalendarBox[][] CalendarLoad(int startDay, int numberOfDays, JSON json)throws IOException{

        //Intitialize a general calendar array size
        CalendarBox[][] calendar = new CalendarBox[5][7];

        int dayInMonth = 1;



        for (int row = 0; row < calendar.length ; row++) {
            for (int col = 0; col < calendar[row].length; col++) {
                //Check if loop is on the first index
                if (row == 0&&dayInMonth == 1) {
                    col = startDay;
                }

                CalendarBox box = new CalendarBox(dayInMonth,row,true);
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
json.loadFile("calendarHolder");
        for (int i = 0; i < numberOfDays ; i++) {
            for (int j = 0; j <2 ; j++) {
                boolean listIsFull = true;
                int k= 0;
//                while(listIsFull == true){
//                    json.readArray("@CalendarSaver" + i + "" + j + ""+k )
//
//                    else{
//                        listIsFull = false;
//                    }
//
//                }
            }
        }







        return calendar;
    }

}
