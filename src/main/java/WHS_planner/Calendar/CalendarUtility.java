package WHS_planner.Calendar;

import WHS_planner.Core.JSON;
import WHS_planner.Schedule.Schedule;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;

public class CalendarUtility {
    private JFXCheckBox checkBox;
    private Schedule schedule;
    private  Calendar whsCalendar;

    public CalendarUtility(Calendar cal)
    {
        this.whsCalendar = cal;
        this.schedule = whsCalendar.getSchedule();
        this.checkBox = schedule.getCheck();
    }

    public static final int SUNDAY = 0, MONDAY = 1,TUESDAY = 2, WEDNESDAY = 3, THURSDAY = 4, FRIDAY = 5, SATURDAY = 6;
    FXMLLoader loader;


    public  CalendarBox[][] CalendarLoad(int startDay, int numberOfDays, JSON json, int month)throws IOException{

        int dayInMonth = 1;

        //Intitialize a general calendar array size
        CalendarBox[][] calendar = new CalendarBox[6][7];
        ArrayList<ArrayList<Task>> listOfTasks = new ArrayList<>();

        for (int dayIndex = 1; dayIndex <= numberOfDays; dayIndex++) {
            boolean finishedReadingTasks = false;
            ArrayList<Task> tasks = new ArrayList<>();
            int taskIndex = 0;

            while(finishedReadingTasks == false){
                try {
                    //0 is task list
                    Object[] rawTask = json.readArray("@CalendarSaver" + dayIndex + ":" + 0 + ":" + taskIndex).toArray();
                    System.out.println(rawTask[0].toString());
                    System.out.println(rawTask[1].toString());

                    Task task = new Task(rawTask[0].toString().substring(21), " ", rawTask[1].toString().substring(21));
                    tasks.add(task);
                    taskIndex ++;
                }catch(Exception e){
                    finishedReadingTasks = true;
                }
            }
            listOfTasks.add(tasks);
        }

        for (int row = 0; row < calendar.length ; row++) {
            for (int col = 0; col < calendar[row].length; col++) {
                //Check if loop is on the first index
                if (row == 0&&dayInMonth == 1) {
                    col = startDay-1;
                }

                if(dayInMonth > numberOfDays){
                    break;
                }

                CalendarBox box = new CalendarBox(dayInMonth,row,true,listOfTasks.get(dayInMonth-1),month, whsCalendar);
                calendar[row][col] = box;
                dayInMonth++;
            }
            if(dayInMonth > numberOfDays){
                break;
            }
        }

        //json.unloadFile();
        return calendar;
    }
}
