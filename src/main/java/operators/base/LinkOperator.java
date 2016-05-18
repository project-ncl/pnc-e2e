package operators.base;

import org.openqa.selenium.WebElement;

/**
 * Created by eunderhi on 09/09/15.
 */
public class LinkOperator extends Operator {

    public LinkOperator(String name) {
        super(name);
    }

    public void clickAndRefresh() {
        clickLink();
        new RefreshOperator().refresh();
    }

    public void clickLink() {
        WebElement element = findLink();
        element.click();
    }

    public WebElement findLink() {
        return findLink(0);
    }

    public WebElement findLink(int n) {
        return getElementByLinkText(name, n);
    }

    public void clickPartialLink() {
        getElementByPartialLinkText(name, 0).click();
    }
}
