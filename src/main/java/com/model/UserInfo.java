package com.model;

/**
 *
 * @author Cristian
 */
public class UserInfo {
    
    private int id, userId;
    private String firstName, lastName, phone, ci;
    
    public UserInfo(int id, int userId, String firstName, String lastName, String phone, String ci) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.ci = ci;
    }
    
    //* Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setCi(String ci) {
        this.ci = ci;
    }
    
    //* Getters
    public int getId() {
        return id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getCi() {
        return ci;
    }
}
