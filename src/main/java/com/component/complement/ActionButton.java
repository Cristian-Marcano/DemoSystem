package com.component.complement;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Cristian
 * Clase complementario de un btn que solo le añade bordeado mas redondeado
 */
public class ActionButton extends JButton {
    
    /**
     * Atributos que mejoran la UI del elemeto
     * 
     * radius: dato que almacena que tan redondeado debe de ser el borde del btn
     * mousePress: variable que cambia cambia cada vez que el mouse es presionado o no para oscurecer o no su background
     */
    
    private int radius = 0;
    private boolean mousePress;

    public ActionButton() {
        initActionButton();
    }
    
    public ActionButton(int radius) {
        this.radius = radius;
        initActionButton();
    }
    
    //* Instancia la clase
    private void initActionButton() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                mousePress = true;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                mousePress = false;
            }
        });
    }

    @Override //* Pinta o renderiza el boton con su redondeado y background 
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        if (mousePress) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(x, y, width, height, radius, radius);
        g2.dispose();
        super.paintComponent(grphcs);
    }
}
