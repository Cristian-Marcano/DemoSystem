package com.component;

import com.demo.Demo;
import com.event.ItemEvent;
import com.model.Product;
import com.ux.TableStockCellEditor;
import com.ui.TableActionCellEditor;
import com.ui.TableActionCellRender;
import com.event.TableActionEvent;
import com.event.TableStockEvent;
import com.model.Client;
import com.model.Sale;
import com.model.User;
import com.model.UserInfo;
import com.service.ClientService;
import com.service.ProductService;
import com.service.SaleInvoiceService;
import com.service.SaleService;
import com.util.ValidateInput;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Cristian
 * Clase componente que se encarga de la busqueda de productos,
 * registro y busqueda de clientes, para asi registrar la venta del cliente
 */
public class SalePanel extends javax.swing.JPanel {
    
    /**
     * Atributos que auxilian la funcionalidad y la logica de la clase
     * 
     * client: objeto que contiene los datos del cliente que va realizar la compra
     * searchMenu: componente swing que permite mostrar y ocultar otros componentes con facilidad
     * panelSearch: componente que contendra las busquedas encontradas gracias al inputSearch y encontrara dentro del searchMenu
     * listProductOnTable: listado de productos que se encuentran en la tabla, se usa de intermediario 
     *                      solo para eliminar el registro de productos seleccionados
     * selectedProducts: almacena los productos seleccionados, se usa en caso de que se siga usando el buscador y muestre
     *                      que ciertos productos ya estan seleccionados
     * listProducts: listado de productos que arroja la base de datos al usar el inputSearch, mas con un booleano (true si ya esta seleccionado)
     *                  (false si no lo esta)
     * tax: monto de impuestos de la venta
     * totalDecimal: monto total de la venta
     * productSelected: producto seleccionado y mostrado con sus datos completos despues de haber sido clickeado en el panelSearch
     */
    
    private Client client = null;
    private JPopupMenu searchMenu;
    private PanelSearch panelSearch;
    private List<Product> listProductOnTable = new ArrayList<>();
    private Set<Product> selectedProducts = new HashSet<>();
    private List<Object[]> listProducts = new ArrayList<>();
    private BigDecimal tax, totalDecimal, subTotalDecimal;
    
    public Product productSelected;
    
    
    /**
     * Creates new form SalePanel
     */
    public SalePanel() {
        initComponents();
        
        //* Cambia de lugar la flecha del JComboBox a la izquierda
        selectClientRif.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        setVisibleFormClient(false);
        
        btnAddClient.setVisible(false);
        
        searchMenu = new JPopupMenu();
        searchMenu.setFocusable(false); // No es focusable para cuando cambia de valores no desenfoque el inputSearch
        panelSearch = new PanelSearch();
        searchMenu.add(panelSearch);
        
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) { }

            @Override //* Obtiene la fila que se presiono el btn (btnActionRemove) y la elimina de la tabla 
            public void onRemove(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(row);
                
                Product productRemove = listProductOnTable.get(row); //* Obtiene el producto por la fila de la tabla
                listProductOnTable.remove(row); //* Elimina el producto del listado
                
                selectedProducts.remove(productRemove); //* Elimina el producto del set de productos seleccionados
                
                validateSelectedProducts();
                
                calculateTableAmount();
            }
        };
        
        TableStockEvent stockEvent = new TableStockEvent() {
            
            @Override //* Obtiene el id del producto y la cantidad del que se desea vender, para calcular el importe
            public BigDecimal stopEditing(int id, int value) throws Exception {
                for(Product product: listProductOnTable) {
                    // Se busca el producto y valida que la cantidad deseada se encuentre en lo disponible
                    if(product.getId() == id && product.getAvailability() >= value) { 
                        return product.getPrice();
                    } else if(product.getId() == id) { // Si la cantidad deseada supera a la cantidad disponible
                        throw new Exception("Stock not availability");
                    }
                }
                
                throw new Exception("Not found it Product id");
            }
            
            @Override //* Cada vez que se termine de editar la cantidad, se calcula el importe de toda la tabla
            public void onEdited() {
                calculateTableAmount();
            }
        };
        
        ItemEvent itemEvent = new ItemEvent() {
            
            @Override //* Se muestra la informacion del producto clickeado del panelSearch
            public void onClick(Product product) {
                panelProductSelected.setVisible(true);
                
                productSelected = product;
                
                productName.setText("<html><body style='max-width: 120px;'>" + product.getTitle() + "</body></html>");
                productPrice.setText("<html><body style='max-width: 120px;'>Precio: " + product.getPrice() + "$</body></html>");
                
                if(product.getAvailability() == 0) { // Si no hay disponibles el fondo se coloca rojo
                    panelProductSelected.setBackground(new Color(219, 55, 55));
                    productStock.setText("<html><body style='max-width: 120px;'>No hay stock disponible</body></html>");
                    if(btnAdd.isVisible()) btnAdd.setVisible(false);
                } else { // Si hay disponibles el fondo se coloca azul
                    panelProductSelected.setBackground(new Color(41, 117, 185));
                    productStock.setText("<html><body style='max-width: 120px;'>" + product.getAvailability() + " disponibles</body></html>");
                    btnAdd.setVisible(true);
                }
                
                searchMenu.setVisible(false);
            }
            
            @Override
            public void onEdit(User user, UserInfo userInfo) { }
            
            @Override
            public void onRemove(int id) { }
        };
        
        //* Se cambia el editor de casilla de la columna 2 para calcular mejor los importes de la tabla
        table.getColumnModel().getColumn(2).setCellEditor(new TableStockCellEditor(3, stockEvent));
        
        //* Se cambia el renderizado y el editor de casilla de la columna 4 para eliminar las filas de la tabla
        table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender(true, new Color(162,175,186), new Color(200, 218, 234)));
        table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event, true, new Color(162,175,186), new Color(200, 218, 234)));
        ((com.component.complement.TableCustom) table).fixTable(scrollPane); // Rellena la parte de arriba a la derecha de la tabla
        
        panelSearch.addItemEvent(itemEvent);
        
        panelProductSelected.setVisible(false);
        btnAdd.setVisible(false);
        
    }
    
    //* Busca los productos semejantes colocados en el inputSearch de la DB
    public List<Product> search(List<String[]> sentencesAndValues) {
        try {
            ProductService productService = new ProductService();
            
            return productService.searchProduct(sentencesAndValues);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        
        return new ArrayList<>();
    }
    
    //* Valida y cambia el icono del listado productos mostrados en el panelSearch
    public void validateSelectedProducts() {
        
        for(Object[] product: listProducts) {
            if(selectedProducts.contains((Product)product[0])) {
                product[1] = true;
            } else product[1] = false;
        }
        
        panelSearch.setListProductSearch(listProducts);
        
        searchMenu.setPreferredSize(panelSearch.getPreferredSize());
        
        searchMenu.repaint();
        searchMenu.revalidate();
    }
    
    //* Cambia la visibilidad de los inputs para registrar el cliente o mostrar sus datos
    private void setVisibleFormClient(boolean visible) {
        labelClientFullName.setVisible(visible);
        labelClientPhone.setVisible(visible);
        labelClientEmail.setVisible(visible);
        
        separatorClienteFullName.setVisible(visible);
        separatorClientPhone.setVisible(visible);
        separatorClientEmail.setVisible(visible);
        
        inputClientFullName.setVisible(visible);
        inputClientPhone.setVisible(visible);
        inputClientEmail.setVisible(visible);
        
        panelTabClient.revalidate();
        panelTabClient.repaint();
    }
    
    //* habilita y deshabilita los inputs en el caso de que solo mostrar informacion
    private void setEnabledFormClient(boolean enable) {
        inputClientFullName.setEnabled(enable);
        inputClientPhone.setEnabled(enable);
        inputClientEmail.setEnabled(enable);
    }
    
    //* Calcula todo el importe total sin impuestos
    public void calculateTableAmount() {
        BigDecimal decimal = new BigDecimal(0);
        
        for(int i=0; i<table.getRowCount(); i++) {
            decimal = decimal.add(new BigDecimal(table.getModel().getValueAt(i, 3).toString()));
        }
            
        setSaleLabel(decimal, 16);
    }
    
    //* Cambiar los montos subTotal, iva y total de la interfaz visual (tambien calcula el tax, totalDecimal)
    public void setSaleLabel(BigDecimal products, int porcent) {
        BigDecimal porcentDecimal = new BigDecimal(porcent);
        tax = products.multiply(porcentDecimal.divide(new BigDecimal(100)));
        
        totalDecimal = tax.add(products).setScale(2, RoundingMode.HALF_UP);
        
        subTotal.setText("<html><body>SubTotal:"+ products.toPlainString() +"$</body></html>");
        iva.setText("<html><body>IVA ("+ porcent +"%):" + tax.toPlainString() + "$</body></html>");
        total.setText("<html><body>Total:" + totalDecimal.toPlainString() + "$</body></html>");
        
        subTotalDecimal = products;
        
        if(products.intValue() > 0) btnSale.setVisible(true);
        else btnSale.setVisible(false);
    }
    
    //* Verifica si esta abierta una transaccion para cerrarla y hacer rollBack (porque los clientes no son commiteados hasta que hayan tenido una compra)
    public void validateClientRif() {
        ClientService clientService = new ClientService();
            
        try {
                
            if(clientService.isConnected()) {
                clientService.applyRollBack();
                clientService.closeConnection();
            }
                
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new com.component.complement.ScrollPaneWin11();
        table = new com.component.complement.TableCustom(new Color(41,117,185), new Color(162,175,186), new Color(200,218, 234), new Color(160,160,175));
        tabbedPane = new com.component.complement.MaterialTabbed();
        panelTabSearch = new javax.swing.JPanel();
        labelSearch = new javax.swing.JLabel();
        separatorSearch = new javax.swing.JSeparator();
        inputSearch = new javax.swing.JTextField();
        panelProductSelected = new com.component.complement.PanelBorderRounded(25);
        productName = new javax.swing.JLabel();
        productStock = new javax.swing.JLabel();
        productPrice = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        panelTabClient = new javax.swing.JPanel();
        TitleClient = new javax.swing.JLabel();
        labelClientRif = new javax.swing.JLabel();
        separatorClientRif = new javax.swing.JSeparator();
        selectClientRif = new com.component.complement.ComboBox<>();
        inputClientRif = new javax.swing.JTextField();
        labelClientFullName = new javax.swing.JLabel();
        separatorClienteFullName = new javax.swing.JSeparator();
        inputClientFullName = new javax.swing.JTextField();
        labelClientPhone = new javax.swing.JLabel();
        separatorClientPhone = new javax.swing.JSeparator();
        inputClientPhone = new javax.swing.JTextField();
        labelClientEmail = new javax.swing.JLabel();
        separatorClientEmail = new javax.swing.JSeparator();
        inputClientEmail = new javax.swing.JTextField();
        btnAddClient = new javax.swing.JButton();
        panelTabSale = new javax.swing.JPanel();
        subTotal = new javax.swing.JLabel();
        iva = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        btnSale = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));

        scrollPane.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Cantidad", "Importe", "Acción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(31);
        table.setShowGrid(true);
        scrollPane.setViewportView(table);

        tabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        panelTabSearch.setBackground(new java.awt.Color(210, 210, 210));

        labelSearch.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelSearch.setForeground(new java.awt.Color(60, 60, 60));
        labelSearch.setLabelFor(inputSearch);
        labelSearch.setText("ID o Nombre:");

        separatorSearch.setForeground(new java.awt.Color(41, 117, 185));

        inputSearch.setBackground(new java.awt.Color(180, 180, 180));
        inputSearch.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputSearch.setForeground(new java.awt.Color(55, 55, 55));
        inputSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputSearch.setName("Buscador"); // NOI18N
        inputSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inputSearchMouseClicked(evt);
            }
        });
        inputSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputSearchKeyReleased(evt);
            }
        });

        panelProductSelected.setBackground(new java.awt.Color(41, 117, 185));

        productName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        productName.setForeground(new java.awt.Color(251, 251, 251));
        productName.setText("{ProductName}");

        productStock.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        productStock.setForeground(new java.awt.Color(251, 251, 251));
        productStock.setText("{nStock} disponible");

        productPrice.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        productPrice.setForeground(new java.awt.Color(251, 251, 251));
        productPrice.setText("{Price}");

        javax.swing.GroupLayout panelProductSelectedLayout = new javax.swing.GroupLayout(panelProductSelected);
        panelProductSelected.setLayout(panelProductSelectedLayout);
        panelProductSelectedLayout.setHorizontalGroup(
            panelProductSelectedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductSelectedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductSelectedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productPrice)
                    .addComponent(productName)
                    .addComponent(productStock))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProductSelectedLayout.setVerticalGroup(
            panelProductSelectedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductSelectedLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(productName)
                .addGap(18, 18, 18)
                .addComponent(productStock)
                .addGap(18, 18, 18)
                .addComponent(productPrice)
                .addContainerGap())
        );

        btnAdd.setBackground(new java.awt.Color(45, 155, 240));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(250, 250, 250));
        btnAdd.setText("Añadir");
        btnAdd.setBorder(null);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTabSearchLayout = new javax.swing.GroupLayout(panelTabSearch);
        panelTabSearch.setLayout(panelTabSearchLayout);
        panelTabSearchLayout.setHorizontalGroup(
            panelTabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTabSearchLayout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTabSearchLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelTabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelProductSelected, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(separatorSearch, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTabSearchLayout.createSequentialGroup()
                        .addComponent(labelSearch)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(inputSearch, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(16, 16, 16))
        );
        panelTabSearchLayout.setVerticalGroup(
            panelTabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabSearchLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(labelSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                .addComponent(panelProductSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        tabbedPane.addTab("Busqueda", new javax.swing.ImageIcon(getClass().getResource("/search.png")), panelTabSearch); // NOI18N

        panelTabClient.setBackground(new java.awt.Color(210, 210, 210));

        TitleClient.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        TitleClient.setForeground(new java.awt.Color(60, 60, 60));
        TitleClient.setText("Formulario");

        labelClientRif.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelClientRif.setForeground(new java.awt.Color(60, 60, 60));
        labelClientRif.setLabelFor(inputClientRif);
        labelClientRif.setText("Documento:");

        separatorClientRif.setForeground(new java.awt.Color(41, 117, 185));

        selectClientRif.setBackground(new java.awt.Color(180, 180, 180));
        selectClientRif.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        selectClientRif.setForeground(new java.awt.Color(55, 55, 55));
        selectClientRif.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "V-", "E-", "J-" }));
        selectClientRif.setBorder(null);
        selectClientRif.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectClientRifItemStateChanged(evt);
            }
        });

        inputClientRif.setBackground(new java.awt.Color(180, 180, 180));
        inputClientRif.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputClientRif.setForeground(new java.awt.Color(55, 55, 55));
        inputClientRif.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputClientRif.setName("Cedula"); // NOI18N
        inputClientRif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputClientRifActionPerformed(evt);
            }
        });
        inputClientRif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inputClientRifKeyTyped(evt);
            }
        });

        labelClientFullName.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelClientFullName.setForeground(new java.awt.Color(60, 60, 60));
        labelClientFullName.setLabelFor(inputClientFullName);
        labelClientFullName.setText("Nombre Completo:");

        separatorClienteFullName.setForeground(new java.awt.Color(41, 117, 185));

        inputClientFullName.setBackground(new java.awt.Color(180, 180, 180));
        inputClientFullName.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputClientFullName.setForeground(new java.awt.Color(55, 55, 55));
        inputClientFullName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputClientFullName.setName("Nombre Completo"); // NOI18N

        labelClientPhone.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelClientPhone.setForeground(new java.awt.Color(60, 60, 60));
        labelClientPhone.setLabelFor(inputClientPhone);
        labelClientPhone.setText("Telefono:");

        separatorClientPhone.setForeground(new java.awt.Color(41, 117, 185));

        inputClientPhone.setBackground(new java.awt.Color(180, 180, 180));
        inputClientPhone.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputClientPhone.setForeground(new java.awt.Color(55, 55, 55));
        inputClientPhone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputClientPhone.setName("Telefono"); // NOI18N

        labelClientEmail.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelClientEmail.setForeground(new java.awt.Color(60, 60, 60));
        labelClientEmail.setLabelFor(inputClientEmail);
        labelClientEmail.setText("Correo:");

        separatorClientEmail.setForeground(new java.awt.Color(41, 117, 185));

        inputClientEmail.setBackground(new java.awt.Color(180, 180, 180));
        inputClientEmail.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputClientEmail.setForeground(new java.awt.Color(55, 55, 55));
        inputClientEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputClientEmail.setName("Correo"); // NOI18N

        btnAddClient.setBackground(new java.awt.Color(45, 155, 240));
        btnAddClient.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnAddClient.setForeground(new java.awt.Color(250, 250, 250));
        btnAddClient.setText("Añadir");
        btnAddClient.setBorder(null);
        btnAddClient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTabClientLayout = new javax.swing.GroupLayout(panelTabClient);
        panelTabClient.setLayout(panelTabClientLayout);
        panelTabClientLayout.setHorizontalGroup(
            panelTabClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTabClientLayout.createSequentialGroup()
                .addGroup(panelTabClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTabClientLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(TitleClient)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTabClientLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(panelTabClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(separatorClientEmail, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(separatorClientPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(separatorClienteFullName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(separatorClientRif)
                            .addComponent(inputClientFullName)
                            .addComponent(inputClientPhone)
                            .addGroup(panelTabClientLayout.createSequentialGroup()
                                .addGroup(panelTabClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelClientEmail)
                                    .addComponent(labelClientPhone)
                                    .addComponent(labelClientFullName)
                                    .addComponent(labelClientRif))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(inputClientEmail)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTabClientLayout.createSequentialGroup()
                                .addComponent(selectClientRif, 0, 80, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(inputClientRif, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(16, 16, 16))
            .addGroup(panelTabClientLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTabClientLayout.setVerticalGroup(
            panelTabClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabClientLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(TitleClient)
                .addGap(18, 18, 18)
                .addComponent(labelClientRif)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorClientRif, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTabClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputClientRif, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(selectClientRif))
                .addGap(18, 18, 18)
                .addComponent(labelClientFullName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorClienteFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputClientFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelClientPhone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorClientPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputClientPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelClientEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorClientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputClientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnAddClient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Cliente", new javax.swing.ImageIcon(getClass().getResource("/client.png")), panelTabClient); // NOI18N

        panelTabSale.setBackground(new java.awt.Color(210, 210, 210));

        subTotal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        subTotal.setForeground(new java.awt.Color(41, 117, 185));
        subTotal.setText("Sub Total: {PriceSubTotal}");

        iva.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        iva.setForeground(new java.awt.Color(41, 117, 185));
        iva.setText("IVA (16.00%): {PriceIVA}");

        total.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        total.setForeground(new java.awt.Color(41, 117, 185));
        total.setText("Total: {PriceTotal}");

        btnSale.setBackground(new java.awt.Color(45, 155, 240));
        btnSale.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSale.setForeground(new java.awt.Color(250, 250, 250));
        btnSale.setText("Registrar Venta");
        btnSale.setBorder(null);
        btnSale.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTabSaleLayout = new javax.swing.GroupLayout(panelTabSale);
        panelTabSale.setLayout(panelTabSaleLayout);
        panelTabSaleLayout.setHorizontalGroup(
            panelTabSaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabSaleLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panelTabSaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iva)
                    .addComponent(subTotal)
                    .addComponent(total))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTabSaleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSale, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTabSaleLayout.setVerticalGroup(
            panelTabSaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabSaleLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(subTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(iva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 267, Short.MAX_VALUE)
                .addComponent(btnSale, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );

        tabbedPane.addTab("Venta", new javax.swing.ImageIcon(getClass().getResource("/purchase.png")), panelTabSale); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
            .addComponent(scrollPane)
        );
    }// </editor-fold>//GEN-END:initComponents

    //* Cuando el inputSearch es clickeado se muestra el panelSearch
    private void inputSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputSearchMouseClicked
        if(panelSearch.getItemSize() > 0)
            searchMenu.show(inputSearch, 0, inputSearch.getHeight());
    }//GEN-LAST:event_inputSearchMouseClicked

    //* Extrae el texto del inputSearch para luego indentificar que tipo de sentencia realizar y aplicar la busqueda
    private void inputSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputSearchKeyReleased
        if(evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_ENTER) {
            String search = inputSearch.getText().trim();
            
            List<String[]> sentencesAndValues = new ArrayList<>();
            
            if(search.matches("\\d+")) {
                sentencesAndValues.add(new String[]{"id = ?", search});
            } else sentencesAndValues.add(new String[]{"title LIKE ?", "%" + search + "%"});
            
            List<Product> products = search(sentencesAndValues);
            
            listProducts.clear(); // Una vez realizada la busqueda el listado items que se encontraban en el panelSearch se remueven
            
            for(Product product: products) {
                listProducts.add(new Object[]{product, selectedProducts.contains(product)});
            }
            
            panelSearch.setListProductSearch(listProducts);
            
            searchMenu.setVisible(false);
            
            if(panelSearch.getItemSize() > 0) 
                searchMenu.show(inputSearch, 0, inputSearch.getHeight());
        }
    }//GEN-LAST:event_inputSearchKeyReleased

    //* Se presiona el btnAdd se añade el producto seleccionado del panelSearch a la tabla
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{productSelected.getId(), productSelected.getTitle(), 1, productSelected.getPrice()});
        
        listProductOnTable.add(productSelected);
        
        selectedProducts.add(productSelected);
        
        validateSelectedProducts();
        
        panelProductSelected.setVisible(false);
        btnAdd.setVisible(false);
    }//GEN-LAST:event_btnAddActionPerformed

    //* Se valida que el inputClientRif solo permita numeros y se remueve el cliente que se encontraba en client tras solo editar su rif
    private void inputClientRifKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputClientRifKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c) || (c == '0' && inputClientRif.getText().isEmpty()))
            evt.consume(); //Bloquea caracteres no numericos y ceros iniciales
        
        if(client != null && evt.getKeyChar() != KeyEvent.VK_ENTER) {
            client = null;
            
            setVisibleFormClient(false);
            btnAddClient.setVisible(false);
            
            validateClientRif();
        }
    }//GEN-LAST:event_inputClientRifKeyTyped

    //* Realiza la busqueda del cliente con el rif insertado, si no lo encuentra se habilitan los inputs para crearlo
    private void inputClientRifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputClientRifActionPerformed
        try {
            String rif = ((String) selectClientRif.getSelectedItem()) + inputClientRif.getText();
            
            ClientService clientService = new ClientService();
            
            client = clientService.getClient(rif);
            
            if(client != null) {
                inputClientFullName.setText(client.getFullName());
                inputClientPhone.setText(client.getPhone());
                inputClientEmail.setText(client.getEmail());
                
                setEnabledFormClient(false);
            } else {
                btnAddClient.setVisible(true);
                setEnabledFormClient(true);
                
                inputClientFullName.setText("");
                inputClientPhone.setText("");
                inputClientEmail.setText("");
            }
            
            setVisibleFormClient(true);
            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_inputClientRifActionPerformed

    //* Valida y extrae los datos de los inputs para crear un cliente mediante una transaccion
    private void btnAddClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClientActionPerformed
        try {
            ValidateInput.isEmptyOrBlank(List.of(inputClientFullName, inputClientPhone, inputClientEmail));
            
            ValidateInput.isMinimumLength(inputClientPhone, 10);
            
            String rif = ((String) selectClientRif.getSelectedItem()) + inputClientRif.getText(), fullName = inputClientFullName.getText(),
                    phone = inputClientPhone.getText(), email = inputClientEmail.getText();
            
            ClientService clientService = new ClientService();
            
            int id = clientService.createClient(fullName, rif, phone, email);
            
            setEnabledFormClient(false);
            
            client = new Client(id, fullName, rif, email, phone);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"No se puede avanzar debido a que: \n" + e.getMessage(),"Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAddClientActionPerformed

    //* Si se llega a cambiar y ya se tenia guardado en el atributo client se vacia
    private void selectClientRifItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectClientRifItemStateChanged
        if(client != null) {
            client = null;
            
            setVisibleFormClient(false);
            btnAddClient.setVisible(false);
            
            validateClientRif();
        }
    }//GEN-LAST:event_selectClientRifItemStateChanged

    //* Calcular el importe total tras ir a la pestaña de venta
    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        int selectedIndex = tabbedPane.getSelectedIndex();
        
        if(selectedIndex == 2) 
            calculateTableAmount();
        
    }//GEN-LAST:event_tabbedPaneStateChanged

    //* Registrar la venta obteniendo los productos desde la tabla y la cantidad que se desea y descontando el stock de los productos
    private void btnSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleActionPerformed
        if(client != null) {
            SaleInvoiceService saleInvoiceService = new SaleInvoiceService();
            
            try {
                int saleInvoiceId = saleInvoiceService.createSaleInvoice(totalDecimal, subTotalDecimal, tax, Demo.user.getId(), client.getId());
                
                List<Sale> listTable = new ArrayList<>();
                
                for(int i=0; i<table.getRowCount(); i++)
                    listTable.add(new Sale(0, saleInvoiceId,
                                            Integer.parseInt(table.getModel().getValueAt(i, 0).toString()),
                                            Integer.parseInt(table.getModel().getValueAt(i, 2).toString()),
                                            new BigDecimal(table.getModel().getValueAt(i, 3).toString())));
                
                ProductService productService = new ProductService();
                productService.decreaseStockProducts(listTable);
                
                SaleService saleService = new SaleService();
                saleService.createSales(listTable);
            } catch(SQLException e) {
                
                try {
                    saleInvoiceService.applyRollBack();
                } catch(SQLException sqlE) { System.err.println("Failed RollBack ???"); }
                
                System.err.println(e.getMessage());
                JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
            }
            
        } else tabbedPane.setSelectedIndex(1); // Si no se a confirmado cliente al usuario lo envia a la pestaña para que lo busque o registre
    }//GEN-LAST:event_btnSaleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TitleClient;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddClient;
    private javax.swing.JButton btnSale;
    private javax.swing.JTextField inputClientEmail;
    private javax.swing.JTextField inputClientFullName;
    private javax.swing.JTextField inputClientPhone;
    private javax.swing.JTextField inputClientRif;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JLabel iva;
    private javax.swing.JLabel labelClientEmail;
    private javax.swing.JLabel labelClientFullName;
    private javax.swing.JLabel labelClientPhone;
    private javax.swing.JLabel labelClientRif;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JPanel panelProductSelected;
    private javax.swing.JPanel panelTabClient;
    private javax.swing.JPanel panelTabSale;
    private javax.swing.JPanel panelTabSearch;
    private javax.swing.JLabel productName;
    private javax.swing.JLabel productPrice;
    private javax.swing.JLabel productStock;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JComboBox<String> selectClientRif;
    private javax.swing.JSeparator separatorClientEmail;
    private javax.swing.JSeparator separatorClientPhone;
    private javax.swing.JSeparator separatorClientRif;
    private javax.swing.JSeparator separatorClienteFullName;
    private javax.swing.JSeparator separatorSearch;
    private javax.swing.JLabel subTotal;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable table;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
