import com.google.gson.Gson;
import com.webservices.payment.logic.LogicConstants;
import com.webservices.payment.messageing.ClientManagerMessenger;
import com.webservices.payment.messageing.TransactionsManagerMessenger;
import com.webservices.payment.model.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.*;

/**
 * MerchantRestInterface handles all rest call for regular account clients
 *
 * @author James, Kim, Jesper, Aleksandar
 * @version 1.0
 */
@Path("")
public class MerchantRestInterface {

	/**
	 * JMS Queue for MDB in class CreateClientMessageBean. creates a new DTU Merchant ACCOUNT
	 */
	@Resource(mappedName = LogicConstants.QUEUE_CLIENT_CREATE)
	private Queue createQueue;

	/**
	 * JMS Queue for MDB in class TransactionMessageBean. do transaction accept clients transactions
	 */
	@Resource(mappedName = LogicConstants.QUEUE_TRANSACTIONS)
	private Queue transactionQueue;


	@Inject
	private JMSContext context;
	
	private Gson gson = new Gson();
	
	/**
	 * Class with all merchant argumets
	 *
	 * @author
	 * @version 1.0
	 */
	public class payAtMerchantArguments {
		
		public String tokenUUID;
		public String merchantUUID;
		public long amount;
	}
	
	/**
	 * <b>POST</b> REST call to initiate a payment
	 *
	 * @param json (required) object string with keys tokenUUID,  merchantUUID and amount
	 * @exception BadRequestException returns json object with key error containing simple string
	 * @execption TransactionException returns json string error with key error, that request failed
	 * @return json string with  key message that transaction is accepted
	 */
	@POST
	@Path("/transactions/")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response payAtMerchant(String json) {
		ITransactionsManager manager = new TransactionsManagerMessenger(context, transactionQueue);
		payAtMerchantArguments arguments = gson.fromJson(json, payAtMerchantArguments.class);

		if(arguments == null ||
				arguments.tokenUUID == null || arguments.tokenUUID.isEmpty() ||
				arguments.merchantUUID == null || arguments.merchantUUID.isEmpty()
				)
			return GetJsonResponse.error(BAD_REQUEST,"Please supply JSON in the format: \"{\"tokenUUID\": \"UUID String\",  \"merchantUUID\": \"UUID String\", \"amount\": \"1000\"}\"");

		UUID tokenUUID = UUID.fromString(arguments.tokenUUID);
		UUID merchantUUID = UUID.fromString(arguments.merchantUUID);
		BigDecimal amount = new BigDecimal(arguments.amount);

		try {
			manager.newTransaction(tokenUUID, merchantUUID, amount);
		} catch (TransactionException e) {
			return GetJsonResponse.error(BAD_REQUEST,e.getMessage());
		}
		
		return GetJsonResponse.succes(ACCEPTED,new JsonMessage("Transaction Accepted"));
	}
	
	public class CreateMerchantArguments {
		public String name;
		public String cvr;
	}


	/**
	 * <b>POST</b> REST call for creating a merchant DTUPay Acoount
	 *
	 * @param json (required) with keys name, John and cvr
	 * @exception BadRequestException returns json error string bad json given
	 * @exception LogicException return json error string. failed at crating merchant
	 * @exception InternalServerErrorException return error string, that something went wrong in the server
	 * @return  json object string with keys  uuid, BankAccountID, firstName, lastName and cpr
	 */
	@POST
	@Path("/merchants/")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response createMerchant(String json) {
		IClientManager manager = new ClientManagerMessenger(context, createQueue);
		CreateMerchantArguments arguments = gson.fromJson(json, CreateMerchantArguments.class);

		if(arguments == null ||
				arguments.cvr == null || arguments.cvr.isEmpty() ||
				arguments.name == null || arguments.name.isEmpty()
				)
			return GetJsonResponse.error(BAD_REQUEST,
					"Please supply JSON in the format: \"{  \"name\": \"John\", \"cvr\": \"10000\"}\"");


		Client merchant = null;
		try {
			merchant = manager.createClient(arguments.name, "merchant", arguments.cvr);
		} catch (LogicException e) {
			return GetJsonResponse.error(BAD_REQUEST,e.getMessage());
		}

		if (merchant == null) {
			return GetJsonResponse.error(INTERNAL_SERVER_ERROR,"failed to create merchant account");
		}
		return GetJsonResponse.succes(CREATED,merchant);
	}


	/**
	 * <b>GET</b> Rest call for testing purposes only
	 */
	@GET
	@Path("/ping")
	@Produces({MediaType.TEXT_PLAIN})
	@Deprecated
	public Response getPing() {
		return Response.status(Response.Status.OK).entity("PONG").build();
	}
}

