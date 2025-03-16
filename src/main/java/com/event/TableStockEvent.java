package com.event;

import java.math.BigDecimal;

/**
 *
 * @author Cristian
 */
public interface TableStockEvent {
    
    public BigDecimal stopEditing(int id, int value) throws Exception;
    
}
