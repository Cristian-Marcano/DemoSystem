package com.ui;

import com.component.PanelActionTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Cristian
 * Clase que se encarga de renderizar el componente PanelActionTable en la casilla de una tabla
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

    /**
     * Atributos que facilitan la integracion exacta del componente en la casilla de la tabla
     * 
     * onlyBtn: confirma si en el panelAction solo va tener el btn de remove (true) o no (false)
     * rowOddBg: color de fondo que tendran las casillas de las filas impares
     * rowEvenBg: color de fondo que tendran las casillas de las filas pares
     */
    
    private boolean onlyBtn;
    private Color rowOddBg, rowEvenBg;
    
    public TableActionCellRender(boolean only, Color rowOddBg, Color rowEvenBg) {
        onlyBtn = only;
        this.rowOddBg = rowOddBg;
        this.rowEvenBg = rowEvenBg;
    }
    
    @Override //* Genera la casilla con el componente PanelActionTable, mas cambia el color de fondo si esta o no seleccionada
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
