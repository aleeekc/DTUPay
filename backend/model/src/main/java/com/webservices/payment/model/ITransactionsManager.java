package com.webservices.payment.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Transaction interface
 *
 * @author Lasse
 * @version 1.0
 */
public interface ITransactionsManager {
    void newTransaction(UUID tokenUUID, UUID merchantUUID, BigDecimal amount) throws TransactionException;
}
