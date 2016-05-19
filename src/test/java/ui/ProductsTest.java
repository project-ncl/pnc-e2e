package ui;

import operators.products.MilestonePageOperator;
import operators.products.ProductPageOperator;
import operators.products.ReleasePageOperator;
import operators.products.VersionPageOperator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.Elements;
import util.RandomName;
import util.Strings;

/**
 * Created by mvaghela on 27/07/15.
 */
public class ProductsTest extends UITest {

    String productName;
    ProductPageOperator operator;

    @Before
    public void createProduct() {
        productName = RandomName.getRandomName();
        operator = new ProductPageOperator(productName);
        operator.newProduct();
    }

    @Test
    public void productInfoCorrect() {

        String actualName = tester.getParagraphText(Elements.NAME_PARAGRAPH);
        String productDescription = tester.getParagraphText(Elements.DESCRIPTION_PARAGRAPH);
        String productAbbreviation = tester.getParagraphText(Elements.ABBREVIATION_PARAGRAPH);
        String productCode = tester.getParagraphText(Elements.CODE_PARAGRAPH);
        String systemName = tester.getParagraphText(Elements.SYSTEM_NAME_PARAGRAPH);

        Assert.assertEquals(productName, actualName);
        Assert.assertEquals(Strings.PRODUCT_DESCRIPTION, productDescription);
        Assert.assertEquals(productAbbreviation, productName);
        Assert.assertEquals(productCode, productName);
        Assert.assertEquals(systemName, productName);
    }

    @Test
    public void createProductVersion() {
        new VersionPageOperator(productName).newVersion();
        assertLinkExists(Strings.PRODUCT_VERSION);
    }

    @Test
    public void addProductMilestone() {
        new VersionPageOperator(productName).newVersion();
        new MilestonePageOperator(productName).createMilestone();
        String fullVersion = Strings.PRODUCT_VERSION + "." + Strings.MILESTONE_VERSION_INPUT;
        tester.findSpan(fullVersion);
    }


    @Test
    public void addProductRelease() throws InterruptedException {
        new VersionPageOperator(productName).newVersion();
        new MilestonePageOperator(productName).createMilestone();
        new ReleasePageOperator(productName).createRelease();
        String fullVersion = Strings.PRODUCT_VERSION + "." + Strings.RELEASE_VERSION_INPUT;
        tester.findSpan(fullVersion);
    }

}
