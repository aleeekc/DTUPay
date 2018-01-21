package com.webservices.payment.messageing;

import com.webservices.payment.model.ITransactionsManager;
import com.webservices.payment.model.TransactionException;

import javax.jms.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Transaction manager for sending JMS messages
 *
 * @author Lasse
 * @version 1.0
 */
public class TransactionsManagerMessenger extends Messenger implements ITransactionsManager {

	/**
	 * Key for the Token UUID Message Property
	 */
	public static final String TOKEN_UUID_PROPERTY = "tokenUUID";

	/**
	 * Key for the Merchant UUID Message Property
	 */
	public static final String MERCHANT_UUID_PROPERTY = "merchantUUID";

	/**
	 * Key for the transaction amount Message Property
	 */
	public static final String AMOUNT_PROPERTY = "amount";

	/**
	 * Constructor - used to call the super constructor Messenger class
	 *
	 * @param context (required) a JMSContext object
	 * @param queue   (required) a Destination Queue object
	 */
	public TransactionsManagerMessenger(JMSContext context, Queue queue) {
		super(context, queue);
	}

	/**
	 * Used to send a JMS message with a new transaction and receive a reply
	 *
	 * @param tokenUUID    (required) UUID of the token
	 * @param merchantUUID (required) the merchants identifier
	 * @param amount       (required) the amount of money needed to be transferred
	 * @throws TransactionException are thrown if the transaction is invalid
	 */
	@Override
	public void newTransaction(UUID tokenUUID, UUID merchantUUID, BigDecimal amount) throws TransactionException {

		setMessageKeyValue(TOKEN_UUID_PROPERTY, tokenUUID.toString());
		setMessageKeyValue(MERCHANT_UUID_PROPERTY, merchantUUID.toString());
		setMessageKeyValue(AMOUNT_PROPERTY, amount.toString());

		sendMessageWithReply();

		if(replyIsException()){
			throw new TransactionException(getExceptionText());
		}

	}
}
