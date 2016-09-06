package operators.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Created by pkralik
 */
public class ButtonDropdownOperator extends Operator {

    public final String XPATH_SELECT = "//*[@selected-id='%s']/div";

    public ButtonDropdownOperator(String dropdown) {

        super(dropdown);
    }

    public void clickSelect(String value) {

        String selectXpath = String.format(XPATH_SELECT, name);
        WebElement element = getElementByXpath(selectXpath);
        element.findElement(By.tagName("button")).click();
        // Timeout issue waiting for dropdown button content.
        waitUntilLink(value).click();
        //element.findElement(By.linkText(value)).click();
    }

    public void clickFirst() {

        String selectXpath = String.format(XPATH_SELECT, name);
        WebElement element = getElementByXpath(selectXpath);
        element.findElement(By.tagName("button")).click();
        element.findElements(By.tagName("li")).get(0).click();
    }
}
