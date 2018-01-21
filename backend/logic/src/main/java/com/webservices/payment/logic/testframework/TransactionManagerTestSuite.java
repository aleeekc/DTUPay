package com.webservices.payment.logic.testframework;

import com.webservices.payment.logic.ClientManager;
import com.webservices.payment.logic.TokenGenerator;
import com.webservices.payment.logic.TokenManager;
import com.webservices.payment.model.*;
import com.webservices.payment.persistence.MemoryPersistencyService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.fail;
/**
 * @author Lasse
 */
public abstract class TransactionManagerTestSuite extends LogicTestFramework {


    protected TransactionManagerTestSuite(ITransactionsManager managerToTest){
        super(
                MemoryPersistencyService.getInstance(),
                new ClientManager(MemoryPersistencyService.getInstance()),
                new TokenManager(MemoryPersistencyService.getInstance()),
                managerToTest);


    }

    @Test
    public void clientHasEnoughFundsTest() throws LogicException {
        setupSingleClientWithToken();
        setupSingleMerchant();

        issueTokenManager.issueToken(client.uuid);

        try {
            transactionManager.newTransaction(token.uuid, merchant.uuid, BigDecimal.valueOf(startingBalance/2));
        } catch (TransactionException e) {
            fail();
        }
    }

    @Test
    public void useTokenTwiceTest() throws LogicException {
        setupSingleClientWithToken();
        setupSingleMerchant();

        issueTokenManager.issueToken(client.uuid);

        try {
            transactionManager.newTransaction(token.uuid, merchant.uuid, BigDecimal.valueOf(startingBalance/4));
            transactionManager.newTransaction(token.uuid, merchant.uuid, BigDecimal.valueOf(startingBalance/4));
            fail();
        } catch (TransactionException e) {
            //Test Passed
        }
    }

    @Test
    public void nonExistingTokenTest() throws LogicException {
        setupSingleClient();
        setupSingleMerchant();

        try {
            transactionManager.newTransaction(UUID.randomUUID(), merchant.uuid, BigDecimal.valueOf(startingBalance/2));
            fail();
        } catch (TransactionException e) {
            //Expected to throw
        }
    }


    @Test
    public void clientDoesNotHaveEnoughFundsTest() throws LogicException {
        setupSingleClientWithToken();
        setupSingleMerchant();

        try {
            transactionManager.newTransaction(token.uuid,merchant.uuid, BigDecimal.valueOf(startingBalance+1));
            fail();
        } catch (TransactionException e) {
            //Expected to throw
        }
    }

    @Test
    public void clientTokenInvalidTest() throws LogicException {
        setupSingleClient();
        setupSingleMerchant();

        Token invalidToken = TokenGenerator.generateToken(client.uuid);
        invalidToken.timeStamp = 0;
        persistencyService.assignTokenToClient(invalidToken,client.uuid);

        try {
            transactionManager.newTransaction(invalidToken.uuid,merchant.uuid, BigDecimal.valueOf(startingBalance/2));
            fail();
        } catch (TransactionException e) {
            //Test Passed
        }
    }

    @Test
    public void missingClientTest() throws LogicException {
        UUID missingClientUUID = UUID.randomUUID();
        setupSingleMerchant();

        Token invalidToken = TokenGenerator.generateToken(missingClientUUID);

        try{
            persistencyService.assignTokenToClient(invalidToken,missingClientUUID);
            fail();
        }catch (PersistenceException e){
            //Test Passed
        }

    }

    @Test
    public void missingMerchantTest() throws LogicException {
        setupSingleClientWithToken();

        try{
            transactionManager.newTransaction(token.uuid,UUID.randomUUID(), BigDecimal.valueOf(startingBalance/2));
            fail();
        }catch (TransactionException e){
            //Test Passed
        }

    }

    @Test
    public void deletedClientAccountTest() throws LogicException {
        setupSingleClientWithToken();
        setupSingleMerchant();

        BankAccountManager.deleteAccount(clientAccount.getId());

        try{
            transactionManager.newTransaction(token.uuid,merchant.uuid, BigDecimal.valueOf(startingBalance/2));
            fail();
        }catch (TransactionException e){
            //Test Passed
        }

        clientAccount = BankAccountManager.createAccount(clientFirstName,clientLastName,cpr,20);
    }

    @Test
    public void deletedMerchantAccountTest() throws LogicException {
        setupSingleClientWithToken();
        setupSingleMerchant();

        BankAccountManager.deleteAccount(merchantAccount.getId());

        try{
            transactionManager.newTransaction(token.uuid,merchant.uuid, BigDecimal.valueOf(startingBalance/2));
            fail();
        }catch (TransactionException e){
            //Test Passed
        }

        merchantAccount = BankAccountManager.createAccount(merchantName,merchantType,cvr,20);
    }
}
