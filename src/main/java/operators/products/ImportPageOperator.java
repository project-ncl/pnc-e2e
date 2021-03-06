package operators.products;

import operators.base.*;

/**
 * Created by pkralik.
 */
public class ImportPageOperator extends Operator {

    public static final String IMPORT_LINK = "Import";
    public static final String IMPORT_PRODUCT_LINK = "Product";
    private static String groupConfigName;

    public ImportPageOperator(String productName) {

        this.name = productName;
    }

    public void importProduct(String version, String gitURL, String revision, String buildScript) {

        menuImportProduct();
        new RefreshOperator().refresh();
        new TextInputDropdownOperator("startFormInput1").clickSelect(name);
        new TextInputOperator("startFormInput2").insertInput(version);
        new TextInputOperator("startFormInput3").insertInput(gitURL);
        new TextInputOperator("startFormInput4").insertInput(revision);
        new SubmitOperator().submit();
        waitUntilId("bcFormInput5");
        new AreaTextOperator("bcFormInput5").textAreaInput(buildScript);
        new ButtonDropdownOperator("data.environmentId").clickFirst();
        new ButtonDropdownOperator("data.projectId").clickSelect(name);
        groupConfigName = waitUntilId("inputf1").getAttribute("value");
        new SubmitOperator().submit("Finish process");
    }

    public void buildConfigurationSet() {

        waitUntilLink(groupConfigName);
        new LinkOperator(groupConfigName).clickLink();
        waitUntilLink("Build Outputs");
        new RefreshOperator().refresh();
        new BuildOperator().startBuild();
    }

    public String getConfigSetName() {

        return groupConfigName;
    }

    public void menuImportProduct() {

        new LinkOperator(IMPORT_LINK).clickLink();
        new LinkOperator(IMPORT_PRODUCT_LINK).clickLink();
    }
}
