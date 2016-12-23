package WHS_planner.Calendar;

import WHS_planner.Core.IO;
import WHS_planner.Core.JSON;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class CalendarUtility {

    public static final int SUNDAY = 0, MONDAY = 1,TUESDAY = 2, WEDNESDAY = 3, THURSDAY = 4, FRIDAY = 5, SATURDAY = 6;
    FXMLLoader loader;

    //Generates a calendar array with the correct amount of days and starting on the start day
    public CalendarBox[][] fillInCalendar(int startDay, int numberOfDays, UIController controller) throws IOException {

        //Initialize a general calendar array size
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

        //json.loadFile("calendarHolder.json");  //redundant??
        for (int i = 0; i < numberOfDays ; i++) {
//            for (int j = 0; j < 2 ; j++) {
                boolean listIsFull = true;
                for (int k = 0; listIsFull == true ; k++) {
                   // System.out.println("For is working");
                    try {
                        //TODO: PRINT WHAT IS THIS ARRAY
                        if(json.readArray("@CalendarSaver" + i + ":" + 0 + ":"+k) == null) {
                            System.out.println("@CalendarSaver" + i + ":" + 0 + ":"+k);
                        }
                        Object[] steve = json.readArray("@CalendarSaver" + i + ":" + 0 + ":"+k ).toArray();
                        if(calendar[i/7][i%7] != null) {
                            calendar[i / 7][i % 7].addTask(0, new Task(steve[0].toString().substring(20), steve[1].toString().substring(20), steve[2].toString().substring(20)));
                            System.out.println("" + steve[0].toString());
                            System.out.println("" + steve[1].toString());
                            System.out.println("" + steve[2].toString());
                        }

                    } catch (Exception e) {
                        listIsFull = false;
                        System.out.println(e);
                    }

//                }

            }
        }
        //json.unloadFile();







        return calendar;
    }

}
