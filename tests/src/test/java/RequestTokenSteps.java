import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import simulators.core.Client;
import simulators.core.ClientDescription;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Kasper, Jesper
 */
public class RequestTokenSteps {

    private BankService bank = new BankServiceService().getBankServicePort();

    private Account usersBankAccount = null;
    private Client existingUser = null;
    private ClientDescription clientDescription = null;
    private String barcode = null;


    @Given("^A user who is already registered$")
    public void a_user_who_is_already_registered() throws Throwable {
        clientDescription = TestingUtils.getNewUserDescription();
        TestingUtils.createBankAccountForUserDescription(clientDescription, 50);
        existingUser = TestingUtils.createClient(clientDescription);
    }

    @When("^The user requests a new token using it's UUID$")
    public void the_user_requests_a_new_token_using_it_s_UUID() throws Throwable {
        barcode = TestingUtils.obtainBarcode(existingUser.uuid.toString());
    }

    @Then("^The user should get a valid token UUID$")
    public void the_user_should_get_a_valid_token_UUID() throws Throwable {
//        assertNotEquals(barcode, "");
        assertNotNull(barcode);
    }

    @Given("^A user who is not already registered$")
    public void a_user_who_is_not_already_registered() throws Throwable {
        clientDescription = TestingUtils.getNewUserDescription();
        existingUser = new Client(clientDescription.firstName, clientDescription.lastName, clientDescription.cpr, new UUID(0,0), "badBank");
    }

    @Then("^The user should not get a valid token UUID$")
    public void the_user_should_not_get_a_valid_token_UUID() throws Throwable {
//        assertEquals(barcode, "");
        assertNull(barcode);
    }
}
