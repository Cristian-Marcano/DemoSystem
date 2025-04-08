package com.event;

import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Cristian
 */
public interface ComponentLoader {
    
    public void scrollableContentLoader(List<JPanel> listPanels) throws Exception;
    
    public void resizeContentLoader() throws Exception;
}
