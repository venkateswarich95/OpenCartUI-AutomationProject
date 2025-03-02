package com.qa.opencart.utilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {
    private WebDriver driver;

    public JavaScriptUtils(WebDriver driver) {
        this.driver = driver;
    }
    /**
     *This method will highlight the elements
     */
    public void flash(WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver; // downcasting

        js.executeScript("arguments[0].setAttribute('style','background: yellow; border: solid 5px red')", element);

        Thread.sleep(2000);

        js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element);

    }

    private void changeColor(String color, WebElement element) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }
    }

    public String getTitleByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.title;").toString();
    }

    public void goBackByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(-1)");
    }

    public void goForwardByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(1)");
    }

    public void refreshBrowserByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(0)");
    }

    public void generateAlert(String message) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("alert('" + message + "')");
    }

    public void generateConfirmPopUp(String message) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("confirm('" + message + "')");
    }

    public String getPageInnerText() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.documentElement.innerText;").toString();
    }

    public void clickElementByJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void sendKeysUsingWithId(String id, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
        //document.getElementById('input-email').value ='tom@gmail.com'
    }

    public void scrollPageDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollPageDown(String height) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, '" + height + "')");
    }

    public void scrollPageUp() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }

    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void drawBorder(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }
    public void addNewTab() throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('');");
        Thread.sleep(5000);
    }
}