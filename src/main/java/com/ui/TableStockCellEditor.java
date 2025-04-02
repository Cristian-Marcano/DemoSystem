package com.ui;

import com.event.TableStockEvent;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Cristian
 */
public class TableStockCellEditor extends DefaultCellEditor {
    
    private int columnAmount;
    private JTable table;
    private TableStockEvent stockEvent;
    
    public TableStockCellEditor(int columnAmount, TableStockEvent event) {
        super(new JTextField());
        this.columnAmount = columnAmount;
        this.stockEvent = event;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JTextField inputEditor = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
        
        inputEditor.setBorder(null);
        this.table = table;
        
        inputEditor.setText((value != null) ? value.toString(): "");
        inputEditor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if(!Character.isDigit(c) || c == '0' && inputEditor.getText().isEmpty())
                    ke.consume(); // Evita caracteres no numéricos y ceros al inicio
            }
        });
        
        return inputEditor;
    }
    
    @Override
    public boolean stopCellEditing() {
        JTextField inputEditor = (JTextField) getComponent();
        
        try {
            int value = Integer.parseInt(inputEditor.getText());
            if(value > 0) {
                int row = table.getSelectedRow();
                int idProduct = (int) table.getValueAt(row, 0); //* La primera columna SIEMPRE contendra el Id
                BigDecimal price = stockEvent.stopEditing(idProduct, value);
                table.setValueAt(price.multiply(new BigDecimal(value)), row, columnAmount);
                
                boolean edited = super.stopCellEditing();
                
                if(edited) stockEvent.onEdited();
                
                return edited;
            }
        } catch(NumberFormatException ignored) {
            System.err.println("No se permite datos no numericos");
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
