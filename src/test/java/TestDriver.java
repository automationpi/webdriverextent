import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import pages.BlazeDemoPage;
import pages.PurchasePage;
import utils.DriverManager;
import utils.Reporting;
import utils.TestBase;

public class TestDriver  extends TestBase {
    private final static Logger logger = Logger.getLogger(TestDriver.class);



    @Parameters({"fromPort", "toPort"})
    @Test()
    public void BlazeDemoTest(String fromPort, String toPort) {
        DriverManager objManager = new DriverManager();
        WebDriver driver = objManager.getDriver("chrome");
        Reporting oReporter =  new Reporting(
                "First_Test_Chrome");
        boolean testresult = true;
        try{
            driver.manage().window().maximize();
            oReporter.reportinfostep("Test case created BY : @Author");
            BlazeDemoPage objBlazeDemoPage = new BlazeDemoPage(driver, oReporter);
            PurchasePage objPurchasePage = new PurchasePage(driver, oReporter);
            objBlazeDemoPage
                    .openURL("https://blazedemo.com/index.php")
                    .searchFlights("Boston","London")
                    .selectFlight();
            objPurchasePage
                    .customerInformationandSubmit();


        }catch(Exception e){
            //to do
        }finally {
            driver.close();
            oReporter.reportendtest();
        }

    }
}
