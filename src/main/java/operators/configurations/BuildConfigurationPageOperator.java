package operators.configurations;

import operators.base.*;
import org.openqa.selenium.By;
import util.Credentials;
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
        new LinkOperator(Elements.CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.BUILD_CONFIGURATION_LINK).findLink(1).click(); //second link must be clicked since they have the same name.
        new ButtonOperator(Elements.CREATE_CONFIGURATION_BUTTON).clickButton();
        new SelectOperator(Elements.BUILD_CONFIGURATION_PROJECT_SELECT).clickSelect(0);
        new TextInputOperator(Elements.BUILD_CONFIGURATION_INPUT).insertInput(name);
        new AreaTextOperator(Elements.BUILD_CONFIGURATION_DESCRIPTION).textAreaInput(Strings.BUILD_CONFIGURATION_DESCRIPTION);
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SCM_URL).insertInput(Strings.BUILD_CONFIGURATION_SCM_URL);
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SCM_REVISION).insertInput(Strings.BUILD_CONFIGURATION_SCM_REVISION);
        new AreaTextOperator(Elements.BUILD_CONFIGURATION_BUILD_SCRIPT).textAreaInput(Strings.BUILD_CONFIGURATION_BUILD_SCRIPT);
        new SelectOperator(Elements.BUILD_CONFIGURATION_ENVIRONMENT).clickSelect(0);
        //new TextInputOperator(Elements.BUILD_CONFIGURATION_PRODUCT).insertInputEnter(Strings.BUILD_CONFIGURATION_VERSION);
        new SubmitOperator().submit();
    }

    public void buildBuildConfiguration() {
        new LinkOperator(Elements.CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.BUILD_CONFIGURATION_LINK).findLink(1).click();
        new LinkOperator(name).clickLink();
        new BuildOperator().startBuild();
    }

    public void menuBuildBuild() {

        new LinkOperator(Elements.BUILDS_LINK).clickLink();
        new LinkOperator(Elements.BUILD_RECORD_LINK).findLink(1).click();
    }
}
