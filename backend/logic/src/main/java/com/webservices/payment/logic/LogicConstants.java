package com.webservices.payment.logic;

/**
 * Class with constants for JMS
 *
 * @author Lasse
 * @version 1.0
 */
public class LogicConstants {
	
	public static final String QUEUE_CLIENT_ISSUE = "java:/jms/queue/dtu-pay-client-issue";
	
	public static final String QUEUE_CLIENT_CREATE = "java:/jms/queue/dtu-pay-client-create";
	
	public static final String QUEUE_TRANSACTIONS = "java:/jms/queue/dtu-pay-transactions";


	/**
	 * Constructor
	 * Hidden to prevent inheritance and instantiation
	 */
	private LogicConstants() {
	
	}
}
