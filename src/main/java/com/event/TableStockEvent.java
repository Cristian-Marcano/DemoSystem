package com.event;

import java.math.BigDecimal;

/**
 *
 * @author Cristian
 * Interface de eventos de la edicion de cualquier casilla
 * de la columna de cantidad de la tabla del PanelSale
 */
public interface TableStockEvent {
    
    public BigDecimal stopEditing(int id, int value) throws Exception;
    
    public void onEdited();
}
