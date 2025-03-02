package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginPageTest extends TestBase {

    @BeforeClass
    public void pageClassInstantiationSetUp() throws InterruptedException {
        homePg=new HomePage(driver);
        regPg=new RegistrationPage(driver);
        loginPg=new LoginPage(driver);
        myaccountPg=new MyAccountPage(driver);
        logoutPg=new LogoutPage(driver);
        homePg.navigateToLoginPage();
        loginPg.waitForPageLoad(1000);
    }

    @Test(description = "TC01_Verify the login page title test")
    public void tc01_verify_the_Login_Page_Title_Test(){
        ChainTestListener.log("TC01_Verify the login page title test");
        Assert.assertEquals(loginPg.getLoginPageTitle(), Constants.LOGIN_PAGE_TITLE);
    }

    @Test(description = "TC02_Verify the login page url test")
    public void tc02_verify_the_Login_Page_Url_Test(){
        ChainTestListener.log("TC02_Verify the login page url test");
        Assert.assertTrue(loginPg.getLoginPageUrl().contains(Constants.LOGIN_PAGE_FRACTION_URL));
    }

    @Test(description = "TC03_Verify the login page elements test",dependsOnMethods = {"tc02_verify_the_Login_Page_Url_Test"})
    public void tc03_verify_the_Login_Page_Elements_Test(){
        ChainTestListener.log("TC03_Verify the login page elements test");
        Assert.assertTrue(loginPg.isLoginBreadCrumbExists());
        Assert.assertTrue(loginPg.isNewCustomerHeadrExists());
        Assert.assertTrue(loginPg.isReturningCustomerExists());
    }

    @Test(description = "TC04_Verify navigate to Registration page from Login page test")
    public void tc04_verify_Navigate_To_RegisterPage_From_LoginPg_Test() throws InterruptedException {
        ChainTestListener.log("TC04_Verify navigate to Registration page from Login page test");
       loginPg.clickOnNewCustomerContinueBtn();
        ChainTestListener.log("Wait for 1sec to load register pg");
        regPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify Register Page title");
        Assert.assertEquals(regPg.getRegisterPageTitle(),Constants.REGISTER_PAGE_TITLE);
        ChainTestListener.log("navigate to login page");
        regPg.navigateToLoginPage();
    }

    @Test(description = "TC05_Verify empty credentials err message test")
    public void tc05_verify_Empty_Credentials_Errmsg_Test() throws InterruptedException {
        ChainTestListener.log("TC05_Verify empty credentials err message test");
        loginPg.doLogin(" "," ");
        ChainTestListener.log("Verify the error message");
        Assert.assertTrue(loginPg.getEmptyCredErrMsg().contains(Constants.LOGIN_PAGE_EMPTY_CRED_ERRMSG));
    }

    @Test(description = "TC06_Verify valid credentials test",dependsOnMethods = {"tc05_verify_Empty_Credentials_Errmsg_Test"})
    public void tc06_verify_Valid_Credentials_Test() throws InterruptedException, IOException {
        ChainTestListener.log("TC06_Verify valid credentials test");
        loginPg.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        ChainTestListener.log("Wait for My Account Pg");
        myaccountPg.waitForPageLoad(1000);
        Assert.assertEquals(myaccountPg.getMyAccPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description = "TC07_Verify logout from myAccount pg test",dependsOnMethods = {"tc06_verify_Valid_Credentials_Test"})
    public void tc07_verify_Logout_From_MyAccPg_Test() throws InterruptedException, IOException {
        ChainTestListener.log("TC07_Verify logout from myAccount pg test");
        myaccountPg.clickOnlogOutLink();
        ChainTestListener.log("Wait for Logoutpg load");
        logoutPg.waitForPageLoad(1000);
        Assert.assertEquals(logoutPg.getLogOutPageTitle(),Constants.LOGOUT_PAGE_TITLE);
        logoutPg.clickOnContinueBtn();
        homePg.waitForPageLoad(1000);
        Assert.assertEquals(homePg.getHomePageTitle(),Constants.HOME_PAGE_TITLE);
    }
}
