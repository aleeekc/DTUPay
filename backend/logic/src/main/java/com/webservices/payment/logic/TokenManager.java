package com.webservices.payment.logic;

import com.webservices.payment.model.*;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;

import java.util.UUID;

/**
 * @author Kim, Jesper
 */
public class TokenManager implements ITokenManager {

	private IPersistencyService ps;

	/**
	 * Constructor
	 *
	 * @param persistencyService The IPersistencyService instance
	 *                           to be used for performing persistency operations
	 */
	public TokenManager(IPersistencyService persistencyService) {
		ps = persistencyService;
	}

	/**
	 * Used for linking a token to a client in the DTU Pay in memory database
	 *
	 * @param clientUUID (required) client ID in UUID format
	 */
    @Override
    public Token issueToken(UUID clientUUID) throws LogicException {
        if(clientUUID == null)
            throw new IllegalArgumentException();

        Client client = ps.retrieveClient(clientUUID);

        if(client == null)
            throw new LogicException("Client not found");

        Token token = TokenGenerator.generateToken(clientUUID);

        ps.assignTokenToClient(token, clientUUID);
        return token;
    }

}
