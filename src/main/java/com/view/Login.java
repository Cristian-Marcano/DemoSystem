package com.view;

import com.component.PanelFormForgot;
import com.component.PanelFormLogin;
import com.component.PanelFormSignUp;
import com.demo.Demo;
import com.event.FormAuthEvent;
import com.model.User;
import com.model.UserInfo;
import com.service.UserInfoService;
import com.service.UserService;
import com.util.RSA;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 * Clase del panel que se encarga de autentificar si el usuario
 * esta registrado en la base de datos o de registrar el admin del aplicación por primera vez
 */
public class Login extends javax.swing.JPanel {
    
    /**
     * Objetos que ayudan la integracion de componentes, eventos a esta interfaz visual
     * 
     * panelFormLogin: formulario que ayuda en el inicio de sesión de usuario
     * panelFormSignUp: formularion que auxilia en la registracion del primer usuario de la aplicacion (admin)
     * panelFormForgot: formularion que coopera en la validacion de datos del usuario en caso de que se le olvido la contraseña
     * authEvent: funciones de eventos que se ejecutan cuando pasa cierta condición, en este caso el inicio de sesion, el registro y en el forgot
     */
    
    public PanelFormLogin panelFormLogin;
    public PanelFormSignUp panelFormSignUp;
    public PanelFormForgot panelFormForgot;
    private FormAuthEvent authEvent;
    
    /**
     * Creates new form LoginPanel
     */
    public Login() {
        initComponents();
        
        authEvent = new FormAuthEvent() {
            @Override //* Funcion que se encarga de validar si el usuario se encuentra en la base de datos y confirmando su contraseña son identicas
            public void onLogin(String username, String password) throws Exception {
                try {
                    UserService userService = new UserService();
                    User user = userService.getUser(username);
                    
                    if(user == null) throw new Exception("Datos invalidos");
                    
                    if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        Demo.user = user;
                        
                        initBackground();
                        
                    } else throw new Exception("Datos invalidos");
                    
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override //* Funcion que se encarga de registrar un nuevo usuario a la app en este caso el admin
            public void onSignUp(String username, String password, String firstName, String lastName, String ci, String phone) {
                
                UserService userService = new UserService();
                UserInfoService userInfoService = new UserInfoService();
                
                try {
                    int userId = userService.createUser(username, password, "admin");
                    
                    UserInfo userInfo = new UserInfo(0, userId, firstName, lastName, phone, ci);
                    
                    userInfoService.createUserInfo(userInfo);
                    
                    initFormLogin();
                } catch (SQLException e) {
                    try {
                        userInfoService.applyRollBack();
                    } catch (SQLException ex) { }
                    System.err.println(e.getMessage());
                    JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override //* Funcion que se encarga de validar los datos del usuario sin la contraseña
            public void onForgot(String username, String firstName, String lastName, String ci) throws Exception {
                try {
                    UserService userService = new UserService();
                    User user = userService.getUser(username);
                    
                    if(user == null) throw new Exception("Datos invalidos");
                    
                    UserInfoService userInfoService = new UserInfoService();
                    UserInfo userInfo = userInfoService.getUserInfo(user.getId());
                    
                    if(user.getUsername().equals(username) && userInfo.getFirstName().equals(firstName) 
                            && userInfo.getLastName().equals(lastName) && userInfo.getCi().equals(ci)) {
                        
                        Demo.user = user;
                        
                        initBackground();
                        
                    } else throw new Exception("Datos invalidos");
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    JOptionPane.showMessageDialog(null,"Ocurrio un Error en la conexion con la Base de Datos","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }
    
    //* Funcion que se dirije a la ventana de la app para usar la funcionalidad de cambiar a la interfaz visual de background
    public void initBackground() {
        Component comp = this.getParent();
        
        while((!(comp instanceof JFrame)) && comp != null)
            comp = comp.getParent();
        
        if(comp instanceof Demo) {
            Demo demoWindow = (Demo) comp;
            demoWindow.goToBackgroundView();
        }
    }
    
    //* Instanciar formulario Registro para mostrarlo en la pantalla
    public void initFormSignUp() {
        panelFormSignUp = new PanelFormSignUp(authEvent);
        setPanelForm(panelFormSignUp);
    }
    
    //* Intanciar formulario inicio de sesion para mostrarlo en la pantalla
    public void initFormLogin() {
        panelFormLogin = new PanelFormLogin(authEvent);
        setPanelForm(panelFormLogin);
        panelFormLogin.addEventForgotPassword(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                initFormForgot();
            }
        });
    }
    
    //* Instanciar formularion forgot
    public void initFormForgot() {
        panelFormForgot = new PanelFormForgot(authEvent);
        setPanelForm(panelFormForgot);
        panelFormForgot.addEventBackLogin(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                initFormLogin();
            }
        });
    }
    
    //* Funcion que se encarga de cambiar los formularios dependiendo del que se le pase por parametro
    private void setPanelForm(JPanel content) {
        content.setSize(content.getPreferredSize());
        panelForm.removeAll();
        panelForm.add(content);
        panelForm.setPreferredSize(content.getPreferredSize());
        repaint();
        revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelForm = new javax.swing.JPanel();

        setBackground(new java.awt.Color(41, 117, 185));
        setMinimumSize(new java.awt.Dimension(1032, 680));

        panelForm.setBackground(new java.awt.Color(210, 210, 210));
        panelForm.setPreferredSize(new java.awt.Dimension(300, 350));

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(490, Short.MAX_VALUE)
                .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(490, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 185, Short.MAX_VALUE)
                .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 185, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelForm;
    // End of variables declaration//GEN-END:variables
}
