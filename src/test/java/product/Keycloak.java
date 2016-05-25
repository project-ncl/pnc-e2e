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

    protected static String sufix;
    protected static final String PROJECT_NAME = "keycloak";

    @BeforeClass
    public static void before() throws Exception {

        sufix = RandomName.getSufix();
    }
    
    @Before
    public void createProject() {

        // Project
        ProjectPageOperator project = new ProjectPageOperator(PROJECT_NAME);
        project.createProjectMetadata();
        project.setName();
        project.submit();
    }

    @Test
    public void createConfiguration() {

         // Build Group Config
        String buildName = "keycloak" + sufix;
        BuildConfigurationSetPageOperator buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        buildGroupConfig.setName();
        buildGroupConfig.submit();
        assertLinkExists(buildName);

        // Build Config
        BuildConfigurationPageOperator keycloak = new BuildConfigurationPageOperator(buildName);
        keycloak.createBuildConfig();
        keycloak.setProject(PROJECT_NAME);
        keycloak.setName();
        keycloak.setScmUrl("https://github.com/keycloak/keycloak.git");
        keycloak.setScmRevision("master");
        keycloak.setBuildScript("mvn clean deploy -Pdistribution");
        keycloak.setConfigEnvironment("Demo Environment 1");
        keycloak.setBuildConfigGroup(buildName);
        keycloak.submit();

        buildGroupConfig.buildBuildConfigurationSet();
        buildGroupConfig.menuBuildGroups();
        assertLinkExists(buildName);
    }
}
