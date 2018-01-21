package com.webservices.payment.logic.test;

import com.webservices.payment.logic.TransactionsManager;
import com.webservices.payment.logic.testframework.TransactionManagerTestSuite;
import com.webservices.payment.persistence.MemoryPersistencyService;

/**
 * @author Lasse
 */
public class TransactionManagerTest extends TransactionManagerTestSuite {


    public TransactionManagerTest() {
        super(new TransactionsManager(MemoryPersistencyService.getInstance()));
    }

}
