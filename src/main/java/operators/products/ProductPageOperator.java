package operators.products;

import operators.base.*;
import util.Elements;
import util.Strings;

/**
 * Created by eunderhi on 10/09/15.
 */
public class ProductPageOperator extends Operator {

    String productName;

    public ProductPageOperator(String productName) {
        this.productName = productName;
    }

    public void newProduct() {

        menuProduct();
        new ButtonOperator(Elements.CREATE_PRODUCT_BUTTON).clickButton();
        new TextInputOperator(Elements.PRODUCT_NAME).insertInput(productName);
        new AreaTextOperator(Elements.PRODUCT_DESCRIPTION).textAreaInput(Strings.PRODUCT_DESCRIPTION);
        new TextInputOperator(Elements.PRODUCT_ABBREVIATION).insertInput(productName);
        new TextInputOperator(Elements.PRODUCT_CODE).insertInput(productName);
        new TextInputOperator(Elements.PRODUCT_SYSTEM_CODE).insertInput(productName);
        new SubmitOperator().submit();
    }

    public void menuProduct() {

        new LinkOperator(Elements.PRODUCT_LINK).clickLink();
    }

    public void createProduct(String description) {

        menuProduct();
        new RefreshOperator().refresh();
        new ButtonOperator(Elements.CREATE_PRODUCT_BUTTON).clickButton();
        new TextInputOperator(Elements.PRODUCT_NAME).insertInput(productName);
        new AreaTextOperator(Elements.PRODUCT_DESCRIPTION).textAreaInput(description);
        new SubmitOperator().submit();
    }
}
