package com.webservices.payment.logic.testframework;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;

import java.math.BigDecimal;

/**
 * @author Erik
 * @version 1.0
 */
public class BankAccountManager {
	private static BankService bank = new BankServiceService().getBankServicePort();
	
	/**
	 * Function for crating an account in the fast bank service
	 *
	 * @param firstName (required) first name of the client
	 * @param lastName  (required) last name of the client
	 * @param cpr       (required) CPR number of the client
	 * @param bal       (required) client balance
	 * @return an Account object with the newly created account
	 */
	public static Account createAccount(String firstName, String lastName, String cpr, int bal) {
		Account newAcc;
		try {
			newAcc = bank.getAccountByCprNumber(cpr);
		} catch (BankServiceException_Exception e1) {
			//Try Creating new account
			dtu.ws.fastmoney.User bankUser = new dtu.ws.fastmoney.User();
			bankUser.setCprNumber(cpr);
			bankUser.setFirstName(firstName);
			bankUser.setLastName(lastName);
			
			try {
				bank.createAccountWithBalance(bankUser, BigDecimal.valueOf(bal));
			} catch (BankServiceException_Exception e2) {
				e2.printStackTrace();
				throw new RuntimeException("Could not create new bank account");
			}
			
			try {
				newAcc = bank.getAccountByCprNumber(cpr);
			} catch (BankServiceException_Exception e3) {
				e3.printStackTrace();
				throw new RuntimeException("Get newly created account");
			}
		}
		return newAcc;
	}
	
	/**
	 * Function for deleting an account in the fast money bank service
	 *
	 * @param accId (required) account identifier
	 */
	public static void deleteAccount(String accId) {
		try {
			bank.retireAccount(accId);
		} catch (BankServiceException_Exception e) {
			throw new RuntimeException("Could not delete account from bank");
		}
	}
}
