package operators.base;

import util.Elements;

/**
 * Created by pkralik
 */
public class MenuOperator extends Operator {

    public MenuOperator() {
        super();
    }

    public void builds() {

        new LinkOperator(Elements.BUILDS_LINK).clickLink();
        new LinkOperator(Elements.BUILD_RECORD_LINK).findLink(1).click();
    }

    public void buildConfigs() {

        new LinkOperator(Elements.CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.BUILD_CONFIGURATION_LINK).findLink(1).click();
    }

    public void buildGroupConfigs() {

        new LinkOperator(Elements.CONFIGURATION_LINK).clickLink();
        new LinkOperator(Elements.BUILD_CONFIGURATION_SET_LINK).clickLink();
    }
}
