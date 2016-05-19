package ui;

import operators.base.ConfirmOperator;
import operators.base.ParagraphOperator;
import operators.configurations.BuildConfigurationSetPageOperator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;
import util.Elements;
import util.RandomName;

/**
 * Created by eunderhi on 28/07/15.
 */
public class ConfigurationSetTest extends UITest {

    protected static String configurationSetName;

    @Test
    public void createConfigurationSet() {
        configurationSetName = RandomName.getRandomName();
        new BuildConfigurationSetPageOperator(configurationSetName).createBuildConfigurationSet();
        assertLinkExists(configurationSetName);
    }

    @Test
    public void addBuildConfiguration() {
        configurationSetName = RandomName.getRandomName();
        BuildConfigurationSetPageOperator operator = new BuildConfigurationSetPageOperator(configurationSetName);
        operator.createBuildConfigurationSet();
        operator.addBuildConfiguration();

        String pageText = new ParagraphOperator(Elements.BUILD_CONFIGURATION_SET_NAME_P).getParagraphText();
        Assert.assertEquals(pageText, configurationSetName);
    }

    @Ignore("'Delete Set' button is not implemented")
    @Test
    public void deleteConfigurationSet() {
        configurationSetName = RandomName.getRandomName();
        new BuildConfigurationSetPageOperator(configurationSetName).createBuildConfigurationSet();

        tester.clickLink(Elements.CONFIGURATION_LINK);
        tester.clickLink(Elements.BUILD_CONFIGURATION_SET_LINK);
        tester.clickLink(configurationSetName);

        tester.clickButton(Elements.BUILD_CONFIGURATION_SET_DELETE_BUTTON);
        tester.getDriver().switchTo().activeElement();
        new ConfirmOperator().confirm();

        tester.clickLink(Elements.CONFIGURATION_LINK);
        tester.clickLink(Elements.BUILD_CONFIGURATION_SET_LINK);
        assertLinkDoesNotExists(configurationSetName);
    }

}
