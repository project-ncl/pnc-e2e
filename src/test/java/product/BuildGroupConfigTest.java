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
public class BuildGroupConfigTest extends UITest {

    private BuildConfigurationSetPageOperator buildGroupConfig;
    private String buildName;
    private String sufix;

    @Before
    public void getSufix() {

        sufix = RandomName.getSufix();
    }

    @Test
    public void pncSimpleProjectConfiguration() {

        createBuildGroupConfiguration("pnc-simple-test", "pnc-simple-test-SNAPSHOT",
                "https://github.com/project-ncl/pnc-simple-test-project.git",
                "master",
                "mvn clean deploy");
    }

    @Test
    public void jdgConfiguration() {

        // Build Group Config
        buildName = "jdg" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);

        // jdg-management-console
        String consoleProject = "jdg-management-console";
        new ProjectPageOperator(consoleProject).createProject("JDG Management Console");
        String consoleName = consoleProject + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(consoleName);
        config.createBuildConfig();
        config.setProject(consoleProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-management-console.git");
        config.setScmRevision("JDG_7.0.0.ER4_pnc_wa__4");
        config.setBuildScript("export NVM_NODEJS_ORG_MIRROR=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node\n"
                + "mvn clean deploy "
                + "-DnodeDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/ "
                + "-DnpmDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/npm/ "
                + "-DnpmRegistryURL=http://jboss-prod-docker.app.eng.bos.redhat.com:49152");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // jdg-infinispan
        String infinispanProject = "jdg-infinispan";
        new ProjectPageOperator(infinispanProject).createProject("JDG Infinispan");
        String infinispanName = infinispanProject + sufix;
        config = new BuildConfigurationPageOperator(infinispanName);
        config.createBuildConfig();
        config.setProject(infinispanProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/infinispan.git");
        config.setScmRevision("JDG_7.0.0.ER4_pnc_wa_4");
        config.setBuildScript("mvn clean deploy -DskipTests -Pdistribution");
        config.setDefaultConfigEnvironment();
        config.setDependencies(consoleName);
        config.setBuildConfigGroup(buildName);
        config.submit();
    }

    @Test
    public void jdg7Configuration() {

        // Build Group Config
        buildName = "jdg" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);

        // infinispan
        String infinispanProject = "jdg-infinispan";
        new ProjectPageOperator(infinispanProject).createProject("JDG Infinispan");
        String infinispanName = infinispanProject + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(infinispanName);
        config.createBuildConfig();
        config.setProject(infinispanProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/infinispan.git");
        config.setScmRevision("JDG_7.0.0.ER4_pnc_wa_2");
        config.setBuildScript("# mvn dependency:tree > deptree\nsleep 6000\n# mvn -X clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // spark
        String sparkProject = "jdg-spark";
        new ProjectPageOperator(sparkProject).createProject("JDG Spark");
        String sparkName = sparkProject + sufix;
        config = new BuildConfigurationPageOperator(sparkName);
        config.createBuildConfig();
        config.setProject(sparkProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-spark.git");
        config.setScmRevision("JDG_7.0.0.ER4");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // camel
        String camelProject = "jdg-camel";
        new ProjectPageOperator(camelProject).createProject("JDG Camel");
        String camelName = camelProject + sufix;
        config = new BuildConfigurationPageOperator(camelName);
        config.createBuildConfig();
        config.setProject(camelProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-camel.git");
        config.setScmRevision("JDG_7.0.0.ER4");
        config.setBuildScript("mvn clean javadoc:javadoc deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // packaging
        String packagingProject = "jdg-packaging";
        new ProjectPageOperator(packagingProject).createProject("JDG Packaging");
        String packagingName = packagingProject + sufix;
        config = new BuildConfigurationPageOperator(packagingName);
        config.createBuildConfig();
        config.setProject(packagingProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/jdg-packaging.git");
        config.setScmRevision("JDG_7.0.0.ER4");
        config.setBuildScript("mvn clean deploy -DskipTests -Pdistribution");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName, camelName);
        config.setBuildConfigGroup(buildName);
        config.submit();
    }

    @Test
    public void ssoConfiguration() {

        createSSOConfiguration("1.9.0.Final-redhat", "keycloak-1.9.0.Final-redhat");
    }

    @Test
    public void sso19xConfiguration() {

        createSSOConfiguration("1.9.x-redhat", "keycloak-1.9.x-redhat");
    }

    private void createBuildGroupConfiguration(String... param) {

        // Build Group Config
        buildName = param[0] + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);

        // PNC Test simple
        new ProjectPageOperator(param[0]).createProject(param[0] + " project");
        String configName = param[1] + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(configName);
        config.createBuildConfig();
        config.setProject(param[0]);
        config.setScmUrl(param[2]);
        config.setScmRevision(param[3]);
        config.setBuildScript(param[4]);
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();
    }

    private void createSSOConfiguration(String revision, String name) {

        // Build Group Config
        buildName = "rh-sso" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();
        assertLinkExists(buildName);

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
