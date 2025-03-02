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

public class RegistrationPage extends WebDriverUtils {
    private Logger log =LogManager.getLogger(RegistrationPage.class);

    public RegistrationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="div[id='content'] h1")
    private WebElement registerAccHeader;

    @FindBy(id="input-firstname")
    private WebElement fNameEditBox;

    @FindBy(name="lastname")
    private WebElement lNameEditBox;

    @FindBy(css="input[placeholder='E-Mail']")
    private WebElement emailEditbox;

    @FindBy(name="telephone")
    private WebElement telephoneEditbox;

    @FindBy(id="input-password")
    private WebElement pswdEditbox;

    @FindBy(css="input[placeholder='Password Confirm']")
    private WebElement confirmPswdEditbox;

    @FindBy(css="label[class='radio-inline'] input[type='radio'][value='1']")
    private WebElement yesSubscribeRadioBtn;

    @FindBy(css="label[class='radio-inline'] input[type='radio'][value='0']")
    private WebElement noSubscribeRadioBtn;

    @FindBy(css="input[type='checkbox'][name='agree']")
    private WebElement privacyPolicyCheckBox;

    @FindBy(css="input[value='Continue']")
    private WebElement continueBtn;

    @FindBy(css="i[class='fa fa-home']")
    private WebElement breadCrumbHomeIcon;

    @FindBy(xpath="//ul[@class='breadcrumb']/li[2]/a")
    private WebElement accountBreadCrumb;

    @FindBy(xpath="//ul[@class='breadcrumb']/li[3]/a[text()='Register']")
    private WebElement registerBreadCrumb;

    @FindBy(css="div[class='alert alert-danger alert-dismissible']")
    private WebElement agreeErrMsg;

    @FindBy(css="div[id='content'] h1")
    private WebElement accCreatedHeadr;

    @FindBy(xpath="//div[@id='content']/p[1]")
    private WebElement accCreatedSuccMsg;

    @FindBy(css="div[class='pull-right'] a")
    private WebElement accCreatedContinueBtn;

    @FindBy(xpath = "//a[text()='contact us']")
    private WebElement accCreatedContactUsLink;

    @FindBy(xpath = "//ul[@class='breadcrumb']/li[last()]/a")
    private WebElement accountCreatedBreadCrumbSuccessLink;

    @FindBy(xpath = "//a[text()='login page']")
    private WebElement regPgLoginLink;


    public String getRegisterPageTitle(){
       return getTitle();
   }

    public String getRegisterPageUrl(){

        return waitForUrlContains(Constants.REGISTER_PAGE_FRACTION_URL);
    }

    public void navigateToHomePage() throws InterruptedException {
        try{
            ChainTestListener.log("In Register page- Click on breadcrumb home icon");
            click(breadCrumbHomeIcon);
        }catch (NoSuchElementException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void navigateToLoginPage() throws InterruptedException {
        try{
            ChainTestListener.log("In Register page- Click on login link");
            click(regPgLoginLink);
        }catch (NoSuchElementException | InterruptedException e) {
            e.printStackTrace();
        }
    }

   public boolean isRegisterAccHeaderExists(){

        return isDisplayed(registerAccHeader);
   }

   public void clickOnContinueBtn(){
        try{
            if(continueBtn.isDisplayed() && continueBtn.isEnabled()){
                ChainTestListener.log("Click on Register page- Continue button");
                click(continueBtn);
            }
        } catch (NoSuchElementException | InterruptedException e) {
            ChainTestListener.log("Continue btn in register page is not found");
            e.printStackTrace();
        }

   }

    public void checkAgreeChckBox(){
        try{
            if(!privacyPolicyCheckBox.isSelected()){
                ChainTestListener.log("Click on Register page- privacyPolicyCheckBox");
                click(privacyPolicyCheckBox);
            }
        } catch (NoSuchElementException | InterruptedException e) {
            ChainTestListener.log("privacy Policy CheckBox in register page is not found");
            e.printStackTrace();
        }
    }

    public void selectSubscribe(String option){
        try{
            if(option.equalsIgnoreCase("Yes")){
                ChainTestListener.log("Select subscribe Yes radio button");
                click(yesSubscribeRadioBtn);
            }else {
                ChainTestListener.log("Select subscribe No radio button");
                click(noSubscribeRadioBtn);
            }
        } catch (NoSuchElementException | InterruptedException e) {
            ChainTestListener.log("Subscribe radio button is not found");
           e.printStackTrace();
        }
    }

    public String getAccCreatedHeader() throws InterruptedException {
        return getText(accCreatedHeadr).trim();
    }

    public String getAccCreatedSuccMsg() throws InterruptedException {
        return getText(accCreatedSuccMsg).trim();
    }

    public WebElement getAccountCreatedHeaderElement() throws InterruptedException{
        return accCreatedHeadr;
    }


    public WebElement getAccountCreatedBreadCrumbSuccessLink(){
        return accountCreatedBreadCrumbSuccessLink;
    }

    public WebElement getAccountCreatedSuccMsg(){
        return accCreatedSuccMsg;
    }

    public void ClickOnAccCreatedContinuBtn() throws InterruptedException {
        try{
            ChainTestListener.log("Click on Account created- Continue button");
            click(accCreatedContinueBtn);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
           }

    public void ClickOnAccCreatedContactUsLink() throws InterruptedException {
        try{
            ChainTestListener.log("Click on Account created- Contact Us link");
            click(accCreatedContactUsLink);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void setFirstNameEditBox(String firstName) throws InterruptedException {
        try{
            ChainTestListener.log("In Register Page- Type first_Name in fNameEditBox");
            type(fNameEditBox,firstName);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

    }

    public String getFirstNameEditBox() throws InterruptedException {
        return getElementAttributeValue(fNameEditBox,"value");
    }
    public void setLastNameEditBox(String lastName) throws InterruptedException {
        try{
            ChainTestListener.log("In Register Page- Type last_Name in lNameEditBox");
            type(lNameEditBox,lastName);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

    }

    public String getLastNameEditBox() throws InterruptedException {
        return getElementAttributeValue(lNameEditBox,"value");
    }

    public void setEmailEditBox(String email) throws InterruptedException {
        try{
            ChainTestListener.log("In Register Page- Type email in emailEditbox");
            type(emailEditbox,email);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

    }

    public String getEmailditBox() throws InterruptedException {
        return getElementAttributeValue(emailEditbox,"value");
    }

    public void setTelephoneEditbox(String telePhone) throws InterruptedException {
        try{
            ChainTestListener.log("In Register Page- Type telephone_No in telephoneEditbox");
            type(telephoneEditbox,telePhone);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

    }

    public String getTelephoneEditBox() throws InterruptedException {
        return getElementAttributeValue(telephoneEditbox,"value");
    }


    public void setPswdEditbox(String pswd) throws InterruptedException {
        try{
            ChainTestListener.log("In Register Page- Type password in pswdEditbox");
            type(pswdEditbox,pswd);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

    }

    public String getPswdEditBox() throws InterruptedException {
        return getElementAttributeValue(pswdEditbox,"value");
    }


    public void setConfirmPswdEditbox(String cPswd) throws InterruptedException {
        try{
            ChainTestListener.log("In Register Page- Type Confirm_password in confirmPswdEditbox");
            type(confirmPswdEditbox,cPswd);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

    }

    public String getConfirmPswdditBox() throws InterruptedException {
        return getElementAttributeValue(confirmPswdEditbox,"value");
    }

    public void setPersonalDetails(String fname,String lname,String email,String telePh) throws InterruptedException {
        setFirstNameEditBox(fname);
        setLastNameEditBox(lname);
        setEmailEditBox(email);
        setTelephoneEditbox(telePh);
    }

    public void setPswd(String pswd,String cPswd) throws InterruptedException {
        setPswdEditbox(pswd);
        setConfirmPswdEditbox(cPswd);
    }




}
