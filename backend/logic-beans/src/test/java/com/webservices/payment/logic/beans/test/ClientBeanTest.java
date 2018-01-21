package com.webservices.payment.logic.beans.test;

import com.webservices.payment.logic.beans.CreateClientMessageBean;
import com.webservices.payment.logic.beans.IssueTokenMessageBean;
import com.webservices.payment.logic.testframework.ClientManagerTestSuite;
import com.webservices.payment.messageing.ClientManagerMessenger;
import com.webservices.payment.model.IClientManager;
import org.junit.BeforeClass;

/**
 * @author Lasse
 */
public class ClientBeanTest extends ClientManagerTestSuite {

    private static TestContext context;

    public ClientBeanTest() {
        super(new ClientManagerMessenger(context,TestQueue.CreateClient));
    }

    @BeforeClass
    public static void beforeClass(){
        context = new TestContext();
        context.addBean(new CreateClientMessageBean(), TestQueue.CreateClient);
    }

}
