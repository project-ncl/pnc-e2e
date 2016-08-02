package product;

import operators.base.RefreshOperator;
import operators.configurations.BuildConfigurationSetPageOperator;
import operators.products.ProductPageOperator;
import operators.projects.ProjectPageOperator;
import operators.products.ImportPageOperator;
import org.junit.Assert;
import org.junit.Test;
import ui.UITest;

/**
 * Created by pkralik
 */
public class ImportProductTest extends UITest {

    @Test
    public void jdg() {

        importConfig("jdg-infinispan", "8.3", "JDG Infinispan",
                "http://git.app.eng.bos.redhat.com/infinispan/infinispan.git",
                "JDG_7.0.0.CR1",
                "mvn clean deploy -DskipTests -Pdistribution");
    }

    @Test
    public void jdgConsole() {

        importConfig("jdg-management-console", "8.3", "JDG Management Console",
                "http://git.app.eng.bos.redhat.com/infinispan/jdg-management-console.git",
                "JDG_7.0.0.CR1",
                "export NVM_NODEJS_ORG_MIRROR=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node\n\n"
                + "mvn clean deploy "
                + "-DnpmDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/npm/ "
                + "-DnodeDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/ "
                + "-DnpmRegistryURL=http://jboss-prod-docker.app.eng.bos.redhat.com:49165");
    }

    @Test
    public void sso() {

        importConfig("keycloak", "2.0", "RH SSO",
                "http://git.engineering.redhat.com/git/users/pkralik/keycloak-prod.git",
                "2.0.0.Final-redhat-1-pnc",
                "mvn clean deploy -Pdistribution "
                + "-pl '!adapters/oidc/jetty/jetty9.1' -pl '!adapters/oidc/jetty/jetty9.2' -pl '!adapters/oidc/spring-boot' -pl '!adapters/oidc/spring-security' -pl '!adapters/oidc/tomcat/tomcat6' -pl '!adapters/oidc/tomcat/tomcat7' -pl '!adapters/oidc/tomcat/tomcat8' -pl '!adapters/oidc/wildfly/wf8-subsystem' -pl '!adapters/saml/jetty/jetty8.1' -pl '!adapters/saml/jetty/jetty9.1' -pl '!adapters/saml/jetty/jetty9.2' -pl '!adapters/saml/tomcat/tomcat6' -pl '!adapters/saml/tomcat/tomcat7' -pl '!adapters/saml/tomcat/tomcat8' -pl '!distribution/adapters/as7-eap6-adapter/as7-adapter-zip' -pl '!distribution/adapters/tomcat6-adapter-zip' -pl '!distribution/adapters/tomcat7-adapter-zip' -pl '!distribution/adapters/tomcat8-adapter-zip' -pl '!distribution/adapters/jetty81-adapter-zip' -pl '!distribution/adapters/jetty91-adapter-zip' -pl '!distribution/adapters/jetty92-adapter-zip' -pl '!distribution/adapters/wf8-adapter/wf8-adapter-zip' -pl '!distribution/adapters/wf8-adapter/wf8-modules' -pl '!distribution/api-docs-dist' -pl '!distribution/feature-packs/adapter-feature-pack' -pl '!distribution/demo-dist' -pl '!distribution/examples-dist' -pl '!distribution/proxy-dist' -pl '!distribution/saml-adapters/as7-eap6-adapter/as7-adapter-zip' -pl '!distribution/saml-adapters/tomcat6-adapter-zip' -pl '!distribution/saml-adapters/tomcat7-adapter-zip' -pl '!distribution/saml-adapters/tomcat8-adapter-zip' -pl '!distribution/saml-adapters/jetty81-adapter-zip' -pl '!distribution/saml-adapters/jetty92-adapter-zip' -pl '!distribution/src-dist' -pl '!model/mongo' -pl '!proxy/proxy-server' -pl '!proxy/launcher/' -pl '!testsuite/proxy' -pl '!testsuite/tomcat6' -pl '!testsuite//tomcat7' -pl '!testsuite/tomcat8' -pl '!testsuite/jetty/jetty81' -pl '!testsuite/jetty/jetty91' -pl '!testsuite/jetty/jetty92' -pl '!testsuite/stress'");
    }

    @Test
    public void eap() {

        importConfig("eap7", "7.0", "JBoss EAP 7",
                "http://git.engineering.redhat.com/git/users/pkralik/wildfly.git",
                "7.0.1.GA-redhat-2-pnc",
                "mvn clean deploy -Prelease");
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
        BuildConfigurationSetPageOperator buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.menuBuildGroups();
        Assert.assertTrue(buildGroupConfig.waitUntilLink(buildName).isDisplayed());
    }
}
