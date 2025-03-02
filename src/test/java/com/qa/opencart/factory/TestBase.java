package com.qa.opencart.factory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import com.aventstack.chaintest.plugins.ChainTestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.LogoutPage;
import com.qa.opencart.pages.MyAccountPage;
import com.qa.opencart.pages.ProductDetailsPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.ResultsPage;
import com.qa.opencart.utilities.Constants;

import static com.qa.opencart.factory.WebDriverFactory.readPropertyValue;
@Listeners(ChainTestListener.class)
public class TestBase {
    public HomePage homePg;
    public RegistrationPage regPg;
    public LoginPage loginPg;
    public LogoutPage logoutPg;
    public MyAccountPage myaccountPg;
    public ResultsPage resultPg;
    public ProductDetailsPage productDetailPg;
    public static WebDriver driver;
    public ResourceBundle rb;// to read config.properties
    public WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(TestBase.class.getName());

    @Parameters({"browser"})
    @BeforeClass
    public void commonSetUp(@Optional("chrome") String browser) throws IOException {
        // rb = ResourceBundle.getBundle("config");// Load config.properties
        ChainTestListener.log("setting the driver");

        driver = WebDriverFactory.getInstance(browser).getDriver();

        ChainTestListener.log("create object for WebDriverWait class");

        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
        // get url from config.properties file
        ChainTestListener.log("open the application url :" + readPropertyValue("appUrl"));

        driver.get(readPropertyValue("appUrl"));
    }
    /**
     * this method verifies the given urls with status code
     * @param url
     * @throws IOException
     */
    public static void verifyUrls(String url) throws IOException {
        //create object for URL class
        URL urlobj=new URL(url);
        //open the connection
        HttpURLConnection hc=(HttpURLConnection) urlobj.openConnection();
        //connect to the given url
        hc.connect();
        //fetch the response statuscode
        int respStatusCode=hc.getResponseCode();

        //get response message
        String respMsg=hc.getResponseMessage();
        //compare the status codes
        if(respStatusCode==200) {
            System.out.println(url+" is working fine:"+respStatusCode+" "+respMsg);
        }else if(respStatusCode>=400) {
            System.err.println(url+" is not working/broken :"+respStatusCode+" "+respMsg);
        }

        //disconnect the url
        hc.disconnect();

    }

    //adding screenshots to the test method
    @AfterMethod
    public void attachScreenshot(ITestResult result,@Optional("chrome") String browser){
        if(!result.isSuccess()){
            ChainTestListener.embed(WebDriverFactory.getInstance(browser).takeScreenshot(), "image/png");
        }
    }
    @Parameters({"browser"})
    @AfterClass

    public void commonTearDown(@Optional("chrome") String browser) {

        ChainTestListener.log("close the browser");

        if (driver != null) {

            WebDriverFactory.getInstance(browser).quitDriver();

        }
    }

}