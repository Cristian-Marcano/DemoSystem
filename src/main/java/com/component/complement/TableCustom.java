package com.component.complement;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Cristian
 * Clase complementaria que customiza el color, la Fuente,
 * el background, el gridColor, entre otros
 */
public class TableCustom extends JTable {
    
    /**
     * Atributos que facilitan la edicion del elemento 
     * 
     * headerBg: color de la cabecera de la tabla
     * rowOddBg: color de fondo de las filas impares de la tabla
     * rowEvenBg: color de fondo de las filas pare  de la tabla
     */
    
    private Color headerBg, rowOddBg, rowEvenBg;
    
    public TableCustom(Color headerBg, Color rowOddBg, Color rowEvenBg, Color gridColor) {
        this.headerBg = headerBg;
        this.rowEvenBg = rowEvenBg;
        this.rowOddBg = rowOddBg;
        getTableHeader().setDefaultRenderer(new TableCostumHeader());
        getTableHeader().setPreferredSize(new Dimension(0, 35));
        setDefaultRenderer(Object.class, new TableCostumCell());
        setGridColor(gridColor);
    }
    
    //* Arregla la parte faltante de la tabla de arriba a la derecha añadiendo un panel con el color de la cabecera
    public void fixTable(JScrollPane scroll) {
        JPanel panel = new JPanel();
        panel.setBackground(headerBg);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
    }
    
    //* Clase que solo se encargara de renderizar la cabecera de la tabla
    private class TableCostumHeader extends DefaultTableCellRenderer {
        
        @Override //* Genera|Renderiza la casilla de la cabecera de la tabla con el color de al cabecera decidido
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            com.setBackground(headerBg);
            com.setForeground(new Color(240, 240, 240));
            com.setFont(new Font("SansSerif", 1, 14));
            return com;
        }
    }
    
    //* Clase que se encargara de renderizar las demas casillas de la tabla
    private class TableCostumCell extends DefaultTableCellRenderer {
        
        @Override //* Genera|Renderiza la casillas de la tabla con su respectivo background dependiendo si su fila es par o impar
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int row, int column) {
            Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, row, column);
            if(isCellSelected(row, column) && (row % 2 == 0)) {
                com.setBackground(rowEvenBg.darker());
            } else if(isCellSelected(row, column)) {
                com.setBackground(rowOddBg.darker());
            } else if(row % 2 == 0){
                com.setBackground(rowEvenBg);
            } else {
                com.setBackground(rowOddBg);
            }
            setBorder(new EmptyBorder(0, 5, 0, 5));
            return com;
        }
    }
}
