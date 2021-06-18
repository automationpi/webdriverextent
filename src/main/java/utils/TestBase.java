package utils;

import com.relevantcodes.extentreports.ExtentReports;
import org.testng.log4testng.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;


import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class TestBase {



    public static String CurrentDirectory = "";
    public static String SuiteName = "";
    public static String ReportPath = "";
    public static String Date = "";
    public static String reportName = "";
    static int passCount = 0;
    static int Totaltest = 0;
    public String testName = "";
    public String timeStamp;
    public String screenShotPath = "";
    private final static Logger logger = Logger.getLogger(TestBase.class);
    @BeforeMethod
    public void beforeMethod() {

    }
    /*
     * Getting test name from testng xml using before test annotation
     */
    @BeforeTest
    public void beforeTest(final ITestContext testContext) {
        testName = testContext.getName();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        Totaltest++;
        logger.info(" ****  Test Completed :"+passCount+" ****");

        try {
        passCount++;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
     * Creating preparing directory and report using before suit annotation
     */
    @BeforeSuite
    public void BeforeSuite(ITestContext ctx) {
        System.out.println("" +
                " ____  _     _____  ____  _      ____  _____  _  ____  _     \n" +
                "/  _ \\/ \\ /\\/__ __\\/  _ \\/ \\__/|/  _ \\/__ __\\/ \\/  _ \\/ \\  /|\n" +
                "| / \\|| | ||  / \\  | / \\|| |\\/||| / \\|  / \\  | || / \\|| |\\ ||\n" +
                "| |-||| \\_/|  | |  | \\_/|| |  ||| |-||  | |  | || \\_/|| | \\||\n" +
                "\\_/ \\|\\____/  \\_/  \\____/\\_/  \\|\\_/ \\|  \\_/  \\_/\\____/\\_/  \\|\n" +
                "                                                             " +
                                            "    ################################  \n" +
                                            "    ##################################  \n" +
                                            "   ###################################  \n" +
                                            "   ####    #####         ####           \n" +
                                            "  ###      #####        #####           \n" +
                                            "  #        #####        #####           \n" +
                                            "  #        #####        ####            \n" +
                                            "           ####         ####            \n" +
                                            "           ####         ####            \n" +
                                            "           ####         ####            \n" +
                                            "          #####        #####            \n" +
                                            "          #####        #####            \n" +
                                            "          ####         #####            \n" +
                                            "          ####         #####            \n" +
                                            "         #####         #####            \n" +
                                            "        ######         #####            \n" +
                                            "        #####          #####        ##  \n" +
                                            "       #######         ######       ##  \n" +
                                            "      #######           ##############  \n" +
                                            "     #######            #############   \n" +
                                            "     #######             ###########    \n" +
                                            "     ######               #########     ");


        try {
            CurrentDirectory = CommonFunction.getCurrentDirectory();
            timeStamp = new SimpleDateFormat("dd-MMM-yyyy'T'HH-mm-ss").format(new Timestamp(System.currentTimeMillis()));
            Date = new SimpleDateFormat("dd-MMM-yyyy").format(new Timestamp(System.currentTimeMillis()));
            SuiteName = "Test_Result" + "_" + ctx.getCurrentXmlTest().getSuite().getName() + "_" + timeStamp;
            ReportPath = CurrentDirectory + "\\reports\\latest\\" + SuiteName;
            File src = new File(CurrentDirectory + "\\reports\\latest");
            CommonFunction.deleteDirectory(src);
            src = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonFunction.createScreenshotFolder(ReportPath + "\\Screenshot");
        screenShotPath = ReportPath + "\\Screenshot";
        Reporting.extent = new ExtentReports(ReportPath + "\\" + SuiteName + ".html", true);
        logger.info("Running report name - " + reportName);
    }
    /*
     * Using After suite annotation to close report and send email with report
     */
    @AfterSuite
    public void afterSuite(ITestContext ctx) throws IOException {
        Reporting.reportflush();

        try {
            File src = new File(CurrentDirectory + "\\reports\\latest");
            File trg = new File(CurrentDirectory + "\\reports\\archive");
            CommonFunction.copy(src, trg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

