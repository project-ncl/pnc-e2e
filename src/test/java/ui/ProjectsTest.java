package ui;

import operators.base.LinkOperator;
import operators.base.RefreshOperator;
import operators.base.SearchOperator;
import operators.projects.ProjectPageOperator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import util.Elements;
import util.RandomName;
import util.Strings;

/**
 * Created by eunderhi on 29/07/15.
 */
public class ProjectsTest extends UITest {

    private static  String projectName;

    @Before
    public void createProject() {
        projectName = RandomName.getRandomName();
        new ProjectPageOperator(projectName).newProject();
    }

    @Test
    public void projectInfoCorrect() {
        String actualName = tester.getParagraphText(Elements.NAME_PARAGRAPH);
        String projectDescription = tester.getParagraphText(Elements.PROJECT_DESCRIPTION_PARAGRAPH);
        String projectUrl = tester.getParagraphText(Elements.PROJECT_URL_PARAGRAPH);
        String projectIssueUrl = tester.getParagraphText(Elements.PROJECT_ISSUE_URL_PARAGRAPH);

        Assert.assertEquals(projectName, actualName);
        Assert.assertEquals(Strings.PROJECT_DESCRIPTION, projectDescription);
        Assert.assertEquals(Strings.PROJECT_URL, projectUrl);
        Assert.assertEquals(Strings.PROJECT_ISSUE_URL, projectIssueUrl);
    }

    @Test
    public void projectExists() {
        new RefreshOperator().refresh();
        new LinkOperator(Elements.CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.PROJECT_LINK).clickLink();
        assertLinkExists(projectName);
    }

    @Test
    @Ignore
    public void searchTest() {
        String unsearchedProject = RandomName.getRandomName();

        new ProjectPageOperator(unsearchedProject).newProject();

        tester.clickLink(Elements.PROJECT_LINK);
        new SearchOperator().search(projectName);

        assertLinkExists(projectName);
        assertLinkDoesNotExists(unsearchedProject);
    }

}
