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

import java.util.List;

public class LoginPage extends WebDriverUtils {
    private Logger log =LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="(//div[@class='well']/ h2)[1]")
    private WebElement newCustomerHeader;

    @FindBy(xpath="//a[text()='Continue']")
    private WebElement newCustomerContinueBtn;

    @FindBy(xpath="//h2[text()='Returning Customer']")
    private WebElement returningCustomerHeader;

    @FindBy(css="input[name='email']")
    private WebElement emailEditBox;

    @FindBy(id="input-password")
    private WebElement passwordEditBox;

    @FindBy(xpath ="(//a[text()='Forgotten Password'])[1]")
    private WebElement forgottenPswdLink;

    @FindBy(css="input[class='btn btn-primary'][value='Login']")
    private WebElement loginBtn;

    @FindBy(css="i[class='fa fa-home']")
    private WebElement breadCrumbHomeIcon;

    @FindBy(xpath="//ul[@class='breadcrumb']/li[last()]/a")
    private WebElement breadCrumbLoginIcon;

    @FindBy(css="div[class='alert alert-danger alert-dismissible']")
    private WebElement emptyCredErrorMsg;


   public String getLoginPageTitle(){
       return getTitle();
   }

    public String getLoginPageUrl(){

       return waitForUrlContains(Constants.LOGIN_PAGE_FRACTION_URL);
    }

   public boolean isLoginBreadCrumbExists(){

       return isDisplayed(breadCrumbLoginIcon);
   }

    public boolean isNewCustomerHeadrExists(){

        return isDisplayed(newCustomerHeader);
    }

    public boolean isReturningCustomerExists(){

        return isDisplayed(returningCustomerHeader);
    }

   public void clickOnNewCustomerContinueBtn(){
       try {
           ChainTestListener.log("Click on Login page- New Customer continue btn");
           click(newCustomerContinueBtn);
       } catch (NoSuchElementException | InterruptedException e) {
          e.printStackTrace();
       }
   }

   public void navigateToForgottenPswdPage() throws InterruptedException {
       try{
           ChainTestListener.log("Click on Login page- Forgotpassword link");
           click(forgottenPswdLink);
       }catch (NoSuchElementException | InterruptedException e) {
           e.printStackTrace();
       }


   }

    public void doLogin(String email,String pswd) throws InterruptedException {
       try{
           ChainTestListener.log("In Login page- Type email in email_editbox");
           type(emailEditBox,email);
           ChainTestListener.log("In Login page- Type password in pswd_editbox");
           type(passwordEditBox,pswd);
           ChainTestListener.log("In Login page- Click on login button");
           click(loginBtn);
       }catch (NoSuchElementException | InterruptedException e) {
           e.printStackTrace();
       }

    }

    public void navigateToHomePage() throws InterruptedException {
       try{
           ChainTestListener.log("In Login page- Click on breadcrumb home icon");
           click(breadCrumbHomeIcon);
       }catch (NoSuchElementException | InterruptedException e) {
           e.printStackTrace();
       }
    }

    public String getEmptyCredErrMsg(){

       return emptyCredErrorMsg.getText();
    }



}
