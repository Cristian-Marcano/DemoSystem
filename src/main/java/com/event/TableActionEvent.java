package com.util;

/**
 *
 * @author Cristian
 */
public interface TableActionEvent {
    
    public void onRemove(int row);
    
    public void onEdit(int row);
}
