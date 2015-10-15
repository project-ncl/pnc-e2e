package ui;

import operators.ImportOperator;
import org.junit.Before;
import org.junit.Test;
import util.RandomName;

/**
 * Created by eunderhi on 15/10/15.
 */
public class ImportTest {

    public static String importName;

    @Before
    public void setUp() {
        importName = RandomName.getRandomName();
        new ImportOperator(importName).doImport();
    }

}
