package com.model;

import java.sql.Timestamp;

/**
 *
 * @author Cristian
 */
public class SaleInvoice {
    
    private int id, userId, clientId;
    private double total;
    private Timestamp createAt;
    
    public SaleInvoice(int id, int userId, int clientId, double total, Timestamp createAt) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.total = total;
        this.createAt = createAt;
    }
    
    //* Setters
    public void setTotal(double total) {
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
    
    public double getTotal() {
        return total;
    }
}
