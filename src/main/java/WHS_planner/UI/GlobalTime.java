package WHS_planner.UI;

import WHS_planner.Schedule.ParseCalendar;
import com.jfoenix.controls.JFXCheckBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by george_jiang on 1/15/17.
 */
public class GlobalTime {

    private JFXCheckBox checkBox;

    public GlobalTime(JFXCheckBox check) {
        if (check== null ) {
            System.out.println("check is null");
        }
        this.checkBox = check;
    }

    private int parseDate(String date) {
        return Integer.parseInt(date.substring(date.indexOf(":") + 1)) + (60*Integer.parseInt(date.substring(0, date.indexOf(":"))));
    }
    //if class index is -1, that means it's not school hours!
    // -2 is advisory, -3 is class meeting
    //TODO: -4 is passing time
    //TODO: -5 is break
    public int getClassIndex() {
        int n = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(n == Calendar.SATURDAY || n == Calendar.SUNDAY) {
            return -1;
        }
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateS = df.format(date);
        int num = parseDate(dateS);
        int mod;
//        System.out.println("num: " + num);
        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) { //wednesday
            if (num >= 450 && num < 495) {
                mod = 0;
            } else if (num >= 495 && num < 535) {
                mod = 1;
            } else if (num >= 535 && num <= 620) {
                mod = -2; //advisory
            } else if (num >= 575 && num < 620) {
                mod = 2;
            } else if (num >= 620 && num < 700) {
                mod = 3;
            } else if (num >= 700 && num < 745) {
                mod = 4;
            } else if (num >= 745 && num <= 785) {
                mod = 5;
            } else {
                mod = -1;
            }
        } else if (checkBox.isSelected()) { //Bell2
            if (num >= 450 && num < 501) {
                mod = 0;
            } else if (num >= 501 && num < 558) {
                mod = 1;
            } else if (num >= 558 && num < 593) {
                mod = -3; //Class meeting
            } else if (num >= 593 && num < 650) {
                mod = 2;
            } else if (num >= 650 && num < 741) {
                mod = 3;
            } else if (num >= 741 && num <= 798) {
                mod = 4;
            } else if (num >= 798 && num <= 855) {
                mod = 5;
            } else {
                mod = -1;
            }
//            return 1;
        } else { //Other days
            if (num >= 450 && num < 512) {
                mod = 0;
            } else if (num >= 512 && num < 579) {
                mod = 1;
            } else if (num >= 579 && num < 641) {
                mod = 2;
            } else if (num >= 641 && num < 736) {
                mod = 3;
            } else if (num >= 736 && num < 798) {
                mod = 4;
            } else if (num >= 798 && num <= 855) {
                mod = 5;
            } else {
                mod = -1;
            }
        }
        return mod;
    }

    String getLetterDay() {
        String result = "error";
        String s = (java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1) + "/" + java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
        ParseCalendar pc = new ParseCalendar();
        try {
            pc.readData();
            result = pc.getDay(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
