package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.TimeUtils;
import com.qa.opencart.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ResultsPage extends WebDriverUtils {
    private Logger log =LogManager.getLogger(ResultsPage.class);

    public ResultsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="div[id='content'] h1")
    private WebElement searchResultHeader;

    @FindBy(xpath="//ul[@class='breadcrumb']/li[2]/a")
    private WebElement searchBreadCrumb;

    @FindBy(css="div[class^='product-layout product-grid']")
    private List<WebElement> searchProductList;

    @FindBy(xpath="//span[text()='My Account']")
    private WebElement myAccMenu;

    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']/li[1]/a")
    private WebElement myAccMenuOpt;


   public String getResultsPageTitle(){
       return getTitle();
   }

    public String getResultsPageUrl(){

       return waitForUrlContains(Constants.RESULTS_PAGE_FRACTION_URL);
    }

   public boolean isSearchResultHeaderExists(){

       return isDisplayed(searchResultHeader);
   }

    public boolean isSearchBreadCrumbExists(){

        return isDisplayed(searchBreadCrumb);
    }

  public void navigateToMyAccPage(){
       try{
           ChainTestListener.log("Click on My Account menu");
               click(myAccMenu);
           ChainTestListener.log("Click on My Account option");
               click(myAccMenuOpt);
       } catch (NoSuchElementException | InterruptedException e) {
           ChainTestListener.log("Not able to navigate to My Account Page");
           e.printStackTrace();
       }
  }

  public void selectProduct(String productName){
       try{
           ChainTestListener.log("Select the desired product from results:"+productName);
           click(getElement(By.linkText(productName)));
           TimeUtils.smallWait();
       } catch (NoSuchElementException | InterruptedException e) {
          ChainTestListener.log("Product name is not found");
          e.printStackTrace();
       }
  }

  public int getSearchProductListSize(){
      ChainTestListener.log("Fetch the total products count in Search results page");
       return searchProductList.size();
  }
}
