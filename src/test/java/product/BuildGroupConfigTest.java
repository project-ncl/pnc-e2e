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

    public void jdg() {

        // Build Group Config
        buildName = "jb-dg-7-rhel-7-candidate" + sufix;
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
                + "-DnpmRegistryURL=http://jboss-prod-docker.app.eng.bos.redhat.com:49155");
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
        config.setScmRevision("JDG_7.0.0.ER4_pnc_wa");
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
                + "-DnpmRegistryURL=http://jboss-prod-docker.app.eng.bos.redhat.com:49155");
        config.setDefaultConfigEnvironment();
        config.setDependencies(infinispanName);
        config.setBuildConfigGroup(buildName);
        config.submit();
    }

    @Test
    public void sso() {

        // Build Group Config
        buildName = "sso-7.1-candidate" + sufix;
        buildGroupConfig = new BuildConfigurationSetPageOperator(buildName);
        buildGroupConfig.createBuildGroupConfig();

        // liquibase
        String liquibaseProject = "liquibase";
        new ProjectPageOperator(liquibaseProject).createProject("Liquidbase project");
        String liquibaseName = "org.liquibase-liquibase-parent-3.4.1" + sufix;
        BuildConfigurationPageOperator config = new BuildConfigurationPageOperator(liquibaseName);
        config.createBuildConfig();
        config.setProject(liquibaseProject);
        config.setScmUrl("git+ssh://user-pnc-gerrit@pnc-gerrit.pnc.dev.eng.bos.redhat.com:29418/pnc/liquibase-parent-3.4.1.redhat.git");
        config.setScmRevision("branch-liquibase-parent-3.4.1");
        config.setBuildScript("mvn clean deploy -DskipTests -P'!rpm' -pl '!liquibase-debian'");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // twitter4j
        String twitter4jProject = "twitter4j";
        new ProjectPageOperator(twitter4jProject).createProject("Twitter4j project");
        String twitter4jName = "org.twitter4j-twitter4j-4.0.4" + sufix;
        config = new BuildConfigurationPageOperator(twitter4jName);
        config.createBuildConfig();
        config.setProject(twitter4jProject);
        config.setScmUrl("git+ssh://user-pnc-gerrit@pnc-gerrit.pnc.dev.eng.bos.redhat.com:29418/pnc/twitter4j-4.0.4.redhat.git");
        config.setScmRevision("branch-4.0.4");
        config.setBuildScript("mvn clean deploy -DskipTests");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // zxing
        String zxingProject = "zxing";
        new ProjectPageOperator(zxingProject).createProject("ZXing project");
        String zxingName = "com.google.zxing-zxing-parent-3.2.1" + sufix;
        config = new BuildConfigurationPageOperator(zxingName);
        config.createBuildConfig();
        config.setProject(zxingProject);
        config.setScmUrl("git+ssh://user-pnc-gerrit@pnc-gerrit.pnc.dev.eng.bos.redhat.com:29418/pnc/zxing-parent-3.2.1.redhat.git");
        config.setScmRevision("branch-zxing-3.2.1");
        config.setBuildScript("mvn clean deploy -DskipTests -Drat.numUnapprovedLicenses=2");
        config.setDefaultConfigEnvironment();
        config.setBuildConfigGroup(buildName);
        config.submit();

        // Keycloak
        String keycloakProject = "keycloak";
        new ProjectPageOperator(keycloakProject).createProject("Keycloak project");
        String keycloakName = "org.keycloak-keycloak-parent-2.2.1.Final" + sufix;
        config = new BuildConfigurationPageOperator(keycloakName);
        config.createBuildConfig();
        config.setProject(keycloakProject);
        config.setScmUrl("git+ssh://user-pnc-gerrit@pnc-gerrit.pnc.dev.eng.bos.redhat.com:29418/pnc/org.keycloak-keycloak-parent-2.2.1.Final-redhat-1-da.git");
        config.setScmRevision("branch-2.2.1.Final-redhat-1-pnc-da");
        config.setBuildScript("mvn clean deploy -DskipTests=true -Pdistribution -pl '!adapters/oidc/jetty/jetty9.1' -pl '!adapters/oidc/jetty/jetty9.3' -pl '!adapters/oidc/spring-boot' -pl '!adapters/oidc/spring-security' -pl '!adapters/oidc/tomcat/tomcat6' -pl '!adapters/oidc/tomcat/tomcat7' -pl '!adapters/oidc/tomcat/tomcat8' -pl '!adapters/oidc/wildfly/wf8-subsystem' -pl '!adapters/saml/jetty/jetty-core' -pl '!adapters/saml/jetty/jetty8.1' -pl '!adapters/saml/jetty/jetty9.1' -pl '!adapters/saml/jetty/jetty9.2' -pl '!adapters/saml/jetty/jetty9.3' -pl '!adapters/saml/tomcat/tomcat6' -pl '!adapters/saml/tomcat/tomcat7' -pl '!adapters/saml/tomcat/tomcat8' -pl '!distribution/adapters/as7-eap6-adapter/as7-adapter-zip' -pl '!distribution/adapters/tomcat6-adapter-zip' -pl '!distribution/adapters/tomcat7-adapter-zip' -pl '!distribution/adapters/tomcat8-adapter-zip' -pl '!distribution/adapters/jetty81-adapter-zip' -pl '!distribution/adapters/jetty91-adapter-zip' -pl '!distribution/adapters/jetty92-adapter-zip' -pl '!distribution/adapters/jetty93-adapter-zip' -pl '!distribution/adapters/wf8-adapter/wf8-adapter-zip' -pl '!distribution/adapters/wf8-adapter/wf8-modules' -pl '!distribution/api-docs-dist' -pl '!distribution/feature-packs/adapter-feature-pack' -pl '!distribution/demo-dist' -pl '!distribution/examples-dist' -pl '!distribution/proxy-dist' -pl '!distribution/saml-adapters/as7-eap6-adapter/as7-adapter-zip' -pl '!distribution/saml-adapters/tomcat6-adapter-zip' -pl '!distribution/saml-adapters/tomcat7-adapter-zip' -pl '!distribution/saml-adapters/tomcat8-adapter-zip' -pl '!distribution/saml-adapters/jetty81-adapter-zip' -pl '!distribution/saml-adapters/jetty92-adapter-zip' -pl '!distribution/saml-adapters/jetty93-adapter-zip' -pl '!distribution/src-dist' -pl '!model/mongo' -pl '!proxy/proxy-server' -pl '!proxy/launcher/' -pl '!testsuite/proxy' -pl '!testsuite/tomcat6' -pl '!testsuite/tomcat7' -pl '!testsuite/tomcat8' -pl '!testsuite/jetty/jetty81' -pl '!testsuite/jetty/jetty91' -pl '!testsuite/jetty/jetty92' -pl '!testsuite/jetty/jetty93'");
        config.setDefaultConfigEnvironment();
        config.setDependencies(liquibaseName, twitter4jName, zxingName);
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
