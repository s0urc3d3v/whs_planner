package WHS_planner.Core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.List;

/**
 * Created by matthewelbing on 27.09.16.
 */
public class HandleSportsData {

    public HandleSportsData() {
        String raw_data = "http://miaa.net/schools/public/WaylWa1";
        //Setting the Chrome Driver as a system property
        String webDriverLocalPath = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator +"Core" + File.separator +"chromedriver";
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            webDriverLocalPath += "-linux";
        }
        System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir") + webDriverLocalPath));
        //Instantiating Chrome Driver - Stolen from Matt.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1024,768");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        ChromeDriver chromeDriver = new ChromeDriver(capabilities);
        //Loading the webpage
        chromeDriver.get(raw_data);
        WebElement tableBody = chromeDriver.findElements(By.className("x-grid3-body")).get(1);
        List<WebElement> tableRows = tableBody.findElements(By.className("x-grid3-row-table"));
        for(WebElement row: tableRows) {
            System.out.println(row.findElements(By.tagName("td")).get(1).getText());
        }
    }

    public static void main(String[] args) {
        WHS_planner.Core.HandleSportsData handleSportsData = new WHS_planner.Core.HandleSportsData();
    }



}
