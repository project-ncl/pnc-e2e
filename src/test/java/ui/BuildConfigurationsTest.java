package ui;

import operators.base.ConfirmOperator;
import operators.configurations.BuildConfigurationPageOperator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import util.Elements;
import util.RandomName;
import util.Strings;


/**
 * Created by mvaghela on 29/07/15.
 */
public class BuildConfigurationsTest extends UITest {

    private static String configurationName;

    @Before
    public void createConfiguration() {
        configurationName = RandomName.getRandomName();
        new BuildConfigurationPageOperator(configurationName).createBuildConfiguration();
    }

    @Test
    public void buildConfigurationInfoCorrect(){
        String gotConfigurationName = tester.getParagraphText(Elements.BUILD_CONFIGURATION_NAME_P);
        String configurationDescription = tester.getParagraphText(Elements.BUILD_CONFIGURATION_DESCRIPTION_P);
        String SCMUrl = tester.getParagraphText(Elements.BUILD_CONFIGURATION_SCM_URL_P);
        String SCMRevision = tester.getParagraphText(Elements.BUILD_CONFIGURATION_SCM_REVISION_P);

        Assert.assertEquals(gotConfigurationName, configurationName);
        Assert.assertEquals(Strings.BUILD_CONFIGURATION_DESCRIPTION, configurationDescription);
        Assert.assertEquals(Strings.BUILD_CONFIGURATION_SCM_URL, SCMUrl);
        Assert.assertEquals(Strings.BUILD_CONFIGURATION_SCM_REVISION, SCMRevision);
    }


    @Test
    public void configurationExists() {
        new BuildConfigurationPageOperator().menuBuildConfigs();
        assertLinkExists(configurationName);
    }

    @Test
    public void cloneConfiguration() {
        new BuildConfigurationPageOperator().menuBuildConfigs();
        tester.clickLink(configurationName);

        tester.clickButton(Elements.BUILD_CONFIGURATION_CLONE_BUTTON);

        new BuildConfigurationPageOperator().menuBuildConfigs();
        assertCloneExists(configurationName);
    }

    @Ignore("'Delete Configuration' button is not implemented")
    @Test
    public void deleteConfiguration() {
        new BuildConfigurationPageOperator().menuBuildConfigs();
        tester.clickLink(configurationName);

        tester.clickButton(Elements.BUILD_CONFIGURATION_DELETE_BUTTON);
        new ConfirmOperator().confirm();

        new BuildConfigurationPageOperator().menuBuildConfigs();
        assertLinkDoesNotExists(configurationName);
    }
}
