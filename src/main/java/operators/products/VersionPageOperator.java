package operators.products;

import operators.base.*;
import util.Elements;
import util.Strings;

/**
 * Created by eunderhi on 10/09/15.
 */
public class VersionPageOperator extends Operator {

    String productName;

    public VersionPageOperator(String productName) {
        this.productName = productName;
    }

    public void newVersion() {

        createVersion(Strings.PRODUCT_VERSION);
    }

    public void createVersion(String version) {
        new LinkOperator(Elements.PRODUCT_LINK).clickLink();
        new RefreshOperator().refresh();
        new LinkOperator(productName).clickLink();
        new ButtonOperator(Elements.CREATE_VERSION_BUTTON).clickButton();
        new TextInputOperator(Elements.VERSION_INPUT).insertInput(version);
        new SubmitOperator().submit();
    }
}
