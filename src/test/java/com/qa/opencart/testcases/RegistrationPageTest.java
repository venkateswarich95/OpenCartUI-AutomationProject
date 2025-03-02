package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.github.javafaker.Faker;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.ExcelUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.qa.opencart.factory.WebDriverFactory.readPropertyValue;

public class RegistrationPageTest extends TestBase{
    private Logger log =LogManager.getLogger(RegistrationPageTest.class.getName());
    String fName,lName,email,telephone;

    @BeforeTest
    public void testDataSetup(){
        ChainTestListener.log("Generating the Fake Test Data for Registration using Faker class ");
        Faker fkobj = new Faker(new Locale("en-US"));
        fName = fkobj.address().firstName();
        lName = fkobj.address().lastName();
        email = fkobj.internet().emailAddress();
        telephone = fkobj.phoneNumber().phoneNumber();
    }

    @BeforeClass
    public void methodName() throws InterruptedException, IOException{
        homePg = new HomePage(driver);
        regPg = new RegistrationPage(driver);
        loginPg = new LoginPage(driver);
        myaccountPg = new MyAccountPage(driver);
        logoutPg = new LogoutPage(driver);

    }

    @BeforeMethod
    public void navigateToRegister() throws InterruptedException{
        ChainTestListener.log("navigate toRegistration page");
        homePg.navigateToRegisterPage();
        myaccountPg.waitForPageLoad(2000);
        ChainTestListener.log("Verify registration page title");
        Assert.assertEquals(regPg.getTitle(),Constants.REGISTER_PAGE_TITLE);
    }

    @Test(description="TC01_Register an account with faker data test")
    public void TC01_verifyREgistrationWithFakerDateTest(){
        ChainTestListener.log("TC01_Register an account with faker data test");
        try{
            ChainTestListener.log("Enter personal details");
            regPg.setPersonalDetails(fName,lName,email,telephone);
            ChainTestListener.log("Set the password");
            String pwd =WebDriverFactory.randomAlphaNumeric();
            regPg.setPswdEditbox(pwd);
            regPg.setConfirmPswdEditbox(pwd);
            ChainTestListener.log("Select the subscribe option yes or no");
            regPg.selectSubscribe("Yes");
            ChainTestListener.log("Select the agree checkbox");
            regPg.checkAgreeChckBox();
            ChainTestListener.log("click on Continue button");
            regPg.clickOnContinueBtn();
            regPg.waitForPageLoad(2000);
            ChainTestListener.log("Verify the Account Creation success message and header text");
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedBreadCrumbSuccessLink()));
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedHeaderElement()));
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedSuccMsg()));
            Assert.assertEquals(regPg.getAccCreatedHeader(),Constants.YOUR_ACCNT_CREATED_HEADER);
            Assert.assertEquals(regPg.getAccCreatedSuccMsg(),Constants.YOUR_ACCNT_CREATED_SUCC_MSG);
            ChainTestListener.log("Click on Continue button in account created success page");
            regPg.ClickOnAccCreatedContinuBtn();
            ChainTestListener.log("wait for the My Account creation page");
            myaccountPg.waitForPageLoad(2000);
            Assert.assertTrue(myaccountPg.getMyAccPageUrl().contains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL));
        }catch(InterruptedException e){
            ChainTestListener.log("account creation failed");
            e.printStackTrace();
        }

    }


    @Test(description="TC02_REgister an account with excel sheet data Test",dataProvider="excelData")
    public void TC02_registerAccountsWithExcelDataTest(String fName,String lName,String telephone, String pwd,String subscribe) throws InterruptedException, IOException{
        ChainTestListener.log("TC01_Register an account with faker data test");
        int iterator = 1;
        try{
            ChainTestListener.log("account "+iterator+" creation started");
            String randomEmail = WebDriverFactory.randomeString()+"@gmail.com";
            ChainTestListener.log("Enter personal details");
            regPg.setPersonalDetails(fName,lName,randomEmail,telephone);
            ChainTestListener.log("Set the password");
            regPg.setPswdEditbox(pwd);
            regPg.setConfirmPswdEditbox(pwd);
            ChainTestListener.log("Select the subscribe option yes or no");
            regPg.selectSubscribe(subscribe);
            ChainTestListener.log("Select the agree checkbox");
            regPg.checkAgreeChckBox();
            ChainTestListener.log("click on Continue button");
            regPg.clickOnContinueBtn();
            regPg.waitForPageLoad(2000);
            ChainTestListener.log("Verify the Account Creation success message and header text");
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedBreadCrumbSuccessLink()));
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedHeaderElement()));
            wait.until(ExpectedConditions.visibilityOf(regPg.getAccountCreatedSuccMsg()));
            Assert.assertEquals(regPg.getAccCreatedHeader(),Constants.YOUR_ACCNT_CREATED_HEADER);
            Assert.assertEquals(regPg.getAccCreatedSuccMsg(),Constants.YOUR_ACCNT_CREATED_SUCC_MSG);
            ChainTestListener.log("Click on Continue button in account created success page");
            regPg.ClickOnAccCreatedContinuBtn();
            ChainTestListener.log("wait for the My Account creation page");
            myaccountPg.waitForPageLoad(2000);
            Assert.assertTrue(myaccountPg.getMyAccPageUrl().contains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL));
            ChainTestListener.log("account "+iterator+" creation Completed");
            iterator++;
        }catch(InterruptedException e){
            ChainTestListener.log("account creation failed");
            e.printStackTrace();
        }

    }

    @DataProvider(name="excelData")
    public Object[][] excelTestData() throws IOException, InvalidFormatException{
        Object[][] data = new ExcelUtils().getTestData(Constants.TEST_DATA_FILE_PATH,Constants.REGISTER_SHEET_NAME);
        return data;
    }


    @AfterMethod
    public void verifyLogoutTest() throws InterruptedException, IOException{
        ChainTestListener.log("TC09_Verify logout from MyAccount Test");
        myaccountPg.clickOnlogOutLink();
        logoutPg.waitForPageLoad(2000);
        ChainTestListener.log("Verify the my account page title");
        Assert.assertEquals(logoutPg.getTitle(),Constants.LOGOUT_PAGE_TITLE);
        logoutPg.clickOnContinueBtn();
        homePg.waitForPageLoad(2000);
        Assert.assertEquals(homePg.getHomePageTitle(),Constants.HOME_PAGE_TITLE);
    }

}