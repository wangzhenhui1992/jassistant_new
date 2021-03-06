package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ldjp.jassistant.base.PJTableCellRender;
import ldjp.jassistant.base.PJTableHeaderUI;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.common.Refreshable;
import ldjp.jassistant.db.DBParser;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 * Index information
 */
public class PanelIndexInfos extends JPanel implements Refreshable {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    BorderLayout mainBorderLayout = new BorderLayout();
	JSplitPane mainSplitPanel = new JSplitPane();
	JPanel panelIndexName = new JPanel();
	JPanel panelColumns = new JPanel();
	BorderLayout borderLayoutIndexName = new BorderLayout();
	BorderLayout borderLayoutColumns = new BorderLayout();
	TitledBorder titledBorderIndexName;
	TitledBorder titledBorderColumns;
	JScrollPane scpIndexName = new JScrollPane();
	JTable tblIndexName = new JTable();
	JScrollPane scpColumns = new JScrollPane();
	KeyTableModel tblIndexNameMode;
	JTable tblColumns = new JTable();
	PJTableCellRender dbTableCellRender = new PJTableCellRender();
	String tableName = null;
	String tableType = null;
	boolean refreshable = true;
	Vector<Vector<String>> vecIndexes = null;

	public PanelIndexInfos() {
		try {
			jbInit();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
        titledBorderIndexName = new TitledBorder(BorderFactory.createEtchedBorder(
                Color.white, new Color(148, 145, 140)),
                WordManager.getWord(WordKeyConsts.W0186));
        titledBorderColumns = new TitledBorder(BorderFactory.createEtchedBorder(
                Color.white, new Color(148, 145, 140)),
                WordManager.getWord(WordKeyConsts.W0187));
		this.setPreferredSize(new Dimension(600, 520));
		this.setLayout(mainBorderLayout);
		mainSplitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPanel.setBorder(null);
		mainSplitPanel.setPreferredSize(new Dimension(600, 495));
		mainSplitPanel.setBottomComponent(panelColumns);
		mainSplitPanel.setLastDividerLocation(240);
		mainSplitPanel.setTopComponent(panelIndexName);
		panelIndexName.setLayout(borderLayoutIndexName);
		panelColumns.setLayout(borderLayoutColumns);
		panelIndexName.setBorder(titledBorderIndexName);
		panelColumns.setBorder(titledBorderColumns);
		this.add(mainSplitPanel, BorderLayout.CENTER);
		panelIndexName.add(scpIndexName, BorderLayout.CENTER);
		scpIndexName.getViewport().add(tblIndexName, null);
		panelColumns.add(scpColumns, BorderLayout.CENTER);
		scpColumns.getViewport().add(tblColumns, null);
		mainSplitPanel.setDividerLocation(240);
		tblIndexName.setRowHeight(18);
		tblIndexName.setDefaultRenderer(Object.class, dbTableCellRender);
        tblIndexName.getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        tblIndexName.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        resetColumnTable();
                    }
                });
		tblIndexName.setColumnSelectionAllowed(false);
		tblIndexName.getTableHeader().setReorderingAllowed(false);
		tblIndexName.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblIndexName.getTableHeader().setUI(new PJTableHeaderUI());
		tblColumns.setRowHeight(18);
		tblColumns.setDefaultRenderer(Object.class, dbTableCellRender);
        tblColumns.getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
		tblColumns.setColumnSelectionAllowed(false);
		tblColumns.getTableHeader().setReorderingAllowed(false);
		tblColumns.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblColumns.getTableHeader().setUI(new PJTableHeaderUI());
	}

	// table header
	String[] indexNameTableHeader = {
	        WordManager.getWord(WordKeyConsts.W0188),
	        WordManager.getWord(WordKeyConsts.W0189)};
	String[] columnTableHeader = {
	        WordManager.getWord(WordKeyConsts.W0190) ,
	        WordManager.getWord(WordKeyConsts.W0191) ,
            WordManager.getWord(WordKeyConsts.W0192) };
	int[] columnWidthIndex = new int[]{ 300, 100 };
	int[] columnWidthColumn = new int[]{ 300, 100, 100 };

	/**
	 * set param that to search data
	 */
	public void setParam(String beanType, String tableName) {
		this.tableType = beanType;
		this.tableName = tableName;
	}

	/**
	 * get data from db and reset the table
	 */
	public void resetData() {
		try {
            vecIndexes = DBParser.getIndexes(Main.getMDIMain().getConnection(),
                    tableName);
			resetIndexNamesTable();
		} catch (SQLException se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
			return;
		}
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
		TableModel modelImport = tblIndexName.getModel();
		TableModel modelExport = tblColumns.getModel();

		if (modelImport instanceof KeyTableModel) {
			((KeyTableModel) modelImport).removeAllRows();
		}
		if (modelExport instanceof KeyTableModel) {
			((KeyTableModel) modelExport).removeAllRows();
		}
	}

	/**
	 * refresh imported keys table
	 */
	void resetIndexNamesTable() {
	    clearData();
		Vector<String> vecHeader = new Vector<String>();

		for (int i = 0; i < indexNameTableHeader.length; i++) {
			vecHeader.add(indexNameTableHeader[i]);
		}

		if (vecIndexes != null && vecIndexes.size() > 0) {
		    Vector<Vector<Object>> vecIdxNames = new Vector<Vector<Object>>();
		    String preIdxName = PJConst.EMPTY;
		    for (int i = 0; i < vecIndexes.size(); i++) {
		        Vector<String> row = vecIndexes.get(i);
                if (!preIdxName.equals(row.get(0))) {
                    preIdxName = row.get(0);
                    Vector<Object> rowIdx = new Vector<Object>();
                    rowIdx.add(row.get(0));
                    rowIdx.add(row.get(1));
                    vecIdxNames.add(rowIdx);
                }
            }
    		KeyTableModel model = new KeyTableModel(vecIdxNames, vecHeader);
    	    tblIndexName.setModel(model);

    		for (int i = 0; i < columnWidthIndex.length; i++) {
                tblIndexName.getColumnModel().getColumn(i)
                        .setPreferredWidth(columnWidthIndex[i]);
    		}
		}
		tblIndexName.repaint();
	}

	/**
	 * refresh exported keys table
	 */
	@SuppressWarnings("unchecked")
    void resetColumnTable() {
	    int selectRow = tblIndexName.getSelectedRow();
	    if (selectRow < 0) {
	        TableModel modelColumn = tblColumns.getModel();
	        if (modelColumn instanceof KeyTableModel) {
	            ((KeyTableModel) modelColumn).removeAllRows();
	        }
	    } else {
    	    KeyTableModel idxModel = (KeyTableModel) tblIndexName.getModel();
    		Vector<String> vecHeader = new Vector<String>();
    		for (int i = 0; i < columnTableHeader.length; i++) {
    			vecHeader.add(columnTableHeader[i]);
    		}
            Vector<Object> selectedRowData = (Vector<Object>) idxModel.getDataVector()
                    .get(selectRow);
            String selectedIdxName = (String)selectedRowData.get(0);

            if (vecIndexes != null && vecIndexes.size() > 0) {
                Vector<Vector<Object>> vecColumns = new Vector<Vector<Object>>();
                for (int i = 0; i < vecIndexes.size(); i++) {
                    Vector<String> row = (Vector<String>) vecIndexes.get(i);
                    if (selectedIdxName.equals(row.get(0))) {
                        Vector<Object> rowIdx = new Vector<Object>();
                        rowIdx.add(row.get(2));
                        rowIdx.add(row.get(3));
                        rowIdx.add(row.get(4));
                        vecColumns.add(rowIdx);
                    }
                }

                KeyTableModel columnModel = new KeyTableModel(vecColumns, vecHeader);
                tblColumns.setModel(columnModel);

                for (int i = 0; i < columnWidthColumn.length; i++) {
                    tblColumns.getColumnModel().getColumn(i)
                            .setPreferredWidth(columnWidthColumn[i]);
                }
            }

	    }
		tblColumns.repaint();
	}


	/**
	 * key table model
	 *
	 */
	class KeyTableModel extends DefaultTableModel {
		/**
         *
         */
        private static final long serialVersionUID = 1L;

        public KeyTableModel(Vector<Vector<Object>> data, Vector<String> header) {
			super(data, header);
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		public void removeAllRows() {
			int rows = super.dataVector.size();
			if (rows > 0){
				fireTableRowsDeleted(0, rows - 1);
				super.dataVector.removeAllElements();
			}
		}
	}
}
