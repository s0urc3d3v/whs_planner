package WHS_planner.Core;

import WHS_planner.Util.OS;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by matthewelbing on 27.09.16.
 */
public class SportsHandler {

    PhantomJSDriver driver;
    String sportSite = "http://miaa.net/schools/public/WaylWa1";
    DesiredCapabilities desiredCapabilities;
    Process driverStart;

    public SportsHandler() {
        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("phantomjs.binary.path", getDriverPath());
    }

    public boolean setup() {
        Runtime runtime = Runtime.getRuntime();
        try {
            driverStart = runtime.exec(getDriverPath() + " --webdriver=8910");
            driver = new PhantomJSDriver(desiredCapabilities);
            driver.get(sportSite);
        } catch (IOException e) {
            e.printStackTrace();
            ErrorHandler.handleGenericError("Failed to initialize web driver", e);
            return false;
        }
        return true;
    }
    
    private String getDriverPath() {
        //These File.separators hurt to type on the inside.
        return "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "phantomjs-" + getSystemName() + File.separator + "bin" + File.separator + "phantomjs";
    }

    private String getSystemName() {
        OS.OSType osType = OS.getOSType();
        if(osType == OS.OSType.LINUX) {
            return "linux";
        } else if(osType == OS.OSType.MAC) {
            return "mac";
        }
        return "windows";
    }

    public String[] getSports() {
        WebElement tableBody = driver.findElements(By.className("x-grid3-body")).get(1);
        List<WebElement> tableRows = tableBody.findElements(By.className("x-grid3-row-table"));
        String[] sports = new String[tableRows.size()];
        for(int i = 0; i < tableRows.size(); i++) {
            sports[i] = tableRows.get(i).findElements(By.tagName("td")).get(1).getText();
        }
        return sports;
    }

    public String[] getEvents(int sportIndex) {
        //TODO: This methods does not work yet
        WebElement tableBody = driver.findElements(By.className("x-grid3-body")).get(1);
        List<WebElement> tableRows = tableBody.findElements(By.className("x-grid3-row-table"));
        WebElement team = tableRows.get(sportIndex);
        team.findElement(By.className("x-grid3-row-checker"));
        team.click();
        team.click();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        WebElement element = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("schedule-table")));
//        System.out.println(driver.findElementByClassName("schedule-table").findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText());
        return null;
    }

    public void close() {
        driver.quit();
        driverStart.destroy();
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("/home/jack/Dev/Github_Projects/whs_planner/log4j.properties");
    }



}
