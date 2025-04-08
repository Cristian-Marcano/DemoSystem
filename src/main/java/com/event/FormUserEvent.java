package com.event;

/**
 *
 * @author Cristian
 */
public interface FormUserEvent {
    
    public void onCreate(String username, String password, String firstName, String lastName, String ci, String phone, String position) throws Exception;
    
    public void onSearch(String username, String firstName, String lastName, String ci, String phone, String position);
    
    public void onEdit(String username, String password, String firstName, String lastName, String ci, String phone, String position) throws Exception;
}
