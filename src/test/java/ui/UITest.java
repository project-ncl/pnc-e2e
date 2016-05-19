package ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import util.UITester;

/**
 * Created by eunderhi on 28/07/15.
 * Base class that implements the basic set up
 * and teardown elements of all UI tests, and provides
 * asserts common to all tests.
 */

public class UITest {

    public UITester tester;

    @Rule
    public ScreenShotTestWatcher testWatcher = new ScreenShotTestWatcher(getClass().getName());

    @Before
    public void setUp() {
        tester = new UITester();
        testWatcher.setTester(tester);
    }

    @After
    public void tearDown() {
    }

    public void assertLinkExists(String linkName) {

        try {
            Thread.sleep(5000);
            tester.getDriver().findElement(By.linkText(linkName)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            throw new AssertionFailedError("link " + linkName + " does not exist when it should");
        }
        catch (InterruptedException e) {
            Logger.getLogger(UITest.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void assertLinkDoesNotExists(String linkName) {
        try {
            tester.getDriver().findElement(By.linkText(linkName)).isEnabled();
        }
        catch(NoSuchElementException e) {
            return;
        }

        throw new AssertionFailedError("link " + linkName + " was visible when it should not have been");
    }


    public void assertBuildRecordExists(String recordName) {
        String linkText = " — " + recordName;
        try {
            tester.getDriver().findElement(By.partialLinkText(linkText)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            throw new AssertionFailedError("Build record " + recordName + " does not exist when it should");
        }
    }

    public void assertCloneExists(String configurationName) {
        String cloneName = "_" + configurationName;
        try {
            tester.getDriver().findElement(By.partialLinkText(cloneName)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            throw new AssertionFailedError("Cloned configuration " + cloneName + " does not exist when it should");
        }
    }

    public void assertTextExists(String recordName) {

        try {
            String h1Text = tester.getDriver().findElement(By.xpath("//h1[@class='ng-binding']")).getText();
            Assert.assertTrue(h1Text.contains(recordName));
        }
        catch (NoSuchElementException e) {
            throw new AssertionFailedError("Build " + recordName + " does not exist when it should");
        }
    }
}

