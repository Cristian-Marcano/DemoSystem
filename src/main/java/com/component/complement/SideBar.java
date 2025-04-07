package com.component.complement;

import com.util.ShowJPanel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * 
 * @author Cristian
 */
public class SideBar extends JPanel {
    
    private Animator animator;
    private ShowJPanel content;
    private JPanel panel;
    private GlassPane glass;
    private JLayeredPane layared;
    private boolean show = false;
    
    public SideBar(JLayeredPane layared, GlassPane glass, JPanel panel) {
        initSideBar(layared, glass, panel);
    }
    
    public SideBar(JLayeredPane layared, GlassPane glass, JPanel panel, boolean show) {
        initSideBar(layared, glass, panel);
        this.show = show;
    }
    
    private void initSideBar(JLayeredPane layared, GlassPane glass, JPanel panel) {
        this.layared = layared;
        this.glass = glass;
        this.panel = panel;
        
        setBounds(layared.getSize().width, 0, 325, layared.getSize().height);
        glass.setBounds(0, 0, layared.getSize().width, layared.getSize().height);
        
        this.layared.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                setSize(getSize().width, layared.getSize().height);
                
                if(show) {
                    setLocation(layared.getSize().width - getSize().width, 0);
                    glass.setSize(layared.getSize().width - getSize().width, layared.getSize().height);
                } else {
                    setLocation(layared.getSize().width, 0);
                    glass.setSize(layared.getSize().width, layared.getSize().height);
                }
            }
        });
        
        content = initShowJPanel();
        content.showPanel();
        
        createAnimator();
    }
    
    public void setPanel(JPanel panel) {
        this.panel = panel;
        content = initShowJPanel();
        content.showPanel();
    }
    
    private ShowJPanel initShowJPanel() {
        ShowJPanel sjp = new ShowJPanel(panel, this, getSize());
        sjp.showPanel();
        return sjp;
    }
    
    public void toggle() {
        start();
    }
    
    private void createAnimator() {
        animator = new Animator(500, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                int startX = show ? layared.getSize().width - getSize().width : layared.getSize().width,
                        endX = show ? layared.getSize().width : layared.getSize().width - getSize().width;
                
                int x = (int) (startX + (endX - startX) * fraction);
                setLocation(x, 0);
                glass.setSize(x, glass.getSize().height);
                
                glass.setOpacity(show ? (1f - fraction) * 0.4f: fraction * 0.4f);
            }
            
            @Override
            public void end() {
                show = !show;
                
                if(!show) {
                    glass.setVisible(false);
                    glass.setOpacity(0f);
                }
                    
            }
        });
        
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        animator.setResolution(10);
    }
    
    private void start() {
        if(!show) glass.setVisible(true);
        
        if(animator.isRunning()) {
            animator.stop();
            float f = animator.getTimingFraction();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0f);
        }
        
        animator.start();
    }
}