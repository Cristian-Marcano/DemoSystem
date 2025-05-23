package com.model;

import java.math.BigDecimal;

/**
 *
 * @author Cristian
 */
public class Sale {
    
    private int id, saleInvoiceId, productId, quantity;
    private BigDecimal amount;
    
    public Sale(int id, int saleInvoiceId, int productId, int quantity, BigDecimal amount) {
        this.id = id;
        this.saleInvoiceId = saleInvoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }
    
    //* Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    //* Getters
    public int getId() {
        return id;
    }
    
    public int getSaleInvoiceId() {
        return saleInvoiceId;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
}
