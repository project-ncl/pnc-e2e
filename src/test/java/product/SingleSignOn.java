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

    protected static String sufix;
    protected static final String PROJECT_NAME = "rh-sso";

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
        String buildName = "rh-sso-1.9.x" + sufix;
        BuildConfigurationSetPageOperator buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        buildGroupConfig.setName();
        buildGroupConfig.submit();
        assertLinkExists(buildName);
        
        // Build Config
        String zxingName = "zxing-3.2.1" + sufix;
        BuildConfigurationPageOperator zxing = new BuildConfigurationPageOperator(zxingName);
        zxing.createBuildConfig();
        zxing.setProject(PROJECT_NAME);
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
        twitter4j.setProject(PROJECT_NAME);
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
        liquibase.setProject(PROJECT_NAME);
        liquibase.setName();
        liquibase.setScmUrl("https://github.com/liquibase/liquibase.git");
        liquibase.setScmRevision("liquibase-parent-3.4.1");
        liquibase.setBuildScript("mvn clean deploy -DskipTests");
        liquibase.setConfigEnvironment("Demo Environment 1");
        liquibase.setBuildConfigGroup(buildName);
        liquibase.submit();

        BuildConfigurationPageOperator keycloak = new BuildConfigurationPageOperator(buildName);
        keycloak.createBuildConfig();
        keycloak.setProject(PROJECT_NAME);
        keycloak.setName();
        keycloak.setScmUrl("http://git.app.eng.bos.redhat.com/git/keycloak-prod.git");
        keycloak.setScmRevision("1.9.x-redhat");
        keycloak.setBuildScript("mvn clean deploy -Pdistribution");
        keycloak.setConfigEnvironment("Demo Environment 1");
        keycloak.setDependencies(zxingName);
        keycloak.setDependencies(twitter4jName);
        keycloak.setDependencies(liquibaseName);
        keycloak.setBuildConfigGroup(buildName);
        keycloak.submit();
        
        buildGroupConfig.buildBuildConfigurationSet();
        buildGroupConfig.menuBuildGroups();
        assertLinkExists(buildName);
    }
}
