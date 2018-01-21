package com.webservices.payment.logic.test;

import com.webservices.payment.logic.TokenGenerator;
import com.webservices.payment.model.Token;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author James, Kasper
 */
public class TokenCreationTest {


    @Test
    public void createTokenTest() {
        Token genToken = TokenGenerator.generateToken(null);

        long timeDiff = System.currentTimeMillis() - genToken.timeStamp;
        File barcodePath = new File(genToken.barcodePath);

        assertNotNull(genToken);
        assertTrue("Time difference was " + timeDiff + ", should be less than 10.", timeDiff < 10e3);
        assertTrue(barcodePath.exists());
        assertNotNull(genToken.barcodePath);
        assertTrue("Timestamp was 0", genToken.timeStamp != 0);
        assertNotNull(genToken.uuid);
    }
}
