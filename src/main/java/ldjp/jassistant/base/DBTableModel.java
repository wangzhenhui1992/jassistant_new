package ldjp.jassistant.base;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;

import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.model.DispatchModel;
import ldjp.jassistant.util.CollectionUtils;
import ldjp.jassistant.util.StringUtil;

/**
 * the model class definition of tables
 */
public class DBTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    // the first line is column name
    // the second line is the type
    DispatchModel data = new DispatchModel();

    /**
     * the flag array that show whether the column to be shown.
     */
    boolean[] showColumnFlags;

    /**
     * current associate talbe
     */
    JTable table = null;

    /**
     * row header
     */
    JTable rowHeader = null;

    /**
     * the table type (table, view);
     * view data should not be edited
     */
    int tableType = PJConst.TABLE_TYPE_TABLE;

    /**
     * the size of extra data before the data
     * 1: columnName vector
     * 2: type class vector
     * 3: size vector
     * 4: key vector
     * 5: comment vector
     */
    // int extraTopSize = 5;

    /**
     * is the content changed
     */
    public boolean isChanged = false;

    /**
     * if the row is editing, the orginal value is stored in
     * this Vector. use to restore the row.
     */
    public Vector<Object> orgRowData = null;

    /**
     * show comment
     */
    public boolean isShowComment = false;

    /**
     * default constructor, empty
     */
    public DBTableModel() {
        super();
    }

    /**
     * constructor with a table
     */
    public DBTableModel(JTable table, JTable rowHeader,
            DispatchModel data, int tableType) {
        this(data);
        this.table = table;
        this.rowHeader = rowHeader;
        this.tableType = tableType;
    }

    /**
     * constructor with a table, enable select columns
     */
    public DBTableModel(JTable table, JTable rowHeader, DispatchModel data,
            int tableType, boolean[] showColumnFlags) {
        this(data);
        this.table = table;
        this.rowHeader = rowHeader;
        this.tableType = tableType;

        if (showColumnFlags != null) {
            this.showColumnFlags = showColumnFlags;
        }
    }

    /*
     * default constructor with data
     */
    public DBTableModel(DispatchModel data) {
        super();
        this.data = data;
        initShowColumnFlags();
    }

    /**
     * set show column flags
     */
    public void setShowColumnFlags(boolean[] showColumnFlags) {
        if (showColumnFlags != null) {
            this.showColumnFlags = showColumnFlags;
        }
        fireTableStructureChanged();
        resetTable();
    }

    /**
     * set cell editor
     */
    public void setCellEditor(CellEditorListener cellEditorListener) {
        for (int i = 0; i < getColumnCount(); i++) {
            int col = getRealColumnIndex(i);
            PJEditorTextField editorComp = new PJEditorTextField(getColumnJavaType(col));
            editorComp.setBorder(null);
            PJDBCellEditor defaultCellEditor = new PJDBCellEditor(editorComp);
            defaultCellEditor.addCellEditorListener(cellEditorListener);
            table.getColumnModel().getColumn(i).setCellEditor(defaultCellEditor);
        }
    }

    /**
     * init show flags
     */
    private void initShowColumnFlags() {
        showColumnFlags = new boolean[data.getClmNms().size()];

        for (int i = 0; i < showColumnFlags.length; i++) {
            showColumnFlags[i] = true;
        }
    }

    /**
     * get the real column index by show index
     */
    public int getRealColumnIndex(int col) {
        int count = -1;
        for (int i = 0; i < showColumnFlags.length; i++) {
            if (!showColumnFlags[i]) {
                continue;
            }
            if (++count == col) {
                return i;
            }
        }

        return 0;
    }

    /**
     * get shown column index by real col
     */
    private int getShowColumnIndex(int realCol) {
        int count = -1;
        for (int i = 0; i < showColumnFlags.length; i++) {
            if (!showColumnFlags[i]) {
                continue;
            }
            ++count;
            if (i == realCol) {
                return count;
            }
        }

        return 0;
    }

    /**
     * These methods always need to be implemented.
     */
    public int getColumnCount() {
        if (data == null || data.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < showColumnFlags.length; i++) {
            if (!showColumnFlags[i]) {
                continue;
            }
            count++;
        }

        return count;
    }

    public int getRowCount() {
        if (data == null || data.isEmpty() || CollectionUtils.isEmpty(data.getVals())) {
            return 0;
        }

        return data.getVals().size();
    }

    public Object getValueAt(int row, int col) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        col = getRealColumnIndex(col);
        Object value = data.getRowVals(row).get(col);

        Class<?> javaType = getColumnJavaType(col);

        return StringUtil.getStringValue(javaType, value);
    }

    public int findRowData(Vector<String[]> valueVector, int beginIndex,
            boolean isCaseSensitive, boolean isPartial) {
        while (beginIndex < getRowCount()) {
            for (int i = 0; i < valueVector.size(); i++) {
                boolean thisFind = false;
                String[] oneCond = (String[]) valueVector.get(i);

                int colIndex = Integer.parseInt(oneCond[0]);
                String value = oneCond[1];
                String orgValue = (String) getValueAt(beginIndex, colIndex);

                if (orgValue == null) {
                    break;
                }
                if (isCaseSensitive) {
                    if ((!isPartial && orgValue.equals(value))
                            || (isPartial && orgValue.indexOf(value) >= 0)) {
                        thisFind = true;
                    }
                } else {
                    if ((!isPartial && orgValue.equalsIgnoreCase(value))
                            || (isPartial && orgValue.toUpperCase().indexOf(
                                    value.toUpperCase()) >= 0)) {
                        thisFind = true;
                    }
                }

                if (thisFind) {
                    if (i == valueVector.size() - 1) {
                        return beginIndex;
                    } else {
                        continue;
                    }
                }

                break;
            }

            beginIndex++;
        }

        return -1;
    }

    public Vector<Object> getRowData(int row) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return data.getRowVals(row);
    }

    public void setRowData(int row, Vector<Object> newData) {
        if (data == null || data.isEmpty()) {
            return;
        }
        data.setRowVals(row, newData);
    }

    public Vector<Class<?>> getColumnJavaType() {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return data.getTypes();
    }

    public Class<?> getColumnJavaType(int col) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        col = getRealColumnIndex(col);
        return data.getTypes().get(col);
    }

    public Vector<String> getColumnName() {
        if (isShowComment) {
            return getColumnComment();
        }

        if (data == null || data.isEmpty()) {
            return null;
        }

        return (Vector<String>) data.getClmNms();
    }

    public String getColumnName(int column) {
        if (isShowComment) {
            return getColumnComment(column);
        }

        if (data == null || data.isEmpty()) {
            return PJConst.EMPTY;
        }

        column = getRealColumnIndex(column);
        return data.getClmNms().get(column);
    }

    public Vector<String> getRealColumnName() {
        if (data == null || data.isEmpty()) {
            return null;
        }

        return data.getClmNms();
    }

    public String getRealColumnName(int column) {
        if (data == null || data.isEmpty()) {
            return PJConst.EMPTY;
        }
        column = getRealColumnIndex(column);
        return data.getClmNms().get(column);
    }

    public Vector<String> getColumnComment() {
        if (data == null || data.isEmpty()) {
            return null;
        }

        return data.getCmts();
    }

    public String getColumnComment(int column) {
        if (data == null || data.isEmpty()) {
            return PJConst.EMPTY;
        }

        column = getRealColumnIndex(column);
        return data.getCmts().get(column);
    }

    public Class<?> getColumnClass(int c) {
        return Object.class;
    }

    public Vector<Boolean> getKeyVector() {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return data.getKeys();
    }

    public Vector<Integer> getSizeVector() {
        if (data == null || data.isEmpty()) {
            return null;
        }

        return data.getSizes();
    }

    public boolean isCellEditable(int row, int col) {
        if (tableType == PJConst.TABLE_TYPE_VIEW) {
            return false;
        } else if (tableType == PJConst.TABLE_TYPE_TABLE) {
            if (getColumnJavaType(col) == Object.class) {
                return false;
            }

            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public void setValueAt(Object aValue, int row, int column) {
        if (data == null || data.isEmpty()) {
            return;
        }

        column = getRealColumnIndex(column);

        if (table != null) {
            try {
                Class<?> javaType = getColumnJavaType(column);
                Object orgValue = data.getRowVals(row).get(column);
                Object orgStrValue = StringUtil.getStringValue(javaType, orgValue);

                if (aValue == null || aValue.equals(PJConst.EMPTY)) {
                    if (orgStrValue == null || orgStrValue.equals(PJConst.EMPTY)) {
                        isChanged = false;
                        return;
                    }
                } else if (aValue.equals(orgStrValue)) {
                    isChanged = false;
                    return;
                }

                aValue = StringUtil.getConvertValueOfType(javaType, aValue);
            } catch (Exception e) {
                return;
            }
        }

        if (orgRowData == null) {
            orgRowData = (Vector<Object>) getRowData(row).clone();
        }
        isChanged = true;

        Vector<Object> dataTemp = data.getRowVals(row);
        dataTemp.set(column, aValue);
    }

    public void addRow(Vector<Object> rowData) {
        if (data == null || data.isEmpty()) {
            return;
        }

        fireTableRowsInserted(getRowCount(), getRowCount() + 1);
        data.addRow(rowData);
    }

    public void addRows(Vector<Vector<Object>> rowsData) {
        if (data == null || data.isEmpty()) {
            return;
        }

        fireTableRowsInserted(getRowCount(), getRowCount() + rowsData.size());
        data.addAllRows(rowsData);
    }

    public void insertRow(Vector<Object> rowData, int row) {
        if (rowData == null || rowData.isEmpty()) {
            return;
        }
        fireTableRowsInserted(0, getRowCount() + 1);
        data.insertRow(row, rowData);
    }

    public void removeRow(int row) {
        if (data == null || data.isEmpty()) {
            return;
        }

        fireTableRowsDeleted(0, getRowCount() - 1);
        data.removeRow(row);
    }

    public void removeAllRows() {
        if (data == null || data.isEmpty()) {
            return;
        }

        int rows = getRowCount();
        if (rows > 0) {
            fireTableRowsDeleted(0, rows - 1);
            data.setVals(new Vector<Vector<Object>>());
        }
    }

    /**
     * reset table show
     */
    public void resetTable() {
        Vector<Class<?>> typeVector = data.getTypes();
        Vector<Integer> sizeVector = data.getSizes();

        for (int i = 0; i < sizeVector.size(); i++) {
            String name = getColumnName(i);
            Class<?> type = typeVector.get(i);
            int displaySize = sizeVector.get(i).intValue();

            if (!showColumnFlags[i]) {
                continue;
            }
            restoreColumnWidth(i, displaySize, name, type);
        }

        table.repaint();
    }

    /**
     * restore one column size.
     */
    public void restoreColumnWidth(int col) {
        if (col < 0) {
            return;
        }

        col = getShowColumnIndex(col);
        if (!showColumnFlags[col]) {
            return;
        }

        Vector<Class<?>> typeVector = data.getTypes();
        Vector<Integer> sizeVector = data.getSizes();

        String name = getColumnName(col);
        Class<?> type = typeVector.get(col);
        int displaySize = sizeVector.get(col).intValue();

        restoreColumnWidth(col, displaySize, name, type);
    }

    /**
     * restore one column size.
     */
    private void restoreColumnWidth(int col, int displaySize, String name, Class<?> type) {
        if (type == java.sql.Timestamp.class
                || type == java.sql.Date.class) {
            displaySize = 15;
        }
        if (displaySize < name.getBytes().length) {
            displaySize = name.getBytes().length;
        }
        if (displaySize > 30) {
            displaySize = 30;
        }

        col = getShowColumnIndex(col);
        table.getColumnModel().getColumn(col).setPreferredWidth(displaySize * 8 + 7);
    }

    public DBTableModel copy() {
        DBTableModel newModel = new DBTableModel();
        newModel.data = data;
        newModel.showColumnFlags = showColumnFlags;
        newModel.table = table;
        newModel.rowHeader = rowHeader;
        newModel.tableType = tableType;
        newModel.isChanged = isChanged;
        newModel.isShowComment = isShowComment;
        newModel.orgRowData = orgRowData;

        return newModel;
    }
}
