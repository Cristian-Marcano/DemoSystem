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
 */
public class TableActionCellEditor extends DefaultCellEditor {

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

    @Override
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