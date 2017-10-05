package ldjp.jassistant.base;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.ImageManager;

/**
 * render of the header
 */
public class PJDBDataTableRowHeaderRender extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    ImageIcon iconSelectedRecord = ImageManager.createImageIcon("selectedrecord.gif");
    protected Color background;
    protected Border cellBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);

    public PJDBDataTableRowHeaderRender() {
        setOpaque(true);
        setBorder(cellBorder);
    }

    public void updateUI() {
        super.updateUI();
        background = UIManager.getColor("TableHeader.background");
    }

    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean selected,
            boolean focused, int row,
            int column) {

        setHorizontalAlignment(SwingConstants.CENTER);
        if (table != null) {
            setFont(table.getFont());
        } else {
            setFont(UIManager.getFont("TableHeader.font"));
        }

        if (selected) {
            setIcon(iconSelectedRecord);
            setText(PJConst.EMPTY);
        } else {
            setIcon(null);
            setText((String) value);
        }
        setBackground(background);
        setForeground(Color.black);

        return this;
    }
}
