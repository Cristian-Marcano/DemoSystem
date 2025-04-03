package com.component.complement;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 * Clase complementaria de un panel que solo le añade bordeado mas redondeado
 */
public class PanelBorderRounded extends JPanel {
    
    /**
     * radius: dato que contiene que tan redondeado debe de ser el borde del panel
     */
    
    private int radius = 0;
    
    public PanelBorderRounded() { }
    
    public PanelBorderRounded(int radius) {
        this.radius = radius;
        setOpaque(false);
    }
    
    @Override //* Pinta o renderiza el componente con su redondeado
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, radius, radius);
        g2.dispose();
    }
}
