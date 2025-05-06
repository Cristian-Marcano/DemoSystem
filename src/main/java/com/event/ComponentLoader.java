package com.event;

import com.model.Sale;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 * Interface de metodos que se ejecutan para realizar los
 * procesos faltantes para cargar mejor el componente
 */
public interface ComponentLoader {
    
    public void scrollableContentLoader(List<JPanel> listPanels) throws Exception;
    
    public void resizeContentLoader() throws Exception;
    
    public void refreshContent();
    
    public void setContentPane(String namePane);
    
    public void goToInvoice(Object[] saleInvoice, List<Sale> sales);
    
    public void goToLogin();
}
