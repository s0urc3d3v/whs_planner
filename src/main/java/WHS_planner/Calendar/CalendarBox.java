package WHS_planner.Calendar;

import java.util.ArrayList;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class CalendarBox {
    private int date;
    private int dayOfTheWeek;
    private ArrayList<Task> homework;
    private ArrayList<Task> tests;

    public CalendarBox(int date, int dayOfTheWeek){
        this.date = date;
        this.dayOfTheWeek = dayOfTheWeek;
        this.homework = new ArrayList<Task>();
        this.tests = new ArrayList<Task>();
    }

    public int getHomeworkCount(){
        return homework.size();
    }

    public int getTestsCount(){
        return tests.size();
    }
}
