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

        importProduct("pnc-simple-test", "1.0", "PNC Simple Test",
                "https://github.com/project-ncl/pnc-simple-test-project.git",
                "master",
                "mvn clean deploy",
                "org.jboss.pnc.test:pnc-simple-test-project");
    }

    @Test
    public void ssoImport() {

        importProduct("keycloak", "1.9", "RH SSO",
                "http://git.app.eng.bos.redhat.com/git/keycloak-prod.git",
                "1.9.0.Final-redhat",
                "mvn clean deploy -Pdistribution -DskipTests=true",
                "org.keycloak:keycloak-parent");
    }

    private void importProduct(String... param) {

        new ProductPageOperator(param[0]).createProduct(param[2] + " product");
        new RefreshOperator().refresh();
        new ProjectPageOperator(param[0]).createProject(param[2] + " project");
        new RefreshOperator().refresh();

        ImportPageOperator product = new ImportPageOperator(param[0]);
        product.importProduct(param[1], param[3], param[4], param[5]);
        product.findProduct(param[6]);
        new RefreshOperator().refresh();
        new BuildOperator().startBuild();
    }
}
