package com.webservices.payment.logic.test;

import com.webservices.payment.logic.ClientManager;
import com.webservices.payment.logic.TokenManager;
import com.webservices.payment.logic.testframework.ClientManagerTestSuite;
import com.webservices.payment.logic.testframework.TokenManagerTestSuite;
import com.webservices.payment.messageing.TokenManagerMessenger;
import com.webservices.payment.persistence.MemoryPersistencyService;

/**
 * @author Lasse
 */
public class TokenManagerTest extends TokenManagerTestSuite {
    public TokenManagerTest() {
        super(new TokenManager(MemoryPersistencyService.getInstance()));
    }
}
