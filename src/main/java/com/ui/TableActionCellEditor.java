package com.ui;

import com.component.PanelActionTable;
import com.event.TableActionEvent;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author Cristian
 * Clase que se encarga de renderizar el componente PanelActionTable cuando la casilla es editable
 */
public class TableActionCellEditor extends DefaultCellEditor {

    /**
     * Atributos que facilitan la integracion exacta del componente en la casilla de la tabla cuando este es editable
     * 
     * event: variable que contiene funciones que se ejecutan cuando ocurren ciertos eventos con el PanelActionTable
     * onlyBtn: confirma si en el panelAction solo va tener el btn de remove (true) o no (false)
     * rowOddBg: color de fondo que tendran las casillas de las filas impares
     * rowEvenBg: color de fondo que tendran las casillas de las filas pares
     */
    
    private TableActionEvent event;
    private boolean onlyBtn;
    private Color rowOddBg, rowEvenBg;

    public TableActionCellEditor(TableActionEvent event, boolean only, Color rowOddBg, Color rowEvenBg) {
        super(new JCheckBox());
        this.event = event;
        onlyBtn = only;
        this.rowOddBg = rowOddBg;
        this.rowEvenBg = rowEvenBg;
    }

    @Override //* Genera la casilla con el componente PanelActionTable y ejecuta la funcion que corresponde dependiendo que btn se presione
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelActionTable action = new PanelActionTable();
        action.onlyRemoveBtn(onlyBtn);
        action.initEvent(event, row);
        if(row % 2 == 0) {
            action.setBackground(rowEvenBg.darker());
        } else {
            action.setBackground(rowOddBg.darker());
        }
        return action;
    }
}