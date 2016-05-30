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
public class SSO extends UITest {

    private BuildConfigurationSetPageOperator buildGroupConfig;
    private String buildName;
    private String sufix;

    @Before
    public void createGroupConfig() {

        sufix = RandomName.getSufix();
        // Build Group Config
        buildName = "rh-sso" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);
    }

    @Test
    public void createConfigurationRevision() {
        ssoConfiguration("1.9.x-redhat", "keycloak-1.9.x-redhat");
    }

    @Test
    public void createConfigurationMaster() {
        ssoConfiguration("master", "keycloak-master-redhat");
    }


    private void ssoConfiguration(String revision, String name) {

        // zxing
        String zxingProject = "zxing";
        new ProjectPageOperator(zxingProject).createProject("ZXing project");
        String zxingName = "zxing-3.2.1" + sufix;
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
        String twitter4jProject = "twitter4j";
        new ProjectPageOperator(twitter4jProject).createProject("Twitter4j project");
        String twitter4jName = "twitter4j-4.0.4" + sufix;
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
        String liquidbaseProject = "liquidbase";
        new ProjectPageOperator(liquidbaseProject).createProject("Liquidbase project");
        String liquibaseName = "liquibase-parent-3.4.1" + sufix;     
        config = new BuildConfigurationPageOperator(liquibaseName);
        config.createBuildConfig();
        config.setProject(liquidbaseProject);
        config.setScmUrl("https://github.com/liquibase/liquibase.git");
        config.setScmRevision("liquibase-parent-3.4.1");
        config.setBuildScript("mvn -P'!rpm' -pl '!liquibase-debian' clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // RH-SSO
        String keycloakProject = "keycloak";
        new ProjectPageOperator(keycloakProject).createProject("Keycloak project");
        String keycloakName = name + sufix;
        config = new BuildConfigurationPageOperator(keycloakName);
        config.createBuildConfig();
        config.setProject(keycloakProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/git/keycloak-prod.git");
        config.setScmRevision(revision);
        config.setBuildScript("mvn clean deploy -Pdistribution -DskipTests=true");
        config.setDefaultConfigEnvironment();
        config.setDependencies(liquibaseName, twitter4jName, zxingName);
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
