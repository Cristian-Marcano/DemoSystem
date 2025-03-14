package com.model;

/**
 *
 * @author Cristian
 */
public class Client {
    
    private int id;
    private String fullName, rif, email, phone;
    
    public Client(int id, String fullName, String rif, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.rif = rif;
        this.email = email;
        this.phone = phone;
    }
    
    //* Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public void setRif(String rif) {
        this.rif = rif;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    //* Getters
    public int getId() {
        return id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getRif() {
        return rif;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
}
