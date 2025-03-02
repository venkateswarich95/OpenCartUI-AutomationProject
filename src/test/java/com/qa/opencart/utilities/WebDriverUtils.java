package com.qa.opencart.utilities;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import com.aventstack.chaintest.plugins.ChainTestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
/**
 *This class is used to write webdriver utility methods
 * created on top of webdriver methods
 */

public class WebDriverUtils{
    private Logger log= LogManager.getLogger(WebDriverUtils.class.getName());
    Actions act;
    protected WebDriver driver;
    private JavaScriptUtils jsUtil;
    private WebDriverWait wait;
    JavascriptExecutor jsx;
    // Constructor
    public WebDriverUtils(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        act = new Actions(driver);
        jsx=(JavascriptExecutor) driver;
        jsUtil=new JavaScriptUtils(driver);
        PageFactory.initElements(driver, this);
    }
    /**
     * Refresh the current browser session
     */
    public void refresh() {
        driver.navigate().refresh();
        ChainTestListener.log("The Current Browser location was Refreshed...");
        //Util.sleep(3000, "The Current Browser location was Refreshed...");
    }

    /**
     * @return Returns the Current Page Title
     */
    public String getTitle() {
        String title = driver.getTitle();
        ChainTestListener.log("Title of the page is :: " + title);
        return title;
    }

    /**
     * @return Current Browser URL
     */
    public String getURL() {
        String url = driver.getCurrentUrl();
        ChainTestListener.log("Current URL is :: " + url);
        return url;
    }

    /**
     * Navigate browser back
     */
    public void navigateBrowserBack() {
        driver.navigate().back();
        ChainTestListener.log("Navigate back");
    }

    /**
     * Navigate browser forward
     */
    public void navigateBrowserForward() {
        driver.navigate().forward();
        ChainTestListener.log("Navigate back");
    }

    /***
     * Builds the By type with given locator strategy
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @return Returns By Type
     */
    public By getByType(String locator) {
        By by = null;
        String locatorType = locator.split("=>")[0];
        locator = locator.split("=>")[1];
        try {
            if (locatorType.contains("id")) {
                by = By.id(locator);
            } else if (locatorType.contains("name")) {
                by = By.name(locator);
            } else if (locatorType.contains("xpath")) {
                by = By.xpath(locator);
            } else if (locatorType.contains("css")) {
                by = By.cssSelector(locator);
            } else if (locatorType.contains("class")) {
                by = By.className(locator);
            } else if (locatorType.contains("tag")) {
                by = By.tagName(locator);
            } else if (locatorType.contains("link")) {
                by = By.linkText(locator);
            } else if (locatorType.contains("partiallink")) {
                by = By.partialLinkText(locator);
            } else {
                ChainTestListener.log("Locator type not supported");
            }
        } catch (Exception e) {
            log.error("By type not found with: " + locatorType);
        }
        return by;
    }

    /**
     * Builds The WebElement By given locator strategy
     *
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *                tag=>example, xpath=>//example, link=>example
     *
     * @return WebElement
     * @throws InterruptedException
     */
    public WebElement getElement(By locator) throws InterruptedException {
        WebElement element=driver.findElement(locator);
        flashElement(element);
        return element;
    }
    /**
     *This method will highlight
     */

    public void flashElement(WebElement element) throws InterruptedException {

        jsUtil.flash(element);

    }

    /***
     *
     * @param locator - locator strategy,
     * @param info - Information about element, usually text on element
     * @return
     */
    public List<WebElement> getElementList(String locator, String info) {
        List<WebElement> elementList = new ArrayList<WebElement>();
        By byType = getByType(locator);
        try {
            elementList = driver.findElements(byType);
            if (elementList.size() > 0) {
                ChainTestListener.log("Element List found with: " + locator);
            } else {
                ChainTestListener.log("Element List not found with: " + locator);
            }
        } catch (Exception e) {
            log.error("Element List not found with: " + locator);
            e.printStackTrace();
        }
        return elementList;
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    /**
     * This method clicks on given element and flashes also
     * @param element
     * @throws InterruptedException
     */
    public void click(WebElement element) throws InterruptedException {
        ChainTestListener.log("wait for the element to be clickable or not");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        flashElement(element);
        ChainTestListener.log("click on the given element");
        element.click();
    }
    public boolean isClickable(WebElement element) throws InterruptedException {
        ChainTestListener.log("wait for the element to be clickable or not");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        flashElement(element);
        return element.isDisplayed();
    }
    /**
     * This method type the given value in the editbox and also flashes
     * @param element
     * @param text
     * @throws InterruptedException
     */
    public void type(WebElement element, String text) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        flashElement(element);
        element.clear();
        element.sendKeys(text);
    }
    /**
     * this method fetches the given element text and stores in string variable
     * @param element
     * @return
     * @throws InterruptedException
     */
    public String getText(WebElement element) throws InterruptedException {
        ChainTestListener.log("wait for the visibility of an element");
        wait.until(ExpectedConditions.visibilityOf(element));
        ChainTestListener.log("fetch the text for an element");
        flashElement(element);
        return element.getText();
    }
    /**
     * This method fetches the element text based on given locator
     * @param locator
     * @return
     * @throws InterruptedException
     */
    public String getText(By locator) throws InterruptedException {
        flashElement(getElement(locator));
        return getElement(locator).getText();
    }
    public void clearText(WebElement element) throws InterruptedException {
        ChainTestListener.log("wait for the element to be clickable or not");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ChainTestListener.log("clear the content in given editbox element");
        flashElement(element);
        element.clear();
    }
    public void clearTextWithBackSapce(WebElement element) throws InterruptedException {
        ChainTestListener.log("wait for the element to be clickable or not");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        flashElement(element);
        while (!element.getDomAttribute("value").toString().contentEquals("")) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    /**
     * @param locator
     * @param attribute
     * @return
     * @throws InterruptedException
     */
    public String getElementAttributeValue(By locator, String attribute) throws InterruptedException {
        flashElement(getElement(locator));
        return getElement(locator).getAttribute(attribute);
    }

    /**
     * Fetches the element Attribute value
     * @param attribute
     * @return
     * @throws InterruptedException
     */
    public String getElementAttributeValue(WebElement element, String attribute) throws InterruptedException {
        flashElement(element);
        return element.getDomProperty(attribute);
    }
    /**
     * This method fetches the element css properties
     * @param element
     * @param cssProperty
     * @return
     * @throws InterruptedException
     */
    public String getElementCssValue(WebElement element,String cssProperty) throws InterruptedException {
        flashElement(element);
        return element.getCssValue(cssProperty);
    }

    /**
     * Check if element is enabled
     * @param element
     * @return
     * @throws InterruptedException
     */
    public Boolean isEnabled(WebElement element) throws InterruptedException {
        Boolean enabled = false;
        flashElement(element);
        if (element != null) {
            enabled = element.isEnabled();
            if (enabled)
                ChainTestListener.log("Element is Enabled::"+element);
            else
                ChainTestListener.log("Element is Disabled::"+element);
        }
        return enabled;
    }
    /***
     * Check if element is enabled with locator
     * @param locator
     * @return
     * @throws InterruptedException
     */
    public Boolean isEnabled(By locator) throws InterruptedException {
        return isEnabled(getElement(locator));
    }

    /**
     * Check if element is displayed
     * @param element
     * @return
     */
    public Boolean isDisplayed(WebElement element) {
        Boolean displayed = false;
        if (element != null) {
            waitForPageLoad(1000);
            displayed = element.isDisplayed();
            if (displayed)
                ChainTestListener.log("Element is displayed::"+element);
            else
                ChainTestListener.log("Element is NOT displayed::"+element);
        }
        return displayed;
    }

    /***
     * Check if element is displayed with locator
     * @param locator
     * @return
     * @throws InterruptedException
     */
    public Boolean isDisplayed(By locator) throws InterruptedException {

        return isDisplayed(getElement(locator));
    }
    /**
     * This method checks whether particular dropdown is selected or not or checkbox is checked or not
     * @param element
     * @return
     */
    public Boolean isSelected(WebElement element) {
        Boolean selected = false;
        if (element != null) {
            selected = element.isSelected();
            if (selected)
                ChainTestListener.log("Element is selected::"+element);
            else
                ChainTestListener.log("Element  is already selected:"+element);
        }
        return selected;
    }

    /**
     * @param locator
     * @return
     * @throws InterruptedException
     */
    public Boolean isSelected(By locator) throws InterruptedException {

        return  isSelected(getElement(locator));
    }

    /**
     * Selects a check box irrespective of its state
     *
     * @param element
     * @throws InterruptedException
     *
     */
    public void check(WebElement element) throws InterruptedException {
        if (!isSelected(element)) {
            flashElement(element);
            click(element);
            ChainTestListener.log("Element is checked::"+element);
        } else {
            ChainTestListener.log("Element :: is already checked::"+element);
        }
    }
    /**
     * Selects a check box irrespective of its state, using locator
     * @param locator
     * @throws InterruptedException
     *
     */
    public void check(By locator) throws InterruptedException {
        check(getElement(locator));
    }

    /**
     * UnSelects a check box irrespective of its state
     * @param element
     * @return
     * @throws InterruptedException
     */
    public void unCheck(WebElement element) throws InterruptedException {
        if (isSelected(element)) {
            flashElement(element);
            click(element);
            ChainTestListener.log("Element is unchecked::"+element);
        } else
            ChainTestListener.log("Element is already unchecked::"+element);
    }

    /**
     * UnSelects a check box irrespective of its state, using locator
     * @param locator
     * @return
     * @throws InterruptedException
     */
    public void unCheck(By locator) throws InterruptedException {
        flashElement(getElement(locator));
        unCheck(getElement(locator));
    }

    /**
     * submit on the search forms, registration forms and submit buttons
     * @param element
     * @return
     * @throws InterruptedException
     */
    public void submit(WebElement element) throws InterruptedException {
        if (element != null){
            flashElement(element);
            element.submit();
            ChainTestListener.log("Element is submitted");
        }
    }

    /***********************SELECT Class Methods ************************/
    /**
     * @param element
     * @param optionToSelect
     */
    public void selectOptionByVisibleText(WebElement element, String optionToSelect) {
        Select sel = new Select(element);
        wait.until(ExpectedConditions.visibilityOf(element));
        sel.selectByVisibleText(optionToSelect);
        ChainTestListener.log("Selected option : " + optionToSelect);
    }
    /**
     * Select dropdown option by value attribute
     * @param element
     * @param value
     */
    public void selectOptionByValueAttribute(WebElement element, String value) {
        Select sel = new Select(element);
        sel.selectByValue(value);
    }
    /**
     * select an option by index
     * @param element
     * @param index
     */
    public void selectOptionByIndex(WebElement element, int index) {
        Select sel = new Select(element);
        sel.selectByIndex(index);
    }
    /**
     * This method fetches all the dropdown options using Select class
     * @param element
     * @return
     */
    public List<WebElement> getAllDropdownOptions(WebElement element){
        Select sel = new Select(element);
        return sel.getOptions();
    }
    /**
     * this method fetches total dropdown options count
     * @param element
     * @return
     */
    public int getDropdownSize(WebElement element) {
        return getAllDropdownOptions(element).size();
    }
    /**
     * This method fetches all the selected dropdown options
     * @param element
     * @return
     */
    public List<WebElement> getAllSelectedDropdownOptions(WebElement element){
        Select sel = new Select(element);
        return sel.getAllSelectedOptions();
    }
    /**
     * get Selected drop down value
     *
     * @param element
     * @return
     */
    public String getSelectedDropDownValue(WebElement element) {
        Select sel = new Select(element);
        return sel.getFirstSelectedOption().getText();
    }

    /**
     * This method checks given option text is present in the dropdown or not
     * @param element
     * @param optionToVerify
     * @throws InterruptedException
     */
    public boolean isDropdownOptionExists(WebElement element, String optionToVerify) throws InterruptedException {
        Select sel = new Select(element);
        boolean exists = false;
        List<WebElement> optList = sel.getOptions();
        for (int i = 0; i < optList.size(); i++) {
            String text = getText(optList.get(i));
            if (text.matches(optionToVerify)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            ChainTestListener.log("Selected Option : " + optionToVerify + " exist");
        } else {
            ChainTestListener.log("Selected Option : " + optionToVerify + " does not exist");
        }
        return exists;
    }
    /**
     * Select an option using Select class based on given dropdown locator
     * @param locator
     * @param value
     * @throws InterruptedException
     */
    public void doSelectDropDownValue(By locator, String value) throws InterruptedException {
        Select select = new Select(getElement(locator));
        List<WebElement> optionsList = select.getOptions();
        for (WebElement e : optionsList) {
            String text = e.getText();
            System.out.println(text);
            if (text.equals(value)) {
                e.click();
                break;
            }
        }
    }


    /**
     * select an option from dropdown without using Select class
     * @param element
     * @param optionToSelect
     * @return
     * @throws InterruptedException
     */
    public void selectOptionList(WebElement element, String optionToSelect) throws InterruptedException {
        List<WebElement> OptionList=element.findElements(By.tagName("option"));
        for (WebElement optionElement : OptionList) {
            ChainTestListener.log("header name is:" + optionElement.getText());
            if (optionElement.getText().equalsIgnoreCase(optionToSelect)) {
                click(optionElement);
                break;
            }
        }
    }
    /**
     * select an option from list
     * @param element
     * @param option
     * @return
     * @throws InterruptedException
     */
    public boolean selectOption(List<WebElement> element, String option) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfAllElements(element));
        for (WebElement webElement : element) {
            if (webElement.getAttribute("value").equalsIgnoreCase(option)) {
                click(webElement);
                return true;
            }
        }
        return false;
    }
    /**
     * Selects an option from list box if list has <ul> and <li>
     * @param optionToSelect
     * @throws InterruptedException
     */
    public void selectOption(WebElement element, String optionToSelect) throws InterruptedException {
        List<WebElement> OptionList=element.findElements(By.tagName("li"));
        wait.until(ExpectedConditions.visibilityOfAllElements(OptionList));
        for (WebElement optionElement : OptionList) {
            ChainTestListener.log("header name is:" + optionElement.getText());
            if (optionElement.getText().equalsIgnoreCase(optionToSelect)) {
                click(optionElement);
                break;
            }
        }
    }
    /**
     * This method will fetch all webelements in the page
     * @param locator
     * @return
     */
    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }
    /**
     * This method fetches the all the elements text and returns List
     * @param locator
     * @return
     */
    public List<String> getElementsTextList(By locator) {
        List<WebElement> eleList = getElements(locator);
        List<String> eleTextList = new ArrayList<String>();
        for (WebElement e : eleList) {
            String text = e.getText();
            eleTextList.add(text);
        }
        return eleTextList;
    }

    public boolean isSingleElementExist(By locator) {
        int actCount = getElements(locator).size();
        System.out.println("actual count of element ===" + actCount);
        if (actCount == 1) {
            return true;
        }
        return false;
    }

    public boolean isTwoElementExist(By locator) {
        int actCount = getElements(locator).size();
        System.out.println("actual count of element ===" + actCount);
        if (actCount == 2) {
            return true;
        }
        return false;
    }

    public boolean isMultipleElementExist(By locator) {
        int actCount = getElements(locator).size();
        System.out.println("actual count of element ===" + actCount);
        if (actCount > 1) {
            return true;
        }
        return false;
    }

    public boolean isMultipleElementExist(By locator, int expElementCount) {
        int actCount = getElements(locator).size();
        System.out.println("actual count of element ===" + actCount);
        if (actCount == expElementCount) {
            return true;
        }
        return false;
    }
    /***Actions class methods*************************/
    /**
     * @param element
     */
    public void clickByActions(WebElement element) {
        ChainTestListener.log("wait for the element to be clickable or not");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ChainTestListener.log("click an element by Actions class click()");
        act.click(element).perform();
    }
    /**
     * This method performs click action by Enter key
     * @param element
     */
    public void clickByEnter(WebElement element) {
        wait.until(ExpectedConditions.visibilityOfAllElements(Arrays.asList(element)));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        /*
         * ChainTestListener.log("press RETURN key"); element.sendKeys(Keys.RETURN);
         */
        ChainTestListener.log("pess ENTER key");
        element.sendKeys(Keys.ENTER);
    }
    /**
     * This method performs click action by position
     * @param element
     */
    public void clickByPosition(WebElement element) {
        Point p = element.getLocation();
        act.moveToElement(element).moveByOffset(p.x, p.y).click().perform();
    }

    /**
     * Mouse Hovers to an element
     * @param locator
     * @throws InterruptedException
     */
    public void mouseHover(By locator) throws InterruptedException {
        act.moveToElement(getElement(locator)).perform();
    }
    /**
     * performs double click operation on given element
     * @param element
     * @param info
     */
    public void DoubleClick(WebElement element, String info) {
        act.doubleClick(element).perform();
        ChainTestListener.log("Double Clicked on :: " + info);
    }

    /**
     * Performs Right Click on given  WebElement
     *@param info
     * @param locator
     * @throws InterruptedException
     */
    public void rightClick(By locator, String info) throws InterruptedException {
        act.contextClick(getElement(locator)).perform();
        ChainTestListener.log("Double Clicked on :: " + info);
    }

    /**
     * Right click a WebElement and select the option
     * @param elementLocator
     * @param itemLocator
     * @throws InterruptedException
     */
    public void selectItemRightClick(By elementLocator, By itemLocator) throws InterruptedException {
        act.contextClick(getElement(elementLocator)).perform();
        click(getElement(itemLocator));
    }

    /**
     * @param key
     */
    public void keyPress(Keys key, String info) {
        act.keyDown(key).perform();
        ChainTestListener.log("Key Pressed :: " + info);
    }
    /**
     * Select sub menus
     * @param htmltag
     * @param parentMenu
     * @param childMenu
     * @throws InterruptedException
     */
    public void selectSubMenu(String htmltag, String parentMenu, String childMenu) throws InterruptedException {
        By parentMenuLocator = By.xpath("//"+htmltag+"[text()='"+parentMenu+"']");
        By childMenuLocator = By.xpath("//"+htmltag+"[text()='"+childMenu+"']");
        WebElement parentMenuElement = getElement(parentMenuLocator);
        act.moveToElement(parentMenuElement).perform();
        Thread.sleep(2000);
        click(getElement(getByType(childMenu)));

    }
    /**
     * this method performs drag the source element and drop at the target element
     * @param src
     * @param tgt
     */
    public void dragAndDrop(WebElement src,WebElement tgt) {
        act.dragAndDrop(src, tgt).perform();
    }
    /**
     * This method performs drag and drop using clickAndHold(), moveToElement() and release()
     * @param src
     * @param tgt
     */
    public void dragAndDrop2(WebElement src,WebElement tgt) {
        //clickAndHold the source eleemnt
        act.clickAndHold(src).perform();
        //move the source to target element
        act.moveToElement(tgt).perform();
        //release the element at target
        act.release().perform();
    }
    /**
     * This method performs dragAndDropBy x,y coordinates using clickAndHold(),moveByOffSet(), release();
     * @param src
     * @param x
     * @param y
     */
    public void dragAndDropBy(WebElement src,int x, int y){
        act.clickAndHold(src).perform();
        act.moveByOffset(x, y).perform();
        act.release().perform();
    }

    public void dragAndDropBy2(WebElement src,int x, int y){
        act.dragAndDropBy(src, x, y).perform();
    }
    /**
     * Switch to iframe with name or id
     * @param frameNameId - Name or Id of the iframe
     */
    public void switchFrame(String frameNameId) {
        try {
            driver.switchTo().frame(frameNameId);
            ChainTestListener.log("Switched to iframe");
        } catch (Exception e) {
            log.error("Cannot switch to iframe");
        }
    }

    /**
     * Switch to iframe with name or id and find element
     * @param frameNameId - Name or Id of the iframe
     * @param locator - Locator of element
     */
    public WebElement findElementInFrame(String frameNameId, By locator) {
        WebElement element = null;
        try {
            driver.switchTo().frame(frameNameId);
            ChainTestListener.log("Switched to iframe");
            element = getElement(locator);
        } catch (Exception e) {
            log.error("Cannot switch to iframe");
        }
        return element;
    }

    public boolean selectOptionCarousel(List<WebElement> element, String option, WebElement nextBtn)
            throws InterruptedException {
        for (WebElement webElement : element) {
            if (webElement.getText().equalsIgnoreCase("")) {
                while (!webElement.isDisplayed()) {
                    click(nextBtn);
                }
                if (webElement.getText().equalsIgnoreCase(option)) {
                    // click(webElement);
                    return true;
                }
            }

        }
        return false;
    }

    /********************ASSERTIONS *************************************/
    public void assertText(WebElement element, String text) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertEquals(element.getText().toLowerCase(), text.toLowerCase(),
                "The text " + text + " is not equals to " + element.getText());
    }
    public void assertContainsText(WebElement element, String text) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.getText().toString().contains(text),
                "The text " + text + " is not equals to " + element.getText().toString());
    }

    public void assertTextContainsElementText(WebElement element, String text) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(text.contains(element.getText().toString()),
                "The text " + text + " is not equals to " + element.getText().toString());
    }
    public void assertTextIsNotEquals(WebElement element, String text) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertFalse(element.getText().equals(text), "The text " + text + " is equals to " + element.getText());
    }
    public void assertSubstring(WebElement element, String text) throws InterruptedException {
        Assert.assertTrue(element.getText().contains(text),
                "The text " + element.getText() + "doesn't contain the string " + text);
    }

    public void assertTextIgnoreCase(WebElement element, String text) throws InterruptedException {
        Assert.assertTrue(element.getText().equalsIgnoreCase(text),
                "The values [" + element.getText() + "] and [" + text + "] are not equals.");
    }
    public boolean assertTextNotPresentInList(List<WebElement> element, String text) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(element.get(0)));
        for (WebElement webElement : element) {
            if (webElement.getText().equalsIgnoreCase(text)) {
                return false;
            }
        }
        return true;
    }

    public static String getAlphaNumericString(int n) {
        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {

                r.append(ch);
                n--;
            }
        }
        // return the resultant string
        return r.toString();
    }

    public static String dateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        String dateist = sdf.format(new Date(0, 0, 0));
        // dateist=dateist.replace(" ", "-").replace(":", "-");
        return dateist;
    }

    public static String getCurrentDay() {
        // Create a Calendar Object
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        // Get Current Day as a number
        int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("Today Int: " + todayInt + "\n");
        // Integer to String Conversion
        String todayStr = Integer.toString(todayInt);
        System.out.println("Today Str: " + todayStr + "\n");
        return todayStr;
    }
    /**
     * This method switches to child window if two windows are available.
     */
    public void switchToChildWindow() {
        String pid=driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        Iterator<String>it=handles.iterator();
        if(handles.size()==2) {
            while(it.hasNext()) {
                if(!pid.equals(it.next())) {
                    driver.switchTo().window(it.next());

                }
            }
        }

    }
    /**
     * This method switches the cursor to parent window
     * @param pwid
     */
    public void switchToParentWindow(String pwid) {
        driver.switchTo().window(pwid);
        driver.switchTo().defaultContent();
    }

    public void switchToWindow(int windowCount) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(windowCount));
    }
    //*********************Wait Utils*****************//
    public String waitForTitleContains(String titleFractionValue) {
        if(wait.until(ExpectedConditions.titleContains(titleFractionValue))) {
            return driver.getTitle();
        }
        else {
            System.out.println("expected title is not visible...");
            return null;
        }
    }

    public String waitForTitleIs(String titleVal) {
        if(wait.until(ExpectedConditions.titleIs(titleVal))) {
            return driver.getTitle();
        }
        else {
            System.out.println("expected title is not visible...");
            return null;
        }
    }


    public String waitForUrlContains(String urlFractionValue) {
        if(wait.until(ExpectedConditions.urlContains(urlFractionValue))) {
            return driver.getCurrentUrl();
        }
        else {
            System.out.println("expected url is not visible...");
            return null;
        }
    }

    public String waitForUrlIs(String urlValue) {

        if(wait.until(ExpectedConditions.urlToBe(urlValue))) {
            return driver.getCurrentUrl();
        }
        else {
            System.out.println("expected url is not visible...");
            return null;
        }
    }

    /***********************ALERT Related methods *********************/
    //FW
    public Alert waitForAlertPresentAndSwitchWithFluentWait(int timeOut, int intervalTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(intervalTime))
                .ignoring(NoAlertPresentException.class)
                .withMessage("Alert not found on the page....");
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public Alert waitForAlertPresentAndSwitch() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }



    public String getAlertText() {
        return waitForAlertPresentAndSwitch().getText();
    }

    public void acceptAlert() {
        waitForAlertPresentAndSwitch().accept();
    }

    public void dismissAlert() {
        waitForAlertPresentAndSwitch().dismiss();
    }

    public void alertSendKeys(String value) {
        waitForAlertPresentAndSwitch().sendKeys(value);
    }

    /*****************FRAME RELATED METHODS*******************************************/
    public void waitForFramePresentAndSwitch(int frameIndex) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFramePresentAndSwitch(By frameLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public void waitForFramePresentAndSwitch(WebElement frameElement) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    public void waitForFramePresentAndSwitch(String nameOrID) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
    }



    /**
     * An expectation for checking that an element is present on the DOM of a page.
     * This does not necessarily mean that the element is visible.
     * @param locator
     * @return
     */
    public WebElement waitForElementPresence(By locator) {

        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
     * @param locator
     * @return
     */
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * An expectation for checking that all elements present on the web page that match the locator are visible.
     * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
     * @param locator
     * default interval time = 500 ms
     * @return
     */
    public List<WebElement> waitForElementsVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }



    /**
     * An expectation for checking that there is at least one element present on a web page.
     * @param locator
     * @return
     */
    public List<WebElement> waitForElementsPresence(By locator) {

        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }


    //*******************Custom Waits**********************//
    public WebElement retryingElement(By locator, int timeOut) throws InterruptedException {
        WebElement element = null;
        int attempts = 0;
        while (attempts < timeOut) {
            try {
                element = getElement(locator);
                System.out.println("element is found in attempt: " + attempts);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("element is not found in attempt : " + attempts + " for " + locator);
                TimeUtils.applyWait(500);
            }

            attempts++;
        }

        if(element == null) {
            System.out.println("element is not found....tried for : " + timeOut + " secs " +
                    " with the interval of 500 millisecs");
            //throw new FrameworkException("TimeOutException");
        }

        return element;

    }

    public WebElement retryingElement(By locator, int timeOut, int intervalTime) throws InterruptedException {
        WebElement element = null;
        int attempts = 0;
        while (attempts < timeOut) {
            try {
                element = getElement(locator);
                System.out.println("element is found in attempt: " + attempts);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("element is not found in attempt : " + attempts + " for " + locator);
                TimeUtils.applyWait(intervalTime);
            }

            attempts++;
        }

        if(element == null) {
            System.out.println("element is not found....tried for : " + timeOut + " secs " +
                    " with the interval of "+ intervalTime  + " secs");
            //throw new FrameworkException("TimeOutException");
        }

        return element;

    }
    private String getFileFromResource(String fileName) throws URISyntaxException{

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI()).getAbsolutePath();
        }

    }

    public void waitForPageLoad(int timeOut) {
        long endTime = System.currentTimeMillis() + timeOut;
        while (System.currentTimeMillis() < endTime) {
            String pageState = jsx.executeScript("return document.readyState").toString();
            if (pageState.equals("complete")) {
                System.out.println("page DOM is fully loaded now.....");
                break;
            }

        }

    }
}