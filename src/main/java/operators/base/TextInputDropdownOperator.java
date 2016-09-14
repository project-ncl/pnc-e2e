package operators.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Created by eunderhi on 09/09/15.
 */
public class TextInputDropdownOperator extends Operator {

    public final String XPATH_SELECT = "//div[input[@name='%s']]";

    public TextInputDropdownOperator(String ngModel) {
        super(ngModel);
    }

    public void clickSelectProject(String value) {
        String selectXpath = String.format(XPATH_SELECT, name);
        WebElement element = getElementByXpath(selectXpath);
        element.findElement(By.name(name)).sendKeys(value);
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        element.findElements(By.tagName("li")).get(0).click();
        //waitUntilLiItem().click();
    }

    public void clickSelect(String value) {
        String selectXpath = String.format(XPATH_SELECT, name);
        WebElement element = getElementByXpath(selectXpath);
        element.findElement(By.name(name)).sendKeys(value);
        waitUntilLi();
        element.findElements(By.tagName("li")).get(0).click();
    }

    public void clickFirst() {
        String selectXpath = String.format(XPATH_SELECT, name);
        WebElement element = getElementByXpath(selectXpath);
        element.click();
        element.findElements(By.tagName("li")).get(0).click();
    }

    public void clickLast() {
        String selectXpath = String.format(XPATH_SELECT, name);
        WebElement element = getElementByXpath(selectXpath);
        element.click();
        List<WebElement> liElements = element.findElements(By.tagName("li"));
        liElements.get(liElements.size() - 1).click();
    }
}
