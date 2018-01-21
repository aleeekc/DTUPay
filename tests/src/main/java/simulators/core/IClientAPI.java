package simulators.core;

import simulators.core.Client;

/**
 * @author Kasper, Jesper, Kim
 */
public interface IClientAPI {


    Client registerClient(String firstName, String lastName, String cpr);
    String requestBarcode(String clientUUID);
}
