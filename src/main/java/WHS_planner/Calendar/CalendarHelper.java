package WHS_planner.Calendar;


import java.util.Calendar;


/**
 * Created by tzur_almog on 11/16/16.
 */
public class CalendarHelper {
    String Month;
    int daysInMonth, weekdayMonthStarts;

    public  CalendarHelper()
    {
        // Find out what month it is
        Month = "error";
        int mon = Calendar.getInstance().get(Calendar.MONTH)+1;

        switch(mon)
        {
            case 1: Month = "January";
                break;
            case 2: Month ="February";
                break;
            case 3: Month ="March";
                break;
            case 4: Month ="April";
                break;
            case 5: Month ="May";
                break;
            case 6: Month ="June";
                break;
            case 7: Month ="July";
                break;
            case 8: Month ="August";
                break;
            case 9: Month ="September";
                break;
            case 10: Month ="October";
                break;
            case 11: Month ="November";
                break;
            case 12: Month ="December";
                break;
            default: Month = "Month getter is broken";
                break;
        }
        // finds out how many days in the month
        daysInMonth = -1;
        switch(mon)
        {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: daysInMonth = 31;
            break;
            case 2: daysInMonth =28;
                break;
            case 4:  case 6: case 9: case 11: daysInMonth =30;
            break;
            default: daysInMonth = -1;
                break;
        }

//      Funds out the  first day of the month

        weekdayMonthStarts = -1;
        int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int subtractiondays= date%7;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        weekdayMonthStarts =  (day - subtractiondays +7)%7;


//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        int dayFinder[] = new int[12];
//        for (int i = 0; i <12 ; i++) {
//            int tempVar = Calendar.set(1,1,1);
//            dayFinder[i] = Calendar.getInstance().get(Calendar.F);
//
//        }





    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public int getWeekdayMonthStarts() {
        return weekdayMonthStarts;
    }

    public String getMonth() {
        return Month;
    }
}