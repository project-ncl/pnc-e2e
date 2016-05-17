package operators.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by eunderhi on 06/10/15.
 */
public class BuildOperator extends Operator {
    public void startBuild() {
        // Alternative locator By.xpath("//button[contains(@ng-click, 'build()')]")
        WebElement startButton = driver.findElement(By.xpath("//span[text() = 'Build'] "));
        Actions action = new Actions(driver);
        action.moveToElement(startButton).build().perform();
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        startButton.click();
    }
}
