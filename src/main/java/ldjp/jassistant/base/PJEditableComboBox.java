package ldjp.jassistant.base;

import javax.swing.JComboBox;

/**
 * for jdk1.4 bugs
 */
public class PJEditableComboBox extends JComboBox {

    private static final long serialVersionUID = 1L;

    public boolean isFocusTraversable() {
        return false;
    }
}
