package com.webservices.payment.logic.testframework;

import com.webservices.payment.logic.TokenManager;
import com.webservices.payment.logic.TransactionsManager;
import com.webservices.payment.messageing.TokenManagerMessenger;
import com.webservices.payment.model.Client;
import com.webservices.payment.model.IClientManager;
import com.webservices.payment.model.LogicException;
import com.webservices.payment.model.Token;
import com.webservices.payment.persistence.MemoryPersistencyService;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * @author Lasse
 */
public abstract class ClientManagerTestSuite extends LogicTestFramework {


    public ClientManagerTestSuite(IClientManager managerToTest){
        super(MemoryPersistencyService.getInstance(),managerToTest,new TokenManager(MemoryPersistencyService.getInstance()),new TransactionsManager(MemoryPersistencyService.getInstance()));
    }

    // Create User
    @Test
    public void createClient() throws LogicException {
        UUID clientID = createClientManager.createClient(clientFirstName, clientLastName, cpr).getUUID();
        Client client = persistencyService.retrieveClient(clientID);

        assertNotNull(client);
        assertEquals(clientFirstName, client.firstName);
        assertEquals(clientLastName, client.lastName);
        assertEquals(clientAccount.getId(), client.bankAccountID);
        assertNotNull(client.getUUID());
    }

    @Test
    public void createClientWithoutBankAccount()  {

        try{
            createClientManager.createClient(clientFirstName, clientLastName, UUID.randomUUID().toString());
            fail();
        }catch (LogicException e){
            // Test Passed
        }
    }

    @Test
    public void createDublicateClient() throws LogicException {
        setupSingleClient();

        try{
            createClientManager.createClient(clientFirstName, clientLastName, cpr);
            fail();
        }catch (LogicException e){
            // Test Passed
        }
    }

    @Test
    public void createClientNullFirstName() throws LogicException {

        try{
            createClientManager.createClient(null, clientLastName, cpr);
            fail();
        }catch (IllegalArgumentException e){
            // Test Passed
        }

    }

    @Test
    public void createClientNullLastName() throws LogicException {

        try{
            createClientManager.createClient(clientFirstName, null, cpr);
            fail();
        }catch (IllegalArgumentException e){
            // Test Passed
        }

    }

    @Test
    public void createClientNullCPR() throws LogicException {

        try{
            createClientManager.createClient(clientFirstName, clientLastName, null);
            fail();
        }catch (IllegalArgumentException e){
            // Test Passed
        }

    }

    @Test
    public void createClientEmptyCPR() throws LogicException {

        try{
            createClientManager.createClient(clientFirstName, clientLastName, "");
            fail();
        }catch (IllegalArgumentException e){
            // Test Passed
        }

    }

}
