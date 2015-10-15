package operators;

import operators.base.LinkOperator;
import operators.base.Operator;
import operators.base.TextInputOperator;
import util.Elements;
import util.Strings;

/**
 * Created by eunderhi on 15/10/15.
 */
public class ImportOperator extends Operator{

    public ImportOperator(String name) {
        super(name);
    }

    public void doImport() {
        new LinkOperator(Elements.IMPORT_LINK).clickLink();
        new LinkOperator(Elements.IMPORT_PRODUCT_LINK).clickLink();
        new TextInputOperator(Elements.IMPORT_NAME).insertInput(name);
        new TextInputOperator(Elements.IMPORT_VERSION).insertInput(Strings.IMPORT_VERSION);
        new TextInputOperator(Elements.IMPORT_URL).insertInput(Strings.IMPORT_URL);
    }

}
