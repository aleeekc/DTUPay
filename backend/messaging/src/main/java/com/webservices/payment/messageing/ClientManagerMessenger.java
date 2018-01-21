package com.webservices.payment.messageing;

import com.google.gson.Gson;
import com.webservices.payment.model.Client;
import com.webservices.payment.model.IClientManager;
import com.webservices.payment.model.LogicException;
import com.webservices.payment.model.Token;

import javax.jms.*;
import java.util.UUID;
import org.jboss.logging.Logger;

/**
 * @author Lasse
 * @version 1.0
 */
public class ClientManagerMessenger extends Messenger implements IClientManager {

    private Gson gson = new Gson();

    public static final String FIRST_NAME_PROPERTY = "FIRST_NAME";
    public static final String LAST_NAME_PROPERTY = "LAST_NAME";
    public static final String CPR_PROPERTY = "CPR";
    public static final String CLIENT_PROPERTY = "CLIENT";
    
    /**
     * Constructor
     *
     * @param context (required) is a JMSContext instance
     * @param queue   (required) is a Queue instance
     */
    public ClientManagerMessenger(JMSContext context, Queue queue) {
        super(context, queue);
        logger = Logger.getLogger(ClientManagerMessenger.class);
    }

    @Override
    public Client createClient(String firstName, String lastName, String cpr) throws LogicException {

        //log("[createClient] Setting Properties");
        setMessageKeyValue(FIRST_NAME_PROPERTY, firstName);
        setMessageKeyValue(LAST_NAME_PROPERTY, lastName);
        setMessageKeyValue(CPR_PROPERTY, cpr);


        sendMessageWithReply();

        if(replyIsException()){
            log("[createClient] Received Logic Exception Reply: " + getExceptionText());
            throw new LogicException(getExceptionText());
        }

        String clientJSON = getReplyValue(CLIENT_PROPERTY);
        //log("[createClient] Received Client JSON: " + clientJSON);

        Client client = gson.fromJson(clientJSON, Client.class);

        if(client == null){
            log("[createClient] Client Parsed to null");
            return client;
        }

        //log("[createClient] Client Parsed to: " + clientJSON);
        return client;
    }
}
