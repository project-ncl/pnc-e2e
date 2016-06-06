package operators.base;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by eunderhi on 09/09/15.
 */
public abstract class Operator {

    public static final int TIMEOUT = 240;
    protected static WebDriver driver;
    public String name;

    public Operator(String name) {
        this.name = name;
    }

    public Operator() {
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public WebElement getElementByXpath(String xpath) {
        try {
            return driver.findElement(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Failed to find xpath element: " + xpath);
        }
    }

    public WebElement getElementByLinkText(String linkText, int n) {
        try {
            return driver.findElements(By.linkText(linkText)).get(n);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Failed to find link: " + linkText);
        }
    }

    public WebElement getElementByPartialLinkText(String linkText, int n) {
        try {
            return driver.findElements(By.partialLinkText(linkText)).get(n);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Failed to find link: " + linkText);
        }
    }

    public WebElement waitUntilId(String id) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public WebElement waitUntilPartialLink(String link) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        return wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(link)));
    }
}
