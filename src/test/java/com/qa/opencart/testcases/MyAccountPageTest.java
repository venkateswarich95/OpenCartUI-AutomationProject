package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;

public class MyAccountPageTest extends TestBase {

    @BeforeClass
    public void pageClassInstantiationSetUp() throws InterruptedException, IOException {
        homePg=new HomePage(driver);
        regPg=new RegistrationPage(driver);
        loginPg=new LoginPage(driver);
        myaccountPg=new MyAccountPage(driver);
        logoutPg=new LogoutPage(driver);
        homePg.navigateToLoginPage();
        loginPg.waitForPageLoad(1000);
        loginPg.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        myaccountPg.waitForPageLoad(1000);
    }

    @Test(description = "TC01_Verify the my account page title test")
    public void tc01_verify_the_MyAccount_Page_Title_Test(){
        ChainTestListener.log("TC01_Verify the my account page title test");
        Assert.assertEquals(myaccountPg.getMyAccPageTitle(), Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description = "TC02_Verify the my account page url test")
    public void tc02_verify_the_MyAccount_Page_Url_Test(){
        ChainTestListener.log("TC02_Verify the my account page url test");
        Assert.assertTrue(myaccountPg.getMyAccPageUrl().contains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL));
    }

    @Test(description = "TC03_Verify the my account page elements test",dependsOnMethods = {"tc02_verify_the_MyAccount_Page_Url_Test"})
    public void tc03_verify_the_MyAccount_Page_Elements_Test() throws InterruptedException {
        ChainTestListener.log("TC03_Verify the login page elements test");
        Assert.assertTrue(myaccountPg.isSearchEditboxExists());
        Assert.assertTrue(myaccountPg.isLogoutExists());
        myaccountPg.pressEscapeKey();
    }

    @Test(description = "TC04_Verify My Account page myAccount menu options presence test")
    public void tc04_verify_MyAcc_Menu_Options_Presence_Test() throws InterruptedException {
        ChainTestListener.log("TC04_Verify My Account page myAccount menu options presence test");
        ChainTestListener.log("Verify myAccount menu options");
        Assert.assertEquals(myaccountPg.getMyAccMenuOptionList(),Constants.EXPECTED_MYACCONT_MENU_OPTIONS_LIST);
        myaccountPg.pressEscapeKey();
    }

    @Test(description = "TC05_Verify My Account page headers list test")
    public void tc05_verify_MyAcc_Page_Headers_List_Test() throws InterruptedException {
        ChainTestListener.log("TC05_Verify My Account page headers list test");
        ChainTestListener.log("Verify My Account page headers list");
        Assert.assertEquals(myaccountPg.getMyAccHeaderList(),Constants.EXPECTED_MYACCONT_HEADERS_LIST);
    }

    @Test(description = "TC06_Verify My Account pg broken links test")
    public void tc06_verify_Broken_Links_Test() throws InterruptedException, IOException {
        ChainTestListener.log("TC06_Verify My Account pg broken links test");
        ChainTestListener.log("Fetch all the links from My Account pg");
       List<WebElement> allLinks=driver.findElements(By.tagName("a"));
       for(WebElement link:allLinks){
           String url=link.getDomAttribute("href");
           verifyUrls(url);
       }
    }

    @Test(description = "TC07_Verify My Account pg product search test",dataProvider = "productData")
    public void tc06_verify_Product_Search_Test(String productName) throws InterruptedException, IOException {
        ChainTestListener.log("TC07_Verify My Account pg product search test");
        resultPg=myaccountPg.doProductSearch(productName);
        ChainTestListener.log("Wait for Results page to load");
        resultPg.waitForPageLoad(1000);
        SoftAssert sa=new SoftAssert();
        sa.assertEquals(resultPg.getResultsPageTitle(),"Search - "+productName);
        sa.assertTrue(resultPg.getSearchProductListSize()>0);
        resultPg.navigateToMyAccPage();
        sa.assertEquals(myaccountPg.getMyAccPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);
        sa.assertAll();
    }

    @DataProvider
    public Object[][] productData(){
        return new Object[][]{
                {"MacBook"},
                {"Canon EOS 5D"},
                {"Apple Cinema 30\""}
        };
    }

}
