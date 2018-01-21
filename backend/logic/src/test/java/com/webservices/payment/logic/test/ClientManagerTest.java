package com.webservices.payment.logic.test;

import com.webservices.payment.logic.ClientManager;
import com.webservices.payment.logic.testframework.ClientManagerTestSuite;
import com.webservices.payment.persistence.MemoryPersistencyService;

/**
 * @author Lasse
 */
public class ClientManagerTest extends ClientManagerTestSuite {
    public ClientManagerTest() {
        super(new ClientManager(MemoryPersistencyService.getInstance()));
    }
}
