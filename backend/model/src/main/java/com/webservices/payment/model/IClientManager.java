package com.webservices.payment.model;

import com.webservices.payment.model.Client;
import com.webservices.payment.model.Token;

import java.util.UUID;

/**
 * Client manager interface
 *
 * @author Kasper
 * @version 1.0
 */
public interface IClientManager {

    Client createClient(String firstName, String lastName, String cpr) throws LogicException;

}
