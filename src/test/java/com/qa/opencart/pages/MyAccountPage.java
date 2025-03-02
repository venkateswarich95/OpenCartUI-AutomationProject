package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class MyAccountPage extends WebDriverUtils {
    private Logger log =LogManager.getLogger(MyAccountPage.class);

    public MyAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//span[text()='My Account']")
    private WebElement myAccMenu;

    @FindBy(css="input[placeholder='Search']")
    private WebElement searchEditBox;

    @FindBy(css="button[class='btn btn-default btn-lg'][type='button']")
    private WebElement searchTorchIcon;

    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']/li[last()]/a")
    private WebElement logOutLink;

    @FindBy(xpath="//ul[@class='breadcrumb']/li[2]/a")
    private WebElement accountBreadCrumb;

    @FindBy(css ="div[id='content'] h2")
    private List<WebElement> myAccHeadersList;

    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']/li/a")
    private List<WebElement> myAccMenuOptList;

    @FindBy(css="i[class='fa fa-home']")
    private WebElement breadCrumbHomeIcon;


   public String getMyAccPageTitle(){
       return getTitle();
   }

    public String getMyAccPageUrl(){

       return waitForUrlContains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL);
    }

   public boolean isSearchEditboxExists(){

       return isDisplayed(searchEditBox);
   }

    public boolean isMyAccHeaderListExists() {
        for (WebElement element : myAccHeadersList) {
            if (!element.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

   public void clickOnMyAccMenu(){
       try {
           ChainTestListener.log("Click on My Account page- my account menu");
           click(myAccMenu);
       } catch (NoSuchElementException | InterruptedException e) {
          e.printStackTrace();
       }
   }

   public boolean isLogoutExists() throws InterruptedException {
       ChainTestListener.log("Click on My Account page- my account menu");
       click(myAccMenu);
       ChainTestListener.log("Verify logout link is visible or not");
       return isDisplayed(logOutLink);
   }

    public void clickOnlogOutLink(){
        try {
            ChainTestListener.log("Click on My Account page- myAccMenu logout link");
            click(myAccMenu);
            click(logOutLink);
        } catch (NoSuchElementException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void navigateToHomePage() throws InterruptedException {
       try{
           ChainTestListener.log("In My Account page- click on breadcrumb home icon to navigate to home pg");
           click(breadCrumbHomeIcon);
       }catch (NoSuchElementException | InterruptedException e) {
           e.printStackTrace();
       }
    }

    public List<String> getMyAccMenuOptionList(){
       List<String> myAccMenuOptionTxtList=new ArrayList<>();
       try{
          clickOnMyAccMenu();
          for(WebElement e:myAccMenuOptList){
              String text=e.getText();
              myAccMenuOptionTxtList.add(text);
          }
       }catch (Exception ex){
        ex.printStackTrace();
       }

       return myAccMenuOptionTxtList;
    }

    public List<String> getMyAccHeaderList(){
        List<String> myAccHeadrTxtList=new ArrayList<>();
        try{
            for(WebElement e:myAccHeadersList){
                String text=e.getText();
                myAccHeadrTxtList.add(text);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return myAccHeadrTxtList;
    }

    public ResultsPage doProductSearch(String productName){
        ChainTestListener.log("Searching for the product:"+productName);
        try {
           if(isSearchEditboxExists()){
               ChainTestListener.log("Type the product name in search editbox");
               type(searchEditBox,productName);
               ChainTestListener.log("Click on search torch icon");
               click(searchTorchIcon);
           }
        }catch (NoSuchElementException | InterruptedException e) {
            ChainTestListener.log("unable to search for the product: as search edit box is not present");
        }
        return  new ResultsPage(driver);
    }

    public void pressEscapeKey(){
       Actions act=new Actions(driver);
       act.sendKeys(Keys.ESCAPE).perform();
    }



}
