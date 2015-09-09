package tasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by eunderhi on 09/09/15.
 */
public class TextInputTask extends Task {

    public TextInputTask(WebDriver driver) {
        super(driver);
    }

    public void insertInput(String elementName, String inputString){
        String inputXpath = String.format("//input[@name='%s']", elementName);
        WebElement element = driver.findElement(By.xpath(inputXpath));
        element.sendKeys(inputString);
    }
}
