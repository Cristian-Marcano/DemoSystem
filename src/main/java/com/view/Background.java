package com.view;

import com.component.Header;
import com.component.HomePanel;
import com.component.InventoryPanel;
import com.component.MenuBar;
import com.component.SalePanel;
import com.component.TrackRecordPanel;
import com.component.UserPanel;
import com.demo.Demo;
import com.event.ComponentLoader;
import com.model.Sale;
import com.util.ShowJPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 * Clase que contiene los componente visuales de la app
 * despues de haberse logeado
 */
public class Background extends javax.swing.JPanel {
    
    /**
     * Objetos que ayudan a la integracion y la carga de componentes visuales a esta interfaz visual 
     * 
     * panelHeader: componente cabecera que contiene el logo, nombre y rol del usuario
     * componentLoader: variable que contiene funciones que se ejecutan cuando ocurren ciertos eventos
     * board: objeto que auxilia la integracion de paneles al dashboard
     * headerP: objeto que coopera  en la integracion de paneles al header
     * contentP: objeto que ayuda la integracion de paneles al content
     */
    
    public Header panelHeader;
    public UserPanel userPanel;
    public SalePanel salePanel;
    public HomePanel homePanel;
    public InventoryPanel inventoryPanel;
    public TrackRecordPanel trackRecordPanel;
    public ComponentLoader componentLoader;
    public ShowJPanel board, headerP, contentP;

    /**
     * Creates new form Background
     */
    public Background() {
        initComponents();
        
        componentLoader = new ComponentLoader() {
            @Override //* Integra|Carga el listado de componentes (items) al contenido del scroll
            public void scrollableContentLoader(List<JPanel> listPanels) throws Exception {
                contentP.showItemsPanel(listPanels);
            }
            
            @Override //* Reescala el tamaño del contenido del scroll calculando la cantidad de items que tiene
            public void resizeContentLoader() throws Exception {
                contentP.resizeScrollPane();
            }
            
            @Override //* Recarga el contenido del componente
            public void refreshContent() {
                contentP.refreshContainer();
            }
            
            @Override
            public void setContentPane(String namePane) {
                if(namePane.contains("UserPanel")) {
                    userPanel = new UserPanel(componentLoader);
                    contentP.setPanel(userPanel);
                    userPanel.initUserContent();
                } else if(namePane.contains("SalePanel")) {
                    salePanel = new SalePanel(componentLoader);
                    contentP.setPanel(salePanel);
                } else if(namePane.contains("InventoryPanel")) { 
                    inventoryPanel = new InventoryPanel();
                    inventoryPanel.initProductContent();
                    contentP.setPanel(inventoryPanel);
                } else if(namePane.contains("HomePanel")) {
                    homePanel = new HomePanel();
                    contentP.setPanel(homePanel);
                }
                contentP.showPanel();
            }
            
            @Override
            public void goToInvoice(Object[] saleInvoice, List<Sale> sales) {
                initInvoice(saleInvoice, sales);
            }
            
            @Override
            public void goToLogin() {
                initLogin();
            }
        };
        
        panelHeader = new Header();
        panelHeader.setUserInfo(Demo.user);
        
        homePanel = new HomePanel();
        
        board = initPanel(new MenuBar(componentLoader), dashboard, new Dimension(dashboard.getPreferredSize()));
        headerP = initPanel(panelHeader, header, new Dimension(header.getPreferredSize()));
        contentP = initPanel(homePanel, content, new Dimension(content.getPreferredSize()));
        
        //userPanel.initUserContent(); // Inicia la carga de listado de usuarios
        //inventoryPanel.initProductContent(); // Inicia la carga de listado de productos
    }
    
    //* Instanciacion del objeto ShowJPanel, pasando por parametros el panel que sera mostrado,
    //* el panel contenedor que se encuentra en esta clase,
    //* y el tamaña que ocupara el panel
    private ShowJPanel initPanel(JPanel panel, JPanel container, Dimension size) {
        ShowJPanel show = new ShowJPanel(panel, container, size);
        show.showPanel();
        return show;
    }
    
    private Demo getDemoWindow() {
        Component comp = this.getParent();
        
        while((!(comp instanceof JFrame)) && comp != null)
            comp = comp.getParent();

        if(comp instanceof Demo) {
            Demo demoWindow = (Demo) comp;
            return demoWindow;
        }

        return null;
    }
    
    public void initInvoice(Object[] saleInvoice, List<Sale> sales) {
        Demo demoWindow = getDemoWindow();
        
        if(demoWindow != null) 
            demoWindow.goToInvoiceView(saleInvoice, sales);
    }
    
    public void initLogin() {
        Demo demoWindow = getDemoWindow();
        
        if(demoWindow != null)
            demoWindow.goToLoginView();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboard = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        content = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(1032, 680));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        dashboard.setBackground(new java.awt.Color(41, 117, 185));
        dashboard.setMinimumSize(new java.awt.Dimension(350, 100));
        dashboard.setPreferredSize(new java.awt.Dimension(405, 720));

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        header.setBackground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        content.setBackground(new java.awt.Color(240, 240, 240));
        content.setMinimumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 875, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    //* Reescalamiento del dashboard, para cuando aumente de tamaño la ventana
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int width = this.getWidth() / 4;
        
        if(width >= dashboard.getMinimumSize().width ) 
            dashboard.setPreferredSize(new Dimension(width, this.getHeight()));
        else 
            dashboard.setPreferredSize(new Dimension(dashboard.getMinimumSize().width, this.getHeight()));
        
        revalidate();
        repaint();
    }//GEN-LAST:event_formComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel header;
    // End of variables declaration//GEN-END:variables
}
