package utils;

import exceptions.AutomationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.log4testng.Logger;

import java.util.Date;


public class DriverManager {
    private final static Logger logger = Logger.getLogger(TestBase.class);

    public WebDriver getDriver(String browser) throws AutomationException {
        WebDriver driver = null;
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--aggressive-cache-discard");
            options.addArguments("--disable-cache");
            options.addArguments("--disable-application-cache");
            options.addArguments("--disable-offline-load-stale-cache");
            options.addArguments("--disk-cache-size=0");
            options.addArguments("headless");
            options.addArguments("window-size=1920x1080");
            options.addArguments("--disable-gpu");
            DesiredCapabilities capabilitiesChrome = new DesiredCapabilities();
            capabilitiesChrome.setCapability("browserName", "chrome");
            capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(capabilitiesChrome);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AutomationException("Unable to launch Browser.");
        }
        return driver;
    }
}
