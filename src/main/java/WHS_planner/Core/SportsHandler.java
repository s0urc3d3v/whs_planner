package WHS_planner.Core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by matthewelbing on 27.09.16.
 */
public class SportsHandler extends WebDriver {

    String sportSite = "http://miaa.net/schools/public/WaylWa1";

    public SportsHandler() {
        super();
        //Setup and Initialize the Driver
        if(setup()) {
            //If setup of the driver worked than load this link
            driver.get(sportSite);
        } else {
            ErrorHandler.handleGenericError("WebDriver in SportsHandler decided to fail :( ", new Exception());
        }
    }

    public String[] getSports() {
        //Find all the sports name boxes
        WebElement tableBody = driver.findElements(By.className("x-grid3-body")).get(1);
        //Get the rows that the names are in
        List<WebElement> tableRows = tableBody.findElements(By.className("x-grid3-row-table"));
        //Make a string array because you want to
        String[] sports = new String[tableRows.size()];
        //Go through the rows
        for(int i = 0; i < tableRows.size(); i++) {
            //Pull out the second table element... that is the sports name
            sports[i] = tableRows.get(i).findElements(By.tagName("td")).get(1).getText();
        }
        //Return over-sized array
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
}
