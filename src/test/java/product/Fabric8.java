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
public class Fabric8 extends UITest {

    private BuildConfigurationSetPageOperator buildGroupConfig;
    private String buildName;
    private String sufix;

    @Before
    public void createGroupConfig() {

        sufix = RandomName.getSufix();
        // Build Group Config
        buildName = "fabric" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);
    }

    @Test
    public void createConfiguration() {

        // Fabric8
        String configProject = "fabric8";
        new ProjectPageOperator(configProject).createProject("Fabric8 project");
        String configName = "io-fabric8-SNAPSHOT" + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(configName);
        config.createBuildConfig();
        config.setProject(configProject);
        config.setScmUrl("https://github.com/fabric8io/fabric8.git");
        config.setScmRevision("master");
        config.setBuildScript("mvn clean deploy -DskipTests=true");
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
