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
    private Color rowOddBg, rowEvenBg;
    
    public TableActionCellRender(boolean only, Color rowOddBg, Color rowEvenBg) {
        onlyBtn = only;
        this.rowOddBg = rowOddBg;
        this.rowEvenBg = rowEvenBg;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        PanelActionTable actionTable = new PanelActionTable();
        actionTable.onlyRemoveBtn(onlyBtn);
        if (isSeleted && row % 2 == 0) {
            actionTable.setBackground(rowEvenBg.darker());
        } else if(isSeleted) {
            actionTable.setBackground(rowOddBg.darker());
        } else if(row % 2 == 0) {
            actionTable.setBackground(rowEvenBg);
        } else {
            actionTable.setBackground(rowOddBg);
        }
        return actionTable;
    }
}
