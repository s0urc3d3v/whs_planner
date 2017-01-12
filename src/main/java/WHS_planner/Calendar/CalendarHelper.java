package WHS_planner.Calendar;


import java.util.Calendar;


/**
 * Created by tzur_almog on 11/16/16.
 */
public class CalendarHelper {

    public String getMonthString(int mon){
        String returnString = "";
        switch(mon)
        {
            case 1: returnString = "January";
                break;
            case 2: returnString ="February";
                break;
            case 3: returnString ="March";
                break;
            case 4: returnString ="April";
                break;
            case 5: returnString ="May";
                break;
            case 6: returnString ="June";
                break;
            case 7: returnString ="July";
                break;
            case 8: returnString ="August";
                break;
            case 9: returnString ="September";
                break;
            case 10: returnString ="October";
                break;
            case 11: returnString ="November";
                break;
            case 12: returnString ="December";
                break;
            default: returnString = "Month getter is broken";
                break;
        }
        return returnString;
    }

    public int getDaysInMonth(int month) {
        month++;
        int daysInMonth = 0;
        switch(month)
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

        return daysInMonth;
    }

    public int getWeekdayMonthStarts(int month) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, 1);
        now.set(Calendar.MONTH, month);
        now.set(Calendar.YEAR, 2017);
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }

    public int getDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
}