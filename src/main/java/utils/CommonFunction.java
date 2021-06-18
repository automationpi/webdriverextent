package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.google.common.io.Files.copy;

public class CommonFunction {
    private final static Logger logger = Logger.getLogger(CommonFunction.class);
    /*
     * *************************************************************************
     * ****** Function Name : deleteDirectory
     * Purpose : Delete a particular directory Return
     * Type : true in case of pass and false in case f failure
     **********************************************************************************/
    public static boolean deleteDirectory(File directory) {
        CommonFunction.waitforSecs(3);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                try {
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].isDirectory()) {
                            deleteDirectory(files[i]);
                            logger.trace("Deleting  : " + files[i]);
                            files[i].delete();
                        } else {
                            logger.trace("Deleting  : " + files[i]);
                            files[i].delete();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            logger.trace("invalid directory " + directory);
        }
        return (true);
    }


    /*
     * Method to wait for given secs
     */
    public static void waitforSecs(int i) {
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /*
     * Method to get current project directory
     */
    public static String getCurrentDirectory() {
        Path currentRelativePath = Paths.get("");
        String CurrentDirectory = currentRelativePath.toAbsolutePath().toString();
        return CurrentDirectory;
    }

    /*
     * *************************************************************************
     * ****** Function Name : createScreenshotFolder
     * Purpose : Create a folder where we keep
     * our test evidences Return Type : Screen shot folder path
     **********************************************************************************/
    public static void createScreenshotFolder(String TestcaseName) {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        // Get current Time
        String date = df.format(calobj.getTime());
        // Remove space from date and time
        String date1 = date.substring(date.lastIndexOf(" ") + 1);
        // Remove ":" and "_" from date and time and make a string
        String date2 = date1.replaceAll(":", "_");
        // Create folder path
        logger.trace("Creating screenshot folder : " + TestcaseName);
        new File(TestcaseName).mkdir();
        return;
    }


    /*
     * *************************************************************************
     * ****** Function Name : copyDirectory
     * Purpose : Copy directory to another directory
     * Parameter: source directory and target directory Return Type : void
     **********************************************************************************/
    private static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }
        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

    /*
     * *************************************************************************
     * ****** Function Name : copyFile
     * Purpose : Copy files of a directory to another directory
     * Parameter: source directory and target directory Return Type : void
     **********************************************************************************/
    private static void copyFile(File source, File target) throws IOException {
        try {
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(target);
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    /*
     * *************************************************************************
     * ****** Function Name : copy
     * Purpose : Copy file of a directory to another directory
     * Parameter: source directory and target directory Return Type : void
     **********************************************************************************/
    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }



    //static boolean WebElementStatus = false;
    /*
     * *************************************************************************
     * ******************* Function Name : waitPeriod
     * Purpose : Wait to load a particular object
     * till a certain time period (35 second) Parameters : Driver and element
     * selector Return Type : If object loaded within given time period then
     * return true otherwise return false
     ************************************************************************************************/
    public static boolean waitPeriod(WebDriver driver, String objectElement) {
        boolean elementstatus = false;
        WebElement element = null;

        WebDriverWait wait = new WebDriverWait(driver, 35);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (objectElement != null) {
            element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(objectElement)));
            if (element != null)
                elementstatus = true;

        }

        return elementstatus;
    }

    /*
     * *************************************************************************
     * ****** Function Name : CaptureScreen
     * Purpose : Take screen shot of requested page on
     * requested remote driver Return Type : Screen shot folder path
     **********************************************************************************/
    public static String CaptureScreen(WebDriver driver) {
        // Get driver
        // WebDriver driver=GetDriver.driver1;
        TakesScreenshot oScn = (TakesScreenshot) driver;
        File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
        Path currentRelativePath = Paths.get("");
        // String s = currentRelativePath.toAbsolutePath().toString();
        String ImagesPath = TestBase.ReportPath + "/Screenshot/" + System.currentTimeMillis();
        File oDest = new File(ImagesPath + ".jpg");
        try {
            FileUtils.copyFile(oScnShot, oDest);
        } catch (IOException e) {
            logger.trace(e.getMessage());
        }
        return ImagesPath + ".jpg";
    }




    /*
     * *************************************************************************
     * ****** Function Name : highlightElementAndTakeScreenshot
     * Purpose : To highlight the
     * webelement before taking screen shot.
     *
     **********************************************************************************/
    public static String highlightElementAndTakeScreenshot(WebElement element, WebDriver driver) {
        if (element != null) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String oldStyle = element.getAttribute("style");

            String args = "arguments[0].setAttribute('style', arguments[1]);";
            js.executeScript(args, element, "border: 4px solid red;display:block;");

        }
        return CaptureScreen(driver);

        // js.executeScript(args, element, oldStyle);
    }

}
