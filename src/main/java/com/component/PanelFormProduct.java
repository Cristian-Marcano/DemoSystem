package com.component;

import com.event.FormProductEvent;
import com.model.Product;
import com.util.TextPrompt;
import com.util.ValidateInput;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Cristian
 */
public class PanelFormProduct extends javax.swing.JPanel {

    private final int mode;
    private Product product;
    private FormProductEvent productEvent;
    private final String REGEX = "^\\d+(\\.\\d{1,2})?$";
    
    public TextPrompt placeholder;
    
    /**
     * Creates new form PanelFormProduct
     */
    public PanelFormProduct(FormProductEvent productEvent, int mode) {
        initComponents();
        placeholder = new TextPrompt("10.98", inputPrice);
        this.mode = mode;
        this.productEvent = productEvent;
        
        if(this.mode == 0) {
            title.setText("Registrar");
            btn.setText("Registrar");
        } else {
            title.setText("Editar");
            btn.setText("Editar");
        }
        
        initDocumentsFilter();
    }
    
    public void setProduct(Product product) {
        this.product = product;
        setValues();
    }
    
    private void setValues() {
        inputTitle.setText(product.getTitle());
        inputStock.setText("" + product.getAvailability());
        inputPrice.setText(product.getPrice().toPlainString());
        textAreaDetail.setText(product.getDetail());
    }
    
    private void initDocumentsFilter() {
        ((AbstractDocument) inputPrice.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String text = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength())).insert(offset, string).toString();

                if(text.matches(REGEX)) {
                    super.insertString(fb, offset, string, attr);
                } // Ignora lo que no coincide
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = new StringBuilder(currentText).replace(offset, offset + length, string).toString();

                if(newText.matches(REGEX)) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = new StringBuilder(currentText).delete(offset, offset + length).toString();

                if(newText.matches(REGEX) || newText.isEmpty()) {
                    super.remove(fb, offset, length);
                }
            }
        });
        
        ((AbstractDocument) inputStock.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String text = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength())).insert(offset, string).toString();

                if(text.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                } // Ignora lo que no coincide
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = new StringBuilder(currentText).replace(offset, offset + length, string).toString();

                if(newText.matches("\\d+")) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = new StringBuilder(currentText).delete(offset, offset + length).toString();

                if(newText.matches("\\d+") || newText.isEmpty()) {
                    super.remove(fb, offset, length);
                }
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

        title = new javax.swing.JLabel();
        labelTitle = new javax.swing.JLabel();
        separatorTitle = new javax.swing.JSeparator();
        inputTitle = new javax.swing.JTextField();
        labelStock = new javax.swing.JLabel();
        separatorStock = new javax.swing.JSeparator();
        inputStock = new javax.swing.JTextField();
        labelPrice = new javax.swing.JLabel();
        separatorPrice = new javax.swing.JSeparator();
        inputPrice = new javax.swing.JTextField();
        labelDetail = new javax.swing.JLabel();
        separatorDetail = new javax.swing.JSeparator();
        scrollTextArea = new javax.swing.JScrollPane();
        textAreaDetail = new javax.swing.JTextArea();
        btn = new javax.swing.JButton();

        setBackground(new java.awt.Color(210, 210, 210));

        title.setFont(new java.awt.Font("Bahnschrift", 0, 20)); // NOI18N
        title.setForeground(new java.awt.Color(60, 60, 60));
        title.setText("Registrar");

        labelTitle.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(60, 60, 60));
        labelTitle.setText("Nombre:");

        separatorTitle.setForeground(new java.awt.Color(41, 117, 185));

        inputTitle.setBackground(new java.awt.Color(180, 180, 180));
        inputTitle.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputTitle.setForeground(new java.awt.Color(55, 55, 55));
        inputTitle.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputTitle.setName("Nombre"); // NOI18N

        labelStock.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelStock.setForeground(new java.awt.Color(60, 60, 60));
        labelStock.setText("Stock:");

        separatorStock.setForeground(new java.awt.Color(41, 117, 185));

        inputStock.setBackground(new java.awt.Color(180, 180, 180));
        inputStock.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputStock.setForeground(new java.awt.Color(55, 55, 55));
        inputStock.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputStock.setName("Stock"); // NOI18N

        labelPrice.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelPrice.setForeground(new java.awt.Color(60, 60, 60));
        labelPrice.setText("Precio:");

        separatorPrice.setForeground(new java.awt.Color(41, 117, 185));

        inputPrice.setBackground(new java.awt.Color(180, 180, 180));
        inputPrice.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        inputPrice.setForeground(new java.awt.Color(55, 55, 55));
        inputPrice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        inputPrice.setName("Precio"); // NOI18N

        labelDetail.setFont(new java.awt.Font("Bahnschrift", 0, 16)); // NOI18N
        labelDetail.setForeground(new java.awt.Color(60, 60, 60));
        labelDetail.setText("Descripcion:");

        separatorDetail.setForeground(new java.awt.Color(41, 117, 185));

        scrollTextArea.setBorder(null);

        textAreaDetail.setBackground(new java.awt.Color(180, 180, 180));
        textAreaDetail.setColumns(20);
        textAreaDetail.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        textAreaDetail.setLineWrap(true);
        textAreaDetail.setRows(5);
        textAreaDetail.setWrapStyleWord(true);
        textAreaDetail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(41, 117, 185)));
        textAreaDetail.setName("Descripcion"); // NOI18N
        scrollTextArea.setViewportView(textAreaDetail);

        btn.setBackground(new java.awt.Color(45, 155, 240));
        btn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn.setForeground(new java.awt.Color(250, 250, 250));
        btn.setText("Registrar");
        btn.setBorder(null);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new java.awt.Dimension(92, 30));
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(title)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(185, 185, 185))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(224, 224, 224))
                    .addComponent(separatorPrice)
                    .addComponent(inputPrice)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(231, 231, 231))
                    .addComponent(separatorStock)
                    .addComponent(inputStock)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(212, 212, 212))
                    .addComponent(separatorTitle)
                    .addComponent(inputTitle)
                    .addComponent(separatorDetail)
                    .addComponent(scrollTextArea))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelStock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorStock, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputStock, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelPrice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelDetail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionPerformed
        try {
            ValidateInput.isEmptyOrBlank(List.of(inputTitle, inputStock, inputPrice, textAreaDetail));
            
            String title = inputTitle.getText(), availability = inputStock.getText(), price = inputPrice.getText(), detail = textAreaDetail.getText();
            
            if(mode==0) {
                
                productEvent.onCreate(title, detail, Integer.parseInt(availability), price);
                
            } else {
                product.setTitle(title);
                product.setAvailability(Integer.parseInt(availability));
                product.setPrice(new BigDecimal(price));
                product.setDetail(detail);
                
                productEvent.onEdit(product);
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"No se puede avanzar debido a que: \n" + e.getMessage(),"Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn;
    private javax.swing.JTextField inputPrice;
    private javax.swing.JTextField inputStock;
    private javax.swing.JTextField inputTitle;
    private javax.swing.JLabel labelDetail;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelStock;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JScrollPane scrollTextArea;
    private javax.swing.JSeparator separatorDetail;
    private javax.swing.JSeparator separatorPrice;
    private javax.swing.JSeparator separatorStock;
    private javax.swing.JSeparator separatorTitle;
    private javax.swing.JTextArea textAreaDetail;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
