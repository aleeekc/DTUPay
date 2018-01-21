package com.webservices.payment.logic.beans;

import com.google.gson.Gson;
import com.webservices.payment.logic.ClientManager;
import com.webservices.payment.logic.LogicConstants;
import com.webservices.payment.messageing.ClientManagerMessenger;
import com.webservices.payment.messageing.Messenger;
import com.webservices.payment.model.Client;
import com.webservices.payment.model.IClientManager;
import com.webservices.payment.model.LogicException;
import com.webservices.payment.persistence.MemoryPersistencyService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

@JMSDestinationDefinition(
        name = LogicConstants.QUEUE_CLIENT_CREATE,
        interfaceName = "javax.jms.Queue",
        destinationName = "dtu-pay-client-create"
)

/**
 * JMS bean used to receive messages about creating clients
 *
 * @author Lasse
 * @version 1.0
 */
@MessageDriven(name = "CreateClientMessageBean", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = LogicConstants.QUEUE_CLIENT_CREATE),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class CreateClientMessageBean implements MessageListener {

    @Inject
    public JMSContext context;

    private Gson gson = new Gson();

    private Logger logger = Logger.getLogger(CreateClientMessageBean.class);

    /**
     * Message Listener
     */
    public void onMessage(Message message) {
        logger.info("[Create Client onMessage] Create Client Received Message");

        String firstName;
        String lastName;
        String cpr;
        Destination replyTo;
        try {
            //logger.info("[Create Client onMessage] Fetching Message Properties");
            firstName = message.getStringProperty(ClientManagerMessenger.FIRST_NAME_PROPERTY);
            lastName = message.getStringProperty(ClientManagerMessenger.LAST_NAME_PROPERTY);
            cpr = message.getStringProperty(ClientManagerMessenger.CPR_PROPERTY);
            replyTo = message.getJMSReplyTo();
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not get properties");
        }

        IClientManager manager = new ClientManager(MemoryPersistencyService.getInstance());
        String property;
        String value;
        Boolean is_exception = false;
        try {
            logger.info("[Create Client onMessage] Calling createClient");
            Client client = manager.createClient(firstName,lastName,cpr);
            value = gson.toJson(client);
            property = ClientManagerMessenger.CLIENT_PROPERTY;
        } catch (LogicException e) {
            logger.info("[Create Client onMessage] Logic Exception! " + e.getMessage());
            property = Messenger.EXCEPTION_PROPERTY;
            value = e.getMessage();
            is_exception = true;
        }

        Message reply = context.createMessage();
        try {
            logger.info("[Create Client onMessage] Creating Reply: (" + property +","+ value +")");
            reply.setStringProperty(property,value);
            reply.setBooleanProperty(Messenger.IS_EXCEPTION_PROPERTY,is_exception);
        } catch (JMSException e) {
            logger.info("[Create Client onMessage] JMSException! " + e.getMessage());
            throw new RuntimeException("Could not set properties");
        }

        logger.info("[Create Client onMessage] Sending Reply");
        context.createProducer().send(replyTo,reply);
    }

}
