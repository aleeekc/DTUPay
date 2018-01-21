package simulators.core;

import simulators.core.Client;

/**
 * @author Kasper, Jesper, Kim
 */
public interface IMerchantAPI {
   Client registerMerchant(String merchantName, String cvr);
   String payAtMerchant(String tokenUUID, String merchantUUID, long amount);
}
