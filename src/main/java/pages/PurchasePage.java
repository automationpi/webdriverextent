package pages;

import exceptions.AutomationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.CommonFunction;
import utils.Reporting;

public class PurchasePage {

    final String strNameId = "inputName";
    final String strAddressId= "address";
    final String strCityId= "city";
    final String strStateId= "state";
    final String strZipId= "zipCode";
    final String strCreditCardId= "creditCardNumber";
    final String strCreditCardYearId= "creditCardYear";
    final String strCreditCardNameId= "nameOnCard";
    final String strPurchaseButtonId= "//*/div/input[@type='submit']";
    final String strOrderIdXpath= "//*/tr[1]/td[2]";

    Reporting objReporter;
    WebDriver driver;


    public PurchasePage(WebDriver driver, Reporting objReporter) {
        this.objReporter = objReporter;
        this.driver = driver;
    }


    public PurchasePage customerInformationandSubmit() throws AutomationException {
        try {
            driver.findElement(By.id(strNameId)).sendKeys("John Doe");
            driver.findElement(By.id(strAddressId)).sendKeys("Some Address");
            driver.findElement(By.id(strCityId)).sendKeys("My City");
            driver.findElement(By.id(strStateId)).sendKeys("Ping Pi");
            driver.findElement(By.id(strZipId)).sendKeys("12345");
            driver.findElement(By.id(strCreditCardId)).sendKeys("1234789636547798");
            driver.findElement(By.id(strCreditCardYearId)).sendKeys("2025");
            driver.findElement(By.id(strCreditCardNameId)).sendKeys("John Doe");

            objReporter.reportpassstepWithScreenshot("Customer information provided",
                    CommonFunction.CaptureScreen(driver));

            driver.findElement(By.xpath(strPurchaseButtonId)).click();

            Assert.assertEquals(driver.findElement(By.xpath("//*/h1")).getText(),"Thank you for your purchase today!");
            objReporter.reportpassstepWithScreenshot("Purchase Sucessful order id: "+ driver.findElement(By.xpath(strOrderIdXpath)).getText(),
                    CommonFunction.CaptureScreen(driver));
        }catch(Exception e){
            objReporter.reportfailedstep("Unable to complete purchase",
                    CommonFunction.highlightElementAndTakeScreenshot(driver.findElement(By.xpath(strOrderIdXpath)), driver));
        }
        return this;

    }

}
