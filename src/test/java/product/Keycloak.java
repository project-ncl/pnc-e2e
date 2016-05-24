package product;

import operators.configurations.BuildConfigurationPageOperator;
import operators.configurations.BuildConfigurationSetPageOperator;
import operators.projects.ProjectPageOperator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ui.UITest;
import util.RandomName;

/**
 * Created by pkralik
 */
public class Keycloak extends UITest {

    protected static String configurationSetName;
    protected static String keycloakName;
    protected static BuildConfigurationSetPageOperator buildGroupConfig;

    @BeforeClass
    public static void before() throws Exception {

        keycloakName = "keycloak" + RandomName.getSufix();
    }
    
    @Before
    public void createProject() {
 
        ProjectPageOperator project = new ProjectPageOperator(keycloakName);
        project.createProjectMetadata();
        project.setName();
        project.submit();
        buildGroupConfig = new BuildConfigurationSetPageOperator(keycloakName);
        buildGroupConfig.createBuildConfigurationSet();
        assertLinkExists(keycloakName);
    }

    @Test
    public void createKeycloakConfiguration() {

        BuildConfigurationPageOperator keycloakConfig = new BuildConfigurationPageOperator(keycloakName);
        keycloakConfig.createBuildConfig();
        keycloakConfig.setProject(keycloakName);
        keycloakConfig.setName(keycloakName);
        keycloakConfig.setScmUrl("https://github.com/keycloak/keycloak.git");
        keycloakConfig.setScmRevision("master");
        keycloakConfig.setBuildScript("mvn clean deploy -Pdistribution");
        keycloakConfig.setConfigEnvironment("Demo Environment 1");
        keycloakConfig.setBuildConfigGroup(keycloakName);
        keycloakConfig.submit();

        buildGroupConfig.buildBuildConfigurationSet();
        buildGroupConfig.menuBuildGroups();
        assertLinkExists(keycloakName);
    }
}
