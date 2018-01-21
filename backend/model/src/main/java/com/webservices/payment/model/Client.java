package com.webservices.payment.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Client class that describes what a logic_tests is to DTUPay system
 *
 * @author Kasper, James
 * @version 1.0
 */

public class Client implements Serializable, IIdentifiable{
	
	public final String firstName;
	public final String lastName;
	public final String cpr;
	public final UUID uuid;
	public final String bankAccountID;
	
	/**
	 * Constructor
	 *
	 * @param firstName     (required)
	 * @param lastName      (required)
	 * @param cpr           (required)
	 * @param uuid          (required)
	 * @param bankAccountID (required)
	 */
	public Client(String firstName, String lastName, String cpr, UUID uuid, String bankAccountID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.cpr = cpr;
		this.uuid = uuid;
		this.bankAccountID = bankAccountID;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Checks if 2 User have the same UUID
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Client other = (Client) o;
		return Objects.equals(uuid, other.uuid);
	}

	/**
	 * Returns the hash of the uuid
	 */
	@Override
	public int hashCode() {

		return Objects.hash(uuid);
	}
}
