package util;

import operators.SetUpOperator;
import operators.TearDownOperator;
import operators.base.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by eunderhi on 27/07/15.
 */
public class UITester {

    public UITester() {
        new SetUpOperator().setUp();
    }

    public void clickLink(String linkText) {
        new LinkOperator(linkText).clickLink();
    }
    
    public void clickButton(String buttonName) {
        new ButtonOperator(buttonName).clickButton();
    }

    public void textInput(String name, String input) {
        new TextInputOperator(name).insertInput(input);
    }

    public void textAreaInput(String elementName, String inputString) {
        new AreaTextOperator(elementName).textAreaInput(inputString);
    }

    public void submit(){
        new SubmitOperator().submit();
    }

    public void clickSelect(String ngModel, int value) {
        new SelectOperator(ngModel).clickSelect(value);
    }

    public void clickFirstNonEmptySelect(String ngModel) {
        new SelectOperator(ngModel).clickFirstNonEmptySelect();
    }

    public void clickCheckBox() {
        new CheckBoxOperator().clickCheckBox();
    }

    public String getParagraphText(String name) {
        return new ParagraphOperator(name).getParagraphText();
    }

    public WebElement findParagraph(String name) {
        return new ParagraphOperator(name).findParagraph();
    }

    public WebElement findSpan(String name) {
        return new SpanOperator(name).findSpan();
    }

    public void takeFailScreenShot(String name) {
        new ScreenShotOperator(name).takeFailScreenShot();
    }

    public void takeSucceedScreenShot(String name) {
        new ScreenShotOperator(name).takeSucceedScreenShot();
    }

    public void quit() {
        new TearDownOperator().tearDown();
    }

    public WebDriver getDriver() {
        return Operator.getDriver();
    }
}
