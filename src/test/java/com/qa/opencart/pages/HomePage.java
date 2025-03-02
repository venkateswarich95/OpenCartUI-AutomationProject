package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.WebDriverUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class HomePage extends WebDriverUtils {
    private Logger log =LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="logo")
    private WebElement openCartLogo;

    @FindBy(xpath="//div[contains(@class,'product-layout')]")
    private List<WebElement> featuredProductList;

    @FindBy(xpath="//span[text()='My Account']")
    private WebElement myAccountMenu;

    @FindBy(linkText="Register")
    private WebElement registerLink;

    @FindBy(linkText="Login")
    private WebElement loginLink;

   public String getHomePageTitle(){
       return getTitle();
   }

   public boolean isOpenCartLogoExists(){
       return isDisplayed(openCartLogo);
   }

   public int getFeaturedSectionCardsCount(){
       return featuredProductList.size();
   }

   public void clickOnMyAccMenu(){
       try {
           ChainTestListener.log("Click on home page my account menu");
           click(myAccountMenu);
       } catch (NoSuchElementException | InterruptedException e) {
          e.printStackTrace();
       }
   }

   public void navigateToRegisterPage() throws InterruptedException {
       try{
           ChainTestListener.log("Click on home page my account menu");
           click(myAccountMenu);
           ChainTestListener.log("Click on My Account menu- Register link");
           click(registerLink);
       }catch (NoSuchElementException | InterruptedException e) {
           e.printStackTrace();
       }

   }

    public void navigateToLoginPage() throws InterruptedException {
       try{
           ChainTestListener.log("Click on home page my account menu");
           click(myAccountMenu);
           ChainTestListener.log("Click on My Account menu- Login link");
           click(loginLink);
       }catch (NoSuchElementException | InterruptedException e) {
           e.printStackTrace();
       }


    }



}
