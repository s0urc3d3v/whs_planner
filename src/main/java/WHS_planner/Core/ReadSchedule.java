package WHS_planner.Core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

import static com.thoughtworks.selenium.SeleneseTestBase.fail;


/**
 * Created by matthewelbing on 23.09.16.
 */
public class ReadSchedule {
    private String username;
    private String password;
    private StringBuffer verificationErrors = new StringBuffer();
    private ChromeDriver chromeDriver;
    private boolean acceptNextAlert = true;
    private String pageSource;
    public ReadSchedule (){
        try{
            String execPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "chromedriver";
            File chromeWebDriverExec = new File(execPath);
            System.setProperty("webdriver.chrome.driver", (execPath));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void authAndFindTableWithIpass(String user, String pass) throws Exception {
        //Auth and navigate to schedule
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1024,768");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        chromeDriver = new ChromeDriver(capabilities);
        String baseUrl = "https://ipass.wayland.k12.ma.us";
        chromeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        chromeDriver.get(baseUrl + "/school/ipass/syslogin.html");
        chromeDriver.findElement(By.name("userid")).clear();
        chromeDriver.findElement(By.name("userid")).sendKeys(user);
        chromeDriver.findElement(By.name("password")).clear();
        chromeDriver.findElement(By.name("password")).sendKeys(pass);
        //chromeDriver.findElement(By.cssSelector("img[alt=\"Login to iPass\"]")).submit();
        chromeDriver.findElement(By.name("password")).sendKeys(Keys.TAB);
        ((JavascriptExecutor) chromeDriver).executeScript("javascript:document.login.submit()"); //working just needed a parenthesis        ((JavascriptExecutor) chromeDriver).executeScript("javascript:document.login.submit()"); //working just needed a parenthesis
        //chromeDriver.get(baseUrl + "https://ipass.wayland.k12.ma.us/school/ipass/index.html?dt=09261636690");
        chromeDriver.get("https://ipass.wayland.k12.ma.us/school/ipass/samschedule.html?m=506&amp;pr=19&amp;dt=09261649872");
        pageSource = chromeDriver.getPageSource();
        Files.write(Paths.get(System.getProperty("user.dir") + File.separator + "raw.html"), pageSource.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        /*
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
        }*/

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
