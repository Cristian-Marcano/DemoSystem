package com.component;

import com.component.complement.SideBar;
import com.event.FormProductEvent;
import com.event.TableActionEvent;
import com.model.Product;
import com.service.ProductService;
import com.ui.TableActionCellEditor;
import com.ui.TableActionCellRender;
import java.awt.Color;
import com.util.TextPrompt;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cristian
 */
public class InventoryPanel extends javax.swing.JPanel {

    private SideBar sideBar;
    private TableActionEvent tableEvent;
    private FormProductEvent productEvent;
    private List<Product> listProducts = new ArrayList<>();
    private PanelFormProduct addFormProduct, editFormProduct;
    
    public TextPrompt placeholder;
    
    /**
     * Creates new form InventoryPanel
     */
    public InventoryPanel() {
        initComponents();
        initOthersElements();
        placeholder = new TextPrompt("Insertar Nombre o ID del Producto", inputSearch);
        
        tableEvent = new TableActionEvent() {
            @Override
            public void onRemove(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                
                int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
                
                int stock = Integer.parseInt(table.getModel().getValueAt(row, 4).toString());
                
                try {
                    ProductService productService = new ProductService();
                    
                    productService.decreaseStock(id, stock);
                    
                    initProductContent();
                } catch(SQLException e) {
                    System.err.println(e.getMessage());
                    JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onEdit(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                
                int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
                String name = table.getModel().getValueAt(row, 1).toString(), detail = table.getModel().getValueAt(row, 2).toString();
                BigDecimal price = new BigDecimal(table.getModel().getValueAt(row, 3).toString());
                int availability = Integer.parseInt(table.getModel().getValueAt(row, 4).toString());
                
                Product product = new Product(id, availability, name, detail, price);
                
                editFormProduct.setProduct(product);
                
                btnAdd.setVisible(false);
                sideBar.setPanel(editFormProduct);
                sideBar.toggle();
                
                table.setEnabled(!table.isEnabled());
                
                scroll.getVerticalScrollBar().setVisible(!scroll.getVerticalScrollBar().isVisible());
                scroll.setEnabled(!scroll.isEnabled());
            }
        };
        
        productEvent = new FormProductEvent() {
            @Override
            public void onCreate(String title, String detail, int availability, String price) throws Exception {
                try {
                    ProductService productService = new ProductService();
                
                    BigDecimal priceDecimal = new BigDecimal(price);
                    
                    productService.createProduct(title, detail, availability, priceDecimal);
                    
                    initProductContent();
                    
                } catch(SQLException e) {
                    
                    if(e.getErrorCode() == 1062) {
                        throw new Exception("No puede insertar un nombre de un producto ya existente");
                    } else {
                        System.err.println(e.getMessage());
                        JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void onEdit(Product product) throws Exception {
                try {
                    ProductService productService = new ProductService();
                
                    productService.updateProduct(product);
                    
                    initProductContent();
                } catch(SQLException e) {
                    
                    if(e.getErrorCode() == 1062) {
                        throw new Exception("No puede insertar un nombre de un producto ya existente");
                    } else {
                        System.err.println(e.getMessage());
                        JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        
//        ((com.component.complement.TableCustom) table).fixTable(scroll);

        table.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender(false, new Color(162,175,186), new Color(200, 218, 234)));
        table.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(tableEvent, false, new Color(162,175,186), new Color(200, 218, 234)));
        
        setComponentZOrder(layared, 0);
        
        layared.setLayout(null);
        glassPane.setVisible(false);
        
        sideBar = new SideBar(layared, (com.component.complement.GlassPane) glassPane);
        
        layared.add(sideBar, JLayeredPane.POPUP_LAYER);
    }
    
    private void initOthersElements() {
        addFormProduct = new PanelFormProduct(productEvent, 0);
        editFormProduct = new PanelFormProduct(productEvent, 1);
        
        glassPane.addMouseListener(new MouseAdapter() {
            @Override //* Si clickea glassPane cuando el sideBar se esta mostrando, se oculta el sideBar tanto hace Visible y habilita los btns e scroll
            public void mousePressed(MouseEvent evt) {
                btnAdd.setVisible(true);
                sideBar.toggle();
                
                table.setEnabled(!table.isEnabled());
                
                scroll.getVerticalScrollBar().setVisible(!scroll.getVerticalScrollBar().isVisible());
                scroll.setEnabled(!scroll.isEnabled());
            }
        });
    }
    
    public void initProductContent() {
        if(listProducts.isEmpty()) {
            ProductService productService = new ProductService();
            
            try {
                listProducts = productService.getProducts();
                
            } catch(SQLException e) {
                System.err.println(e.getMessage());
                JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        if(model.getRowCount() > 0) {
            model.setRowCount(0);
        }
        
        for(Product product: listProducts)
            model.addRow(new Object[]{product.getId(), product.getTitle(), product.getDetail(), product.getPrice(), product.getAvailability()});
        
        listProducts.clear();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navInventory = new javax.swing.JPanel();
        iconSearch = new javax.swing.JLabel();
        inputSearch = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        scroll = new com.component.complement.ScrollPaneWin11();
        table = new com.component.complement.TableCustom(new Color(41,117,185), new Color(162,175,186), new Color(200,218, 234), new Color(160,160,175));
        layared = new javax.swing.JLayeredPane();
        glassPane = new com.component.complement.GlassPane();

        setBackground(new java.awt.Color(250, 250, 250));

        navInventory.setBackground(new java.awt.Color(240, 240, 240));
        navInventory.setPreferredSize(new java.awt.Dimension(875, 65));

        iconSearch.setBackground(new java.awt.Color(180, 180, 180));
        iconSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        iconSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        iconSearch.setOpaque(true);
        iconSearch.setPreferredSize(new java.awt.Dimension(32, 27));

        inputSearch.setBackground(new java.awt.Color(180, 180, 180));
        inputSearch.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputSearch.setForeground(new java.awt.Color(55, 55, 55));
        inputSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputSearch.setPreferredSize(new java.awt.Dimension(64, 27));

        btnAdd.setBackground(new java.awt.Color(45, 155, 240));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(250, 250, 250));
        btnAdd.setText("Añadir");
        btnAdd.setBorder(null);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAdd.setMaximumSize(new java.awt.Dimension(80, 27));
        btnAdd.setMinimumSize(new java.awt.Dimension(80, 27));
        btnAdd.setPreferredSize(new java.awt.Dimension(80, 27));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navInventoryLayout = new javax.swing.GroupLayout(navInventory);
        navInventory.setLayout(navInventoryLayout);
        navInventoryLayout.setHorizontalGroup(
            navInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navInventoryLayout.createSequentialGroup()
                .addContainerGap(274, Short.MAX_VALUE)
                .addComponent(iconSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        navInventoryLayout.setVerticalGroup(
            navInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navInventoryLayout.createSequentialGroup()
                .addGroup(navInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navInventoryLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navInventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(navInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(iconSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );

        scroll.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Descripcion", "Precio", "Stock", "Accion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(31);
        scroll.setViewportView(table);

        javax.swing.GroupLayout glassPaneLayout = new javax.swing.GroupLayout(glassPane);
        glassPane.setLayout(glassPaneLayout);
        glassPaneLayout.setHorizontalGroup(
            glassPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 875, Short.MAX_VALUE)
        );
        glassPaneLayout.setVerticalGroup(
            glassPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );

        layared.setLayer(glassPane, javax.swing.JLayeredPane.MODAL_LAYER);

        javax.swing.GroupLayout layaredLayout = new javax.swing.GroupLayout(layared);
        layared.setLayout(layaredLayout);
        layaredLayout.setHorizontalGroup(
            layaredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 875, Short.MAX_VALUE)
            .addGroup(layaredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(glassPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layaredLayout.setVerticalGroup(
            layaredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
            .addGroup(layaredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(glassPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scroll)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(layared))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navInventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(layared))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        btnAdd.setVisible(false);
        sideBar.setPanel(addFormProduct);
        sideBar.toggle();
        
        table.setEnabled(!table.isEnabled());
        
        scroll.getVerticalScrollBar().setVisible(!scroll.getVerticalScrollBar().isVisible());
        scroll.setEnabled(!scroll.isEnabled());
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JPanel glassPane;
    private javax.swing.JLabel iconSearch;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JLayeredPane layared;
    private javax.swing.JPanel navInventory;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
