package com.webservices.payment.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Token class that describes what a token is to DTUPay system
 *
 * @author James, Kasper, Kim
 * @version 1.0
 */

public class Token implements Serializable, IIdentifiable {

	/**
	 * Constructor
	 * @param uuid        (required) token id in a <b>UUID format</b>
	 * @param owner		  (required) user id in a <b>UUID format</b>
     * @param barcodePath (required) location of the generated barcode file on the server
     * @param timestamp   (required) note that the timestamp should be <b>long</b>
     */
	public Token(UUID uuid, UUID owner, String barcodePath, long timestamp) {
		this.uuid = uuid;
		ownerUUID = owner;
		this.barcodePath = barcodePath;
		this.timeStamp = timestamp;
		this.used = false;
	}


	public boolean used;
	public UUID ownerUUID;
	public UUID uuid;
	public String barcodePath;
	public long timeStamp;

	/**
	 * Returns the clients UUID
	 */
	@Override
	public UUID getUUID() {
		return uuid;
	}

	public static final long EXPIRATION_TIME = 300000; // Valid for 5 minutes

	public boolean isValid() {
		long timeDiff = System.currentTimeMillis() - timeStamp;

		if (timeDiff > EXPIRATION_TIME || used) {
			return false;
		}

		return true;
	}
}
