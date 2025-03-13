package com.event;

/**
 *
 * @author Cristian
 */
public interface TableActionEvent {
    
    public void onRemove(int row);
    
    public void onEdit(int row);
}
