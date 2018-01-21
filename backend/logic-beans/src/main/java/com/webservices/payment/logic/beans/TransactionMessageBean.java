package com.webservices.payment.logic.beans;

import com.webservices.payment.logic.LogicConstants;
import com.webservices.payment.logic.TransactionsManager;
import com.webservices.payment.messageing.TransactionsManagerMessenger;
import com.webservices.payment.model.ITransactionsManager;
import com.webservices.payment.model.TransactionException;
import com.webservices.payment.persistence.MemoryPersistencyService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * JMS bean used to receive messages about transactions
 *
 * @author Lasse
 * @version 1.0
 */
@JMSDestinationDefinition(
        name = LogicConstants.QUEUE_TRANSACTIONS,
        interfaceName = "javax.jms.Queue",
        destinationName = "dtu-pay-transactions"
)

@MessageDriven(name = "TransactionMessageBean", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = LogicConstants.QUEUE_TRANSACTIONS),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class TransactionMessageBean implements MessageListener {

    @Inject
    public JMSContext context;
    
    /**
     * Message Listener
     *
     * @param message
     */
    public void onMessage(Message message) {

        ITransactionsManager manager = new TransactionsManager(MemoryPersistencyService.getInstance());

        UUID tokenUUID;
        UUID merchantUUID;
        BigDecimal amount;

        try {
            tokenUUID = UUID.fromString(message.getStringProperty(TransactionsManagerMessenger.TOKEN_UUID_PROPERTY));
            merchantUUID = UUID.fromString(message.getStringProperty(TransactionsManagerMessenger.MERCHANT_UUID_PROPERTY));
            amount = new BigDecimal(message.getStringProperty(TransactionsManagerMessenger.AMOUNT_PROPERTY));
        } catch (JMSException e) {
            e.printStackTrace();
            return;
        }

        Message reply = context.createMessage();
        try {
            manager.newTransaction(tokenUUID,merchantUUID,amount);

            try {
                reply.setBooleanProperty(TransactionsManagerMessenger.IS_EXCEPTION_PROPERTY,false);
                context.createProducer().send(message.getJMSReplyTo(),reply);
            } catch (JMSException e) {
                e.printStackTrace();
            }

        } catch (TransactionException e) {
            try {
                reply.setStringProperty(TransactionsManagerMessenger.EXCEPTION_PROPERTY,e.getMessage());
                reply.setBooleanProperty(TransactionsManagerMessenger.IS_EXCEPTION_PROPERTY,true);
                context.createProducer().send(message.getJMSReplyTo(),reply);
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

}
