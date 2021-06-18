package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.log4testng.Logger;
import exceptions.AutomationException;

public class Reporting {
    public static ExtentReports extent;
    public ExtentTest test;
    public String testName = "";
    private final static Logger logger = Logger.getLogger(Reporting.class);
    public boolean failFlag = false;

    // Constructor to initiate the extend report testcase and log object
    public Reporting(String TestcaseName) {
        test = Reporting.extent.startTest(TestcaseName);
        testName = TestcaseName;
        failFlag = false;
        logger.info("TestName - "+TestcaseName);

    }

    // method to publish the complete test suite report
    public static void reportflush() {
        // calling flush writes everything to the log file
        extent.flush();
        //  extent.close();
    }

    // method to handle the Test pass event for extent reports
    public void reportpassstep(String message) throws AutomationException {
        test.log(LogStatus.PASS, message);
    }

    // method to handle the Test failed event for extent reports
    public void reportFailStep(String message) throws AutomationException {
        test.log(LogStatus.FAIL, message);
        logger.info("Error @ " + testName);
        logger.error(message);
    }

    // method to handle the Test pass with screenshot  for extent reports
    public void reportpassstepWithScreenshot(String message, String screenShotPath) throws AutomationException {
        screenShotPath = screenShotPath.replace(TestBase.ReportPath, ".");
        String image = test.addScreenCapture(screenShotPath);
        test.log(LogStatus.PASS, message + test.addScreenCapture(screenShotPath));
    }

    // method to handle the Test fail event for extent reports
    public void reportfailedstep(String message, String screenShotPath) throws AutomationException {
        screenShotPath = screenShotPath.replace(TestBase.ReportPath, ".");
        String image = test.addScreenCapture(screenShotPath);
        test.log(LogStatus.FAIL, message + "<br>" + test.addScreenCapture(screenShotPath));
        this.failFlag = true;
        throw new AutomationException("Test Failed!");
    }

    public void reportWarningstep(String message, String screenShotPath) throws AutomationException {
        screenShotPath = screenShotPath.replace(TestBase.ReportPath, ".");
        String image = test.addScreenCapture(screenShotPath);
        test.log(LogStatus.WARNING, message + "<br>" + test.addScreenCapture(screenShotPath));
        this.failFlag = false;
    }

    // method to handle the info event for extent reports
    public void reportinfostep(String message) {
        test.log(LogStatus.INFO, message);
    }

    // method to close the testcase writing in extentend report
    public void reportendtest() {
        if(this.failFlag == true)
        extent.endTest(test);
    }

}

