package com.webservices.payment.logic.beans.test;

import com.webservices.payment.logic.beans.CreateClientMessageBean;
import com.webservices.payment.logic.beans.IssueTokenMessageBean;
import com.webservices.payment.logic.beans.TransactionMessageBean;
import com.webservices.payment.logic.testframework.ClientManagerTestSuite;
import com.webservices.payment.logic.testframework.TransactionManagerTestSuite;
import com.webservices.payment.messageing.ClientManagerMessenger;
import com.webservices.payment.messageing.TransactionsManagerMessenger;
import com.webservices.payment.model.IClientManager;
import org.junit.BeforeClass;
/**
 * @author Lasse
 */
public class TransactionBeanTest extends TransactionManagerTestSuite {

    private static TestContext context;

    public TransactionBeanTest() {
        super(new TransactionsManagerMessenger(context,TestQueue.Transaction));
    }

    @BeforeClass
    public static void beforeClass(){
        context = new TestContext();
        context.addBean(new TransactionMessageBean(), TestQueue.Transaction);
    }

}
