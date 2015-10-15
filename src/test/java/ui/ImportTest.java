package ui;

import operators.ImportOperator;
import org.junit.Before;
import org.junit.Test;
import util.RandomName;

/**
 * Created by eunderhi on 15/10/15.
 */
public class ImportTest extends UITest {

    public static String importName;

    @Before
    public void doImport() {
        importName = RandomName.getRandomName();
        new ImportOperator(importName).doImport();
    }

    @Test
    public void createImport() {}

}
