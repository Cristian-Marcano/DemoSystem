package com.ux;

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
 * Clase que auxilia el calculo de importe de un producto de la tabla
 */
public class TableStockCellEditor extends DefaultCellEditor {
    
    /**
     * Atributos que facilitan el calculo del importe de un producto de la fila de la tabla
     * 
     * columnAmount: dato que identifica la columna que contendra los importe de los productos
     * table: objeto que referencia la tabla en cuestion que se esta usando
     * stockEvent: variable que contiene funciones que se ejecutan cuando ocurren ciertos eventos
     */
    
    private int columnAmount;
    private JTable table;
    private TableStockEvent stockEvent;
    
    public TableStockCellEditor(int columnAmount, TableStockEvent event) {
        super(new JTextField());
        this.columnAmount = columnAmount;
        this.stockEvent = event;
    }
    
    @Override //* Genera la casilla con un JTextField que solo permite numeros
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
    
    //* Obtiene el texto escrito en el JTextField y lo utiliza para calcular el importe ejecutando las funciones de stockEvent,
    @Override //* para obtener el precio unitario y la cantidad del producto disponible
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
                
                if(edited) stockEvent.onEdited(); //* Una vez editado muestra en el SalePanel el cambio de subTotal, Tax, Total
                
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
