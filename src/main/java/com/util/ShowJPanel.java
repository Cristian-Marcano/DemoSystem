package com.util;

import com.component.complement.ScrollPaneWin11;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Cristian
 * Clase que facilita la conexion entre el panel y el contenedor,
 * ayudando tambien en la iteracion y adicion de items que poco a poco se añaden al panel
 * y facilitando el cambio entre paneles de forma resumida
 */
public class ShowJPanel {
    
    /**
     * size: tamaño que contendra el panel
     * panel: interfaz de alguna parte de la app
     * container: parte de la ventana donde se muestra el panel
     * containerLayout: Layout del contenedor (forma de mostrar el panel en el contenedor)
    */
    private Dimension size;
    private JPanel panel, container;
    private GroupLayout containerLayout;
    
    //* Constructor
    public ShowJPanel(JPanel p, JPanel contain, Dimension size) {
        this.size = size; 
        panel = p;
        container = contain;
        containerLayout = new GroupLayout(container);
    }
    
    //* Cambiar Panel
    public void setPanel(JPanel p) {
        panel = p;
    }
    
    //* Refrescar contenedor (ayuda a actualizar la imagen en pantalla del contenedor)
    public void refreshContainer() {
        container.revalidate();
        container.repaint();
    }
    
    //* Mostrar panel en el contenedor (adiriendo el panel al layout del contenedor)
    public void showPanel() {
        panel.setPreferredSize(size);
        
        container.removeAll();
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        container.setLayout(containerLayout);
        
        refreshContainer();
    }
    
    //* Mostrar un listado de items al panel en columna (adiere los items al panel si y solo si el panel tiene Scroll)
    public void showItemsPanel(List<JPanel> panelList) throws Exception {
        Component componentScroll = getScrollPane(panel);
        
        if(componentScroll instanceof ScrollPaneWin11) {
            
            JScrollPane scroll = (JScrollPane) componentScroll;
            
            JPanel panelContent = (JPanel) scroll.getViewport().getView();
            
            for(JPanel item : panelList)
                panelContent.add(item);
            
            GroupLayout panelContentLayout = new GroupLayout(panelContent);
            
            panelContent.setLayout(panelContentLayout);
            
            GroupLayout.SequentialGroup hSequentialGroupLayout = panelContentLayout.createSequentialGroup();
            GroupLayout.ParallelGroup hParallelGroupLayout = panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            
            GroupLayout.SequentialGroup vGroupLayout = panelContentLayout.createSequentialGroup();
            
            hSequentialGroupLayout.addGap(10, 90, 90);
            
            for(Component comp: panelContent.getComponents()) {
                if(comp instanceof JPanel) {
                    hParallelGroupLayout.addComponent(comp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, comp.getMaximumSize().width);
                
                    vGroupLayout.addGap(42, 42, 42)
                        .addComponent(comp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                }
            }
            
            hSequentialGroupLayout.addGroup(hParallelGroupLayout).addGap(10, 90, 90);
            vGroupLayout.addContainerGap(20, Short.MAX_VALUE);
            
            panelContentLayout.setHorizontalGroup(
                panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, hSequentialGroupLayout)
            );
            
            panelContentLayout.setVerticalGroup(
                panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(vGroupLayout)
            );
            
            refreshContainer();
            
        } else throw new Exception("the panel does not contain a JScrollPane"); //! Si el panel no contiene un JScrollPane retorna una Excepcion
    }
    
    //* Reescala el tamaño del JScrollPane de acuerdo a la cantidad de items que contiene el panel del JScrollPane
    public void resizeScrollPane() throws Exception {
        Component componentScroll = getScrollPane(panel);
        
        if(componentScroll instanceof JScrollPane) {
            
            JScrollPane scroll = (JScrollPane) componentScroll;
            
            JPanel panelContent = (JPanel) scroll.getViewport().getView();
            
            int heightSize = 42;
            
            for(Component comp: panelContent.getComponents()) 
                if(comp instanceof JPanel) heightSize += comp.getPreferredSize().height + 42;
            
            
            panelContent.setPreferredSize(new Dimension(panelContent.getPreferredSize().width, heightSize));
            
            panelContent.revalidate();
            panelContent.repaint();
        } else throw new Exception("the panel does not contain a JScrollPane");
    }
    
    //* Retorna el ScrollPane que sea hijo directo del panel o en este caso (contain)
    public Component getScrollPane(Container contain) {
        for (Component comp : contain.getComponents()) {
            if(comp instanceof JScrollPane)
                return comp;
        }
        return null;
    }
    
    //* Remueve un componente (un JPanel) del panel o del JScrollPane
    public void removeThisComponent(Component comp) {
        Component componentScroll = getScrollPane(panel);
        
        if(componentScroll instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) componentScroll;
            
            JPanel panelContent = (JPanel) scroll.getViewport().getView();
            
            panelContent.remove(comp);
        } else panel.remove(comp);
            
        refreshContainer();
    }
}
