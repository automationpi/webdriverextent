package pages;

import exceptions.AutomationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.CommonFunction;
import utils.Reporting;

public class BlazeDemoPage {
    Reporting objReporter;
    WebDriver driver;

    final String strTitle = "/html/body/div[1]/div/div/a[2]";
    final String strNameFrom = "fromPort";
    final String strNameTo = "toPort";
    final String strSearchLandingPage = "//*/form/div/input";
    final String strSelectFlight = "//*/table/tbody/tr[1]/td[1]/input";

    public BlazeDemoPage(WebDriver driver, Reporting objReporter) {
        this.objReporter = objReporter;
        this.driver = driver;
    }

    public BlazeDemoPage openURL(String URL) throws AutomationException {
        driver.get(URL);
        WebElement objTitle = driver.findElement(By.xpath(strTitle));
        boolean WebElementStatus = CommonFunction.waitPeriod(driver, strTitle);
        if (WebElementStatus) {
            Assert.assertEquals(objTitle.getText(),"Travel The World");

            objReporter.reportpassstepWithScreenshot(URL + " opened",
                    CommonFunction.CaptureScreen(driver));
        } else {
            objReporter.reportfailedstep("Some issue in launching" + URL,
                    CommonFunction.highlightElementAndTakeScreenshot(objTitle, driver));
        }
        return this;
    }

    public BlazeDemoPage searchFlights(String from, String to) throws AutomationException {
        try {
            driver.findElement(By.name(strNameFrom)).sendKeys(from);
            driver.findElement(By.name(strNameTo)).sendKeys(to);
            driver.findElement(By.xpath(strSearchLandingPage)).click();

            Assert.assertEquals(driver.findElement(By.xpath("//*/h3")).getText(),"Flights from "+from+" to "+to+":");
            objReporter.reportpassstepWithScreenshot("Search Sucessful from "+from+" to "+to,
                    CommonFunction.CaptureScreen(driver));
        }catch(Exception e){
            objReporter.reportfailedstep("Unable to select flight from :" + from + " to :"+to,
                    CommonFunction.highlightElementAndTakeScreenshot(driver.findElement(By.xpath(strSearchLandingPage)), driver));
        }
        return this;

    }


    public BlazeDemoPage selectFlight() throws AutomationException {
        try {
            driver.findElement(By.xpath(strSelectFlight)).click();
      }catch(Exception e){

        }
        return this;

    }


}
