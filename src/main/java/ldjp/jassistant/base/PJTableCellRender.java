package ldjp.jassistant.base;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ldjp.jassistant.util.UIUtil;

/**
 * the render of cell
 */
public class PJTableCellRender extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    // default font for all table grids
    static Font defaultFont = new Font("dialog", Font.PLAIN, 12);

    /**
     * default constructor
     */
    public PJTableCellRender() {
        defaultFont = UIUtil.getDefaultFont(UIUtil.GRID_FONT);
    }

    /**
     * update default font
     */
    public static synchronized void updateFont() {
        defaultFont = UIUtil.getDefaultFont(UIUtil.GRID_FONT);
    }

    public Component getTableCellRendererComponent(JTable table,
            Object value,boolean isSelected,boolean hasFocus,
            int row,int column) {
        table.setFont(defaultFont);

        return super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus,
                row, column);
    }
}
