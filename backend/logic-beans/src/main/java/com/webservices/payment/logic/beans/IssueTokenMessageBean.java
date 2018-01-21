package com.webservices.payment.logic.beans;

import com.google.gson.Gson;
import com.webservices.payment.logic.ClientManager;
import com.webservices.payment.logic.LogicConstants;
import com.webservices.payment.logic.TokenManager;
import com.webservices.payment.messageing.ClientManagerMessenger;
import com.webservices.payment.messageing.Messenger;
import com.webservices.payment.messageing.TokenManagerMessenger;
import com.webservices.payment.model.IClientManager;
import com.webservices.payment.model.ITokenManager;
import com.webservices.payment.model.LogicException;
import com.webservices.payment.model.Token;
import com.webservices.payment.persistence.MemoryPersistencyService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.util.UUID;

/**
 * JMS bean used to receive messages about issuing tokens
 *
 * @author Lasse
 * @version 1.0
 */
@JMSDestinationDefinition(
        name = LogicConstants.QUEUE_CLIENT_ISSUE,
        interfaceName = "javax.jms.Queue",
        destinationName = "dtu-pay-client-issue"
)

@MessageDriven(name = "IssueTokenMessageBean", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = LogicConstants.QUEUE_CLIENT_ISSUE),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class IssueTokenMessageBean implements MessageListener {

    @Inject
    public JMSContext context;

    private Gson gson = new Gson();

    private Logger logger = Logger.getLogger(IssueTokenMessageBean.class);

    /**
     * Message Listener
     *
     * @param message
     */
    public void onMessage(Message message) {
        logger.info("[Issue Token onMessage] Create Client Received Message");

        UUID clientUUID;
        Destination replyTo;
        try {
            logger.info("[Issue Token onMessage] Fetching Message Properties");
            clientUUID = UUID.fromString(message.getStringProperty(TokenManagerMessenger.CLIENT_UUID_PROPERTY));
            replyTo = message.getJMSReplyTo();
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not get properties");
        }

        ITokenManager manager = new TokenManager(MemoryPersistencyService.getInstance());
        String property;
        String value;
        boolean is_exception = false;
        try {
            logger.info("[Issue Token onMessage] Calling issueToken");
            Token token = manager.issueToken(clientUUID);
            property = TokenManagerMessenger.TOKEN_PROPERTY;
            value = gson.toJson(token);
        } catch (LogicException e) {
            logger.info("[Issue Token onMessage] Logic Exception: " + e.getMessage());
            property = Messenger.EXCEPTION_PROPERTY;
            value = e.getMessage();
            is_exception = true;
        }


        Message reply = context.createMessage();
        try {
            logger.info("[Issue Token onMessage] Creating Reply: (" + property +","+ value +")");
            reply.setStringProperty(property,value);
            reply.setBooleanProperty(Messenger.IS_EXCEPTION_PROPERTY,is_exception);
        } catch (JMSException e) {
            logger.info("[Issue Token onMessage] JMSException! " + e.getMessage());
            throw new RuntimeException("Could not set properties");
        }

        logger.info("[Issue Token onMessage] Sending Reply");
        context.createProducer().send(replyTo,reply);
    }

}
