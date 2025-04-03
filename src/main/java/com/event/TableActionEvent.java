package com.event;

/**
 *
 * @author Cristian
 * Interface de eventos del PanelActionTable que se encuentran 
 * en la ultima columna de la tabla del PanelSale
 */
public interface TableActionEvent {
    
    public void onRemove(int row);
    
    public void onEdit(int row);
}
