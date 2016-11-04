package WHS_planner.Core;

import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

import static com.thoughtworks.selenium.SeleneseTestBase.fail;


/**
 * Created by matthewelbing on 23.09.16.
 * Grabs Schedule from IPass
 */
public class ReadSchedule {
    private String username;
    private String password;
    private StringBuffer verificationErrors = new StringBuffer();
    private HeadlessWebDriver hWebDriver;
    private boolean acceptNextAlert = true;
    private String pageSource;
    public ReadSchedule (){

    }
    public void authAndFindTableWithIpass(String user, String pass) throws Exception {
        //Auth and navigate to schedule
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("window-size=1024,768");
        hWebDriver = new HeadlessWebDriver();
        if(hWebDriver.setup()) {
            String baseUrl = "https://ipass.wayland.k12.ma.us";
            hWebDriver.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            hWebDriver.driver.get(baseUrl + "/school/ipass/syslogin.html");
            hWebDriver.driver.findElement(By.name("userid")).clear();
            hWebDriver.driver.findElement(By.name("userid")).sendKeys(user);
            hWebDriver.driver.findElement(By.name("password")).clear();
            hWebDriver.driver.findElement(By.name("password")).sendKeys(pass);
            //hWebDriver.driver.findElement(By.cssSelector("img[alt=\"Login to iPass\"]")).submit();
            hWebDriver.driver.findElement(By.name("password")).sendKeys(Keys.TAB);
            hWebDriver.driver.executeScript("javascript:document.login.submit()"); //working just needed a parenthesis
            //hWebDriver.driver.get(baseUrl + "https://ipass.wayland.k12.ma.us/school/ipass/index.html?dt=09261636690");
            hWebDriver.driver.get("https://ipass.wayland.k12.ma.us/school/ipass/samschedule.html?m=506&amp;pr=19&amp;dt=09261649872");
            pageSource = hWebDriver.driver.getPageSource();
            Files.write(Paths.get(System.getProperty("user.dir") + File.separator + "raw.html"), pageSource.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            hWebDriver.close();
            String verificationErrorString = verificationErrors.toString();
            if (!"".equals(verificationErrorString)) {
                fail(verificationErrorString);
            }
        } else {
            ErrorHandler.handleGenericError("WebDriver failed to initialize", new Exception());
        }
    }
    private boolean isElementPresent(By by) {
        try {
            hWebDriver.driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            hWebDriver.driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = hWebDriver.driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

}