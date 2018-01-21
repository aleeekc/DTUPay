package com.webservices.payment.persistence;

import com.webservices.payment.model.*;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.UUID;

/**
 * In memory database for storing Clients and Tokens
 *
 * @author Lasse, Jesper
 * @version 1.0
 */
@Singleton
public class MemoryPersistencyService implements IPersistencyService {
    private static MemoryPersistencyService ourInstance = new MemoryPersistencyService();

    /**
     * Constructor
     */
    private MemoryPersistencyService() {

    }
    
    /**
     * @return the singleton instance of the class
     */
    public static MemoryPersistencyService getInstance() {
        return ourInstance;
    }

    private ArrayList<Client> clients = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();

    /**
     * Storing a client object in an ArrayList
     * @param client Client object to be stored (required not null)
     */
    @Override
    public void storeClient(Client client) {

        if(client == null)
            throw new IllegalArgumentException("[MemoryPersistencyService:storeClient] Tried To Store Null Client!");

        clients.add(client);
    }
    
    /**
     * Getting a client object from the Client ArrayList based on the CPR
     *
     * @param cpr CPR number of the Client to be retrieved (required not null)
     * @return The Client with the specified CPR or null if no client could be found
     */
    @Override
    public Client retrieveClient(String cpr) {

        if (cpr == null)
            throw new IllegalArgumentException();

        for (Client client : clients) {
            if(client.cpr.equals(cpr))
                return client;
        }
        return null;
    }
    
    /**
     * Retriving a client object from the client ArrayList based on the  tokenID
     *
     * @param tokenID UUID of the Client to be retrieved (required not null)
     * @return the Client with the specified UUID or null if no client could be found
     */
    @Override
    public Client retrieveHolderOfToken(UUID tokenID) {

        if (tokenID == null)
            throw new IllegalArgumentException();

        for (Token token : tokens) {
            if (token.uuid.equals(tokenID))
                return retrieveClient(token.ownerUUID);
        }
        return null;
    }
    
    /**
     * Getting a client object from the client based on the client UUID
     *
     * @param id (required not null)
     */
    @Override
    public Client retrieveClient(UUID id) {

        if (id == null)
            throw new IllegalArgumentException();

        for (Client client : clients) {
            if(client.getUUID().equals(id))
                return client;
        }
        return null;
    }
    
    /**
     * Persists a token and links it to a user
     *
     * @param token Token to be stored (required not null)
     * @param clientUUID UUID of the owner of the Token (required not null)
     * @throws PersistenceException if no user could be found matching the given id
     */
    @Override
    public void assignTokenToClient(Token token, UUID clientUUID) {

        if (clientUUID == null || token == null)
            throw new IllegalArgumentException();

        Client client = retrieveClient(clientUUID);

        if (client == null)
            throw new PersistenceException("No client with uuid: " + clientUUID);

        token.ownerUUID = clientUUID;
        tokens.add(token);
    }
    
    /**
     * Gets a token object from the in memory database based on a token UUID
     *
     * @param tokenUUID token identifier (required not null)
     * @return token object or null if no token could be found
     */
    @Override
    public Token retrieveToken(UUID tokenUUID) {
        if (tokenUUID == null)
            throw new IllegalArgumentException();

        for (Token token : tokens) {
            if(token.uuid.equals(tokenUUID))
                return token;
        }
        return null;
    }

    /**
     * Marks a token as used
     *
     * @param tokenUUID (required)  token identifier.
     */
    @Override
    public void markTokenAsUsed(UUID tokenUUID) {
        Token token = retrieveToken(tokenUUID);
        token.used = true;
    }
}
