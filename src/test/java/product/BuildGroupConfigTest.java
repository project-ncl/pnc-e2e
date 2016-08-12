package product;

import operators.configurations.BuildConfigurationPageOperator;
import operators.configurations.BuildConfigurationSetPageOperator;
import operators.projects.ProjectPageOperator;
import org.junit.After;
import org.junit.Assert;
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
    public void jdg() {

        // Build Group Config
        buildName = "jdg" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();

        // jdg-management-console
        String consoleProject = "jdg-management-console";
        new ProjectPageOperator(consoleProject).createProject("JDG Management Console");
        String consoleName = consoleProject + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(consoleName);
        config.createBuildConfig();
        config.setProject(consoleProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-management-console.git");
        config.setScmRevision("JDG_7.0.0.ER4_pnc_wa__4");
        config.setBuildScript("export NVM_NODEJS_ORG_MIRROR=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node\n\n"
                + "mvn clean deploy "
                + "-DnpmDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/npm/ "
                + "-DnodeDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/ "
                + "-DnpmRegistryURL=http://jboss-prod-docker.app.eng.bos.redhat.com:49162");
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
        config.setScmRevision("JDG_7.0.0.ER4_pnc_wa_5");
        config.setBuildScript("mvn clean deploy -DskipTests -Pdistribution");
        config.setDefaultConfigEnvironment();
        config.setDependencies(consoleName);
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
        config.setScmRevision("JDG_7.0.0.ER4_pnc_wa_1");
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
        config.setDependencies(camelName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // hadoop
        String hadoopProject = "jdg-hadoop";
        new ProjectPageOperator(hadoopProject).createProject("JDG Hadoop");
        String hadoopName = hadoopProject + sufix;
        config = new BuildConfigurationPageOperator(hadoopName);
        config.createBuildConfig();
        config.setProject(hadoopProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-hadoop.git");
        config.setScmRevision("JDG_7.0.0.ER4");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // cassandra
        String cassandraProject = "jdg-cachestore-cassandra";
        new ProjectPageOperator(cassandraProject).createProject("JDG Cachestore Cassandra");
        String cassandraName = cassandraProject + sufix;
        config = new BuildConfigurationPageOperator(cassandraName);
        config.createBuildConfig();
        config.setProject(cassandraProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-cachestore-cassandra.git");
        config.setScmRevision("JDG_7.0.0.ER4");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // archetype
        String archetypeProject = "jdg-cachestore-archetype";
        new ProjectPageOperator(archetypeProject).createProject("JDG custom cache store archetype");
        String archetypeName = archetypeProject + sufix;
        config = new BuildConfigurationPageOperator(archetypeName);
        config.createBuildConfig();
        config.setProject(archetypeProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-cachestore-archetype.git");
        config.setScmRevision("JDG_7.0.0.ER4");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // jsClient
        String jsClientProject = "jdg-js-client";
        new ProjectPageOperator(jsClientProject).createProject("JDG JS client");
        String jsClientName = jsClientProject + sufix;
        config = new BuildConfigurationPageOperator(jsClientName);
        config.createBuildConfig();
        config.setProject(jsClientProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-js-client.git");
        config.setScmRevision("JDG_7.0.0.ER4");
        config.setBuildScript("export NVM_NODEJS_ORG_MIRROR=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node\n\n"
                + "mvn clean deploy "
                + "-DnpmDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/npm/ "
                + "-DnodeDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/ "
                + "-DnpmRegistryURL=http://jboss-prod-docker.app.eng.bos.redhat.com:49162");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();
    }

    @Test
    public void jdgER9() {

        // Build Group Config
        buildName = "jdg" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();

        // jdg-management-console
        String consoleProject = "jdg-management-console";
        new ProjectPageOperator(consoleProject).createProject("JDG Management Console");
        String consoleName = consoleProject + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(consoleName);
        config.createBuildConfig();
        config.setProject(consoleProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-management-console.git");
        config.setScmRevision("JDG_7.0.0.ER9_pnc_wa");
        config.setBuildScript("export NVM_NODEJS_ORG_MIRROR=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node\n\n"
                + "mvn clean deploy "
                + "-DnpmDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/npm/ "
                + "-DnodeDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/ "
                + "-DnpmRegistryURL=http://jboss-prod-docker.app.eng.bos.redhat.com:49159");
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
        config.setScmRevision("JDG_7.0.0.ER9");
        config.setBuildScript("mvn clean deploy -DskipTests -Pdistribution");
        config.setDefaultConfigEnvironment();
        config.setDependencies(consoleName);
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
        config.setScmRevision("JDG_7.0.0.ER9");
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
        config.setScmRevision("JDG_7.0.0.ER9");
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
        config.setScmRevision("JDG_7.0.0.ER9");
        config.setBuildScript("mvn clean deploy -DskipTests -Pdistribution");
        config.setDefaultConfigEnvironment();
        config.setDependencies(camelName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // hadoop
        String hadoopProject = "jdg-hadoop";
        new ProjectPageOperator(hadoopProject).createProject("JDG Hadoop");
        String hadoopName = hadoopProject + sufix;
        config = new BuildConfigurationPageOperator(hadoopName);
        config.createBuildConfig();
        config.setProject(hadoopProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-hadoop.git");
        config.setScmRevision("JDG_7.0.0.ER9");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // cassandra
        String cassandraProject = "jdg-cachestore-cassandra";
        new ProjectPageOperator(cassandraProject).createProject("JDG Cachestore Cassandra");
        String cassandraName = cassandraProject + sufix;
        config = new BuildConfigurationPageOperator(cassandraName);
        config.createBuildConfig();
        config.setProject(cassandraProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-cachestore-cassandra.git");
        config.setScmRevision("JDG_7.0.0.ER9");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // archetype
        String archetypeProject = "jdg-cachestore-archetype";
        new ProjectPageOperator(archetypeProject).createProject("JDG custom cache store archetype");
        String archetypeName = archetypeProject + sufix;
        config = new BuildConfigurationPageOperator(archetypeName);
        config.createBuildConfig();
        config.setProject(archetypeProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-cachestore-archetype.git");
        config.setScmRevision("JDG_7.0.0.ER9");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();

        // jsClient
        String jsClientProject = "jdg-js-client";
        new ProjectPageOperator(jsClientProject).createProject("JDG JS client");
        String jsClientName = jsClientProject + sufix;
        config = new BuildConfigurationPageOperator(jsClientName);
        config.createBuildConfig();
        config.setProject(jsClientProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/infinispan/jdg-js-client.git");
        config.setScmRevision("JDG_7.0.0.ER9");
        config.setBuildScript("export NVM_NODEJS_ORG_MIRROR=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node\n\n"
                + "mvn clean deploy "
                + "-DnpmDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/npm/ "
                + "-DnodeDownloadRoot=http://rcm-guest.app.eng.bos.redhat.com/rcm-guest/staging/jboss-dg/node/ "
                + "-DnpmRegistryURL=http://jboss-prod-docker.app.eng.bos.redhat.com:49159");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();
    }

    @Test
    public void sso() {

        // Build Group Config
        buildName = "keycloak" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();

        // freemarker
        String freemarkerProject = "freemarker";
        new ProjectPageOperator(freemarkerProject).createProject("Liquidbase project");
        String freemarkerName = "freemarker-2.3.23.redhat" + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(freemarkerName);
        config.createBuildConfig();
        config.setProject(freemarkerProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/git/freemarker.git");
        config.setScmRevision("v2.3.23");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // liquibase
        String liquibaseProject = "liquibase";
        new ProjectPageOperator(liquibaseProject).createProject("Liquidbase project");
        String liquibaseName = "liquibase-parent-3.4.1.redhat" + sufix;
        config = new BuildConfigurationPageOperator(liquibaseName);
        config.createBuildConfig();
        config.setProject(liquibaseProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/git/jboss-mobile/liquibase.git");
        config.setScmRevision("liquibase-parent-3.4.1");
        config.setBuildScript("mvn -P'!rpm' -pl '!liquibase-debian' clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // twitter4j
        String twitter4jProject = "twitter4j";
        new ProjectPageOperator(twitter4jProject).createProject("Twitter4j project");
        String twitter4jName = "twitter4j-4.0.4.redhat" + sufix;
        config = new BuildConfigurationPageOperator(twitter4jName);
        config.createBuildConfig();
        config.setProject(twitter4jProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/git/twitter4j.git/");
        config.setScmRevision("4.0.4");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // zxing
        String zxingProject = "zxing";
        new ProjectPageOperator(zxingProject).createProject("ZXing project");
        String zxingName = "zxing-parent-3.2.1.redhat" + sufix;
        config = new BuildConfigurationPageOperator(zxingName);
        config.createBuildConfig();
        config.setProject(zxingProject);
        config.setScmUrl("http://git.app.eng.bos.redhat.com/git/zxing.git");
        config.setScmRevision("zxing-3.2.1");
        config.setBuildScript("mvn clean deploy -DskipTests -Drat.numUnapprovedLicenses=2");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // Keycloak
        String keycloakProject = "keycloak";
        new ProjectPageOperator(keycloakProject).createProject("Keycloak project");
        String keycloakName = "keycloak-parent-1.9.0.Final-redhat-1" + sufix;
        config = new BuildConfigurationPageOperator(keycloakName);
        config.createBuildConfig();
        config.setProject(keycloakProject);
        config.setScmUrl("http://git.engineering.redhat.com/git/users/pkralik/keycloak-prod.git");
        config.setScmRevision("1.9.0.Final-redhat-1-pnc");
        config.setBuildScript("mvn clean deploy -Pdistribution");
        config.setDefaultConfigEnvironment();
        config.setDependencies(freemarkerName, liquibaseName, twitter4jName, zxingName);
        config.setBuildConfigGroup(buildName);
        config.submit();
    }

    @After
    public void buildGroupConfig() {

        buildGroupConfig.buildBuildConfigurationSet();
        buildGroupConfig.menuBuildGroups();
        Assert.assertTrue(buildGroupConfig.waitUntilLink(buildName).isDisplayed());
    }
}
