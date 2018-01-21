package com.webservices.payment.logic.testframework;

import com.webservices.payment.logic.ClientManager;
import com.webservices.payment.logic.TokenManager;
import com.webservices.payment.logic.TransactionsManager;
import com.webservices.payment.model.*;
import com.webservices.payment.persistence.MemoryPersistencyService;
import dtu.ws.fastmoney.Account;
import org.junit.After;
import org.junit.Before;

import java.util.UUID;
/**
 * @author Lasse
 */
public abstract class LogicTestFramework {

    protected final IPersistencyService persistencyService;
    protected final ITokenManager issueTokenManager;
    protected final IClientManager createClientManager;
    protected final ITransactionsManager transactionManager;

    protected static final String clientFirstName = "John";
    protected static final String clientLastName = "Doe";
    protected static final String merchantName = "Notto";
    protected static final String merchantType = "Grocer";
    protected static final int startingBalance = 100;

    protected String cpr ;
    protected Account clientAccount;
    protected Client client;

    protected String cvr;
    protected Account merchantAccount;
    protected Client merchant;

    protected Token token;

    protected LogicTestFramework(IPersistencyService persistencyService, IClientManager createClientManager, ITokenManager issueTokenManager,ITransactionsManager transactionManager){
        this.persistencyService = persistencyService;
        this.createClientManager = createClientManager;
        this.issueTokenManager = issueTokenManager;
        this.transactionManager = transactionManager;
    }


    protected LogicTestFramework(){
        this.persistencyService = MemoryPersistencyService.getInstance();
        this.createClientManager = new ClientManager(persistencyService);
        this.issueTokenManager = new TokenManager(persistencyService);
        this.transactionManager = new TransactionsManager(persistencyService);
    }

    @Before
    public void before() {
        cpr = UUID.randomUUID().toString();
        cvr = UUID.randomUUID().toString();
        clientAccount = BankAccountManager.createAccount(clientFirstName, clientLastName, cpr, startingBalance);
        merchantAccount = BankAccountManager.createAccount(merchantName, merchantType, cvr, startingBalance);
    }

    @After
    public void after() {
        BankAccountManager.deleteAccount(clientAccount.getId());
        BankAccountManager.deleteAccount(merchantAccount.getId());
    }

    protected void setupSingleClient() throws LogicException {
        client = createClientManager.createClient(clientFirstName,clientLastName, cpr);
    }

    protected void setupSingleMerchant() throws LogicException {
        merchant = createClientManager.createClient(merchantName,merchantType,cvr);
    }

    protected void setupSingleClientWithToken() throws LogicException {
        setupSingleClient();
        token = issueTokenManager.issueToken(client.uuid);
    }
}
