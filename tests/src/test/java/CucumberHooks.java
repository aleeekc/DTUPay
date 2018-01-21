import cucumber.api.java.After;
import cucumber.api.java.Before;

public class CucumberHooks {
    @Before
    public void before(){

    }

    @After
    public void after(){
        TestingUtils.deleteAllBankAccounts();
    }
}
