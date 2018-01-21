package com.webservices.payment.logic;

import com.webservices.payment.model.*;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;

import java.util.UUID;

/**
 * @author Kasper, Jesper
 */
public class ClientManager implements IClientManager {
	
	private IPersistencyService ps;
	
	/**
	 * Constructor
     *
	 * @param persistencyService The IPersistencyService instance
	 *                           to be used for performing persistency operations
	 */
	public ClientManager(IPersistencyService persistencyService) {
		ps = persistencyService;
	}
	
	/**
	 * Used for creating a client in the bank service and storing it in the
	 * DTU Pay in memory database
	 * @param firstName (required)
	 * @param lastName  (required)
	 * @param cpr       (required)
	 * @return a client object created from the input params
	 */
    @Override
    public Client createClient(String firstName, String lastName, String cpr) throws LogicException {

        if(firstName == null)
            throw new IllegalArgumentException("First name cannot be null");

        if(lastName == null)
            throw new IllegalArgumentException("Last name cannot be null");

        if(cpr == null || cpr.isEmpty() )
            throw new IllegalArgumentException("CPR number cannot be null and must be non empty");

        // Retrieve Client Account
        BankService bank = new BankServiceService().getBankServicePort();
        Account clientAccount;
        try {
            clientAccount = bank.getAccountByCprNumber(cpr);
        } catch (BankServiceException_Exception e) {
            throw new LogicException("New client does not have an account at FastMoney Bank");
        }

        // Check if Client already Exists
        Client client = ps.retrieveClient(cpr);
        if(client != null){
            throw new LogicException("Client already exists in DTU-Pay");
        }

        // Create new client
        client = new Client(firstName, lastName, cpr, UUID.randomUUID(), clientAccount.getId());

        // Store and return the newly created client
        ps.storeClient(client);
        return client;
    }


}
