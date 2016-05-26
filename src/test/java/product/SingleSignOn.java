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
public class SingleSignOn extends UITest {

    private BuildConfigurationSetPageOperator buildGroupConfig;
    private final String PROJECT_NAME = "rh-sso";
    private String buildName;
    private String sufix;

    @Before
    public void createGroupConfig() {

        sufix = RandomName.getSufix();
        // Project
        new ProjectPageOperator(PROJECT_NAME).createProject("RH SSO project");
        // Build Group Config
        buildName = PROJECT_NAME + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);
    }

    @Test
    public void createConfig() {

        // Build Config
        String zxingName = "zxing-3.2.1" + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(zxingName);
        config.createBuildConfig();
        config.setProject(PROJECT_NAME);
        config.setScmUrl("https://github.com/zxing/zxing.git");
        config.setScmRevision("zxing-3.2.1");
        config.setBuildScript("mvn clean deploy -DskipTests -Drat.numUnapprovedLicenses=2");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        String twitter4jName = "twitter4j-4.0.4" + sufix;
        config = new BuildConfigurationPageOperator(twitter4jName);
        config.createBuildConfig();
        config.setProject(PROJECT_NAME);
        config.setScmUrl("https://github.com/yusuke/twitter4j");
        config.setScmRevision("4.0.4");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        String liquibaseName = "liquibase-parent-3.4.1" + sufix;
        config = new BuildConfigurationPageOperator(liquibaseName);
        config.createBuildConfig();
        config.setProject(PROJECT_NAME);
        config.setScmUrl("https://github.com/liquibase/liquibase.git");
        config.setScmRevision("liquibase-parent-3.4.1");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        String ssoName = "rh-sso-1.9.x-redhat" + sufix;
        config = new BuildConfigurationPageOperator(ssoName);
        config.createBuildConfig();
        config.setProject(PROJECT_NAME);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/git/keycloak-prod.git");
        config.setScmRevision("1.9.x-redhat");
        config.setBuildScript("mvn clean deploy -Pdistribution");
        config.setDefaultConfigEnvironment();
        config.setDependencies(zxingName, twitter4jName, liquibaseName);
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
