package com.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Cristian
 */
public class SaleInvoice {
    
    private int id, userId, clientId;
    private BigDecimal total, subTotal, tax;
    private Timestamp createAt;
    
    public SaleInvoice(int id, int userId, int clientId, BigDecimal total, BigDecimal subTotal, BigDecimal tax, Timestamp createAt) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.total = total;
        this.subTotal = subTotal;
        this.tax = tax;
        this.createAt = createAt;
    }
    
    //* Setters
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
    public void setTax(BigDecimal tax) {
        this.tax = tax;
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
    
    public BigDecimal getSubTotal() {
        return subTotal;
    }
    
    public BigDecimal getTax() {
        return tax;
    }
    
    public Timestamp getCreateAt() {
        return createAt;
    }
}
