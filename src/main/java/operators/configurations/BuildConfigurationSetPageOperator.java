package operators.configurations;

import operators.base.*;
import util.Elements;

/**
 * Created by eunderhi on 14/09/15.
 */
public class BuildConfigurationSetPageOperator extends Operator {

    public BuildConfigurationSetPageOperator(String name) {
        super(name);
    }

    public void createBuildConfigurationSet() {

        new MenuOperator().buildGroupConfigs();
        new ButtonOperator(Elements.CREATE_CONFIGURATION_SET_BUTTON).clickButton();
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SET_INPUT).insertInput(name);
        new SubmitOperator().submit();
    }

    public void buildBuildConfigurationSet() {

        new MenuOperator().buildGroupConfigs();
        new RefreshOperator().refresh();
        new LinkOperator(name).clickLink();
        new BuildOperator().startBuild();
    }

    public void addBuildConfiguration() {

        new MenuOperator().buildGroupConfigs();
        new LinkOperator(name).clickLink();
        new ButtonOperator(Elements.ADD_BUILD_CONFIGURATION_BUTTON).clickButton();
        new SelectOperator(Elements.ADD_BUILD_CONFIGURATION_SELECT).clickFirstNonEmptySelect();
        new CheckBoxOperator().clickCheckBox();
        new SubmitOperator().submit();
    }

}
