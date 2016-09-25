package WHS_planner.Core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.Period;
import org.seleniumhq.selenium.fluent.TestableString;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.thoughtworks.selenium.SeleneseTestBase.fail;


/**
 * Created by matthewelbing on 23.09.16.
 */
public class ReadSchedule {
    private String username;
    private String password;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver chromeDriver;
    private FluentWebDriver fluentWebDriver;
    private boolean acceptNextAlert = true;
    public ReadSchedule (){
        try{
            File chromeWebDriverExec = new File("/Users/matthewelbing/School/whs_planner_app_maven/src/main/resources/Core/chromedriver");
            System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir") + "/src/main/resources/Core/chromedriver"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void authAndFindTableWithIpass(String user, String pass) throws Exception {
        //Auth and navigate to schedule
        chromeDriver = new ChromeDriver();
        fluentWebDriver = new FluentWebDriver(chromeDriver);
        String baseUrl = "https://ipass.wayland.k12.ma.us";
        chromeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        chromeDriver.get(baseUrl + "/school/ipass/syslogin.html");
        chromeDriver.findElement(By.name("userid")).clear();
        chromeDriver.findElement(By.name("userid")).sendKeys(user);
        chromeDriver.findElement(By.name("password")).clear();
        chromeDriver.findElement(By.name("password")).sendKeys(pass);
        chromeDriver.get(baseUrl + "/school/ipass/samschedule.html?m=506&pr=19&dt=09241660439");
        TestableString rawHtmlData = fluentWebDriver.div(By.className("heading")).within(Period.secs(3)).getText();

        //Read schedule table
        String tableData[][] = new String[100][100];
        WebElement scheduleTable = chromeDriver.findElement(By.className("boxHdr"));
        List<WebElement> tableRows = scheduleTable.findElements(By.className("DATA"));
        int row_number, collum_number;
        row_number = 1;
        for (WebElement tableRowElement : tableRows){
            List<WebElement> tableCols = tableRowElement.findElements(By.xpath("td"));
            collum_number = 1;
            for (WebElement tableColElement : tableCols){
                System.out.println("row # "+row_number+", col # "+collum_number+ "text="+tableColElement.getText());
                tableData[row_number][collum_number] = tableColElement.getText();
                collum_number++;
            }
        row_number++;
        }

        chromeDriver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
    private boolean isElementPresent(By by) {
        try {
            chromeDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            chromeDriver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = chromeDriver.switchTo().alert();
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
