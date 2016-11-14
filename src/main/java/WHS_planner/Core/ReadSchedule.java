package WHS_planner.Core;

import WHS_planner.Schedule.GrabDay;

import java.net.*;
import java.util.List;


/**
 * Created by matthewelbing on 23.09.16.
 * Grabs Schedule from IPass
 *
 * RIP Selenium we will never forget you
 */
public class ReadSchedule {
    private String username;
    private String password;
    private StringBuffer verificationErrors = new StringBuffer();
    private boolean acceptNextAlert = true;
    private String pageSource;
    public ReadSchedule (){}

    private HttpURLConnection connection;
    private List<String> cookies;
    private final String USER_AGENT = "Mozilla/5.0";

    public void authAndFindTableWithIpass(String user, String pass) throws Exception {
        GrabDay grabDay = new GrabDay(user, pass);
        grabDay.grabSchedule("raw.html");
    }
}