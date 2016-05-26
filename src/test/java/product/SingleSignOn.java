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
    private String buildName;
    private String sufix;

    @Before
    public void createGroupConfig() {

        sufix = RandomName.getSufix();
        // Build Group Config
        buildName = "rh-sso-group" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);
    }

    @Test
    public void createKeycloak() {

        // zxing
        String zxingProject = "zxing-project";
        String zxingName = "zxing-3.2.1" + sufix;
        new ProjectPageOperator(zxingProject).createProject("ZXing project");
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(zxingName);
        config.createBuildConfig();
        config.setProject(zxingProject);
        config.setScmUrl("https://github.com/zxing/zxing.git");
        config.setScmRevision("zxing-3.2.1");
        config.setBuildScript("mvn clean deploy -DskipTests -Drat.numUnapprovedLicenses=2");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // twitter4j
        String twitter4jProject = "twitter4j-project";
        String twitter4jName = "twitter4j-4.0.4" + sufix;
        new ProjectPageOperator(twitter4jProject).createProject("Twitter4j project");
        config = new BuildConfigurationPageOperator(twitter4jName);
        config.createBuildConfig();
        config.setProject(twitter4jProject);
        config.setScmUrl("https://github.com/yusuke/twitter4j");
        config.setScmRevision("4.0.4");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // liquidbase
        String liquidbaseProject = "liquidbase-project";
        String liquibaseName = "liquibase-parent-3.4.1" + sufix;
        new ProjectPageOperator(liquidbaseProject).createProject("Liquidbase project");
        config = new BuildConfigurationPageOperator(liquibaseName);
        config.createBuildConfig();
        config.setProject(liquidbaseProject);
        config.setScmUrl("https://github.com/liquibase/liquibase.git");
        config.setScmRevision("liquibase-parent-3.4.1");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // RH-SSO
        String keycloakProject = "keycloak-project";
        String keycloakName = "keycloak-1.9.x-redhat" + sufix;
        new ProjectPageOperator(keycloakProject).createProject("Keycloak project");
        config = new BuildConfigurationPageOperator(keycloakName);
        config.createBuildConfig();
        config.setProject(keycloakProject);
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
