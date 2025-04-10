package com.event;

import com.model.User;
import com.model.UserInfo;

/**
 *
 * @author Cristian
 * Interface de eventos que se ejecutan para realizar
 * un proceso en el formulario de Usuario
 */
public interface FormUserEvent {
    
    public void onCreate(String username, String password, String firstName, String lastName, String ci, String phone, String position) throws Exception;
    
    public void onSearch(String username, String firstName, String lastName, String ci, String phone, String position);
    
    public void onEdit(User user, UserInfo userInfo) throws Exception;
}
