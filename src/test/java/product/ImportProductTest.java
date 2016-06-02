package product;

import operators.base.BuildOperator;
import operators.base.LinkOperator;
import operators.base.RefreshOperator;
import operators.products.ProductPageOperator;
import operators.projects.ProjectPageOperator;
import operators.products.ImportPageOperator;
import org.junit.Test;
import ui.UITest;

/**
 * Created by pkralik
 */
public class ImportProductTest extends UITest {

    @Test
    public void pncSimpleProjectImport() {

        // pnc-simple-test
        final String productName = "pnc-simple-test";
        final String productVersion = "1.0";
        new ProductPageOperator(productName).createProduct("PNC Simple Test product");
        new RefreshOperator().refresh();
        //new VersionPageOperator(productName).createVersion(productVersion);
        //assertLinkExists(productVersion);
        new ProjectPageOperator(productName).createProject("PNC Simple Test project");
        new RefreshOperator().refresh();

        ImportPageOperator product = new ImportPageOperator(productName);
        product.importProduct(productVersion, "https://github.com/project-ncl/pnc-simple-test-project.git", "master");
        product.finishImport();
        new LinkOperator("org.jboss.pnc.test:pnc-simple-test-project").clickPartialLink();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        new RefreshOperator().refresh();
        new BuildOperator().startBuild();
    }
}
