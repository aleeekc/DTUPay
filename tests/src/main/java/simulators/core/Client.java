package simulators.core;

import java.util.UUID;

/**
 * Client class that describes what a logic_tests is to DTUPay system
 *
 * @author James, Kasper
 * @version 1.0
 */

public class Client {
	
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
}
