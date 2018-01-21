package com.webservices.payment.persistence;

import com.webservices.payment.model.Client;
import com.webservices.payment.model.IPersistencyService;
import com.webservices.payment.model.PersistenceException;
import com.webservices.payment.model.Token;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Lasse, Jesper
 */
public class MemoryPersistencyServiceTest {

    private UUID tokenID;
    private Token token;
    private String clientCPR;
    private UUID clientID;
    private Client client;
    private IPersistencyService service;

    @org.junit.Before
    public void setUp() {
        service = MemoryPersistencyService.getInstance();
        //service = new MongoPersistencyService();

        clientID = UUID.randomUUID();
        clientCPR = UUID.randomUUID().toString();
        client = new Client("Jon","Doe", clientCPR, clientID, "BADBANKACCOUNTID");

        tokenID = UUID.randomUUID();
        token = new Token(tokenID, clientID, "path",System.currentTimeMillis());
    }

    @org.junit.Test
    public void storeClient() {
        service.storeClient(client);
        assertEquals(client,service.retrieveClient(clientID));
    }

    @org.junit.Test
    public void storeNullclient() {

        try {
            service.storeClient(null);
            fail();
        } catch (IllegalArgumentException ignored) {

        }
    }

    @org.junit.Test
    public void retrieveClient() {
        service.storeClient(client);
        assertEquals(client,service.retrieveClient(clientCPR));
    }

    @org.junit.Test
    public void retrieveNullClient() {
        service.storeClient(client);
        try{
            service.retrieveClient((String) null);
            fail();
        }
        catch (IllegalArgumentException ignored){ }

        try{
            service.retrieveClient((UUID) null);
            fail();
        }
        catch (IllegalArgumentException ignored){}
    }

    @org.junit.Test
    public void retrieveMissingClient() {
        service.storeClient(client);
        assertNull(service.retrieveClient(UUID.randomUUID()));
        assertNull(service.retrieveClient(UUID.randomUUID().toString()));
    }

    @org.junit.Test
    public void retrieveHolderOfToken() {
        service.storeClient(client);
        service.assignTokenToClient(token,clientID);

        Client holder = service.retrieveHolderOfToken(tokenID);
        assertEquals(client,holder);
    }

    @org.junit.Test
    public void retrieveNullHolderOfToken() {
        service.storeClient(client);
        service.assignTokenToClient(token,clientID);

        try {
            service.retrieveHolderOfToken(null);
            fail();
        } catch (IllegalArgumentException ignored) {

        }
    }

    @org.junit.Test
    public void retrieveMissingHolderOfToken() {
        service.storeClient(client);
        service.assignTokenToClient(token,clientID);

        Client holder = service.retrieveHolderOfToken(UUID.randomUUID());
        assertNull(holder);

    }


    @org.junit.Test
    public void assignTokenToClient() {
        service.storeClient(client);
        service.assignTokenToClient(token,clientID);

        Client holder = service.retrieveHolderOfToken(tokenID);
        assertEquals(client,holder);
    }

    @org.junit.Test
    public void assignNullTokenToClient() {
        service.storeClient(client);

        try{
            service.assignTokenToClient(null,clientID);
            fail();
        }
        catch (IllegalArgumentException ignored){ }

        try{
            service.assignTokenToClient(token,null);
            fail();
        }
        catch (IllegalArgumentException ignored){}
    }

    @org.junit.Test
    public void assignTokenToMissingClient() {
        service.storeClient(client);
        try {
            service.assignTokenToClient(token,UUID.randomUUID());
            fail();
        } catch (PersistenceException ignored) {

        }
    }

    @org.junit.Test
    public void markTokenUsed() {
        service.storeClient(client);
        service.assignTokenToClient(token, client.uuid);
        service.markTokenAsUsed(token.uuid);
        assertTrue(service.retrieveToken(token.uuid).used);
    }
}
