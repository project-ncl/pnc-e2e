package ui.builds;

import operators.base.LinkOperator;
import operators.base.RefreshOperator;
import operators.configurations.BuildConfigurationPageOperator;
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
        operator.menuBuilds();
        new RefreshOperator().refresh();
        new LinkOperator("# ").clickPartialLink();
        assertTextExists(configurationName);
    }

}
