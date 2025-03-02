package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage extends WebDriverUtils {
    private Logger log =LogManager.getLogger(LogoutPage.class);

    public LogoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="div[id='content'] h1")
    private WebElement accLogoutHeadr;

    @FindBy(xpath="(//div[@id='content']/p)[1]")
    private WebElement accLoggedOffMsg;

    @FindBy(css="div[class='pull-right'] a")
    private WebElement continueBtn;

    @FindBy(css="i[class='fa fa-home']")
    private WebElement breadCrumbHomeIcon;

    @FindBy(xpath="//ul[@class='breadcrumb']/li[last()]/a")
    private WebElement breadCrumbLogOut;


   public String getLogOutPageTitle(){
       return getTitle();
   }

    public String getLogoutPageUrl(){

       return waitForUrlContains(Constants.LOGOUT_PAGE_FRACTION_URL);
    }

   public boolean isAccLogoutHeadrExists(){

       return isDisplayed(accLogoutHeadr);
   }

    public boolean isAccLoggedOffMsgExists(){

        return isDisplayed(accLoggedOffMsg);
    }

    public boolean isLogoutBreadcrumbExists(){

        return isDisplayed(breadCrumbLogOut);
    }

   public void clickOnContinueBtn(){
       try {
           ChainTestListener.log("Click on Logout page- Continue button");
           click(continueBtn);
       } catch (NoSuchElementException | InterruptedException e) {
          e.printStackTrace();
       }
   }

    public void navigateToHomePage() throws InterruptedException {
       try{
           ChainTestListener.log("Click on Logout page- breadcrumb home icon");
           click(breadCrumbHomeIcon);
       }catch (NoSuchElementException | InterruptedException e) {
           e.printStackTrace();
       }


    }





}
