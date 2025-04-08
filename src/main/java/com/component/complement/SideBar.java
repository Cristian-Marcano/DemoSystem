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
 * Clase complementario de un JPanel que se 
 * muestra y oculta con animacion
 */
public class SideBar extends JPanel {
    
    /**
     * Atributos que facilitaron la funcionalidad y la mejora de la UI
     * 
     * animator: objeto que permite realizar la animaciones de acuerdo a un tiempo que se le pase en ms y funciones que contiene
     * content: objeto que auxilia a la adicion de un panel a este SideBar
     * panel: componente que se mostrara dentro del SideBar
     * glass: panel que oscurece alrededor del SideBar para transmitir sensacion de focus
     * layared: elemento en el que se encuentra el SideBar y se usa para tener las medidas en caso de que se reescale
     * show: dato que se se usa en condicionales para saber si SideBar se esta mostrando o no
     */
    
    private Animator animator;
    private ShowJPanel content;
    private JPanel panel;
    private GlassPane glass;
    private JLayeredPane layared;
    private boolean show = false; // Inicialmente el sideBar no se muestra
    
    public SideBar(JLayeredPane layared, GlassPane glass) {
        initSideBar(layared, glass);
    }
    
    public SideBar(JLayeredPane layared, GlassPane glass, boolean show) {
        initSideBar(layared, glass);
        this.show = show;
    }
    
    //* Instancia las funciones y atributos de la clase
    private void initSideBar(JLayeredPane layared, GlassPane glass) {
        this.layared = layared;
        this.glass = glass;
        
        setBounds(layared.getSize().width, 0, 325, layared.getSize().height);
        glass.setBounds(0, 0, layared.getSize().width, layared.getSize().height);
        
        this.layared.addComponentListener(new ComponentAdapter() {
            
            @Override //* Se reescala el sideBar y el glass cada vez que el layared sea reescalado (su altura o anchura cambie)
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
        
        createAnimator();
    }
    
    //* Cambia el componente que se muestra dentro del sideBar
    public void setPanel(JPanel panel) {
        this.panel = panel;
        content = initShowJPanel();
    }
    
    //* Instancia el objeto con sideBar como padre y sus medidas
    private ShowJPanel initShowJPanel() {
        ShowJPanel sjp = new ShowJPanel(panel, this, getSize());
        sjp.showPanel();
        return sjp;
    }
    
    //* Activa o desctiva el SideBar para mostrarse
    public void toggle() {
        start();
    }
    
    //* Crea la animacion de acuerdo a un tiempo de 500ms
    private void createAnimator() {
        animator = new Animator(500, new TimingTargetAdapter() {
            
            @Override //* Aplica los cambios del sideBar y glass cada vez que suceda un tick
            public void timingEvent(float fraction) {
                int startX = show ? layared.getSize().width - getSize().width : layared.getSize().width,
                        endX = show ? layared.getSize().width : layared.getSize().width - getSize().width;
                
                int x = (int) (startX + (endX - startX) * fraction);
                setLocation(x, 0);
                glass.setSize(x, glass.getSize().height);
                
                glass.setOpacity(show ? (1f - fraction) * 0.4f: fraction * 0.4f);
            }
            
            @Override //* Cambia show cada vez que termina por completo la animacion
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
        animator.setResolution(10); // Cada 10ms aplica un tick
    }
    
    //* Empieza la animacion de mostrarse o ocultarse
    private void start() {
        if(!show) glass.setVisible(true);
        
        if(animator.isRunning()) {
            animator.stop();
            float f = animator.getTimingFraction();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0f);
        }
        
        animator.start(); // Empieza la animacion 
    }
}