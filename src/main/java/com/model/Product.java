package com.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Cristian
 */
public class Product {
    
    private int id, availability;
    private String title, detail;
    private BigDecimal price;
    
    public Product(int id, int availability, String title, String detail, BigDecimal price) {
        this.id = id;
        this.availability = availability;
        this.title = title;
        this.detail = detail;
        this.price = price;
    }
    
    //* Setters
    public void setAvailability(int availability) {
        this.availability = availability;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    //* Getters
    public int getId() {
        return id;
    }
    
    public int getAvailability() {
        return availability;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return id == product.getId();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
