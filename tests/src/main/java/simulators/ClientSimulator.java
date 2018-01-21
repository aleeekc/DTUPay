package simulators;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import simulators.core.BarcodeDescription;
import simulators.core.Client;
import simulators.core.ClientDescription;
import simulators.core.IClientAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Jesper, Kasper, Kim
 */
public class ClientSimulator implements IClientAPI {
    String baseURI;

    public ClientSimulator(String baseURI){
        this.baseURI = baseURI;
    }

    public Client registerClient(String firstName, String lastName, String cpr) {
        ClientDescription desc = new ClientDescription(firstName, lastName, cpr);
        HttpResponse<Client> r;
        try {
            r = Unirest.post(baseURI+ "/DTUPay/" + "clients/").header("Content-Type", "application/json").body(desc).asObject(Client.class);
        } catch (Exception e) {
            return null;
        }
        return r.getBody();
    }

    public String requestBarcode(String clientUUID) {
        HttpResponse<BarcodeDescription> r;
        String url;
        try {
            url = baseURI+ "/DTUPay/clients/barcode/";
            r = Unirest.post(url).header("Content-Type", "application/json").body(clientUUID).asObject(BarcodeDescription.class);
        } catch (UnirestException e) {
            return null;
        }

        return r.getBody().barcodeUUID;
    }
}
