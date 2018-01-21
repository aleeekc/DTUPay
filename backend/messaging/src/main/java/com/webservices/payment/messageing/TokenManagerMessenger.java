package com.webservices.payment.messageing;

import com.google.gson.Gson;
import com.webservices.payment.model.*;
import org.jboss.logging.Logger;

import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.UUID;

/**
 * @author Lasse
 * @version 1.0
 */
public class TokenManagerMessenger extends Messenger implements ITokenManager {

    private Gson gson = new Gson();

    public static final String TOKEN_PROPERTY = "TOKEN";
    public static final String CLIENT_UUID_PROPERTY = "USER_UUID";

    /**
     * Constructor
     *
     * @param context (required) is a JMSContext instance
     * @param queue   (required) is a Queue instance
     */
    public TokenManagerMessenger(JMSContext context, Queue queue) {
        super(context, queue);
        logger = Logger.getLogger(TokenManagerMessenger.class);
    }

    @Override
    public Token issueToken(UUID clientUUID) throws LogicException {

        if(clientUUID == null)
            throw new IllegalArgumentException("client UUID must not be null");

        setMessageKeyValue(CLIENT_UUID_PROPERTY, clientUUID.toString());

        sendMessageWithReply();

        if(replyIsException())
            throw new LogicException(getExceptionText());

        String tokenJSON = getReplyValue(TOKEN_PROPERTY);
        //log("[issueToken] Received Token JSON: " + tokenJSON);

        Token token = gson.fromJson(tokenJSON, Token.class);

        if(token == null){
            log("[issueToken] Token Parsed to null");
            return token;
        }

        //log("[issueToken] Client Parsed to: " + token);
        return token;
    }
}
