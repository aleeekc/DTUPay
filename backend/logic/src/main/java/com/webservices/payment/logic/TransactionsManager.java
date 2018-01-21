package com.webservices.payment.logic;

import com.webservices.payment.model.*;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Custom transaction exception
 *
 * @author Lasse, James
 * @version 1.0
 */
public class TransactionsManager implements ITransactionsManager {
	
	private IPersistencyService ps;
	
	/**
	 * Constructor
	 *
	 * @param ps (required) IPersistencyService instance that contains the
	 *           DTU Pay in memory database
	 */
	public TransactionsManager(IPersistencyService ps) {
		this.ps = ps;
	}
	
	
	/**
	 * Used to generate a new transaction
	 *
	 * @param tokenUUID    (required) UUID of the token
	 * @param merchantUUID (required) the merchants identifier
	 * @param amount       (required) amount of money that need to be transferred
	 *                     Note: The amount is BigDecimal
	 * @throws TransactionException
	 */
    @Override
    public void newTransaction(UUID tokenUUID, UUID merchantUUID, BigDecimal amount) throws TransactionException {
        BankService bank = new BankServiceService().getBankServicePort();

        Token token = ps.retrieveToken(tokenUUID);
        if(token == null){
            throw new TransactionException("Token could not be retrieved");
        }

        if(!token.isValid()){
            throw new TransactionException("Token is invalid");
        }

        Client client = ps.retrieveClient(token.ownerUUID);
        if(client == null){
            throw new TransactionException("Client could not be retrieved");
        }

        // Verify that client has enough money in bank account.
        Account clientBankAcc;
        try {
            clientBankAcc = bank.getAccountByCprNumber(client.cpr);
            if (clientBankAcc.getBalance().compareTo(amount) <= -1 ) {
                throw new TransactionException("Client balance was less than" + amount + ". Transaction failed.");
            }
        } catch (BankServiceException_Exception e) {
            throw new TransactionException("Failed to retrieve Client account balance. Transaction failed.");

        }

        Client merchant = ps.retrieveClient(merchantUUID);

        // Ensure that the merchant actually exists in the DTU PAY system.
        if (merchant == null) {
            throw new TransactionException("Unable to retrieve merchant account");
        }

        // Attempt to transfer the money.
        try {
            bank.transferMoneyFromTo(client.bankAccountID, merchant.bankAccountID, amount, "Purchase at merchant " + merchant.firstName);
        } catch (BankServiceException_Exception e) {
            throw new TransactionException("Unable to complete the transaction at the bank");
        }

        ps.markTokenAsUsed(token.uuid);
    }

}
