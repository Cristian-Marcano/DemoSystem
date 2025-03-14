package com.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Cristian
 */
public class SaleInvoice {
    
    private int id, userId, clientId;
    private BigDecimal total;
    private Timestamp createAt;
    
    public SaleInvoice(int id, int userId, int clientId, BigDecimal total, Timestamp createAt) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.total = total;
        this.createAt = createAt;
    }
    
    //* Setters
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
    
    //* Getters
    public int getId() {
        return id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public int getClientId() {
        return clientId;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public Timestamp getCreateAt() {
        return createAt;
    }
}
