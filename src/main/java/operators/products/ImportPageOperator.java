package operators.products;

import operators.base.*;
import util.Elements;

/**
 * Created by pkralik.
 */
public class ImportPageOperator extends Operator {

    public static final String IMPORT_LINK = "Import";
    public static final String IMPORT_PRODUCT_LINK = "Product";

    public ImportPageOperator(String productName) {

        this.name = productName;
    }

    public void importProduct(String version, String gitURL, String revision) {

        menuImportProduct();
        new RefreshOperator().refresh();
        new TextInputDropdownOperator("startFormInput1").clickSelect(name);
        new TextInputOperator("startFormInput2").insertInput(version);
        new TextInputOperator("startFormInput3").insertInput(gitURL);
        new TextInputOperator("startFormInput4").insertInput(revision);
        new SubmitOperator().submit();
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
        }
    }

    public void finishImport(String buildScript) {

        new AreaTextOperator("bcFormInput5").textAreaInput(buildScript);
        new ButtonDropdownOperator("data.environmentId").clickFirst();
        new ButtonDropdownOperator("data.projectId").clickSelect(name);
        new SubmitOperator().submit("Finish process");
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
        }
    }

    public void menuImportProduct() {

        new LinkOperator(IMPORT_LINK).clickLink();
        new LinkOperator(IMPORT_PRODUCT_LINK).clickLink();
    }
}
