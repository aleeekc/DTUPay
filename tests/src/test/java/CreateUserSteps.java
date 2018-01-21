import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import simulators.core.Client;
import simulators.core.ClientDescription;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Aleksandar Kasper
 */
public class CreateUserSteps {

    private BankService bank = new BankServiceService().getBankServicePort();

    private Account usersBankAccount = null;
    private Client user = null;
    private Client existingUser = null;
    private ClientDescription clientDescription = null;


    @Given("^The user already registered with DTU PAY$")
    public void a_user_already_registered_with_DTU_PAY() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        if(clientDescription == null) clientDescription = TestingUtils.getNewUserDescription();
        existingUser = TestingUtils.createClient(clientDescription);
    }

    @Given("^The user is registered with Fast Money Bank$")
    public void the_user_is_registered_with_Fast_Money_Bank() throws Throwable {
        if(clientDescription == null) clientDescription = TestingUtils.getNewUserDescription();
        usersBankAccount = TestingUtils.createBankAccountForUserDescription(clientDescription, 50);
    }

    @When("^The user tries to register with DTU PAY$")
    public void the_user_tries_to_register_with_DTU_PAY() throws Throwable {
        user = TestingUtils.createClient(clientDescription);
    }

    @Then("^User creation fails$")
    public void user_creation_fails() throws Throwable {
        assertNull(user.firstName);
        assertNull(user.lastName);
        assertNull(user.uuid);
        assertNull(user.cpr);
        assertNull(user.bankAccountID);
    }

    @Given("^The user not registered with DTU PAY$")
    public void a_user_not_registered_with_DTU_PAY() throws Throwable {
        clientDescription = TestingUtils.getNewUserDescription();
    }

    @Then("^User creation succeeds$")
    public void user_creation_succeeds() throws Throwable {
        assertNotNull(user.firstName);
        assertNotNull(user.lastName);
        assertNotNull(user.uuid);
        assertNotNull(user.cpr);
        assertNotNull(user.bankAccountID);
    }

    @Given("^The user is not registered with Fast Money Bank$")
    public void the_user_is_not_registered_with_Fast_Money_Bank() throws Throwable {
        TestingUtils.deleteBankAccountForUserDescription(clientDescription);
    }

}

