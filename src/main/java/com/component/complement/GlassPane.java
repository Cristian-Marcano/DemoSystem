package com.component.complement;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public class GlassPane extends JPanel {
    
    private float opacity;
    
    public GlassPane() {
        setOpaque(false);
    }
    
    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }
    
    @Override
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
