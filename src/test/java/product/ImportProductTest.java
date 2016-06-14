package product;

import operators.base.BuildOperator;
import operators.base.RefreshOperator;
import operators.configurations.BuildConfigurationSetPageOperator;
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
    public void pncSimpleProject() {

        importConfig("pnc-simple-test", "1.0", "PNC Simple Test",
                "https://github.com/project-ncl/pnc-simple-test-project.git",
                "master",
                "mvn clean deploy");
    }

    @Test
    public void jdg() {

        importConfig("jdg-infinispan", "7.0", "JDG Infinispan",
                "http://git.app.eng.bos.redhat.com/infinispan/infinispan.git",
                "JDG_7.0.0.ER4_pnc_wa_4",
                "mvn clean deploy -Pdistribution -DskipTests=true");
    }

    @Test
    public void fabric8() {

        importConfig("fabric8", "8.0", "Fabric8",
                "https://github.com/fabric8io/fabric8.git",
                "master",
                "mvn clean deploy -DskipTests=true");
    }

    @Test
    public void keycloak() {

        importConfig("keycloak", "1.9", "Keycloak",
                "https://github.com/keycloak/keycloak.git",
                "master",
                "mvn clean deploy -Pdistribution -DskipTests=true");
    }

    @Test
    public void pnc() {

        importConfig("pnc-ncl", "1.0", "PNC NCL",
                "https://github.com/project-ncl/pnc.git",
                "master",
                "mvn clean deploy -DskipTests=true");
    }

    @Test
    public void antlr() {

        importConfig("antlr", "2.7", "Antlr",
                "http://git.app.eng.bos.redhat.com/git/antlr2.git",
                "9f6163d",
                "mvn clean deploy");
    }

    @Test
    public void sso() {

        importConfig("keycloak", "1.9", "RH SSO",
                "http://git.app.eng.bos.redhat.com/git/keycloak-prod.git",
                "1.9.0.CR1",
                "mvn clean deploy -Pdistribution "
                + "-Dmavensign.sign.skip=* "
                + "-Dmavensign.expand.skip=* "
                + "-Denforce-skip=false "
                + "-Dversion.suffix=redhat-1 "
                + "-Drepo-reporting-removal=true "
                + "-DskipTests");
    }

    private void importConfig(String... param) {

        new ProductPageOperator(param[0]).createProduct(param[2] + " product");
        new RefreshOperator().refresh();
        new ProjectPageOperator(param[0]).createProject(param[2] + " project");
        new RefreshOperator().refresh();

        ImportPageOperator product = new ImportPageOperator(param[0]);
        product.importProduct(param[1], param[3], param[4], param[5]);
        product.buildConfigurationSet();

        String buildName = product.getConfigSetName();
        new BuildConfigurationSetPageOperator(buildName).menuBuildGroups();
        assertLinkExists(buildName);
    }
}
