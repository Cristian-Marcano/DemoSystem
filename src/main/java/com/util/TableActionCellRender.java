package com.util;

import com.component.PanelActionTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Cristian
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

    private boolean onlyBtn;
    
    public TableActionCellRender(boolean only) {
        onlyBtn = only;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        PanelActionTable actionTable = new PanelActionTable();
        actionTable.onlyRemoveBtn(onlyBtn);
        if (isSeleted == false && row % 2 == 0) {
            actionTable.setBackground(com.getBackground());
        } else {
            actionTable.setBackground(com.getBackground());
        }
        return actionTable;
    }
}
