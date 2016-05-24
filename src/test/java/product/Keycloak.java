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

    protected static String buildName;
    protected static String configurationSetName;
    protected static BuildConfigurationSetPageOperator buildGroupConfig;

    @BeforeClass
    public static void before() throws Exception {

        buildName = "keycloak" + RandomName.getSufix();
    }
    
    @Before
    public void createConfiguration() {
 
        // Project
        ProjectPageOperator project = new ProjectPageOperator(buildName);
        project.createProjectMetadata();
        project.setName();
        project.submit();

        // Build Group Config
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildConfigurationSet();
        assertLinkExists(buildName);
    }

    @Test
    public void buildConfiguration() {

        // Build Config
        BuildConfigurationPageOperator buildConfig = new BuildConfigurationPageOperator(buildName);
        buildConfig.createBuildConfig();
        buildConfig.setProject(buildName);
        buildConfig.setName(buildName);
        buildConfig.setScmUrl("https://github.com/keycloak/keycloak.git");
        buildConfig.setScmRevision("master");
        buildConfig.setBuildScript("mvn clean deploy -Pdistribution");
        buildConfig.setConfigEnvironment("Demo Environment 1");
        buildConfig.setBuildConfigGroup(buildName);
        buildConfig.submit();
        
        buildGroupConfig.buildBuildConfigurationSet();
        buildGroupConfig.menuBuildGroups();
        assertLinkExists(buildName);
    }
}
