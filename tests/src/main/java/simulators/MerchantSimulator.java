package simulators;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import simulators.core.Client;
import simulators.core.CreateMerchantArguments;
import simulators.core.IMerchantAPI;
import simulators.core.PayAtMerchantArguments;

/**
 * @author Kim, Jesper, Kasper
 */
public class MerchantSimulator implements IMerchantAPI {
    String baseURI;

    public MerchantSimulator(String baseURI) {
        this.baseURI = baseURI;
    }

    public Client registerMerchant(String merchantName, String cvr) {
        CreateMerchantArguments args = new CreateMerchantArguments(merchantName, cvr);
        HttpResponse<Client> r;
        try {
            r = Unirest.post(baseURI + "/DTUPay/" + "merchants/").header("Content-Type", "application/json").body(args).asObject(Client.class);
            int status = r.getStatus();
        } catch (UnirestException e) {
            return null;
        }

        return r.getBody();

    }

    public String payAtMerchant(String tokenUUID, String merchantUUID, long amount) {
        PayAtMerchantArguments args = new PayAtMerchantArguments(tokenUUID, merchantUUID, amount);

        HttpResponse<String> r;
        int status = 0;
        try {
            r = Unirest.post(baseURI + "/DTUPay/" + "transactions/").header("Content-Type", "application/json").body(args).asString();
            status = r.getStatus();
        } catch (UnirestException e) {
            return null;
        }
        return r.getBody();
    }
}
