

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import org.junit.Test;
import simulators.core.Client;
import simulators.core.ClientDescription;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
/**
 * @author James Erik Groving Meade (s173476)
 */
public class TokenDoubleSpendSteps {

	BankService bank = new BankServiceService().getBankServicePort();
	private Client merchant = null;
	private Client existingUser = null;
	private ClientDescription clientDescription = null;
	private ClientDescription merchantDescription = null;
	private String barcode = null;


	@Given("^A registered user with balance (.*) sends a token to a merchant to pay$")
	public void createUserWithBalance(String balance) throws Throwable{
		clientDescription = TestingUtils.getNewUserDescription();

		//Ensure that the bank account used is fresh, with the desired balance:
		TestingUtils.deleteBankAccountForUserDescription(clientDescription);
		TestingUtils.createBankAccountForUserDescription(clientDescription, Integer.parseInt(balance));

		existingUser = TestingUtils.createClient(clientDescription);
	}

	@And("^The merchant submits the transaction for (.*) and it succeeds")
	public void issueTokenAndPay(String amt) throws Throwable{
		// obtain token
		barcode = TestingUtils.obtainBarcode(existingUser.uuid.toString());

		// Create new 'user' for merchant. Then delete a possible account, and create new one.
		merchantDescription = TestingUtils.getNewUserDescription();
		TestingUtils.deleteBankAccountForUserDescription(merchantDescription);
		TestingUtils.createBankAccountForUserDescription(merchantDescription, 0);
		merchant = TestingUtils.createMerchant(merchantDescription);

		TestingUtils.payAtMerchant(barcode, merchant.uuid.toString(), Integer.parseInt(amt));

		Account merchAccount = bank.getAccountByCprNumber(merchantDescription.cpr);
		assertEquals(merchAccount.getBalance(), BigDecimal.valueOf(Integer.parseInt(amt)));
	}

	@When("^The merchant submits the transaction for (.*) again$")
	public void attemptPurchaseOfPrice(String amt){
		TestingUtils.payAtMerchant(barcode, merchant.uuid.toString(), Integer.parseInt(amt));
	}

	@Then("^The second transaction for (.*) fails$")
	public void ensureNoTrasactionTookPlace(String amt) throws Throwable{
		Account merchAccount = bank.getAccountByCprNumber(merchantDescription.cpr);
		assertEquals(merchAccount.getBalance(), BigDecimal.valueOf(Integer.parseInt(amt)));
	}
}
