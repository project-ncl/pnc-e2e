package product;

import operators.configurations.BuildConfigurationPageOperator;
import operators.configurations.BuildConfigurationSetPageOperator;
import operators.projects.ProjectPageOperator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ui.UITest;
import util.RandomName;

/**
 * Created by pkralik
 */
public class Keycloak extends UITest {

    private BuildConfigurationSetPageOperator buildGroupConfig;
    private final String PROJECT_NAME = "keycloak-project";
    private String buildName;
    private String sufix;

    @Before
    public void createGroupConfig() {

        sufix = RandomName.getSufix();
        // Project
        new ProjectPageOperator(PROJECT_NAME).createProject("Keycloak project");
        // Build Group Config
        buildName = "keycloak-group" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);
    }

    @Test
    public void createConfig() {

        // Build Config
        String keycloakName = "keycloak-master" + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(keycloakName);
        config.createBuildConfig();
        config.setProject(PROJECT_NAME);
        config.setScmUrl("https://github.com/keycloak/keycloak.git");
        config.setScmRevision("master");
        config.setBuildScript("mvn clean deploy -Pdistribution");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();
    }

    @After
    public void buildGroupConfig() {

        buildGroupConfig.buildBuildConfigurationSet();
        buildGroupConfig.menuBuildGroups();
        assertLinkExists(buildName);
    }
}
