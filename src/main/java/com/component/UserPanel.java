package com.component;

import com.component.complement.ActionButton;
import com.component.complement.SideBar;
import com.event.FormUserEvent;
import com.model.UserInfo;
import com.service.UserInfoService;
import com.service.UserService;
import com.util.ShowJPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristian
 */
public class UserPanel extends javax.swing.JPanel {

    private SideBar sideBar;
    private FormUserEvent userEvent;
    private PanelFormUser addFormUser, searchFormUser, editFormUser;
    private ActionButton btnAdd, btnSearch;
    
    /**
     * Creates new form UserPanel
     */
    public UserPanel() {
        initComponents();
        
        userEvent = new FormUserEvent() {
            @Override
            public void onCreate(String username, String password, String firstName, String lastName, String ci, String phone, String position) throws Exception {
                UserService userService = new UserService();
                UserInfoService userInfoService = new UserInfoService();
                
                try {
                    int userId = userService.createUser(username, password, position);
                    
                    UserInfo userInfo = new UserInfo(0, userId, firstName, lastName, phone, ci);
                    
                    userInfoService.createUserInfo(userInfo);
                    
                } catch(SQLException e) {
                    try {
                        userInfoService.applyRollBack();
                    } catch (SQLException ex) { }
                    
                    if(e.getErrorCode() == 1062) {
                        String ex = (e.getMessage().contains("username")) ? "un nombre de usuario" : 
                                (e.getMessage().contains("ci")) ? "una cedula" : "un nro. de telefono";
                        
                        throw new Exception("No puede insertar " + ex + " ya existente");
                        
                    } else {
                        System.err.println(e.getMessage());
                        JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void onSearch(String username, String firstName, String lastName, String ci, String phone, String position) {
                UserService userService = new UserService();
                
                try {
                    List<String[]> sentencesAndValues = new ArrayList<>();
                    
                    if(!username.isEmpty() && username.isBlank()) sentencesAndValues.add(new String[]{"u.username LIKE ?", "%" + username + "%"});
                    
                    if(!firstName.isEmpty() && !firstName.isBlank()) sentencesAndValues.add(new String[]{"ui.first_name LIKE ?", "%" + firstName + "%"});
                    
                    if(!lastName.isEmpty() && !lastName.isBlank()) sentencesAndValues.add(new String[]{"ui.last_name LIKE ?", "%" + lastName + "%"});
                    
                    if(!ci.isEmpty() && !ci.isBlank()) sentencesAndValues.add(new String[]{"ui.ci = ?", ci});
                    
                    if(!phone.isEmpty() && !phone.isBlank()) sentencesAndValues.add(new String[]{"ui.phone = ?", phone});
                    
                    if(!position.contains("Cualquiera")) sentencesAndValues.add(new String[]{"u.position = ?", position});
                    
                    List<Object[]> listUsers = userService.searchUsers(sentencesAndValues);
                    
                } catch(SQLException e) {
                    System.err.println(e.getMessage());
                    JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            
            @Override
            public void onEdit(String username, String password, String firstName, String lastName, String ci, String phone, String position) throws Exception {
                
            }
        };
        
        initOthersElements();
        
        layared.setLayout(null);
        glassPane.setVisible(false);
        
        sideBar = new SideBar(layared, (com.component.complement.GlassPane) glassPane);
        
        layared.add(sideBar, JLayeredPane.POPUP_LAYER);
        layared.add(btnAdd, JLayeredPane.MODAL_LAYER);
        layared.add(btnSearch, JLayeredPane.MODAL_LAYER);
    }
    
    private void initOthersElements() {
        btnAdd = new ActionButton(10); 
        btnSearch = new ActionButton(10);
        
        addFormUser = new PanelFormUser(userEvent, 1);
        searchFormUser = new PanelFormUser(userEvent, 0);
        editFormUser = new PanelFormUser(userEvent, 2);
        
        btnAdd.setBackground(new Color(41,117,185));
        btnAdd.setBounds(layared.getSize().width - 52, layared.getSize().height - 60, 36, 36);
        btnAdd.setIcon(new ImageIcon(getClass().getResource("/add-user.png")));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnSearch.setBackground(new Color(41,117,185));
        btnSearch.setBounds(layared.getSize().width - 52, layared.getSize().height - 120, 36, 36);
        btnSearch.setIcon(new ImageIcon(getClass().getResource("/search-white.png")));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnAdd.setVisible(false);
                btnSearch.setVisible(false);
                
                sideBar.setPanel(addFormUser);
                sideBar.toggle();
                
                scroll.getVerticalScrollBar().setVisible(!scroll.getVerticalScrollBar().isVisible());
                scroll.setEnabled(!scroll.isEnabled());
            }
        });
        
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnAdd.setVisible(false);
                btnSearch.setVisible(false);
                
                sideBar.setPanel(searchFormUser);
                sideBar.toggle();
                
                scroll.getVerticalScrollBar().setVisible(!scroll.getVerticalScrollBar().isVisible());
                scroll.setEnabled(!scroll.isEnabled());
            }
        });
        
        glassPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                sideBar.toggle();
                
                btnAdd.setVisible(true);
                btnSearch.setVisible(true);
                
                scroll.getVerticalScrollBar().setVisible(!scroll.getVerticalScrollBar().isVisible());
                scroll.setEnabled(!scroll.isEnabled());
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layared = new javax.swing.JLayeredPane();
        glassPane = new com.component.complement.GlassPane();
        scroll = new com.component.complement.ScrollPaneWin11();
        content = new javax.swing.JPanel();

        setBackground(new java.awt.Color(240, 240, 240));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout glassPaneLayout = new javax.swing.GroupLayout(glassPane);
        glassPane.setLayout(glassPaneLayout);
        glassPaneLayout.setHorizontalGroup(
            glassPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
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
            .addComponent(glassPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layaredLayout.setVerticalGroup(
            layaredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(glassPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        scroll.setBorder(null);
        scroll.setPreferredSize(new java.awt.Dimension(600, 630));

        content.setBackground(new java.awt.Color(240, 240, 240));
        content.setPreferredSize(new java.awt.Dimension(600, 620));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );

        scroll.setViewportView(content);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layared)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layared)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        btnAdd.setLocation(layared.getSize().width - 52, layared.getSize().height - 60);
        btnSearch.setLocation(layared.getSize().width - 52, layared.getSize().height - 120);
    }//GEN-LAST:event_formComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JPanel glassPane;
    private javax.swing.JLayeredPane layared;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
