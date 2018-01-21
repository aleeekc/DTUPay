package com.webservices.payment.model;

import java.util.UUID;

/**
 * IIdentifiable
 *
 * @author Lasse
 * @version 1.0
 */
public interface IIdentifiable {
    
    /**
     * Returns UUID
     *
     * @return UUID
     */
    public UUID getUUID();
}
