package com.model;

/**
 *
 * @author Cristian
 */
public class Product {
    
    private int id, availability;
    private String title, detail;
    private double price;
    
    public Product(int id, int availability, String title, String detail, double price) {
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
    
    public void setPrice(double price) {
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
    
    public double getPrice() {
        return price;
    }
}
