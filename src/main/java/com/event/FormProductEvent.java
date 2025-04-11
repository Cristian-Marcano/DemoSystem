package com.event;

import com.model.Product;

/**
 *
 * @author Cristian
 */
public interface FormProductEvent {
    
    public void onCreate(String title, String detail, int availability, String price) throws Exception;
    
    public void onEdit(Product product) throws Exception;
}
