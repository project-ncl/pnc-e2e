package operators;

import operators.base.Operator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import util.Credentials;

import java.util.concurrent.TimeUnit;

/**
 * Created by eunderhi on 09/09/15.
 */
public class SetUpOperator extends Operator {

    private Credentials creds = new Credentials();
    private static final int LOAD_WAIT_TIME = 10;

    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(LOAD_WAIT_TIME, TimeUnit.SECONDS);
        loginOrGoToBaseURL();
    }

    private void loginOrGoToBaseURL() {
        if(creds.hasLoginURL()) {
            String username = creds.getUsername();
            String password = creds.getPassword();
            String loginUrl = creds.getLoginURL();
            new LoginOperator().login(loginUrl, username, password);
        }
        else {
            String baseURL = creds.getBaseURL();
            driver.get(baseURL);
        }
    }

}
