import com.google.gson.Gson;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import dtu.ws.fastmoney.*;
import dtu.ws.fastmoney.User;

import simulators.ClientSimulator;
import simulators.MerchantSimulator;
import simulators.core.Client;
import simulators.core.ClientDescription;
import simulators.core.IClientAPI;
import simulators.core.IMerchantAPI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * @author Kasper, Aleksandar
 */
public class TestingUtils {

    private static Map<String, String> environment = System.getenv();
    private static final String localAddress = "";
    private static final String remoteAddress = "";
    private static final String baseAddress = environment.containsKey("REMOTE_TEST") ? remoteAddress : localAddress;

    private static final String baseFirstName = "KobeFirst";
    private static final String baseLastName = "KobeLast";

    private static int userCount = 0;

    private static BankService bank = new BankServiceService().getBankServicePort();
    private static ArrayList<Account> issuedAccounts = new ArrayList<Account>();

    private static IClientAPI clientSimulator = new ClientSimulator(baseAddress);
    private static IMerchantAPI merchantSimulator = new MerchantSimulator(baseAddress);


    static {
        System.out.println(baseAddress);

        Unirest.setObjectMapper(new ObjectMapper() {
            Gson gson = new Gson();

            public <T> T readValue(String s, Class<T> aClass) {
                return gson.fromJson(s, aClass);
            }

            public String writeValue(Object o) {
                return gson.toJson(o);
            }
        });
    }


    public static ClientDescription getNewUserDescription() {
        return new ClientDescription(baseFirstName, baseLastName + userCount++, UUID.randomUUID().toString());
    }

    public static Account createBankAccountForUserDescription(ClientDescription ud, int balance) throws BankServiceException_Exception {
        return createBankAccountWithBalance(ud.firstName, ud.lastName, ud.cpr, balance);
    }

    public static void deleteBankAccountForUserDescription(ClientDescription ud) {
        try {
            Account acc = bank.getAccountByCprNumber(ud.cpr);
            bank.retireAccount(acc.getId());
        } catch (Exception e) {
            //Account doesn't exist
        }
    }


    private static Account createBankAccountWithBalance(String firstName, String lastName, String cpr, int balance) throws BankServiceException_Exception {

        BankService bank = new BankServiceService().getBankServicePort();
        User bankUser = new User();
        bankUser.setFirstName(firstName);
        bankUser.setLastName(lastName);
        bankUser.setCprNumber(cpr);

        String accountUUID = bank.createAccountWithBalance(bankUser, new BigDecimal(balance));

        Account acc = bank.getAccount(accountUUID);
        issuedAccounts.add(acc);
        return acc;
    }


    public static void deleteAllBankAccounts() {
        for (Account acc : issuedAccounts) {
            try {
                bank.retireAccount(acc.getId());
            } catch (BankServiceException_Exception e) {
                // Seems our account was deleted elsewhere
            }
        }
    }


    public static String obtainBarcode(String userUUID) {
        return clientSimulator.requestBarcode(userUUID);
    }

    public static Client createClient(ClientDescription desc) throws UnirestException {
        return clientSimulator.registerClient(desc.firstName, desc.lastName, desc.cpr);
    }

    public static String payAtMerchant(String tokenUUID, String merchantUUID, long amount) {
        return merchantSimulator.payAtMerchant(tokenUUID, merchantUUID, amount);
    }

    public static Client createMerchant(ClientDescription ud) {
        return merchantSimulator.registerMerchant(ud.firstName, ud.cpr);
    }


    public static void cleanUpDTUPAY() {
        GetRequest result = Unirest.get(baseAddress + "/delete");
    }
}

