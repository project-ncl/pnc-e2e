package operators.projects;

import operators.base.*;
import util.Elements;
import util.Strings;

/**
 * Created by eunderhi on 10/09/15.
 */
public class ProjectPageOperator extends Operator {

    public ProjectPageOperator(String name) {
        super(name);
    }

    public void newProject() {
        new LinkOperator(Elements.BUILD_CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.PROJECT_LINK).clickAndRefresh();
        new ButtonOperator(Elements.CREATE_PROJECT_BUTTON).clickButton();
        new TextInputOperator(Elements.PROJECT_NAME).insertInput(name);
        new AreaTextOperator(Elements.PROJECT_DESCRIPTION).textAreaInput(Strings.PROJECT_DESCRIPTION);
        new TextInputOperator(Elements.PROJECT_URL).insertInput(Strings.PROJECT_URL);
        new TextInputOperator(Elements.PROJECT_ISSUE_URL).insertInput(Strings.PROJECT_ISSUE_URL);
        new SubmitOperator().submit();
    }

    public void menuProjectsMetadata() {

        new LinkOperator(Elements.BUILD_CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.PROJECT_LINK).clickAndRefresh();
    }

    public void createProjectMetadata() {

        menuProjectsMetadata();
        new ButtonOperator(Elements.CREATE_PROJECT_BUTTON).clickButton();
    }

    public void setName() {

        new TextInputOperator(Elements.PROJECT_NAME).insertInput(name);
    }

    public void submit() {

        new SubmitOperator().submit();
    }
}
