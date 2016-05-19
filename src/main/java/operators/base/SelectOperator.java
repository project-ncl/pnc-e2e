package operators.base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by eunderhi on 09/09/15.
 */
public class SelectOperator extends Operator {

    public SelectOperator(String ngModel) {
        super(ngModel);
    }

    public void clickSelect(int value) {
        String selectXpath = String.format("//select[@ng-model='%s']", name);
        WebElement element = getElementByXpath(selectXpath);
        element.click();
        for (int i = 0; i < value; i++) {
            element.sendKeys(Keys.ARROW_DOWN);
        }
        element.sendKeys(Keys.ENTER);
    }

    public void clickFirstNonEmptySelect() {
        String selectXpath = String.format("//select[@ng-model='%s']", name);
        WebElement element = getElementByXpath(selectXpath);
        element.click();
        Select select = new Select(element);
        String text = select.getFirstSelectedOption().getText();

        while(text.trim().length() <= 0) {
            element.sendKeys(Keys.ARROW_DOWN);
            text = select.getFirstSelectedOption().getText();
        }
        element.sendKeys(Keys.ENTER);
    }
}