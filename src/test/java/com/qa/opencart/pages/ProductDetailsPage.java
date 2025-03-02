package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.TimeUtils;
import com.qa.opencart.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsPage extends WebDriverUtils {
    private Logger log =LogManager.getLogger(ProductDetailsPage.class);

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="div[id='content'] h1")
    private WebElement productHeader;

    @FindBy(xpath="//div[@id='content']/div/div[2]/ul[@class='list-unstyled'][position()=1]/li")
    private List<WebElement> productMetaDataList;

    @FindBy(xpath="//div[@id='content']/div/div[2]/ul[@class='list-unstyled'][position()=2]/li")
    private List<WebElement> productPriceList;

    @FindBy(xpath="//ul[@class='thumbnails']/li/a/img")
    private List<WebElement> productImageList;

    @FindBy(css="input[id='input-quantity']")
    private WebElement quantityEditBox;

    @FindBy(css ="button[id='button-cart']")
    private WebElement addToCartBtn;

    @FindBy(xpath="//ul[@class='breadcrumb']/li[last()]/a")
    private WebElement productBreadCrumb;

    @FindBy(css="i[class='fa fa-home']")
    private WebElement breadCrumbHomeIcon;

    @FindBy(css="div[class='alert alert-success alert-dismissible']")
    private WebElement addToCartSucMsg;

    @FindBy(linkText="shopping cart")
    private WebElement shoppingCartLink;


   public String getProductDetailsPageTitle(){
       return getTitle();
   }


   public boolean isProductHeaderExists(){

       return isDisplayed(productHeader);
   }

    public boolean isProductBreadCrumbExists(){

        return isDisplayed(productBreadCrumb);
    }

    public String getProductName() throws InterruptedException {
       return getText(productHeader);
    }

    public int getProductImageCount() throws InterruptedException {
        return productImageList.size();
    }

    public String getAddToCartSuccessMsg() throws InterruptedException {
        return getText(addToCartSucMsg).trim();
    }

    public void navigateToShoppingCartPage(){
       try{
           ChainTestListener.log("In Product Details page- click on shopping cart link");
           click(shoppingCartLink);
       } catch (NoSuchElementException | InterruptedException e) {
           ChainTestListener.log("Not able to see shopping cart link");
           e.printStackTrace();
       }
    }

   public void clickOnAddToCartBtn(){
       try {
           ChainTestListener.log("Click on add to cart button");
           click(addToCartBtn);
           TimeUtils.smallWait();
       } catch (NoSuchElementException | InterruptedException e) {
          e.printStackTrace();
       }
   }

  public void setQuantity(String quantity) throws InterruptedException {
      ChainTestListener.log("Entering the product quantity in quantity editbox");
      type(quantityEditBox,quantity);
  }

    public void navigateToHomePage() throws InterruptedException {
       try{
           ChainTestListener.log("In Product Details page- click on breadcrumb home icon");
           click(breadCrumbHomeIcon);
       }catch (NoSuchElementException | InterruptedException e) {
           e.printStackTrace();
       }
    }

   private Map<String,String> productMap;

   public void getProductMetaData(){
       ChainTestListener.log("Fetching product meta data");
       ChainTestListener.log("Product meta data count is:"+productMetaDataList.size());
       for(WebElement pmd:productMetaDataList){
           String metaTxt=pmd.getText().trim();//Brand: Apple
           ChainTestListener.log("split the string based on :");
           String[] metaDataArray=metaTxt.split(":");
           ChainTestListener.log("Fetch meta key and value");
           String metaKey=metaDataArray[0].trim();
           String metaValue=metaDataArray[1].trim();
           ChainTestListener.log("Insert meta key and value into Map");
           productMap.put(metaKey,metaValue);
       }
   }

   public void getProductPriceData(){
       ChainTestListener.log("Product meta price count is:"+productPriceList.size());
       String price=productPriceList.get(0).getText().trim();
       String extraTaxPrice=productPriceList.get(1).getText().trim();
       ChainTestListener.log("Store the price value in the map");
       productMap.put("actual price",price);
       productMap.put("actual tax price",extraTaxPrice);
   }

   public Map<String,String> getProductInformation(){
       productMap=new HashMap<>();
       getProductMetaData();
       getProductPriceData();
       return productMap;

   }



}
