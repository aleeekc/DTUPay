import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import dtu.ws.fastmoney.*;
import simulators.core.Client;
import simulators.core.ClientDescription;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Kim, Kasper
 */
public class PayAtMerchantSteps {
    BankService bank = new BankServiceService().getBankServicePort();

    private Account usersBankAccount = null;
    private Client merchant = null;
    private Client existingUser = null;
    private ClientDescription clientDescription = null;
    private String barcode = null;
    private String paymentResult = null;



    @Given("^A registered user with a balance of (.*)$")
    public void createUserWithBalance(String balance) throws Throwable{
        clientDescription = TestingUtils.getNewUserDescription();

        //Ensure that the bank account used is fresh, with the desired balance:
        TestingUtils.deleteBankAccountForUserDescription(clientDescription);
        usersBankAccount = TestingUtils.createBankAccountForUserDescription(clientDescription, Integer.parseInt(balance));

        existingUser = TestingUtils.createClient(clientDescription);
    }



    @And("^An issued token")
    public void issueToken() throws Throwable{
        barcode = TestingUtils.obtainBarcode(existingUser.uuid.toString());
    }


    @And ("^A registered merchant with a valid bank account$")
    public void registerMerchant() throws BankServiceException_Exception {
        ClientDescription ud = TestingUtils.getNewUserDescription();

        TestingUtils.deleteBankAccountForUserDescription(ud);
        TestingUtils.createBankAccountForUserDescription(ud, 10);


        merchant = TestingUtils.createMerchant(ud);
        assertNotNull(merchant);
    }


    @But("^The issued token has expired$")
    public void expireIssuedToken(){

        throw new PendingException();
    }


    @When("^The user purchases an item costing (.*)$")
    public void attemptPurchaseOfPrice(String price){

        long amount = Long.parseLong(price);

        paymentResult = TestingUtils.payAtMerchant(barcode, merchant.uuid.toString(), amount);

    }


    @Then("^Money should not be transferred from the user to the merchant$")
    public void ensureNoTrasactionTookPlace() throws Throwable{
        Account userAccount = bank.getAccountByCprNumber(clientDescription.cpr);
        assertEquals(userAccount.getTransactions().size(), 0);

    }


    @Then ("^Money should be transferred from the user to the merchant$")
    public void ensureTransactionTookPlace() throws Throwable{

        Account userAccount = bank.getAccountByCprNumber(clientDescription.cpr);
        assertEquals( 1,userAccount.getTransactions().size());
    }


    @Given("^The merchant is no longer with DTU PAY$")
    public void the_merchant_is_no_longer_with_DTU_PAY() throws Throwable {
        merchant = new Client(merchant.firstName, merchant.lastName, merchant.cpr, UUID.randomUUID(), merchant.bankAccountID);
    }
    @Given("^A merchant registered with DTU PAY$")
    public void a_merchant_registered_with_DTU_PAY() throws Throwable {

        ClientDescription merchantDescription = TestingUtils.getNewUserDescription();
        TestingUtils.createBankAccountForUserDescription(merchantDescription, 50);
        merchant = TestingUtils.createMerchant(merchantDescription);
        TestingUtils.deleteBankAccountForUserDescription(merchantDescription);
        assertNotNull(merchant);
    }

    @Given("^The merchant does not have a bank account$")
    public void the_merchant_does_not_have_a_bank_account() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Account merchantBankAccount;
        try {
            merchantBankAccount = bank.getAccountByCprNumber(merchant.cpr);
        } catch (Exception e){
            return;
        }

        bank.retireAccount(merchantBankAccount.getId());
    }

}
