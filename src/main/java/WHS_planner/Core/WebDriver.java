package WHS_planner.Core;

import WHS_planner.Util.OS;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

/**
 * Created by jack on 10/31/16.
 * Headless Web Driver Abstraction to make it easier to use :P
 */
public class WebDriver {
    protected PhantomJSDriver driver;
    private DesiredCapabilities desiredCapabilities;
    private Process driverStart;

    public WebDriver() {
        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("phantomjs.binary.path", getDriverPath());
    }

    public boolean setup() {
        Runtime runtime = Runtime.getRuntime();
        try {
            driverStart = runtime.exec(getDriverPath() + " --webdriver=8910");
            driver = new PhantomJSDriver(desiredCapabilities);
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
        OS.type osType = OS.getOSType();
        if(osType == OS.type.LINUX) {
            return "linux";
        } else if(osType == OS.type.MAC) {
            return "mac";
        }
        return "windows";
    }

    public void close() {
        driver.quit();
        driverStart.destroy();
    }
}
