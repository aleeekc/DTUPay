package com.webservices.payment.logic.testframework;

import com.webservices.payment.logic.ClientManager;
import com.webservices.payment.logic.TransactionsManager;
import com.webservices.payment.model.*;
import com.webservices.payment.persistence.MemoryPersistencyService;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
/**
 * @author Lasse
 */
public abstract class TokenManagerTestSuite extends LogicTestFramework {


    public TokenManagerTestSuite(ITokenManager managerToTest){
        super(
                MemoryPersistencyService.getInstance(),
                new ClientManager(MemoryPersistencyService.getInstance()),
                managerToTest,
                new TransactionsManager(MemoryPersistencyService.getInstance()));
    }



    @Test
    public void issueTokenTest() throws LogicException {
        setupSingleClient();

        Token newToken = issueTokenManager.issueToken(client.getUUID());

        assertTrue(newToken.isValid());
        assertEquals(client.uuid,newToken.ownerUUID);
        assertNotNull(newToken.uuid);

        File tmpFile = new File(newToken.barcodePath);
        assertTrue(tmpFile.exists());
    }

    @Test
    public void issueTokenNullUUID() throws LogicException {
        try{
            issueTokenManager.issueToken(null);
            fail();
        }catch (IllegalArgumentException e){
            // Test Passed
        }
    }

    @Test
    public void issueTokenNonExistingClient() {
        try{
            issueTokenManager.issueToken(UUID.randomUUID());
            fail();
        }catch (LogicException e){
            // Test Passed
        }
    }

}
