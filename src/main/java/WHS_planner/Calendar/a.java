package WHS_planner.Calendar;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class a {
//    public static void main(String[] args) {
//        int calendar[][] = new int[5][7];
//        int startDay;
//        int dayInMonth;
//
//        for (int r = 0; r < 5 ; r++) {
//            for (int c = 0; c < 7; c++) {
//                if (r == 1) {
//                    c = startDay;
//
//                }
//                System.out.println(""+dayInMonth);
//                dayInMonth ++;
//            }
//
//        }
//
//        }
//
//
//    }
    public static int[][] fillCalendarDates(int startDay1) {
        int calendar[][] = new int[5][7];
        int startDay = startDay1;

        int dayInMonth = 1;

        for (int r = 0; r < 5 ; r++) {
            for (int c = 0; c < 7; c++) {
                if (r == 1) {
                    c = startDay;

                }
                calendar[r][c] = dayInMonth;
                dayInMonth ++;
            }

        }
        return calendar;
    }
}
