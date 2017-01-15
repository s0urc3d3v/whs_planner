package WHS_planner.UI;

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

    public GlobalTime(JFXCheckBox check)
    {
        this.checkBox = check;
    }


    //if class index is -1, that means it's not school hours!
    public int getClassIndex()
    {
        int n = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(n == Calendar.SATURDAY || n == Calendar.SUNDAY)
        {
            return -1;
        }


        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateS = df.format(date);
        int num = parseDate(dateS);
        int mod;
        //wednesday
        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) {
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
        }
        //Bell2
        else if (checkBox.isSelected()) {
            if (num >= 450 && num < 501) {
                mod = 0;
            } else if (num >= 501 && num < 558) {
                mod = 1;
            } else if (num >= 558 && num < 593) {
                //Class meeting
                mod = -3;
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

            return 1;
        }
        //other days
        else {
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

    private double progressVal()
    {

        int n = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(n == Calendar.SATURDAY || n == Calendar.SUNDAY)
        {
            return 1;
        }

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateS = df.format(date);
        int num = parseDate(dateS);
        double mod;
        //wednesday
        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) {
            if (num >= 450 && num < 495) {
                mod = (495 - num) / 45.0;
            } else if (num >= 495 && num < 535) {
                mod = (575 - num) / 40.0;
            } else if (num >= 535 && num <= 620) {
                mod = (620 - num) / 40.0;
            } else if (num >= 575 && num < 620) {
                mod = (620 - num) / 45.0;
            } else if (num >= 620 && num < 700) {
                mod = (700 - num) / 80.0;
            } else if (num >= 700 && num < 745) {
                mod = (745 - num) / 45.0;
            } else if (num >= 745 && num <= 785) {
                mod = (785 - num) / 40.0;
            } else {
                mod = 1;
            }
        }
        //Bell2
        else if (checkBox.isSelected()) {
            if (num >= 450 && num < 501) {
                mod = (501 - num) / 56;
            } else if (num >= 501 && num < 558) {
                mod = (558 - num) / 62;
            } else if (num >= 558 && num < 593) {
                //Class meeting
                mod = (593 - num) / 30;
            } else if (num >= 593 && num < 650) {
                mod = (650 - num) / 57;
            } else if (num >= 650 && num < 741) {
                mod = (741 - num) / 91;
            } else if (num >= 741 && num <= 798) {
                mod = (798 - num) / 57;
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num) / 57;
            } else {
                mod = 1;
            }

            return 1;
        }
        //other days
        else {
            if (num >= 450 && num < 512) {
                mod = (512 - num) / 62.0;
            } else if (num >= 512 && num < 579) {
                mod = (579 - num) / 67.0;
            } else if (num >= 579 && num < 641) {
                mod = (641 - num) / 62.0;
            } else if (num >= 641 && num < 736) {
                mod = (736 - num) / 95.0;
            } else if (num >= 736 && num < 798) {
                mod = (798 - num) / 62.0;
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num) / 57.0;
            } else {
                mod = 1;
            }
        }
        return mod;
    }

    private int timeLeft()
    {
        int n = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(n == Calendar.SATURDAY || n == Calendar.SUNDAY)
        {
            return 0;
        }
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateS = df.format(date);
        int num = parseDate(dateS);
        double mod;
        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) {
            if (num >= 450 && num < 495) {
                mod = (495 - num);
            } else if (num >= 495 && num < 535) {
                mod = (575 - num);
            } else if (num >= 535 && num <= 620) {
                mod = (620 - num);
            } else if (num >= 575 && num < 620) {
                mod = (620 - num);
            } else if (num >= 620 && num < 700) {
                mod = (700 - num);
            } else if (num >= 700 && num < 745) {
                mod = (745 - num);
            } else if (num >= 745 && num <= 785) {
                mod = (785 - num);
            } else {
                mod = 1;
            }
        } else if (checkBox.isSelected()) {
            if (num >= 450 && num < 501) {
                mod = (501 - num);
            } else if (num >= 501 && num < 558) {
                mod = (558 - num);
            } else if (num >= 558 && num < 593) {
                //Class meeting
                mod = (593 - num);
            } else if (num >= 593 && num < 650) {
                mod = (650 - num);
            } else if (num >= 650 && num < 741) {
                mod = (741 - num);
            } else if (num >= 741 && num <= 798) {
                mod = (798 - num);
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num);
            } else {
                mod = 1;
            }

            return 1;
        } else {
            if (num >= 450 && num < 512) {
                mod = (512 - num);
            } else if (num >= 512 && num < 579) {
                mod = (579 - num);
            } else if (num >= 579 && num < 641) {
                mod = (641 - num);
            } else if (num >= 641 && num < 736) {
                mod = (736 - num);
            } else if (num >= 736 && num < 798) {
                mod = (798 - num);
            } else if (num >= 798 && num <= 855) {
                mod = (855 - num);
            } else {
                mod = 0;
            }
        }
        return (int) mod;
    }


    private int parseDate(String date) {
        String hour = date.substring(0, date.indexOf(":"));
        String minute = date.substring(date.indexOf(":") + 1);
        int hr = Integer.parseInt(hour);
        int min = Integer.parseInt(minute);
        min += (hr * 60);
        return min;
    }
}
