package ldjp.jassistant.base;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.event.EventListenerList;

import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.common.ParamTransferEvent;
import ldjp.jassistant.common.ParamTransferListener;

/**
 * basic dialog
 */
public class PJDialogBase extends JDialog {

    private static final long serialVersionUID = 1L;

    public PJDialogBase(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
    }

    public PJDialogBase() {
        this(null, PJConst.EMPTY, true);
    }

    protected void initLocation(Frame parent) {
        Point x = parent.getLocation();
        Dimension screenSize = parent.getSize();
        Dimension frameSize = getSize();
        if (x.x <= 0) {
            x.x = 0;
            x.y = 0;
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        }
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation(((screenSize.width - frameSize.width) / 2) + x.x,
                ((screenSize.height - frameSize.height) / 2) + x.y);
    }

    /** default button handle  **/
    /**
     * default button definition
     */
    JButton defaultButton = null;

    class DefaultButtonFocusAction extends FocusAdapter {

        JComponent comp = null;

        public DefaultButtonFocusAction(JComponent comp) {
            this.comp = comp;
        }

        public void focusGained(FocusEvent fe) {
            JRootPane rootPane = comp.getRootPane();
            if (rootPane != null)
                rootPane.setDefaultButton(defaultButton);
        }
    }

    public void setDefaultButton(JButton defaultButton) {
        this.defaultButton = defaultButton;
        addDefaultButtonAction(this);
        this.getRootPane().setDefaultButton(defaultButton);
    }

    private void addDefaultButtonAction(Container parentContainer) {
        if (parentContainer == null) {
            return;
        }
        Component[] allComponents = parentContainer.getComponents();
        if (allComponents == null) {
            return;
        }
        for (Component c : allComponents) {
            if (c instanceof javax.swing.JTextField ||
            		c instanceof javax.swing.JTextArea ||
            		c instanceof javax.swing.JTable ||
            		c instanceof javax.swing.JComboBox ||
            		c instanceof javax.swing.JRadioButton ||
            		c instanceof javax.swing.JCheckBox ||
            		c instanceof javax.swing.JPasswordField) {
                c.addFocusListener(new DefaultButtonFocusAction(
                        (JComponent) c));
            } else if (c instanceof java.awt.Container) {
                addDefaultButtonAction((Container) c);
            }
        }

    }

    /** View Translation **/

    /**
     * Listener for view
     */
    protected transient EventListenerList eventList = new EventListenerList();

    /**
     * add event
     */
    public void addParamTransferListener(ParamTransferListener listener) {
        eventList.add(ParamTransferListener.class, listener);
    }

    /**
     * delete event
     */
    public void removeParamTransferListener(ParamTransferListener listener) {
        eventList.remove(ParamTransferListener.class, listener);
    }

    /**
     * parameter transfer event
     */
    protected void fireParamTransferEvent(Object obj, int opFlag) {
        EventListener array[] = eventList.getListeners(ParamTransferListener.class);
        ParamTransferEvent transferEvent = new ParamTransferEvent(this);

        transferEvent.setParam(obj);
        transferEvent.setOpFlag(opFlag);

        for (int i = array.length - 1; i >= 0; i--) {
            if (!(array[i] instanceof ParamTransferListener)) {
                continue;
            }
            ((ParamTransferListener) array[i]).paramTransfered(transferEvent);
        }
    }
}
