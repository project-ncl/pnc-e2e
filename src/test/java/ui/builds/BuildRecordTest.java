package ui.builds;

import operators.configurations.BuildConfigurationPageOperator;
import operators.base.MenuOperator;
import org.junit.Test;
import ui.UITest;
import util.RandomName;

/**
 * Created by eunderhi on 16/09/15.
 */
public class BuildRecordTest extends UITest {

    @Test
    public void startBuildTest() {
        String configurationName = RandomName.getRandomName();
        BuildConfigurationPageOperator operator = new BuildConfigurationPageOperator(configurationName);
        operator.createBuildConfiguration();
        operator.buildBuildConfiguration();
        new MenuOperator().builds();
        assertLinkExists(configurationName);
    }

}
