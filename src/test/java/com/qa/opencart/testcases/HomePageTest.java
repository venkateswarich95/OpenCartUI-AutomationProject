package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.utilities.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTest extends TestBase {

    @BeforeClass
    public void pageClassInstantiationSetUp(){
        homePg=new HomePage(driver);
        regPg=new RegistrationPage(driver);
        loginPg=new LoginPage(driver);
    }

    @Test(description = "TC01_Verify the home page title test")
    public void verify_the_Home_Page_Title_Test(){
        ChainTestListener.log("TC01_Verify the home page title test");
        Assert.assertEquals(homePg.getHomePageTitle(), Constants.HOME_PAGE_TITLE);
    }

    @Test(description = "TC02_Verify the home page Opencart logo test")
    public void verify_the_Home_Page_Opencart_Logo_Test(){
        ChainTestListener.log("TC02_Verify the home page Opencart logo test");
        Assert.assertTrue(homePg.isOpenCartLogoExists());
    }

    @Test(description = "TC03_Verify the home page Featured section Cards count test")
    public void verify_the_Home_Page_Featured_Section_Cards_Count_Test(){
        ChainTestListener.log("TC03_Verify the home page Featured section Cards count test");
        Assert.assertTrue(homePg.getFeaturedSectionCardsCount()==4);
    }

    @Test(description = "TC04_Verify navigate to Registration page from Home page test")
    public void verify_Navigate_To_RegisterPage_From_HomePg_Test() throws InterruptedException {
        ChainTestListener.log("TC04_Verify navigate to Registration page from Home page test");
       homePg.navigateToRegisterPage();
        ChainTestListener.log("Wait for 1sec to load register pg");
        regPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify Register Page title");
        Assert.assertEquals(regPg.getRegisterPageTitle(),Constants.REGISTER_PAGE_TITLE);
        ChainTestListener.log("Click on Register Page breadcrumb home icon");
        regPg.navigateToHomePage();
    }

    @Test(description = "TC05_Verify navigate to Login page from Home page test")
    public void verify_Navigate_To_LoginPage_From_HomePg_Test() throws InterruptedException {
        ChainTestListener.log("TC04_Verify navigate to Login page from Home page test");
        homePg.navigateToLoginPage();
        ChainTestListener.log("Wait for 1sec to load login pg");
        loginPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify Login Page title");
        Assert.assertEquals(loginPg.getLoginPageTitle(),Constants.LOGIN_PAGE_TITLE);
        ChainTestListener.log("Click on Login Page breadcrumb home icon");
       loginPg.navigateToHomePage();

    }
}
