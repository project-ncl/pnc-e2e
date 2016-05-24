package ui.builds;

import operators.configurations.BuildConfigurationSetPageOperator;
import org.junit.Test;
import ui.UITest;
import util.RandomName;

/**
 * Created by eunderhi on 16/09/15.
 */
public class BuildConfigurationSetRecordsTest extends UITest {

    @Test
    public void startBuildTest() {
        String configurationName = RandomName.getRandomName();
        BuildConfigurationSetPageOperator operator = new BuildConfigurationSetPageOperator(configurationName);
        operator.createBuildConfigurationSet();
        operator.buildBuildConfigurationSet();
        operator.menuBuildGroups();
        assertLinkExists(configurationName);
    }
}
