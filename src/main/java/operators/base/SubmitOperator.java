package operators.base;

import org.openqa.selenium.WebElement;

/**
 * Created by eunderhi on 09/09/15.
 */
public class SubmitOperator extends Operator {

    public void submit() {

        String inputButtonXpath = "//input[@type='submit']";
        WebElement element = getElementByXpath(inputButtonXpath);
        element.click();
    }

    public void submit(String text) {

        String inputButtonXpath = String.format("//input[@type='submit'][@value='%s']", text);
        WebElement element = getElementByXpath(inputButtonXpath);
        element.click();
    }
}
