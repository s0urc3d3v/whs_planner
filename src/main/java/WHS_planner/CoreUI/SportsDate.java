package WHS_planner.CoreUI;


import java.text.SimpleDateFormat;
import java.util.Date;

public class SportsDate {
    private Date date;
    private String event;
    private SimpleDateFormat dateFormat;

    public SportsDate(Date d, String s){
        date = d;
        event = s;
        dateFormat = new SimpleDateFormat("h:mm a EEE, MMM d");
    }

    public void setEventDate(String date, String evnt) {
        //TODO: this fuckin method

    }

    @Override
    public String toString() {
        return dateFormat.format(date) +
                ": " + event;
    }
}
