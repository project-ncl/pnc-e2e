package product;

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
        //new VersionPageOperator(productName).createVersion(productVersion);
        //assertLinkExists(productVersion);
        new ProjectPageOperator(productName).createProject("PNC Simple Test project");

        ImportPageOperator product = new ImportPageOperator(productName);
        product.importProduct(productVersion, "https://github.com/project-ncl/pnc-simple-test-project.git", "master");
        product.finishImport("mvn javadoc:jar deploy");
    }
}
