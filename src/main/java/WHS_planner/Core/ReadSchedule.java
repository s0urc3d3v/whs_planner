package WHS_planner.Core;

import java.io.OutputStreamWriter;
import java.net.*;
import com.thoughtworks.selenium.*;


/**
 * Created by matthewelbing on 23.09.16.
 */
public class ReadSchedule {
    private String username;
    private String password;
    public ReadSchedule (){
        //getUserName and getPass gets users user and pass for ipass
    }
    public static void authAndFindTableWithIpass(String user, String pass) throws Exception {
        Selenium selenium = new DefaultSelenium("localhost", 4444, "*chrome", "https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html");
        selenium.start();
        selenium.open("https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html");
        selenium.type("name=userid", user);
        selenium.type("name=password", pass);
        selenium.click("css=img[alt=\"Login to iPass\"]");
        selenium.waitForPageToLoad("30000");
        selenium.click("id=nodeIcon1");
        selenium.click("link=iStudent Schedule");
        selenium.stop();
    }

}
