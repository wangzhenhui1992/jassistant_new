package ldjp.jassistant.base;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import ldjp.jassistant.model.DispatchModel;
import ldjp.jassistant.util.CollectionUtils;

/**
 * the model class of the row header
 */
public class PJTableRowHeaderModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    /**
     * the flag that whether to show the row numbers
     */
    public static boolean showRowNumbers = false;

    // the first line is column name
    // the second line is the type
    DispatchModel data = new DispatchModel();

    /**
     * current associate table
     */
    JTable table = null;

    /**
     * the size of extra data before the data
     * 1: columnName vector
     * 2: type class vector
     * 3: size vector
     * 4: key vector
     * 5: comment vector
     */
    // int extraTopSize = 5;

    // default constructor
    public PJTableRowHeaderModel() {
        super();
    }

    // constructor with a table
    public PJTableRowHeaderModel(JTable table, DispatchModel data) {
        this(data);
        this.table = table;
    }

    // default constructor
    public PJTableRowHeaderModel(DispatchModel data) {
        super();
        this.data = data;
    }

    // These methods always need to be implemented.
    public int getColumnCount() {
        return 1;
    }

    public int getRowCount() {
        if (data == null || data.isEmpty() || CollectionUtils.isEmpty(data.getVals())) {
            return 0;
        }
        return data.getVals().size();
    }

    public Object getValueAt(int row, int col) {
        if (showRowNumbers) {
            return String.valueOf(row + 1);
        }

        return null;
    }

    public void fireRowAdded() {
        fireTableRowsInserted(0, getRowCount() + 1);
    }

    public void fireRowDelete() {
        fireTableRowsDeleted(0, getRowCount() - 1);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object aValue, int row, int column) {
        return;
    }

    /**
     * reset table show
     */
    public void resetTable() {
        int height = (data.size() - 5) * 18;
        int width = 18;
        if (showRowNumbers) {
            width = String.valueOf(getRowCount()).length() * 8;

            if (width < 18) {
                width = 18;
            }
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(width);
        table.setPreferredSize(new Dimension(width, height));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
    }
}
