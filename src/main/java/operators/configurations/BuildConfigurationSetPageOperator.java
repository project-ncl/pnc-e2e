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

        menuBuildGroupConfigs();
        new ButtonOperator(Elements.CREATE_CONFIGURATION_SET_BUTTON).clickButton();
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SET_INPUT).insertInput(name);
        new SubmitOperator().submit();
    }

    public void buildBuildConfigurationSet() {

        menuBuildGroupConfigs();
        new LinkOperator(name).clickLink();
        new BuildOperator().startBuild();
    }

    public void addBuildConfiguration() {

        menuBuildGroupConfigs();
        new LinkOperator(name).clickLink();
        new ButtonOperator(Elements.ADD_BUILD_CONFIGURATION_BUTTON).clickButton();
        new SelectOperator(Elements.ADD_BUILD_CONFIGURATION_SELECT).clickFirstNonEmptySelect();
        new CheckBoxOperator().clickCheckBox();
        new SubmitOperator().submit();
    }

    public void menuBuildGroupConfigs() {

        new LinkOperator(Elements.CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.BUILD_CONFIGURATION_SET_LINK).clickLink();
        new RefreshOperator().refresh();
    }

    public void menuBuildGroups() {

        new LinkOperator(Elements.BUILDS_LINK).clickLink();
        new LinkOperator(Elements.BUILD_CONFIGURATION_SET_RECORDS_LINK).clickLink();
        new RefreshOperator().refresh();
    }

    public void createBuildGroupConfig() {

        menuBuildGroupConfigs();
        new ButtonOperator(Elements.CREATE_CONFIGURATION_SET_BUTTON).clickButton();
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SET_INPUT).insertInput(name);
        new SubmitOperator().submit();
        waitUntilLink(name);
    }
}
