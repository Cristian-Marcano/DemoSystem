package com.component;

import com.event.FormAuthEvent;
import com.util.ValidateInput;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristian
 * Clase de componente visual que se encarga de mostrar
 * los inputs del formulario de login
 */
public class PanelFormLogin extends javax.swing.JPanel {
    
    /**
     * Atributos que facilitan la funcionalidad y logica del componente
     * 
     * show: dato que valida si mostrar el texto del inputPassword, cuando iconShowAndHidden es presionado
     * authEvent: variable que contiene funciones que se ejecutan cuando ocurren ciertos eventos
     */
    
    private boolean show = false;
    private FormAuthEvent authEvent;
    
    /**
     * Creates new form PanelFormLogin
     */
    public PanelFormLogin(FormAuthEvent authEvent) {
        initComponents();
        this.authEvent = authEvent;
    }
    
    //* Añade un ActionEvent al btnForgotPassword para cambiar de componente a PanelFormForgot
    public void addEventForgotPassword(ActionListener event) {
        btnFotgotPassword.addActionListener(event);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        labelUsername = new javax.swing.JLabel();
        separatorUsername = new javax.swing.JSeparator();
        inputUsername = new javax.swing.JTextField();
        labelPassword = new javax.swing.JLabel();
        separatorPassword = new javax.swing.JSeparator();
        inputPassword = new javax.swing.JPasswordField();
        iconShowAndHidden = new javax.swing.JLabel();
        btnFotgotPassword = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();

        setBackground(new java.awt.Color(210, 210, 210));
        setPreferredSize(new java.awt.Dimension(300, 350));

        title.setFont(new java.awt.Font("Bahnschrift", 0, 20)); // NOI18N
        title.setForeground(new java.awt.Color(60, 60, 60));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Iniciar Sesión");

        labelUsername.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelUsername.setForeground(new java.awt.Color(60, 60, 60));
        labelUsername.setLabelFor(inputUsername);
        labelUsername.setText("Usuario:");

        separatorUsername.setForeground(new java.awt.Color(41, 117, 185));

        inputUsername.setBackground(new java.awt.Color(180, 180, 180));
        inputUsername.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputUsername.setForeground(new java.awt.Color(55, 55, 55));
        inputUsername.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputUsername.setName("Usuario"); // NOI18N

        labelPassword.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelPassword.setForeground(new java.awt.Color(60, 60, 60));
        labelPassword.setLabelFor(inputPassword);
        labelPassword.setText("Contraseña:");

        separatorPassword.setForeground(new java.awt.Color(41, 117, 185));

        inputPassword.setBackground(new java.awt.Color(180, 180, 180));
        inputPassword.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputPassword.setName("Contraseña"); // NOI18N

        iconShowAndHidden.setBackground(new java.awt.Color(180, 180, 180));
        iconShowAndHidden.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconShowAndHidden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eye.png"))); // NOI18N
        iconShowAndHidden.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        iconShowAndHidden.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconShowAndHidden.setOpaque(true);
        iconShowAndHidden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconShowAndHiddenMouseClicked(evt);
            }
        });

        btnFotgotPassword.setBackground(new java.awt.Color(210, 210, 210));
        btnFotgotPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFotgotPassword.setForeground(new java.awt.Color(65, 126, 217));
        btnFotgotPassword.setText("¿Olvido la Contraseña?");
        btnFotgotPassword.setBorder(null);
        btnFotgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnLogin.setBackground(new java.awt.Color(45, 155, 240));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(250, 250, 250));
        btnLogin.setText("Iniciar");
        btnLogin.setBorder(null);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.setPreferredSize(new java.awt.Dimension(92, 30));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(labelPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(162, 162, 162))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(labelUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(190, 190, 190))
                    .addComponent(inputUsername, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separatorUsername, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separatorPassword, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnFotgotPassword)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(iconShowAndHidden, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(title)
                .addGap(18, 18, 18)
                .addComponent(labelUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconShowAndHidden, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnFotgotPassword)
                .addGap(20, 20, 20)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //* Valida y extrae el texto de los inputs para ejecutar el evento de login de authEvent
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        try {
            ValidateInput.isEmptyOrBlank(List.of(inputUsername, inputPassword));
            
            String username = inputUsername.getText(), password = new String(inputPassword.getPassword());
            
            authEvent.onLogin(username, password);
            
        } catch(Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"No se puede avanzar debido a que: \n" + e.getMessage(),"Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    //* Cambia la legibilidad del texto que se encuentra en el inputPassword presionando el iconShowAndHidden
    private void iconShowAndHiddenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconShowAndHiddenMouseClicked
        show = !show;
        if(show) {
            inputPassword.setEchoChar((char) 0);
            iconShowAndHidden.setIcon(new ImageIcon(getClass().getResource("/closed-eye.png")));
        } else {
            inputPassword.setEchoChar('•');
            iconShowAndHidden.setIcon(new ImageIcon(getClass().getResource("/eye.png")));
        }
    }//GEN-LAST:event_iconShowAndHiddenMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFotgotPassword;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel iconShowAndHidden;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JTextField inputUsername;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JSeparator separatorPassword;
    private javax.swing.JSeparator separatorUsername;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
