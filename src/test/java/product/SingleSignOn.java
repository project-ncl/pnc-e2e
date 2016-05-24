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
public class SingleSignOn extends UITest {

    protected static String buildName;
    protected static String configurationSetName;
    protected static String sufix;
    protected static BuildConfigurationSetPageOperator buildGroupConfig;

    @BeforeClass
    public static void before() throws Exception {

        sufix = RandomName.getSufix();
        buildName = "rh-sso-1.9.x-redhat" + sufix;
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
        String zxingName = "zxing-3.2.1" + sufix;
        BuildConfigurationPageOperator zxing = new BuildConfigurationPageOperator(zxingName);
        zxing.createBuildConfig();
        zxing.setProject(buildName);
        zxing.setName();
        zxing.setScmUrl("https://github.com/zxing/zxing.git");
        zxing.setScmRevision("zxing-3.2.1");
        zxing.setBuildScript("mvn clean deploy -DskipTests -Drat.numUnapprovedLicenses=2");
        zxing.setConfigEnvironment("Demo Environment 1");
        zxing.setBuildConfigGroup(buildName);
        zxing.submit();

        String twitter4jName = "twitter4j-4.0.4" + sufix;
        BuildConfigurationPageOperator twitter4j = new BuildConfigurationPageOperator(twitter4jName);
        twitter4j.createBuildConfig();
        twitter4j.setProject(buildName);
        twitter4j.setName();
        twitter4j.setScmUrl("https://github.com/yusuke/twitter4j");
        twitter4j.setScmRevision("4.0.4");
        twitter4j.setBuildScript("mvn clean deploy -DskipTests");
        twitter4j.setConfigEnvironment("Demo Environment 1");
        twitter4j.setBuildConfigGroup(buildName);
        twitter4j.submit();

        String liquibaseName = "liquibase-parent-3.4.1" + sufix;
        BuildConfigurationPageOperator liquibase = new BuildConfigurationPageOperator(liquibaseName);
        liquibase.createBuildConfig();
        liquibase.setProject(buildName);
        liquibase.setName();
        liquibase.setScmUrl("https://github.com/liquibase/liquibase.git");
        liquibase.setScmRevision("liquibase-parent-3.4.1");
        liquibase.setBuildScript("mvn clean deploy -DskipTests");
        liquibase.setConfigEnvironment("Demo Environment 1");
        liquibase.setBuildConfigGroup(buildName);
        liquibase.submit();

        BuildConfigurationPageOperator buildConfig = new BuildConfigurationPageOperator(buildName);
        buildConfig.createBuildConfig();
        buildConfig.setProject(buildName);
        buildConfig.setName();
        buildConfig.setScmUrl("http://git.app.eng.bos.redhat.com/git/keycloak-prod.git");
        buildConfig.setScmRevision("1.9.x-redhat");
        buildConfig.setBuildScript("mvn clean deploy -Pdistribution");
        buildConfig.setConfigEnvironment("Demo Environment 1");
        buildConfig.setDependencies(zxingName);
        buildConfig.setDependencies(twitter4jName);
        buildConfig.setDependencies(liquibaseName);
        buildConfig.setBuildConfigGroup(buildName);
        buildConfig.submit();
        
        buildGroupConfig.buildBuildConfigurationSet();
        buildGroupConfig.menuBuildGroups();
        assertLinkExists(buildName);
    }
}
