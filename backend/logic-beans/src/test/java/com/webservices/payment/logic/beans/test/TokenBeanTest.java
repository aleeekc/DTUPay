package com.webservices.payment.logic.beans.test;

import com.webservices.payment.logic.beans.CreateClientMessageBean;
import com.webservices.payment.logic.beans.IssueTokenMessageBean;
import com.webservices.payment.logic.testframework.ClientManagerTestSuite;
import com.webservices.payment.logic.testframework.TokenManagerTestSuite;
import com.webservices.payment.messageing.ClientManagerMessenger;
import com.webservices.payment.messageing.TokenManagerMessenger;
import org.junit.BeforeClass;
/**
 * @author Lasse
 */
public class TokenBeanTest extends TokenManagerTestSuite {

    private static TestContext context;

    public TokenBeanTest() {
        super(new TokenManagerMessenger(context,TestQueue.IssueToken));
    }

    @BeforeClass
    public static void beforeClass(){
        context = new TestContext();
        context.addBean(new IssueTokenMessageBean(), TestQueue.IssueToken);
    }

}
