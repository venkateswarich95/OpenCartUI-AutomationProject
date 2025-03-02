package com.qa.opencart.factory;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.io.FileHandler;
import com.qa.opencart.utilities.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.qa.opencart.factory.TestBase.driver;

public class WebDriverFactory {

    private static final Logger log = LogManager.getLogger(WebDriverFactory.class.getName());
    public ResourceBundle rb;// to read config.properties
    public static String highlight;
    // Singleton
    private static volatile WebDriverFactory instance;
    private static ThreadLocal<WebDriver>tlDriver = new ThreadLocal<>();
    //create a private constructor
    private WebDriverFactory(){}
    private void initDriver(String browser){
        switch (browser){
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                //   chromeOptions.addArguments("--incognito");
                //   chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--disable-gpu"); // Optional, improves compatibility
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.setAcceptInsecureCerts(true);
                tlDriver.set(new ChromeDriver(chromeOptions));
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary("\"C:\\Program Files\\Mozilla Firefox\\firefox.exe\"");
                //firefoxOptions.addArguments("--private");
                firefoxOptions.addArguments("disable-infobars");
                firefoxOptions.setAcceptInsecureCerts(true);
                firefoxOptions.addArguments("--headless=new");
                tlDriver.set(new FirefoxDriver(firefoxOptions));
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--inprivate");
                edgeOptions.addArguments("disable-infobars");
                edgeOptions.setAcceptInsecureCerts(true);
                tlDriver.set(new EdgeDriver(edgeOptions));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser:"+browser);
        }
        //maximize the window
        getDriver().manage().window().maximize();
    }
    //create a getInstance method
    public static WebDriverFactory getInstance(String browser){
        if(instance==null){
            synchronized (WebDriverFactory.class){
                if(instance==null){
                    instance = new WebDriverFactory();
                }
            }
        }
        if(tlDriver.get()==null){
            instance.initDriver(browser);
        }
        return instance;
    }
    public static WebDriver getDriver(){
        return tlDriver.get();
    }

    public static void quitDriver(){
        if(tlDriver.get()!=null){
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }

    /**
     * This method reads the property value from properties file
     * @param key
     * @return
     *
     */

    public static String readPropertyValue(String key) throws IOException {

        ChainTestListener.log("Create Object for Properties class");

        Properties prop = new Properties();

        ChainTestListener.log("Read the properties file");

        try {

            FileInputStream fis = new FileInputStream(new File(Constants.CONFIG_DIRECTORY));

            prop.load(fis);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

        return prop.getProperty(key);

    }

    public static String randomeString() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        return (generatedString);
    }

    public static String randomeNumber() {
        String generatedString2 = RandomStringUtils.randomNumeric(10);
        return (generatedString2);
    }
    /**
     * generate the random email
     *
     */
    public static String randomAlphaNumeric() {
        String st = RandomStringUtils.randomAlphabetic(4);
        String num = RandomStringUtils.randomNumeric(3);

        return (st + "@" + num);
    }


    /**
     * takescreenshot
     * @return
     */

    public byte[] takeScreenshot(){
        return ((TakesScreenshot)(driver)).getScreenshotAs(OutputType.BYTES);
    }
    public static String getScreenshot(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = Constants.USER_DIRECTORY + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (Exception e) {
            e.getMessage();
        }
        return destination;

    }

}