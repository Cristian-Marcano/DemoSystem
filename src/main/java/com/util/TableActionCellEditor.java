/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.util;

import com.component.PanelActionTable;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author Cristian
 */
public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;
    private boolean onlyBtn;

    public TableActionCellEditor(TableActionEvent event, boolean only) {
        super(new JCheckBox());
        this.event = event;
        onlyBtn = only;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelActionTable action = new PanelActionTable();
        action.onlyRemoveBtn(onlyBtn);
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}