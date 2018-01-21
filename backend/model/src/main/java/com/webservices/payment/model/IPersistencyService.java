package com.webservices.payment.model;

import java.util.UUID;

/**
 * @author Jesper, Lasse
 */
public interface IPersistencyService {

    void storeClient(Client client);

    Client retrieveClient(String cpr);

    Client retrieveClient(UUID id);

    Client retrieveHolderOfToken(UUID token);

    void assignTokenToClient(Token token, UUID clientID);

    Token retrieveToken(UUID tokenUUID);

    void markTokenAsUsed(UUID uuid);
}
