package com.component.complement;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public class PanelBorderRounded extends JPanel {
    
    private int radius = 0;
    
    public PanelBorderRounded() { }
    
    public PanelBorderRounded(int radius) {
        this.radius = radius;
        setOpaque(false);
    }
    
    @Override
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
