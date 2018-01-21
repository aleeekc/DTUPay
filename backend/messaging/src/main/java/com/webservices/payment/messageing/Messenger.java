package com.webservices.payment.messageing;

import javax.jms.*;
//import java.util.logging.Logger;
import org.jboss.logging.Logger;

/**
 * Messenger class that simplifies the creation and sending of JMS messages in and extending class
 *
 * @see ClientManagerMessenger
 * @see TransactionsManagerMessenger
 *
 * @author Lasse
 * @version 1.0
 */
public abstract class Messenger {

	/**
	 * Key for the exception flag Message Property
	 */
	public static final String IS_EXCEPTION_PROPERTY = "is_exception";

	/**
	 * Key for the Exception Message Property
	 */
	public static final String EXCEPTION_PROPERTY = "exception";

    private JMSContext context;
	private Destination queue;
	private TextMessage message;
	private Message reply;

	Logger logger;

	/**
	 * Constructor
	 *
	 * @param context (required) is a JMSContext instance
	 * @param queue   (required) is a JMS Destination Queue instance
	 */
	Messenger(JMSContext context, Queue queue) {
		this.context = context;
		this.queue = queue;
		message = context.createTextMessage();
	}
	
	/**
	 * Setting a key value pair of the Message String Property
	 *
	 * @param key   (required)
	 * @param value (required)
	 */
	void setMessageKeyValue(String key, String value) {
		try {
			message.setStringProperty(key, value);
		} catch (JMSException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not set Property of Message");
		}
	}
	
	/**
	 * Retrieving a value based on a key from a Message Property
	 *
	 * @param key (required)
	 * @return value String
	 */
	String getReplyValue(String key) {
		try {
			return reply.getStringProperty(key);
		} catch (JMSException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not get Property of Message");
		}
	}
	
	/**
	 * Setting a text inside the message
	 *
	 * @param text (required) text that needs to be send to the message bean
	 */
	void setMessageText(String text) {
		try {
			message.setText(text);
		} catch (JMSException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not set Text of Message");
		}
	}
	
	/**
	 * Retrieving the text from a message bean
	 *
	 * @return message text
	 */
	String getReplyText() {
		try {
			if (reply instanceof TextMessage) {
				return ((TextMessage) reply).getText();
			} else {
				throw new RuntimeException("Reply Is Not Text Message");
			}
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Send a JMS message that expects a reply
	 */
	void sendMessageWithReply() {

		// Send Msg and Receive Reply
		Destination temporaryQueue = context.createTemporaryQueue();

		try {
			message.setJMSReplyTo(temporaryQueue);
		} catch (JMSException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not set ReplyTo of Message");
		}

		JMSConsumer replyConsumer = context.createConsumer(temporaryQueue);
		log("[messaging] Sending Message With Reply");
		context.createProducer().send(queue, message);
		log("[messaging] Waiting for Receive");
		reply = replyConsumer.receive();
		log("[messaging] Reply Received");
	}
	
	/**
	 * Send a JMS message without expecting a reply
	 */
	void sendMessage() {
		
		// Send Msg
		log("[messaging] Sending Message (Not Waiting for reply)");
		context.createProducer().send(queue, message);
	}

	/**
	 * Returns true if result message is an exception
	 */
	boolean replyIsException() {
		try {
			boolean isException = reply.getBooleanProperty(IS_EXCEPTION_PROPERTY);
			return isException;
		} catch (JMSException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not get is_exception property of reply");
		}
	}

	/**
	 * Returns the exception message of the reult
	 */
	String getExceptionText() {
        return getReplyValue(EXCEPTION_PROPERTY);
    }

    void log(String message){
		if(logger == null)
			return;

		logger.info(message);
	}
}
