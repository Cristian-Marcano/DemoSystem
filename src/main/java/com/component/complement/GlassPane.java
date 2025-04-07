package com.component.complement;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 * Clase complementaria que ayuda a sombrear
 * el panel debajo de esta para llamar la atencion de un componente
 */
public class GlassPane extends JPanel {
    
    /**
     * opacity: dato que se usa para la transparencia del background
     */
    
    private float opacity;
    
    public GlassPane() {
        setOpaque(false);
    }
    
    //* Cambia la opacidad
    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }
    
    @Override //* Renderiza|Pinta el componente con opacidad instanciada
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        
        if(opacity > 0) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0,0,0,opacity));
            g2.fillRect(0, 0, getSize().width, getSize().height);
            g2.dispose();
        }
    }
}
