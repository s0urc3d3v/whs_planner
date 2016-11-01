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
        //Set the capabilities, because you want it to be able to do stuff
        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("phantomjs.binary.path", getDriverPath());
    }

    public boolean setup() {
        //Start a shell thingy to run stuff
        Runtime runtime = Runtime.getRuntime();
        try {
            //Start PhantomJS in said shell
            driverStart = runtime.exec(getDriverPath() + " --webdriver=8910");
            //Start the driver which is separate :(
            driver = new PhantomJSDriver(desiredCapabilities);
        } catch (IOException e) {
            //Tell the world when it breaks
            e.printStackTrace();
            ErrorHandler.handleGenericError("Failed to initialize web driver", e);
            return false;
        }
        //:)
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
        //Quit the driver and destroy the shell process for PhantomJS
        driver.quit();
        driverStart.destroy();
    }
}
