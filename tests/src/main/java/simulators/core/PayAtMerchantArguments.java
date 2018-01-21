package simulators.core;

/**
 * @author Aleksandar, Kasper
 */
public class PayAtMerchantArguments {

    public String tokenUUID;
    public String merchantUUID;
    public long amount;

    public PayAtMerchantArguments(String tokenUUID, String merchantUUID, long amount) {
        this.tokenUUID = tokenUUID;
        this.merchantUUID = merchantUUID;
        this.amount = amount;
    }
}
