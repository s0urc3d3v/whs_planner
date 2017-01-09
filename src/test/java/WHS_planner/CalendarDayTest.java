package WHS_planner;

import WHS_planner.Calendar.CalendarHelper;

/**
 * Created by tzur_almog on 11/22/16.
 */
public class CalendarDayTest {
    public static void main(String[] args) {
        CalendarHelper jesus = new CalendarHelper();
        System.out.println(" the month starts on  = "+ jesus.getWeekdayMonthStarts());
        System.out.println(" the month is  = "+ jesus.getMonth());
        System.out.println(" days in month = "+ jesus.getDaysInMonth());
    }
}
