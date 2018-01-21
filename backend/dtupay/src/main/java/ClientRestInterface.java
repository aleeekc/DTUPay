import com.google.gson.Gson;
import com.webservices.payment.logic.LogicConstants;
import com.webservices.payment.messageing.ClientManagerMessenger;
import com.webservices.payment.messageing.TokenManagerMessenger;
import com.webservices.payment.model.*;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

/**
 * ClientRestInterface handles all rest call for regular account clients
 *
 * @author Kasper, Kim, Jesper, Aleksandar
 * @version 1.0
 */
@Path("")
public class ClientRestInterface {

    private Logger logger = Logger.getLogger(ClientRestInterface.class);


	/**
	 * JMS Queue for MDB in class CreateClientMessageBean. creates a new DTU ACCOUNT
	 */
    @Resource(mappedName = LogicConstants.QUEUE_CLIENT_CREATE)
    private Queue createQueue;

	/**
	 * JMS Queue for MDB in class IssueTokenMessageBean. Obtains a DTU ACCOUNT token
	 */
    @Resource(mappedName = LogicConstants.QUEUE_CLIENT_ISSUE)
    private Queue issueQueue;
	/**
	 * JMS Queue for MDB in class TransactionMessageBean. do transaction from client to merchant
	 */
    @Resource(mappedName = LogicConstants.QUEUE_TRANSACTIONS)
    private Queue transactionQueue;

    private Gson gson = new Gson();

    @Inject
    private JMSContext context;

    public class CreateClientArguments{
        public String firstName;
        public String lastName;
        public String cpr;
    }

	/**
	 * <b>POST</b> REST call for creating a client
	 * Same as createmerchant
	 *
	 * @param json (required) with keys firstName, lastName and cpr
	 * @exception BadRequestException returns simple message bad json format given
	 * @exception LogicException as BadRequestExecptions returns message failed to create client
	 * @exception Exception as InteralServerException returns simple message that the server fails
	 * @return return new DTUPay Account Json object with keys uuid, bankAccountID, firstName, lastName and cpr
	 */
    @POST
    @Path("/clients/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createClient(String json) {
        IClientManager manager = new ClientManagerMessenger(context,createQueue);

        CreateClientArguments arguments = gson.fromJson(json,CreateClientArguments.class);

        if(arguments == null)
            return GetJsonResponse.error(BAD_REQUEST,"Please supply JSON in the format: \"{  \"firstName\": \"Jonh\",  \"lastName\": \"Doe\", \"cpr\": \"Some String\"}\"");

        Client client;
        try {
            client = manager.createClient(arguments.firstName,arguments.lastName,arguments.cpr);
        }catch (LogicException e){
            return GetJsonResponse.error(BAD_REQUEST,e.getMessage());
        }catch (Exception other){
            return GetJsonResponse.error(INTERNAL_SERVER_ERROR,other.getMessage());
        }

        if (client == null) {
            return GetJsonResponse.error(INTERNAL_SERVER_ERROR,"Something went wrong in createClient and the returned client is therefore null");
        }

        return GetJsonResponse.succes(CREATED,client);
    }

	/**
	 * <b>GET</b> Rest call for generating a barcodeimage
	 * and returns the genereated BarcodeID
	 *
	 * @param dtuPayAccountID (required) UUID of the client in string format
	 *  @exception BadRequestException return  json error string empty account or wrong format given id given
	 * @exception LogicException returns json error string
	 * @exception InternalServerErrorException resturns a json error string that the server failed the request
	 */
    @POST
    @Path("/clients/barcode/")
    @Produces({ MediaType.APPLICATION_JSON})
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response obtainBarcode(String dtuPayAccountID) {

        if(dtuPayAccountID == null || dtuPayAccountID.isEmpty())
            return GetJsonResponse.error(BAD_REQUEST,"Please supply a uuidString Query Parameter containing the uuid of the client");

        UUID uuid = UUID.fromString(dtuPayAccountID);
        if(uuid == null)
            return GetJsonResponse.error(BAD_REQUEST,"Please supply a valid uuid");


        ITokenManager userManager = new TokenManagerMessenger(context,issueQueue);
        Token token;
        try {
            token = userManager.issueToken(uuid);
        } catch (LogicException e) {
            return GetJsonResponse.error(BAD_REQUEST,e.getMessage());
        }catch (Exception other){
            return GetJsonResponse.error(INTERNAL_SERVER_ERROR,other.getMessage());
        }

        if (token == null) {
            return GetJsonResponse.error(INTERNAL_SERVER_ERROR,"Something went wrong in issueToken and the returned token is therefore null");
        }

        return GetJsonResponse.succes(CREATED,new BarcodeTransporter(token.uuid));

    }
}