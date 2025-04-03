package com.event;

import com.model.Product;

/**
 *
 * @author Cristian
 * Interface de eventos de Items que se encuentran
 * en el inputSearch del PanelSearch
 */
public interface ItemEvent {
    
    public void onClick(Product product);
    
}
