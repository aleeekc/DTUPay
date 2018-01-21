package com.webservices.payment.model;

import java.util.UUID;

/**
 * Client manager interface
 *
 * @author Lasse
 * @version 1.0
 */
public interface ITokenManager {
    Token issueToken(UUID client) throws LogicException;
}
