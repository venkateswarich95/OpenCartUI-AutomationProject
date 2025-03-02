package com.qa.opencart.testcases;

import com.qa.opencart.factory.TestBase;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.qa.opencart.factory.WebDriverFactory.readPropertyValue;

public class ProductDetailsPageTest extends TestBase{
    private Logger log =LogManager.getLogger(ProductDetailsPageTest.class.getName());


    @BeforeClass
    public void methodName() throws InterruptedException, IOException{
        homePg = new HomePage(driver);
        regPg = new RegistrationPage(driver);
        loginPg = new LoginPage(driver);
        myaccountPg = new MyAccountPage(driver);
        logoutPg = new LogoutPage(driver);
        resultPg = new ResultsPage(driver);
        productDetailPg = new ProductDetailsPage(driver);
        log.info("navigate to login page");
        homePg.navigateToLoginPage();
        loginPg.doLogin(readPropertyValue("username"),readPropertyValue("pwd"));
        myaccountPg.waitForPageLoad(2000);
        log.info("Verify my account page title");
        Assert.assertEquals(myaccountPg.getTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }


    @Test(description="TC01_Verify product Image Test",dataProvider="productImages",priority=1)
    public void TC01_verifyProductImageTest(String searchkeyword,String productName,int imgCount) throws InterruptedException, IOException{
        log.info("TC01_Perform the product search Test");
        resultPg =myaccountPg.doProductSearch(searchkeyword);
        resultPg.waitForPageLoad(2000);
        log.info("Verify the results page title");
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(resultPg.getSearchProductListSize()>0);
        resultPg.selectProduct(productName);
        int actualimageCount = productDetailPg.getProductImageCount();
        sa.assertEquals(actualimageCount,imgCount);
        resultPg.navigateToMyAccPage();
        myaccountPg.waitForPageLoad(2000);
        sa.assertAll();
    }

    @DataProvider(name="productImages")
    public Object[][] productImageTestData(){
        return new Object[][]{
                {"Samsung","Samsung SyncMaster 941BW",1},
                {"MacBook","MacBook Air",4},
                {"iMac","iMac",3},
                {"Apple","Apple Cinema 30\"",6}
        };
    }
    @Test(description="TC02_Verify product metadata Test",priority=2)
    public void TC02_verifyProductMetadataTest() throws InterruptedException, IOException{
        log.info("TC02_Verify product metadata Test");
        String productName="MacBook";
        resultPg =myaccountPg.doProductSearch(productName);
        resultPg.waitForPageLoad(2000);
        log.info("Verify the results page title");
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(resultPg.getSearchProductListSize()>0);
        resultPg.selectProduct("MacBook Pro");
        Map<String,String> actualProductInfoMap =productDetailPg.getProductInformation();
        sa.assertEquals(actualProductInfoMap.get("Brand"),"Apple");
        sa.assertEquals(actualProductInfoMap.get("Product Code"),"Product 18");
        sa.assertEquals(actualProductInfoMap.get("Reward Points"),"800");
        sa.assertEquals(actualProductInfoMap.get("Availability"),"Out Of Stock");
        sa.assertEquals(actualProductInfoMap.get("actual price"),"$2,000.00");
        resultPg.navigateToMyAccPage();
        myaccountPg.waitForPageLoad(2000);
        sa.assertAll();
    }

    @Test(description="TC03_Verify logout from MyAccount Test",dependsOnMethods="TC02_verifyProductMetadataTest")
    public void TC09_verifyLogoutTest() throws InterruptedException, IOException{
        log.info("TC03_Verify logout from MyAccount Test");
        myaccountPg.clickOnlogOutLink();
        logoutPg.waitForPageLoad(2000);
        log.info("Verify the my account page title");
        Assert.assertEquals(logoutPg.getTitle(),Constants.LOGOUT_PAGE_TITLE);
        logoutPg.clickOnContinueBtn();
        homePg.waitForPageLoad(2000);
        Assert.assertEquals(homePg.getHomePageTitle(),Constants.HOME_PAGE_TITLE);
    }

}