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
        buildName = "keycloak" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);
    }

    @Test
    public void createConfiguration() {

        // Keycloak
        String configProject = "keycloak";
        new ProjectPageOperator(configProject).createProject("Keycloak project");
        String configName = configProject + "-master" + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(configName);
        config.createBuildConfig();
        config.setProject(configProject);
        config.setScmUrl("https://github.com/keycloak/keycloak.git");
        config.setScmRevision("master");
        config.setBuildScript("mvn clean deploy -Pdistribution -DskipTests=true");
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
