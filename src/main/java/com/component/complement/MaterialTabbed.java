package com.component.complement;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

/**
 *
 * @author Cristian
 */
public class MaterialTabbed extends JTabbedPane {

    public MaterialTabbed() {
        setUI(new MaterialTabbedUI());
    }
    
    public class MaterialTabbedUI extends MetalTabbedPaneUI {
        
        private Animator animator;
        private Rectangle currentRectangle;
        private TimingTarget target;
        
        public MaterialTabbedUI() {
        }
        
        public void setCurrentRectangle(Rectangle currenRectangle) {
            this.currentRectangle = currenRectangle;
            repaint();
        }
        
        @Override
        public void installUI(JComponent jc) {
            super.installUI(jc);
            animator = new Animator(500);
            animator.setResolution(0);
            animator.setAcceleration(0.5f);
            animator.setDeceleration(0.5f);
            tabPane.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent ce) {
                    int selected = tabPane.getSelectedIndex();
                    if(selected != -1 && currentRectangle != null) {
                        if(animator.isRunning()) {
                            animator.stop();
                        }
                        animator.removeTarget(target);
                        target = new PropertySetter(MaterialTabbedUI.this, "currentRectangle", currentRectangle, getTabBounds(selected, calcRect));
                        animator.addTarget(target);
                        animator.start();
                    }
                }   
            });
        }
        
        @Override
        protected Insets getTabInsets(int i, int i1) {
            return new Insets(10, 10, 10, 10);
        }
        
        @Override
        protected void paintTabBorder(Graphics grphcs, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(3, 155, 216));
            if((currentRectangle==null || !animator.isRunning()) && isSelected) {
                currentRectangle = new Rectangle(x, y, w, h);
            }
            if(currentRectangle!=null) {
                switch (tabPlacement) {
                    case TOP:
                        g2.fillRect(currentRectangle.x, currentRectangle.y + currentRectangle.height - 3, currentRectangle.width, 3);
                        break;
                    case BOTTOM:
                        g2.fillRect(currentRectangle.x, currentRectangle.y, currentRectangle.width, 3);
                        break;
                    case LEFT:
                        g2.fillRect(currentRectangle.x + currentRectangle.width - 3, currentRectangle.y, 3, currentRectangle.height);
                        break;
                    case RIGHT:
                        g2.fillRect(currentRectangle.x, currentRectangle.y, 3, currentRectangle.height);
                        break;
                }
            }
            g2.dispose();
        }
        
         @Override
        protected void paintContentBorder(Graphics grphcs, int tabPlacement, int selectedIndex) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(200, 200, 200));
            Insets insets = getTabAreaInsets(tabPlacement);
            int width = tabPane.getWidth();
            int height = tabPane.getHeight();
            int tabHeight, tabWidth;
            switch(tabPlacement) {
                case TOP:
                    tabHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                    g2.drawLine(insets.left, tabHeight, width - insets.right - 1, tabHeight);
                    break;
                case BOTTOM:
                    tabHeight = height - calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                    g2.drawLine(insets.left, tabHeight, width - insets.right - 1, tabHeight);
                    break;
                case LEFT:
                    tabWidth = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                    g2.drawLine(tabWidth, insets.top, tabWidth, height - insets.bottom - 1);
                    break;
                case RIGHT:
                    tabWidth = width - calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth) - 1;
                    g2.drawLine(tabWidth, insets.top, tabWidth, height - insets.bottom - 1);
                    break;
            }
            g2.dispose();
        }
        
        @Override
        protected void paintFocusIndicator(Graphics grphcs, int i, Rectangle[] rctngls, int i1, Rectangle rctngl, Rectangle rctngl1, boolean bln) {
            tabPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            if (tabPane.isOpaque()) {
                super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
            }
        }
    }
}
