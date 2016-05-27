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
    private String buildName;
    private String sufix;

    @Before
    public void createGroupConfig() {

        sufix = RandomName.getSufix();
        // Build Group Config
        buildName = "keycloak-group" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);
    }

    @Test
    public void createKeycloak() {

        // Keycloak
        String keycloakProject = "keycloak-project";
        String keycloakName = "keycloak-1.9.x" + sufix;
        new ProjectPageOperator(keycloakProject).createProject("Keycloak project");
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(keycloakName);
        config.createBuildConfig();
        config.setProject(keycloakProject);
        config.setScmUrl("https://github.com/keycloak/keycloak.git");
        config.setScmRevision("1.9.x");
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
