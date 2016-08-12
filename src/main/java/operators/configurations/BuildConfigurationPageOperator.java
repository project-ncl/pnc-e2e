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

    public BuildConfigurationPageOperator() {
    }

    public void createBuildConfiguration() {
        menuBuildConfigs();
        new ButtonOperator(Elements.CREATE_CONFIGURATION_BUTTON).clickButton();
        new TextInputDropdownOperator(Elements.BUILD_CONFIGURATION_PROJECT_SELECT).clickFirst();
        new TextInputOperator(Elements.BUILD_CONFIGURATION_INPUT).insertInput(name);
        new AreaTextOperator(Elements.BUILD_CONFIGURATION_DESCRIPTION).textAreaInput(Strings.BUILD_CONFIGURATION_DESCRIPTION);
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SCM_URL).insertInput(Strings.BUILD_CONFIGURATION_SCM_URL);
        new TextInputOperator(Elements.BUILD_CONFIGURATION_SCM_REVISION).insertInput(Strings.BUILD_CONFIGURATION_SCM_REVISION);
        new AreaTextOperator(Elements.BUILD_CONFIGURATION_BUILD_SCRIPT).textAreaInput(Strings.BUILD_CONFIGURATION_BUILD_SCRIPT);
        new TextInputDropdownOperator(Elements.BUILD_CONFIGURATION_ENVIRONMENT).clickLast();
        //new TextInputOperator(Elements.BUILD_CONFIGURATION_PRODUCT).insertInputEnter(Strings.BUILD_CONFIGURATION_VERSION);
        new SubmitOperator().submit();
    }

    public void buildBuildConfiguration() {
        menuBuildConfigs();
        new RefreshOperator().refresh();
        new LinkOperator(name).clickLink();
        new BuildOperator().startBuild();
    }

    public void menuBuildConfigs() {

        new LinkOperator(Elements.CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.BUILD_CONFIGURATION_LINK).findLink(1).click();
    }

    public void menuBuilds() {

        new LinkOperator(Elements.BUILDS_LINK).clickLink();
        new LinkOperator(Elements.BUILD_RECORD_LINK).findLink(1).click();
    }

    public void createBuildConfig() {

        menuBuildConfigs();
        new RefreshOperator().refresh();
        new ButtonOperator(Elements.CREATE_CONFIGURATION_BUTTON).clickButton();
        new TextInputOperator(Elements.BUILD_CONFIGURATION_INPUT).insertInput(name);
    }

    public void setProject(String projectName) {

        new TextInputDropdownOperator(Elements.BUILD_CONFIGURATION_PROJECT_SELECT).clickSelect(projectName);
    }

    public void setScmUrl(String scmUrl) {

        new TextInputOperator(Elements.BUILD_CONFIGURATION_SCM_URL).insertInput(scmUrl);
    }

    public void setScmRevision(String scmRevision) {

        new TextInputOperator(Elements.BUILD_CONFIGURATION_SCM_REVISION).insertInput(scmRevision);
    }

    public void setBuildScript(String buildScript) {

        new AreaTextOperator(Elements.BUILD_CONFIGURATION_BUILD_SCRIPT).textAreaInput(buildScript);
    }

    public void setDependencies(String... dependencies) {

        for (String dependency : dependencies) {
            new TextInputDropdownOperator("input-dependencies").clickSelect(dependency);
        }
    }

    public void setConfigEnvironment(String configEnvironment) {

        new TextInputDropdownOperator(Elements.BUILD_CONFIGURATION_ENVIRONMENT).clickSelect(configEnvironment);
    }

    public void setDefaultConfigEnvironment() {

        setConfigEnvironment("Demo");
    }

    public void setLastConfigEnvironment() {

        new TextInputDropdownOperator(Elements.BUILD_CONFIGURATION_ENVIRONMENT).clickLast();
    }

    public void setBuildConfigGroup(String buildConfigGroup) {

        new TextInputDropdownOperator("input-build-group-configs").clickSelect(buildConfigGroup);
    }

    public void submit() {

        new SubmitOperator().submit();
        waitUntilName(name);
    }
}
