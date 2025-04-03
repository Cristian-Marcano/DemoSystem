package com.event;

/**
 *
 * @author Cristian
 * Interface de eventos de autenticacion
 */
public interface FormAuthEvent {
    
    public void onLogin(String username, String password) throws Exception;
    
    public void onSignUp(String username, String password, String firstName, String lastName, String ci, String phone);
    
    public void onForgot(String username, String firstName, String lastName, String ci) throws Exception;
}
