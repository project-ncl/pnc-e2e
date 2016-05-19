package operators.configurations;

import operators.base.*;
import util.Elements;
import util.Strings;

/**
 * Created by eunderhi on 10/09/15.
 */
public class BuildConfigurationPageOperator extends Operator {

    public BuildConfigurationPageOperator(String name) {
        super(name);
    }

    public void createBuildConfiguration() {
        new MenuOperator().buildConfigs();
        new ButtonOperator(Elements.CREATE_CONFIGURATION_BUTTON).clickButton();
        new TextInputDropdownOperator(Elements.BUILD_CONFIGURATION_PROJECT_SELECT).clickFirst();
        new TextInputOperator(Elements.BUILD_CONFIGURATION_INPUT).insertInput(name);
        new AreaTextOperator(Elements.BUILD_CONFIGURATION_DESCRIPTION).textAreaInput(Strings.BUILD_CONFIGURATION_DESCRIPTION);
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SCM_URL).insertInput(Strings.BUILD_CONFIGURATION_SCM_URL);
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SCM_REVISION).insertInput(Strings.BUILD_CONFIGURATION_SCM_REVISION);
        new AreaTextOperator(Elements.BUILD_CONFIGURATION_BUILD_SCRIPT).textAreaInput(Strings.BUILD_CONFIGURATION_BUILD_SCRIPT);
        new TextInputDropdownOperator(Elements.BUILD_CONFIGURATION_ENVIRONMENT).clickFirst();
        //new TextInputOperator(Elements.BUILD_CONFIGURATION_PRODUCT).insertInputEnter(Strings.BUILD_CONFIGURATION_VERSION);
        new SubmitOperator().submit();
    }

    public void buildBuildConfiguration() {
        new MenuOperator().buildConfigs();
        new RefreshOperator().refresh();
        new LinkOperator(name).clickLink();
        new BuildOperator().startBuild();
    }
}
