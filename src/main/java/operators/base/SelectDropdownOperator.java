package operators.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by eunderhi on 09/09/15.
 */
public class SelectDropdownOperator extends Operator {

    public final String XPATH_SELECT = "//div[input[@name='%s']]";

    public SelectDropdownOperator(String ngModel) {
        super(ngModel);
    }

    public void clickSelect(int value) {
        String selectXpath = String.format(XPATH_SELECT, name);
        WebElement element = getElementByXpath(selectXpath);
        element.click();
        element.findElements(By.tagName("li")).get(value).click();
    }

    public void clickFirstNonEmptySelect() {
        String selectXpath = String.format(XPATH_SELECT, name);
        WebElement element = getElementByXpath(selectXpath);
        element.click();
        element.findElements(By.tagName("li")).get(0).click();
    }

}
