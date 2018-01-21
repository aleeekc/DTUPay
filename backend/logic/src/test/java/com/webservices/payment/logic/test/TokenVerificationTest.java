package com.webservices.payment.logic.test;

import com.webservices.payment.logic.TokenGenerator;
import com.webservices.payment.logic.testframework.LogicTestFramework;
import com.webservices.payment.model.LogicException;
import com.webservices.payment.model.Token;
import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Aleksandar, James
 */
public class TokenVerificationTest extends LogicTestFramework {


    public TokenVerificationTest() {
        super();
    }

    @Test
    public void tokenVerificationTest() throws LogicException {

        setupSingleClient();

        Token token = TokenGenerator.generateToken(client.uuid);
        Token token2 = TokenGenerator.generateToken(client.uuid);

        token2.timeStamp = 0;

        assertNotEquals(token.uuid,token2.uuid);
        assertTrue(token.isValid());
        assertFalse(token2.isValid());
    }


}
