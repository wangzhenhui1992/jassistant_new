package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import ldjp.jassistant.base.PJTableCellRender;
import ldjp.jassistant.base.RollOverButton;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.common.Refreshable;
import ldjp.jassistant.db.DBParser;
import ldjp.jassistant.model.ColumnDescriptionData;
import ldjp.jassistant.util.CollectionUtils;
import ldjp.jassistant.util.ImageManager;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 */

public class PanelColumnDesc extends JPanel implements Refreshable {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    String tableType;
	String tableName;
	boolean refreshable = true;
	BorderLayout rightBorderLayout = new BorderLayout();
	GridLayout topGridLayout = new GridLayout();
	JToolBar toolBarTop = new JToolBar();
	JPanel panelCenter = new JPanel();
	JScrollPane scpColoumnDesc = new JScrollPane();
	BorderLayout layoutTableBorderLayout = new BorderLayout();
	JTable tblColumnDesc = new JTable();
	RollOverButton btnRefresh = new RollOverButton();
	PJTableCellRender dbTableCellRender = new PJTableCellRender();
	ImageIcon iconRefresh = ImageManager.createImageIcon("refreshtable.gif");
	ImageIcon iconSearchColumn = ImageManager.createImageIcon("editonerow.gif");
	JTextField txtSearchColumn = new JTextField();
    RollOverButton btnSearchColumn = new RollOverButton();

	public PanelColumnDesc() {
		try {
			jbInit();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setLayout(rightBorderLayout);
		panelCenter.setLayout(layoutTableBorderLayout);
		btnRefresh.setToolTipText(WordManager.getWord(WordKeyConsts.W0177));
		btnRefresh.setText(PJConst.EMPTY);
		btnRefresh.setIcon(iconRefresh);
		btnRefresh.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRefresh_actionPerformed(e);
			}
		});
        toolBarTop.add(btnRefresh);

        txtSearchColumn.setToolTipText(WordManager.getWord(WordKeyConsts.W0176));
        txtSearchColumn.setSize(200, 20);
        txtSearchColumn.setPreferredSize(new Dimension(200, 20));
        txtSearchColumn.setMaximumSize(new Dimension(200, 20));
        txtSearchColumn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchColumn();
            }
        });
        toolBarTop.add(txtSearchColumn, null);
        btnSearchColumn.setIcon(iconSearchColumn);
        btnSearchColumn.setToolTipText(WordManager.getWord(WordKeyConsts.W0176));
        toolBarTop.add(btnSearchColumn);
        btnSearchColumn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchColumn();
            }
        });

		toolBarTop.setFloatable(false);
		this.add(toolBarTop, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		panelCenter.add(scpColoumnDesc,  BorderLayout.CENTER);
		scpColoumnDesc.getViewport().add(tblColumnDesc, null);
		tblColumnDesc.setShowGrid(false);
		tblColumnDesc.setShowVerticalLines(true);
		tblColumnDesc.setDefaultRenderer(Object.class, dbTableCellRender);
		tblColumnDesc.setModel(dataModel);
		tblColumnDesc.setRowHeight(18);
        tblColumnDesc.getSelectionModel().setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblColumnDesc.setColumnSelectionAllowed(true);
		tblColumnDesc.getTableHeader().setReorderingAllowed(false);
		tblColumnDesc.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
	}


    /** Event Handle Area **/

	// refresh button action
	void btnRefresh_actionPerformed(ActionEvent e) {
		resetData();
	}

    String preSearch = PJConst.EMPTY;
    int searchedRowIdx = -1;
    int searchedColIdx = -1;
    void searchColumn() {
        String txtForSearch = txtSearchColumn.getText();
        if (PJConst.EMPTY.equals(txtForSearch.trim())) {
            preSearch = PJConst.EMPTY;
            return;
        }
        int i = 0;
        int j = 0;
        if (preSearch.equals(txtForSearch) && searchedRowIdx >= 0) {
            i = searchedRowIdx;
            j = searchedColIdx + 1;
            if (j > 1) {
                i++;
                j = 0;
            }
        } else if (!preSearch.equals(txtForSearch)) {
            searchedRowIdx = -1;
            searchedColIdx = -1;
        }
        preSearch = txtForSearch;
        for (; i < columnDescData.size(); i++) {
            Vector<Object> oneColumnVec = columnDescData.get(i);
            for (; j < 2; j++) {
                String name = (String) oneColumnVec.get(j);
                if (name.toUpperCase().indexOf(txtForSearch.toUpperCase()) < 0) {
                    continue;
                }
                searchedRowIdx = i;
                searchedColIdx = j;
                tblColumnDesc.changeSelection(searchedRowIdx, searchedColIdx, false,
                        false);
                return;
            }
            j = 0;
        }
        searchedRowIdx = -1;
        searchedColIdx = -1;
    }


    /** Business Area **/

	// Table init
	DBColumnDescModel dataModel = new DBColumnDescModel();
	String[] tableColumnHeaders = new String[]{
	        WordManager.getWord(WordKeyConsts.W0178),
	        WordManager.getWord(WordKeyConsts.W0179),
	        WordManager.getWord(WordKeyConsts.W0180),
	        WordManager.getWord(WordKeyConsts.W0171),
	        WordManager.getWord(WordKeyConsts.W0182),
	        WordManager.getWord(WordKeyConsts.W0183),
	        WordManager.getWord(WordKeyConsts.W0184),
	        WordManager.getWord(WordKeyConsts.W0185
	                )};
	int[] columnWidth = new int[]{160, 200, 30, 80, 40, 40, 40, 60};
	Vector<Vector<Object>> columnDescData = new Vector<Vector<Object>>();

	/**
	 * Table data initialization
	 */
	public void setTableColumnDescData(ArrayList<ColumnDescriptionData> listData) {
		dataModel.removeAllRows();

		if (CollectionUtils.isEmpty(listData)) {
		    return;
		}

        for (int i=0; i<listData.size(); i++) {
            Vector<Object> vecOneRecord = new Vector<Object>();

            ColumnDescriptionData oneData = listData.get(i);

            String columnName = oneData.getColumnName();
            int pkseq = oneData.getPrimaryKeySeq();
            String dataType = oneData.getColumnTypeName();
            int columnSize = oneData.getColumnSize();
            int precision = oneData.getPrecision();
            String isNullable = oneData.getIsNullable();

            vecOneRecord.add(columnName);
            if (oneData.getComment() != null) {
                vecOneRecord.add(oneData.getComment());
            } else {
                vecOneRecord.add(PJConst.EMPTY);
            }
            if (pkseq > 0) {
                vecOneRecord.add(String.valueOf(pkseq));
            } else {
                vecOneRecord.add(PJConst.EMPTY);
            }
            vecOneRecord.add(dataType);
            vecOneRecord.add(String.valueOf(columnSize));
            if (precision > 0) {
                vecOneRecord.add(String.valueOf(precision));
            } else {
                vecOneRecord.add(PJConst.EMPTY);
            }
            vecOneRecord.add(isNullable);
            if (oneData.getDefaultValue() != null) {
                vecOneRecord.add(oneData.getDefaultValue());
            } else {
                vecOneRecord.add(PJConst.EMPTY);
            }

            dataModel.addRow(vecOneRecord);
        }

        for (int i = 0; i < columnWidth.length; i++) {
            tblColumnDesc.getColumnModel().getColumn(i)
                    .setPreferredWidth(columnWidth[i]);
        }
	}

	/**
	 * set parameter that to search data
	 * Refreshable method
	 */
	public void setParam(String beanType, String tableName) {
		this.tableType = beanType;
		this.tableName = tableName;
	}

	/**
	 * search column descriptions from database
	 * Refreshable method
	 *
	 * @param tableName
	 */
	public void resetData() {
	    ArrayList<ColumnDescriptionData> columnListData = null;
		try {
			Connection conn = Main.getMDIMain().getConnection();
			columnListData = DBParser.getColumnDescription(conn, tableName);
		} catch (SQLException se) {
			se.printStackTrace();
			MessageManager.showMessage("MCSTC202E", se.getMessage());
			return;
		}

		setTableColumnDescData(columnListData);

		refreshDisplay();
	}

	/**
	 * set refreshable flag
	 * Refreshable method
	 */
	public void setRefreshable(boolean b) {
		this.refreshable = b;
	}

	/**
	 * check it is need to refresh
	 * Refreshable method
	 */
	public boolean isReFreshable() {
		return refreshable;
	}

	/**
	 * clear all table data
	 * Refreshable method
	 */
	public void clearData() {
		dataModel.removeAllRows();
	}

	/**
	 * View Settings
	 */
	public void refreshDisplay() {
		btnRefresh.setEnabled(true);
		tblColumnDesc.clearSelection();
		tblColumnDesc.repaint();
	}

	/**
	 * Class definition for model of tables
	 */
	class DBColumnDescModel extends AbstractTableModel {
		/**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        // default constructor
		public DBColumnDescModel() {
			super();
		}

		// These methods always need to be implemented.
		public int getColumnCount() {
			return tableColumnHeaders.length;
		}

		public int getRowCount() {
			return columnDescData.size();
		}

		public Object getValueAt(int row, int col) {
			return columnDescData.get(row).get(col);
		}

		public String getColumnName(int column) {
			return tableColumnHeaders[column];
		}

		public Class<?> getColumnClass(int c) {
			Object value = getValueAt(0, c);
			if (value != null) {
				return value.getClass();
			}
			return Object.class;
		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}

		public void setValueAt(Object aValue, int row, int column) {
			 columnDescData.get(row).set(column, aValue);
		}

		public void addRow(Vector<Object> rowData) {
			int rows = columnDescData.size();
			fireTableRowsInserted(0, rows + 1);
			columnDescData.add(rowData);
		}

		public void addRow(int index, Vector<Object> rowData) {
			int rows = columnDescData.size();
			fireTableRowsInserted(0, rows + 1);
			columnDescData.add(index, rowData);
		}

		public void removeRow(int row) {
			int rows = columnDescData.size();
			if (rows > 0){
				fireTableRowsDeleted(0, rows - 1);
				columnDescData.remove(row);
			}
		}

		public void removeAllRows() {
			int rows = columnDescData.size();
			if (rows > 0){
				fireTableRowsDeleted(0, rows - 1);
				columnDescData.removeAllElements();
			}
		}
	}
}
