package com.event;

import com.model.Product;
import com.model.User;
import com.model.UserInfo;

/**
 *
 * @author Cristian
 * Interface de eventos de Items
 */
public interface ItemEvent {
    
    public void onClick(Product product);
    
    public void onEdit(User user, UserInfo userInfo);
    
    public void onRemove(int id);
}
