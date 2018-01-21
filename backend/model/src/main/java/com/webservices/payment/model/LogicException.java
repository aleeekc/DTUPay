package com.webservices.payment.model;

/**
 * Custom transaction exception
 *
 * @author Lasse
 * @version 1.0
 */
public class LogicException extends Exception {

	/**
	 * Constructor
	 *
	 * @param message (required) the message that the exception will throw
	 */
	public LogicException(String message) {
		super(message);
	}
}
