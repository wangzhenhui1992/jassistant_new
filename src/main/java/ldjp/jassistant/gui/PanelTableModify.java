package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.CellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import ldjp.jassistant.base.DBTableModel;
import ldjp.jassistant.base.PJDBDataTable;
import ldjp.jassistant.base.PJDBDataTableRowHeader;
import ldjp.jassistant.base.PJDBDataTableRowHeaderRender;
import ldjp.jassistant.base.PJTableRowHeaderModel;
import ldjp.jassistant.base.RollOverButton;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.common.ParamTransferEvent;
import ldjp.jassistant.common.ParamTransferListener;
import ldjp.jassistant.common.Refreshable;
import ldjp.jassistant.db.DBParser;
import ldjp.jassistant.model.DispatchModel;
import ldjp.jassistant.util.FilterSortManager;
import ldjp.jassistant.util.ImageManager;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.UIUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 * table data browser and modify panel
 *
 */
public class PanelTableModify extends JPanel implements Refreshable, ParamTransferListener {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    ImageIcon iconCommit = ImageManager.createImageIcon("commit.gif");
	ImageIcon iconAddRow = ImageManager.createImageIcon("addrow.gif");
	ImageIcon iconCancelEdit = ImageManager.createImageIcon("canceledit.gif");
	ImageIcon iconDeleteRow = ImageManager.createImageIcon("deleterow.gif");
	ImageIcon iconPostEdit = ImageManager.createImageIcon("postedit.gif");
	ImageIcon iconRefreshTable = ImageManager.createImageIcon("refreshtable.gif");
	ImageIcon iconEditOneRow = ImageManager.createImageIcon("editonerow.gif");
	ImageIcon iconFilterSort = ImageManager.createImageIcon("filtersort.gif");
	ImageIcon iconFilterSortNothing = ImageManager.createImageIcon("filtersortnothing.gif");
	ImageIcon iconFilterSortError = ImageManager.createImageIcon("filtersorterror.gif");
	ImageIcon iconRecordCount = ImageManager.createImageIcon("recordcount.gif");
	ImageIcon iconFindGridData = ImageManager.createImageIcon("findgriddata.gif");
	ImageIcon iconSortColumnASC = ImageManager.createImageIcon("sortcolumnasc.gif");
	ImageIcon iconSortColumnDESC = ImageManager.createImageIcon("sortcolumndesc.gif");
	ImageIcon iconShowRowNumber = ImageManager.createImageIcon("showrownumber.gif");
	ImageIcon iconEditColumn = ImageManager.createImageIcon("editcolumn.gif");
	ImageIcon iconCopyRow = ImageManager.createImageIcon("copy.gif");
	ImageIcon iconBatchInsertRows = ImageManager.createImageIcon("batchinsertrows.gif");
	ImageIcon iconSaveTableData = ImageManager.createImageIcon("savetabledata.gif");
	ImageIcon iconImportTableData = ImageManager.createImageIcon("importtabledatas.gif");
	ImageIcon iconSelectColumns = ImageManager.createImageIcon("selectcolumns.gif");
	ImageIcon iconUpdateColumn = ImageManager.createImageIcon("updatecolumn.gif");
	ImageIcon iconSearchColumn = ImageManager.createImageIcon("editonerow.gif");

	BorderLayout mainBorderLayout = new BorderLayout();
	JPanel panelMainTable = new JPanel();
	BorderLayout tblBorderLayout = new BorderLayout();
	JScrollPane scpMainTable = new JScrollPane();
	JTable tableDBTable = new PJDBDataTable();
	TableSelectionRowListener selectionRowListener = new TableSelectionRowListener();
	ScrollChangeListener scrollChangeListener = new ScrollChangeListener();
	ModifyCellEditorListener modifyCellEditorListener = new ModifyCellEditorListener();
	ButtonPopupMenuActionListener buttonPopupMenuActionListener = new ButtonPopupMenuActionListener();
	ShowPopupMouseListener showPopupMouseListener = new ShowPopupMouseListener();
	JToolBar toolBarTop = new JToolBar();
	JToolBar toolBarButtom = new JToolBar();
	RollOverButton btnCommit = new RollOverButton();
	RollOverButton btnFilterSort = new RollOverButton();
	RollOverButton btnImportTableData = new RollOverButton();
	RollOverButton btnSaveAs = new RollOverButton();
	RollOverButton btnFindGridData = new RollOverButton();
	RollOverButton btnAddRec = new RollOverButton();
	RollOverButton btnRefresh = new RollOverButton();
	RollOverButton btnDelete = new RollOverButton();
	RollOverButton btnCancelEdit = new RollOverButton();
	RollOverButton btnPostEdit = new RollOverButton();
	JCheckBox chkShowComment = new JCheckBox(WordManager.getWord(WordKeyConsts.W0263));
	JTextField txtSearchColumn = new JTextField();
	RollOverButton btnSearchColumn = new RollOverButton();
	JComboBox<Object> cmbQuickFilter = new JComboBox<Object>();
	JButton btnQuickFilterSearch = new JButton();
	JButton btnLeftTopCorner = new JButton();
	JButton btnLeftBottomCorner = new JButton();
	PJDBDataTableRowHeader rowHeader = new PJDBDataTableRowHeader(tableDBTable);
	JPopupMenu popupMenu = new JPopupMenu();
	JMenuItem mnuItemEditCell = new JMenuItem(WordManager.getWord(WordKeyConsts.W0277));
	JMenuItem mnuItemEditRow = new JMenuItem(WordManager.getWord(WordKeyConsts.W0278));
	JMenuItem mnuItemDeleteRow = new JMenuItem(WordManager.getWord(WordKeyConsts.W0279));
	JMenuItem mnuItemAddRow = new JMenuItem(WordManager.getWord(WordKeyConsts.W0280));
	JMenuItem mnuItemCopyRow = new JMenuItem(WordManager.getWord(WordKeyConsts.W0281));
    JMenuItem mnuItemBatchInsertRows = new JMenuItem(
            WordManager.getWord(WordKeyConsts.W0282));
    JMenuItem mnuItemImportTableData = new JMenuItem(
            WordManager.getWord(WordKeyConsts.W0283));
    JMenuItem mnuItemSaveAs = new JMenuItem(WordManager.getWord(WordKeyConsts.W0284));
    JMenuItem mnuItemFindGridData = new JMenuItem(
            WordManager.getWord(WordKeyConsts.W0285));
	JMenuItem mnuItemSelectColumns = new JMenuItem(WordManager.getWord(WordKeyConsts.W0286));
    JCheckBoxMenuItem mnuItemShowRowNumber = new JCheckBoxMenuItem(
            WordManager.getWord(WordKeyConsts.W0287));
    JMenuItem mnuItemCountRowNumber = new JMenuItem(
            WordManager.getWord(WordKeyConsts.W0288));
	JPopupMenu headerPopupMenu = new JPopupMenu();
	JMenuItem mnuItemSortAsc = new JMenuItem(WordManager.getWord(WordKeyConsts.W0041));
	JMenuItem mnuItemSortDesc = new JMenuItem(WordManager.getWord(WordKeyConsts.W0042));
    JMenuItem mnuItemFilterColumn = new JMenuItem(
            WordManager.getWord(WordKeyConsts.W0051));
    JMenuItem mnuItemUpdateColumn = new JMenuItem(
            WordManager.getWord(WordKeyConsts.W0289));

	String beanType = PJConst.BEAN_TYPE_TABLE;
	String tableName;
	boolean[] showColumnFlags;
	boolean refreshable = true;
	ResultSet rs = null;
	boolean isLoadOver = false;
	int lastScrollY = 0;
	int lastEditedRow = -1;
	int lastInsertRow = -1;

	static boolean isShowComment = false;
    static Map<String, Vector<String>> filterHistoryMap =
            new HashMap<String, Vector<String>>();

	public PanelTableModify() {
		try {
			jbInit();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setPreferredSize(new Dimension(600, 520));
		this.setLayout(mainBorderLayout);
		panelMainTable.setLayout(tblBorderLayout);
		btnCommit.setEnabled(true);
		btnCommit.setToolTipText(WordManager.getWord(WordKeyConsts.W0143));
		btnCommit.setActionCommand("Commit");
		btnCommit.setIcon(iconCommit);
		btnCommit.addActionListener(buttonPopupMenuActionListener);
		btnAddRec.addActionListener(buttonPopupMenuActionListener);
		btnAddRec.setEnabled(true);
		btnAddRec.setToolTipText(WordManager.getWord(WordKeyConsts.W0280));
		btnAddRec.setIcon(iconAddRow);
		btnRefresh.setToolTipText(WordManager.getWord(WordKeyConsts.W0290));
		btnRefresh.setIcon(iconRefreshTable);
		btnRefresh.addActionListener(buttonPopupMenuActionListener);
		btnRefresh.setVerifyInputWhenFocusTarget(false);
		btnDelete.setEnabled(false);
		btnDelete.setToolTipText(WordManager.getWord(WordKeyConsts.W0279));
		btnDelete.setIcon(iconDeleteRow);
		btnDelete.addActionListener(buttonPopupMenuActionListener);
		btnCancelEdit.addActionListener(buttonPopupMenuActionListener);
		btnCancelEdit.setIcon(iconCancelEdit);
		btnCancelEdit.setToolTipText(WordManager.getWord(WordKeyConsts.W0291));
		btnCancelEdit.setVerifyInputWhenFocusTarget(false);
		btnPostEdit.addActionListener(buttonPopupMenuActionListener);
		btnPostEdit.setIcon(iconPostEdit);
		btnPostEdit.setToolTipText(WordManager.getWord(WordKeyConsts.W0292));
		btnFilterSort.setIcon(iconFilterSortNothing);
		btnFilterSort.setActionCommand("Commit");
		btnFilterSort.setToolTipText(WordManager.getWord(WordKeyConsts.W0039));
		btnFilterSort.setEnabled(true);
		btnFilterSort.addActionListener(buttonPopupMenuActionListener);
		btnImportTableData.setIcon(iconImportTableData);
		btnImportTableData.setToolTipText(WordManager.getWord(WordKeyConsts.W0293));
		btnImportTableData.addActionListener(buttonPopupMenuActionListener);
		btnSaveAs.setIcon(iconSaveTableData);
		btnSaveAs.setToolTipText(WordManager.getWord(WordKeyConsts.W0294));
		btnSaveAs.addActionListener(buttonPopupMenuActionListener);
		btnFindGridData.setIcon(iconFindGridData);
		btnFindGridData.setToolTipText(WordManager.getWord(WordKeyConsts.W0295));
		btnFindGridData.addActionListener(buttonPopupMenuActionListener);
		this.add(panelMainTable,  BorderLayout.CENTER);
		panelMainTable.add(scpMainTable, BorderLayout.CENTER);
		this.add(toolBarTop, BorderLayout.NORTH);
		this.add(toolBarButtom, BorderLayout.SOUTH);
		toolBarButtom.setLayout(new BorderLayout(5, 5));
		toolBarButtom.add(cmbQuickFilter, BorderLayout.CENTER);
		toolBarButtom.add(btnQuickFilterSearch, BorderLayout.EAST);
		cmbQuickFilter.setEditable(true);
		cmbQuickFilter.setBackground(SystemColor.control.brighter());
		cmbQuickFilter.getEditor().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        doQuickFilter();
                    }
                });
            }
        });
//		cmbQuickFilter.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
//		    public void keyTyped(KeyEvent e) {
//		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    e.consume();
//	                doQuickFilter();
//		        }
//		    }
//		});
		btnQuickFilterSearch.setIcon(iconFilterSort);
		btnQuickFilterSearch.setText(WordManager.getWord(WordKeyConsts.W0051));
		btnQuickFilterSearch.setSize(100, 20);
		btnQuickFilterSearch.setPreferredSize(new Dimension(100, 20));
		btnQuickFilterSearch.setMaximumSize(new Dimension(100, 20));
		btnQuickFilterSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doQuickFilter();
            }
        });
//		Border rollBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED,
//										Color.white,
//										SystemColor.control,
//										SystemColor.control,
//										new Color(103, 101, 98));
		scpMainTable.setRowHeaderView(rowHeader);
        rowHeader.getSelectionModel().setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		rowHeader.getSelectionModel().addListSelectionListener(selectionRowListener);
		rowHeader.addMouseListener(showPopupMouseListener);
		btnLeftTopCorner.setIcon(iconEditOneRow);
		btnLeftTopCorner.setToolTipText(WordManager.getWord(WordKeyConsts.W0296));
		btnLeftTopCorner.setPreferredSize(new Dimension(20, 20));
		btnLeftTopCorner.addActionListener(buttonPopupMenuActionListener);
		scpMainTable.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, btnLeftTopCorner);
		btnLeftBottomCorner.setIcon(iconRecordCount);
		btnLeftBottomCorner.setToolTipText(WordManager.getWord(WordKeyConsts.W0297));
		btnLeftBottomCorner.setPreferredSize(new Dimension(20, 20));
		btnLeftBottomCorner.addActionListener(buttonPopupMenuActionListener);
		scpMainTable.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, btnLeftBottomCorner);
		scpMainTable.getViewport().add(tableDBTable, null);
		toolBarTop.add(btnCommit, null);
		toolBarTop.add(btnFilterSort, null);
		JToolBar.Separator separator = new JToolBar.Separator(new Dimension(2, 28));
		separator.setBorder(BorderFactory.createEtchedBorder());
		toolBarTop.add(separator, null);
		toolBarTop.add(btnImportTableData, null);
		toolBarTop.add(btnSaveAs, null);
		toolBarTop.add(btnFindGridData, null);
		separator = new JToolBar.Separator(new Dimension(2, 28));
		separator.setBorder(BorderFactory.createEtchedBorder());
		toolBarTop.add(separator, null);
		toolBarTop.add(btnAddRec, null);
		toolBarTop.add(btnDelete, null);
        separator = new JToolBar.Separator(new Dimension(2, 28));
        separator.setBorder(BorderFactory.createEtchedBorder());
		toolBarTop.add(separator, null);
		toolBarTop.add(btnPostEdit, null);
		toolBarTop.add(btnCancelEdit, null);
		toolBarTop.add(btnRefresh, null);
        separator = new JToolBar.Separator(new Dimension(2, 28));
        separator.setBorder(BorderFactory.createEtchedBorder());
        toolBarTop.add(separator, null);
		toolBarTop.add(chkShowComment, null);
		toolBarTop.setFloatable(false);
		chkShowComment.setSelected(isShowComment);
		chkShowComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableModel model = tableDBTable.getModel();
				int selRow = tableDBTable.getSelectedRow();
				int selCol = tableDBTable.getSelectedColumn();
				if (model instanceof DBTableModel) {
				    isShowComment = chkShowComment.isSelected();
					((DBTableModel) model).isShowComment = chkShowComment.isSelected();
					tableDBTable.setModel(((DBTableModel) model).copy());
					((DBTableModel) model).resetTable();
					tableDBTable.changeSelection(selRow, selCol, false, false);
				}
			}
		});
        txtSearchColumn.setToolTipText(WordManager.getWord(WordKeyConsts.W0176));
		txtSearchColumn.setSize(100, 20);
		txtSearchColumn.setPreferredSize(new Dimension(100, 20));
		txtSearchColumn.setMaximumSize(new Dimension(100, 20));
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
		toolBarButtom.setFloatable(false);
		tableDBTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableDBTable.setColumnSelectionAllowed(true);
		tableDBTable.getSelectionModel().addListSelectionListener(selectionRowListener);
		tableDBTable.addMouseListener(showPopupMouseListener);
        tableDBTable.registerKeyboardAction(buttonPopupMenuActionListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK, false),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		tableDBTable.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelEditRow();
			}
		}, KeyStroke.getKeyStroke((char) 27), JComponent.WHEN_FOCUSED);
		tableDBTable.getTableHeader().addMouseListener(showPopupMouseListener);
		scpMainTable.getViewport().addChangeListener(scrollChangeListener);

		mnuItemEditCell.setIcon(iconEditColumn);
		mnuItemEditRow.setIcon(iconEditOneRow);
		mnuItemAddRow.setIcon(iconAddRow);
		mnuItemDeleteRow.setIcon(iconDeleteRow);
		mnuItemCopyRow.setIcon(iconCopyRow);
		mnuItemBatchInsertRows.setIcon(iconBatchInsertRows);
		mnuItemImportTableData.setIcon(iconImportTableData);
		mnuItemSaveAs.setIcon(iconSaveTableData);
		mnuItemFindGridData.setIcon(iconFindGridData);
		mnuItemSelectColumns.setIcon(iconSelectColumns);
		mnuItemShowRowNumber.setIcon(iconShowRowNumber);
		mnuItemCountRowNumber.setIcon(iconRecordCount);

		mnuItemSortAsc.setIcon(iconSortColumnASC);
		mnuItemSortDesc.setIcon(iconSortColumnDESC);
		mnuItemFilterColumn.setIcon(iconFilterSort);
		mnuItemUpdateColumn.setIcon(iconUpdateColumn);

		popupMenu.add(mnuItemEditCell);
		popupMenu.add(mnuItemEditRow);
		popupMenu.add(mnuItemDeleteRow);
		popupMenu.add(mnuItemAddRow);
		popupMenu.add(mnuItemCopyRow);
		popupMenu.addSeparator();
		popupMenu.add(mnuItemBatchInsertRows);
		popupMenu.add(mnuItemImportTableData);
		popupMenu.add(mnuItemSaveAs);
		popupMenu.addSeparator();
		popupMenu.add(mnuItemFindGridData);
		popupMenu.add(mnuItemSelectColumns);
		popupMenu.add(mnuItemShowRowNumber);
		popupMenu.add(mnuItemCountRowNumber);

		headerPopupMenu.add(mnuItemSortAsc);
		headerPopupMenu.add(mnuItemSortDesc);
		headerPopupMenu.addSeparator();
		headerPopupMenu.add(mnuItemFilterColumn);
		headerPopupMenu.add(mnuItemUpdateColumn);

		mnuItemEditCell.addActionListener(buttonPopupMenuActionListener);
		mnuItemEditRow.addActionListener(buttonPopupMenuActionListener);
		mnuItemDeleteRow.addActionListener(buttonPopupMenuActionListener);
		mnuItemAddRow.addActionListener(buttonPopupMenuActionListener);
		mnuItemCopyRow.addActionListener(buttonPopupMenuActionListener);
		mnuItemImportTableData.addActionListener(buttonPopupMenuActionListener);
		mnuItemBatchInsertRows.addActionListener(buttonPopupMenuActionListener);
		mnuItemSaveAs.addActionListener(buttonPopupMenuActionListener);
		mnuItemFindGridData.addActionListener(buttonPopupMenuActionListener);
		mnuItemSelectColumns.addActionListener(buttonPopupMenuActionListener);
		mnuItemShowRowNumber.addActionListener(buttonPopupMenuActionListener);
		mnuItemCountRowNumber.addActionListener(buttonPopupMenuActionListener);

		mnuItemSortAsc.addActionListener(buttonPopupMenuActionListener);
		mnuItemSortDesc.addActionListener(buttonPopupMenuActionListener);
		mnuItemFilterColumn.addActionListener(buttonPopupMenuActionListener);
		mnuItemUpdateColumn.addActionListener(buttonPopupMenuActionListener);
	}

	/**
	 * if the bean type is view, some actions will be disabled
	 */
	void initViewStatus() {
		if (!PJConst.BEAN_TYPE_TABLE.equals(beanType)) {
			mnuItemEditCell.setEnabled(true);
			mnuItemEditCell.setText(WordManager.getWord(WordKeyConsts.W0298));
			mnuItemEditRow.setEnabled(true);
			mnuItemEditRow.setText(WordManager.getWord(WordKeyConsts.W0299));
			mnuItemDeleteRow.setEnabled(false);
			mnuItemAddRow.setEnabled(false);
			mnuItemCopyRow.setEnabled(false);
			mnuItemBatchInsertRows.setEnabled(false);
			mnuItemImportTableData.setEnabled(false);

			btnCommit.setEnabled(false);
			btnImportTableData.setEnabled(false);
			btnAddRec.setEnabled(false);
			btnDelete.setEnabled(false);
			btnPostEdit.setEnabled(false);
			btnCancelEdit.setEnabled(false);
		}
	}

	/**
	 * set param that to search data
	 */
	public void setParam(String beanType, String tableName) {
		this.beanType = beanType;
		this.tableName = tableName;

		initViewStatus();
		initFilterHistory();
	}

	void initFilterHistory() {
	    Vector<String> filterList = filterHistoryMap.get(this.tableName);
	    if (filterList != null) {
	    	Vector<Object> filterLst =changeTypeToObject(filterList);
    	    DefaultComboBoxModel<Object> modelQuickFilter = new DefaultComboBoxModel<Object>(filterLst);
    	    cmbQuickFilter.setModel(modelQuickFilter);
    	    cmbQuickFilter.setSelectedItem(PJConst.EMPTY);
	    }
	}
	
	private Vector<Object> changeTypeToObject(Vector<String> target){
		if (target == null) return null;
		Vector<Object> result =new Vector<Object>();
		for (String element : target) {
			result.add(element);
		}
		return result;
	}

	/**g
	 * reset the data to the table
	 *
	 */
	public void resetData() {
		resetData(null, null);
	}

	/**
	 * header clicked, sort or filter data
	 *
	 */
	public void resetData(String extraOrder, String extraFilter) {
		if (rs != null) {
			try {
				rs.close();
				Statement st = rs.getStatement();
				if (st != null) {
					st.close();
				}
			} catch (SQLException se) {
				// MessageManager.showMessage("MCSTC202E", se.getMessage());
			}
		}

		try {
			lastEditedRow = -1;
			lastInsertRow = -1;
			lastScrollY = 0;
			isLoadOver = false;
			scpMainTable.getVerticalScrollBar().setValue(0);

			Object[] ret = DBParser.getTableData(Main.getMDIMain().getConnection(),
										tableName,
										Main.getMDIMain().currentConnURL,
										extraOrder, extraFilter);
			rs = (ResultSet) ret[0];
			DispatchModel data = (DispatchModel) ret[1];
			boolean isFilterSuccess = ((Boolean) ret[2]).booleanValue();

			if (!isFilterSuccess) {
                MessageManager.showMessage("MCSTC202E",
                        WordManager.getWord(WordKeyConsts.W0300));
				btnFilterSort.setIcon(iconFilterSortError);
			} else {
                if (FilterSortManager.hasFilterAndSort(Main.getMDIMain().currentConnURL,
                        tableName)) {
					btnFilterSort.setIcon(iconFilterSort);
				} else {
					btnFilterSort.setIcon(iconFilterSortNothing);
				}
			}

			int tableType = PJConst.TABLE_TYPE_TABLE;
			if (PJConst.BEAN_TYPE_TABLE.equals(beanType)) {
				tableType = PJConst.TABLE_TYPE_TABLE;
			} else if (PJConst.BEAN_TYPE_VIEW.equals(beanType)) {
				tableType = PJConst.TABLE_TYPE_VIEW;
			} else {
				tableType = PJConst.TABLE_TYPE_READONLY;
			}

            PJTableRowHeaderModel rowHeaderModel = new PJTableRowHeaderModel(rowHeader,
                    data);
			rowHeader.setModel(rowHeaderModel);
            DBTableModel dataModel = new DBTableModel(tableDBTable, rowHeader, data,
                    tableType, showColumnFlags);
			dataModel.isShowComment = isShowComment;
			tableDBTable.setModel(dataModel);

			if (data != null && data.size() > 3) {
				rowHeaderModel.resetTable();
                rowHeader.getColumnModel().getColumn(0)
                        .setCellRenderer(new PJDBDataTableRowHeaderRender());
				dataModel.resetTable();
			}

			dataModel.setCellEditor(modifyCellEditorListener);
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
		TableModel model = tableDBTable.getModel();

		if (model instanceof DBTableModel) {
			((DBTableModel) model).removeAllRows();
		}

		lastScrollY = 0;
		scpMainTable.getVerticalScrollBar().setValue(0);
		isLoadOver = false;

		try {
			if (rs != null) {
				Statement stmt = rs.getStatement();
				if (stmt != null) {
					stmt.close();
				}
			}
		} catch (SQLException se) {
			// MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}

	void doQuickFilter() {
	    final String filter = (String) cmbQuickFilter.getSelectedItem();
	    if (StringUtil.isEmptyWithTrim(filter)) {
	        return;
	    }

        Vector<String> filterList = filterHistoryMap.get(this.tableName);
        if (filterList == null) {
            filterList = new Vector<String>();
            filterHistoryMap.put(this.tableName, filterList);
        }

        if (!filterList.contains(filter)) {
            filterList.add(0, filter);
        }
        Vector<Object> filterLst =changeTypeToObject(filterList);
        DefaultComboBoxModel<Object> modelQuickFilter = new DefaultComboBoxModel<Object>(filterLst);
        cmbQuickFilter.setModel(modelQuickFilter);
        cmbQuickFilter.setSelectedItem(filter);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                resetData(null, filter);
            }
        });
	}

	/**
	 * table selection listener
	 */
	class TableSelectionRowListener implements javax.swing.event.ListSelectionListener {

		public void valueChanged(javax.swing.event.ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
			    return;
			}
			Object source = e.getSource();
            if (source == tableDBTable.getSelectionModel()) {
                int selectedRow = tableDBTable.getSelectedRow();
                if (rowHeader.getSelectedRow() != selectedRow && selectedRow >= 0) {
                    rowHeader.getSelectionModel().removeListSelectionListener(
                            selectionRowListener);
                    rowHeader.changeSelection(selectedRow, 0, false, false);
                    rowHeader.getSelectionModel().addListSelectionListener(
                            selectionRowListener);
                }

                Runnable laterDo = new Runnable() {
                    public void run() {
                        selectionChanged();
                    }
                };
                SwingUtilities.invokeLater(laterDo);
            } else if (source == rowHeader.getSelectionModel()) {
                int[] selectedRows = rowHeader.getSelectedRows();

                if (selectedRows.length > 0) {
                    tableDBTable.changeSelection(selectedRows[0], 0, false, false);
                    for (int i : selectedRows) {
                        tableDBTable.addRowSelectionInterval(i, i);
                        tableDBTable.addColumnSelectionInterval(0,
                                tableDBTable.getColumnCount() - 1);
                    }
                    tableDBTable.requestFocusInWindow();
                } else {
                    tableDBTable.clearSelection();
                }
            }
		}
	}

	/**
	 * the table selection changed
	 */
	private void selectionChanged() {
		int selectedRow = tableDBTable.getSelectedRow();

		if (selectedRow >= 0) {
			if (PJConst.BEAN_TYPE_TABLE.equals(this.beanType)) {
				btnDelete.setEnabled(true);
			} else {
				btnDelete.setEnabled(false);
			}
		} else {
			btnDelete.setEnabled(false);
			rowHeader.clearSelection();
		}

		if (lastEditedRow >= 0 && selectedRow != lastEditedRow
				&& selectedRow < tableDBTable.getRowCount()) {
			updateRowDataToDB(lastEditedRow);
		}
	}

	/**
	 * update one column, it may be a FileStream or other.
	 */
	@SuppressWarnings("unchecked")
    void updateOneColumn(Object[] param) {
		int row = Integer.parseInt((String) param[0]);
		int col = Integer.parseInt((String) param[1]);
		String newValue = (String) param[2];
		Class<?> type = (Class<?>)param[3];

		DBTableModel model = (DBTableModel) tableDBTable.getModel();
		if (type != Object.class) {
			model.setValueAt(newValue, row, col);

			if (isContentChanged()) {
				lastEditedRow = tableDBTable.getSelectedRow();
			}
		} else {
			int sepPos = newValue.indexOf(":");
			String action = newValue.substring(0, sepPos);
			String filePath = newValue.substring(sepPos + 1).trim();
			Vector<Object> dataVector = model.getRowData(row);
            Vector<Object> keyValueVector = (Vector<Object>) dataVector.get(dataVector
                    .size() - 1);
			String columnName = model.getRealColumnName(col);
			Vector<String> columnNameVector = model.getRealColumnName();
			Vector<Boolean> keyVector = model.getKeyVector();
			Vector<Class<?>> typeVector = model.getColumnJavaType();

			updateObjectColumn(action, columnName, filePath,
								columnNameVector, typeVector,
								keyVector, keyValueVector);
		}

		tableDBTable.repaint();
	}

	/**
	 * update blob clob column. load from file or save to file
	 */
    void updateObjectColumn(String action, String columnName, String filePath,
            Vector<String> columnNameVector, Vector<Class<?>> typeVector,
            Vector<Boolean> keyVector, Vector<Object> keyValueVector) {
		try {
			if (action.startsWith("Save to")) {
				DBParser.saveObjectColumn(Main.getMDIMain().getConnection(), columnName,
											filePath, columnNameVector,
											typeVector, keyVector,
											keyValueVector, tableName);
			} else if (action.startsWith("Load from")) {
				DBParser.updateObjectColumn(Main.getMDIMain().getConnection(), columnName,
											filePath, columnNameVector,
											typeVector, keyVector,
											keyValueVector, tableName);
			}
		} catch (SQLException se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}

	/**
	 * update row data
	 */
	void updateOneRecord(int row, Vector<Object> newRowData) {
		lastEditedRow = row;
		TableModel model = tableDBTable.getModel();
		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			dbModel.setRowData(row, newRowData);
			updateRowDataToDB(lastEditedRow);
			tableDBTable.repaint();
		}
	}

	/**
	 * update row data to database
	 */
	boolean updateRowDataToDB(int lastEditedRow) {

		if (lastEditedRow < 0) {
			return true;
		}

		TableModel model = tableDBTable.getModel();

		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			Vector<String> columnName = dbModel.getRealColumnName();
			Vector<Class<?>> typeData = dbModel.getColumnJavaType();
			Vector<Boolean> keyVector = dbModel.getKeyVector();
			Vector<Object> rowData = dbModel.getRowData(lastEditedRow);
			Vector<Object> orgRowData = dbModel.orgRowData;

			try {
				boolean success = false;

				if (lastInsertRow == lastEditedRow) {
					if (checkRowNull(rowData)) {
						deleteRowData(lastEditedRow);
						return true;
					}
                    success = DBParser.insertRowData(Main.getMDIMain().getConnection(),
                            columnName, typeData, rowData, tableName);
				} else {
                    success = DBParser.updateRowData(Main.getMDIMain().getConnection(),
                            columnName, typeData, keyVector, rowData, orgRowData,
                            tableName);
				}

				if (success) {
					updateRowKeyValue(rowData, keyVector);
				} else {
					MessageManager.showMessage("MCSTC009E");
					dbModel.removeRow(lastEditedRow);
				}
				tableDBTable.repaint();
				this.lastEditedRow = -1;
				this.lastInsertRow = -1;
				dbModel.orgRowData = null;
			} catch (SQLException se) {
				MessageManager.showMessage("MCSTC202E", se.getMessage());
				tableDBTable.changeSelection(lastEditedRow, 0, false, false);
				return false;
			}
		}

		return true;
	}

	/**
	 * check the row data whether is null
	 */
	boolean checkRowNull(Vector<Object> rowData) {
		if (rowData != null) {
			for (int i = 0; i < rowData.size(); i++) {
				if (rowData.get(i) instanceof String) {
					if (((String) rowData.get(i)).length() > 0) {
						return false;
					}
				} else if (rowData.get(i) != null) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * update the key value if the key value is changed
	 */
	@SuppressWarnings("unchecked")
	void updateRowKeyValue(Vector<Object> rowData, Vector<Boolean> keyVector) {
		if (rowData != null && !rowData.isEmpty()) {
            Vector<Object> keyValueVector = (Vector<Object>) rowData
                    .get(rowData.size() - 1);
			if (keyValueVector == null) {
				keyValueVector = new Vector<Object>();
				for (int i = 0; i < keyVector.size(); i++) {
					if (((Boolean) keyVector.get(i)).booleanValue()) {
						keyValueVector.add(rowData.get(i));
					}
				}
				rowData.set(rowData.size() - 1, keyValueVector);
			} else {
				int index = -1;
				for (int i = 0; i < keyVector.size(); i++) {
					if (((Boolean) keyVector.get(i)).booleanValue()) {
						keyValueVector.set(++index, rowData.get(i));
					}
				}
			}
		}
	}

	/**
	 * delete the selected row data
	 */
	void deleteRowData(int selectedRow) {
		if (selectedRow < 0) {
			return;
		}

		TableModel model = tableDBTable.getModel();

		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			Vector<String> columnName = dbModel.getRealColumnName();
			Vector<Class<?>> typeData = dbModel.getColumnJavaType();
			Vector<Boolean> keyVector = dbModel.getKeyVector();
			Vector<Object> rowData = dbModel.getRowData(selectedRow);

			tableDBTable.clearSelection();

			if (lastInsertRow == selectedRow) {
				this.lastInsertRow = -1;
				this.lastEditedRow = -1;

				dbModel.removeRow(selectedRow);
				dbModel.orgRowData = null;
				tableDBTable.repaint();

				repaintRowHeader();

				return;
			}

			if (MessageManager.showMessage("MCSTC007Q") != 0) {
				tableDBTable.changeSelection(selectedRow, 0, false, false);
				return;
			}

			try {
                boolean success = DBParser.deleteRowData(Main.getMDIMain()
                        .getConnection(), columnName, typeData, keyVector, rowData,
                        tableName);

				if (success) {
					dbModel.removeRow(selectedRow);
				} else {
					MessageManager.showMessage("MCSTC009E");
					dbModel.removeRow(selectedRow);
				}
				this.lastInsertRow = -1;
				this.lastEditedRow = -1;
				dbModel.orgRowData = null;

				repaintRowHeader();

				rowHeader.repaint();
				tableDBTable.repaint();
			} catch (SQLException se) {
				MessageManager.showMessage("MCSTC202E", se.getMessage());
				tableDBTable.changeSelection(selectedRow, 0, false, false);
			}
		}
	}

	/**
	 * delete current table all data with filter
	 *
	 */
	void deleteAllData() {
		if (MessageManager.showMessage("MCSTC018Q") != 0) {
			return;
		}

		try {
            DBParser.deleteAllData(Main.getMDIMain().getConnection(), tableName,
                    Main.getMDIMain().currentConnURL, true);

			TableModel model = tableDBTable.getModel();

			if (model instanceof DBTableModel) {
				DBTableModel dbModel = (DBTableModel) model;
				dbModel.removeAllRows();
			}

			repaintRowHeader();

			rowHeader.repaint();
			tableDBTable.repaint();
		} catch (SQLException se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}

	/**
	 * add one record before the selected row.
	 * if no selection will add to the first
	 */
	@SuppressWarnings("unchecked")
    void addRowData(int selectedRow) {

		if (tableDBTable.isEditing()) {
			CellEditor editor = tableDBTable.getCellEditor();
			if (editor != null) {
				editor.stopCellEditing();
			}
		}

		if (lastEditedRow >= 0) {
			if (!updateRowDataToDB(lastEditedRow)) {
				return;
			}
		}

		TableModel model = tableDBTable.getModel();
		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			Vector<String> columnName = dbModel.getRealColumnName();
			Vector<Object> rowData = new Vector<Object>();

			for (int i = 0; i < columnName.size(); i++) {
				rowData.add(null);
			}
			// add key map
			rowData.add(null);

			if (selectedRow < 0) {
				selectedRow = 0;
			}

			lastInsertRow = selectedRow;
			lastEditedRow = selectedRow;
			dbModel.orgRowData = (Vector<Object>) rowData.clone();
			dbModel.insertRow(rowData, selectedRow);

			repaintRowHeader();

			try {
				WaitDoInsertRowRunnable waitDo =
							new WaitDoInsertRowRunnable(selectedRow);
				SwingUtilities.invokeLater(waitDo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * edit selected row data
	 */
	@SuppressWarnings("unchecked")
    void editRowData(int selectRow) {
		if (selectRow < 0 || selectRow >= tableDBTable.getRowCount()) {
			return;
		}

		int tableType = PJConst.TABLE_TYPE_TABLE;
		if (PJConst.BEAN_TYPE_TABLE.equals(this.beanType)) {
			tableType = PJConst.TABLE_TYPE_TABLE;
		} else if (PJConst.BEAN_TYPE_VIEW.equals(this.beanType)) {
			tableType = PJConst.TABLE_TYPE_VIEW;
		} else {
			tableType = PJConst.TABLE_TYPE_READONLY;
		}

		TableModel model = tableDBTable.getModel();
		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			Vector<String> columnName = dbModel.getRealColumnName();
			Vector<String> commentVector = dbModel.getColumnComment();
			Vector<Class<?>> typeData = dbModel.getColumnJavaType();
			Vector<Integer> sizeVector = dbModel.getSizeVector();
			Vector<Boolean> keyVector = dbModel.getKeyVector();
			Vector<Object> rowData = dbModel.getRowData(selectRow);

			if (dbModel.orgRowData == null) {
				dbModel.orgRowData = (Vector<Object>) rowData.clone();
			}

			DialogEditOneRow dialogEditOneRow = new DialogEditOneRow();
			dialogEditOneRow.initUI(columnName, commentVector, typeData,
									sizeVector, keyVector,
									rowData, selectRow, tableType);
			dialogEditOneRow.addParamTransferListener(this);
			dialogEditOneRow.setVisible(true);
			dialogEditOneRow.removeParamTransferListener(this);
		}
	}

	/**
	 * copy selected row data and add to before
	 */
	@SuppressWarnings("unchecked")
    void copyRowData(int selectedRow) {
		if (selectedRow < 0) {
			return;
		}

		if (tableDBTable.isEditing()) {
			CellEditor editor = tableDBTable.getCellEditor();
			if (editor != null) {
				editor.stopCellEditing();
			}
		}

		if (lastEditedRow >= 0) {
			if (!updateRowDataToDB(lastEditedRow)) {
				return;
			}
		}

		TableModel model = tableDBTable.getModel();
		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			Vector<Object> rowData = dbModel.getRowData(selectedRow);

			lastInsertRow = selectedRow;
			lastEditedRow = selectedRow;
			dbModel.orgRowData = null;
			Vector<Object> insertRowVec = (Vector<Object>) rowData.clone();
            insertRowVec.set(rowData.size() - 1,
                    ((Vector<Object>) rowData.get(rowData.size() - 1)).clone());
			dbModel.insertRow(insertRowVec, selectedRow);

			try {
				WaitDoInsertRowRunnable waitDo =
							new WaitDoInsertRowRunnable(selectedRow);
				SwingUtilities.invokeLater(waitDo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * cancel edit row and restore original data
	 */
	@SuppressWarnings("unchecked")
    void cancelEditRow() {
		if (tableDBTable.isEditing()) {
			CellEditor editor = tableDBTable.getCellEditor();
			if (editor != null) {
				editor.cancelCellEditing();
			}
		}

		if (lastInsertRow >= 0) {
			deleteRowData(lastInsertRow);
		} else if (lastEditedRow >= 0) {
			TableModel model = tableDBTable.getModel();

			if (model instanceof DBTableModel) {
				DBTableModel dbModel = (DBTableModel) model;
                dbModel.setRowData(lastEditedRow,
                        (Vector<Object>) dbModel.orgRowData.clone());
				dbModel.orgRowData = null;
				tableDBTable.repaint();
				lastEditedRow = -1;
			}
		}
	}

	/**
	 * post edit row but does not commit it.
	 */
	void postEditRow() {
		if (tableDBTable.isEditing()) {
			CellEditor editor = tableDBTable.getCellEditor();
			if (editor != null) {
				editor.stopCellEditing();
			}
		}
	}

	/**
	 * find one record by value
	 */
    void findGridData(Vector<String> findValue, boolean isCaseSensitive,
            boolean isPartial) {
		TableModel model = tableDBTable.getModel();
		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;

			Vector<String[]> findValueVector = new Vector<String[]>(findValue.size());
			for (int i = 0; i < findValue.size(); i++) {
				String oneCond = findValue.get(i);
				int sepPos = oneCond.indexOf("=");
				String columnName = oneCond.substring(0, sepPos);
				String value = oneCond.substring(sepPos + 1);

				int columnIndex = dbModel.findColumn(columnName);

				findValueVector.add(new String[]{String.valueOf(columnIndex), value});
			}

			int beginIndex = 0;
			while (!isLoadOver || beginIndex == 0) {
                int findRowIndex = dbModel.findRowData(findValueVector, beginIndex,
                        isCaseSensitive, isPartial);
				if (findRowIndex >= 0) {
					rowHeader.changeSelection(findRowIndex, 0, false, false);
					tableDBTable.changeSelection(findRowIndex, 0, false, false);
					return;
				}

				beginIndex = dbModel.getRowCount();
				getMoreData();
			}

			MessageManager.showMessage("MCSTC015I");
		}
	}

	String preSearch = PJConst.EMPTY;
	int searchedIdx = -1;
	void searchColumn() {
	    String txtForSearch = txtSearchColumn.getText();
	    if (PJConst.EMPTY.equals(txtForSearch.trim())) {
	        preSearch = PJConst.EMPTY;
	        return;
	    }
        int selRow = tableDBTable.getSelectedRow();
        if (selRow < 0) {
            selRow = 0;
        }
        TableModel model = tableDBTable.getModel();
        if (model instanceof DBTableModel) {
            DBTableModel dbModel = (DBTableModel) model;
            Vector<String> vecColumn = dbModel.getColumnName();
            int i = 0;
            if (preSearch.equals(txtForSearch) && searchedIdx >= 0) {
                i = searchedIdx + 1;
            } else if (!preSearch.equals(txtForSearch)) {
                searchedIdx = -1;
            }
            preSearch = txtForSearch;
            for (; i < vecColumn.size(); i++) {
                String name = (String) vecColumn.get(i);
                if (name.toUpperCase().indexOf(txtForSearch.toUpperCase()) >= 0) {
                    searchedIdx = i;
                    tableDBTable.changeSelection(selRow, searchedIdx, false, false);
                    return;
                }
            }
            searchedIdx = -1;
        }
	}

	/**
	 * get more data
	 */
	void getMoreData() {
		if (isLoadOver) {
			return;
		}

		try {
			TableModel model = tableDBTable.getModel();
			if (model instanceof DBTableModel) {
				DBTableModel dbModel = (DBTableModel) model;
				Vector<Boolean> keyVector = dbModel.getKeyVector();
                Vector<Vector<Object>> moreData = DBParser.getMoreQueryData(rs,
                        UIUtil.getDefaultFetchSize(), keyVector);
				dbModel.addRows(moreData);
				if (moreData.isEmpty()) {
					isLoadOver = true;
				}
			}

			TableModel rowHeaderModel = rowHeader.getModel();
			if (rowHeaderModel instanceof PJTableRowHeaderModel) {
				((PJTableRowHeaderModel) rowHeaderModel).resetTable();
			}
		} catch (SQLException se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}

	/**
	 * sort column
	 *
	 */
	void sortColumn(String strColumnIndex, String direction) {
		DBTableModel model = (DBTableModel) tableDBTable.getModel();
		String columnName = model.getRealColumnName(Integer.parseInt(strColumnIndex));

		resetData(columnName + " " + direction, null);
	}

	/**
	 * filter single column
	 *
	 */
	void filterColumn(int columnIndex) {
		StringBuffer extraFilter = new StringBuffer();
		DBTableModel model = (DBTableModel) tableDBTable.getModel();
		String columnName = model.getRealColumnName(columnIndex);
		Class<?> type = model.getColumnJavaType(columnIndex);

		// operators init
		String[] operators = {"=", "<>", ">", ">=", "<", "<=",
							"LIKE", "IS NOT NULL", "IS NULL"};
		JComboBox<Object> cmbOperator = new JComboBox<Object>(operators);
        String inputValue = (String) MessageManager.showInputDialog(new Object[] {
                columnName, cmbOperator }, "Filter column");

		String operatorValue = (String) cmbOperator.getSelectedItem();
		if (operatorValue.equals(operators[7]) || operatorValue.equals(operators[8])) {
			extraFilter.append(columnName);
			extraFilter.append(" " + operatorValue);
		} else if (!StringUtil.isEmpty(inputValue) && type != java.lang.Object.class) {
			extraFilter.append(columnName);
			extraFilter.append(" " + operatorValue + " ");
			if (type != null && type.getSuperclass() != java.lang.Number.class) {
				if (!inputValue.startsWith("'")) {
					inputValue = "'" + inputValue;
				}
				if (!inputValue.endsWith("'")) {
					inputValue = inputValue + "'";
				}
			}
			extraFilter.append(inputValue);
		}

		if (!extraFilter.toString().equals(PJConst.EMPTY)) {
			cmbQuickFilter.setSelectedItem(extraFilter.toString());
			doQuickFilter();
		}
	}

	/**
	 * update single column
	 *
	 */
	void updateColumn(int columnIndex) {
		DBTableModel model = (DBTableModel) tableDBTable.getModel();
		String columnName = model.getRealColumnName(columnIndex);
		Class<?> type = model.getColumnJavaType(columnIndex);

		// operators init
		String[] operators = {"Update With Input", "Set NULL"};
		JComboBox<Object> cmbOperator = new JComboBox<Object>(operators);
        String inputValue = (String) MessageManager.showInputDialog(new Object[] {
                columnName, cmbOperator }, "Update column");

		String operatorValue = (String) cmbOperator.getSelectedItem();
		try {
			if (operatorValue.equals(operators[1])) {
			    if (MessageManager.showMessage("MCSTC020Q") != JOptionPane.YES_OPTION) {
			        return;
			    }
                DBParser.updateOneColumn(Main.getMDIMain().getConnection(), columnName,
                        null, type, tableName);
				resetData();
            } else if (!StringUtil.isEmptyWithTrim(inputValue)
                    && type != java.lang.Object.class) {
                if (MessageManager.showMessage("MCSTC020Q") != JOptionPane.YES_OPTION) {
                    return;
                }
                DBParser.updateOneColumn(Main.getMDIMain().getConnection(), columnName,
                        StringUtil.getConvertValueOfType(type, inputValue), type,
                        tableName);
				resetData();
			}
		} catch (Exception e) {
			MessageManager.showMessage("MCSTC202E", e.getMessage());
		}
	}

	/**
	 * set show column flags
	 *
	 */
	void setShowColumnFlags(boolean[] retFlags) {
		showColumnFlags = retFlags;
		DBTableModel model = (DBTableModel) tableDBTable.getModel();
		model.setShowColumnFlags(showColumnFlags);
		model.setCellEditor(modifyCellEditorListener);
	}

	/**
	 * repaint rowheader
	 *
	 */
	private void repaintRowHeader() {
		TableModel headerModel = rowHeader.getModel();
		if (headerModel instanceof PJTableRowHeaderModel) {
			((PJTableRowHeaderModel) headerModel).resetTable();
			scpMainTable.setRowHeaderView(rowHeader);
		}
	}

	/**
	 * for jdk's thread event.
	 */
	class WaitDoInsertRowRunnable implements Runnable {
		int selectedRow;

		public WaitDoInsertRowRunnable(int selectedRow) {
			this.selectedRow = selectedRow;
		}

		public void run() {
			rowHeader.changeSelection(selectedRow, 0, false, false);
			tableDBTable.changeSelection(selectedRow, 0, false, false);
		}
	}

	/** Event Handle Area **/
	/**
	 * right popup menu action
	 */
	class ButtonPopupMenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object item = e.getSource();

			if (item == btnCommit) {
				commit();
			} else if (item == btnFilterSort) {
				showFilterAndSortDialog();
			} else if (item == btnImportTableData || item == mnuItemImportTableData) {
				showImportTableDataDialog();
			} else if (item == btnRefresh) {
				resetData();
			} else if (item == btnCancelEdit) {
				cancelEditRow();
			} else if (item == btnPostEdit) {
				postEditRow();
			} else if (item == mnuItemEditCell) {
				showEditCellDialog(mnuItemEditCell.getActionCommand());
			} else if (item == mnuItemAddRow || item == btnAddRec) {
				addRowData(tableDBTable.getSelectedRow());
			} else if (item == mnuItemDeleteRow || item == btnDelete) {
				deleteRowData(tableDBTable.getSelectedRow());
			} else if (item == mnuItemEditRow || item == btnLeftTopCorner) {
				editRowData(tableDBTable.getSelectedRow());
			} else if (item == mnuItemCopyRow) {
				copyRowData(tableDBTable.getSelectedRow());
			} else if (item == mnuItemBatchInsertRows) {
				showBatchInsertRowsDialog();
			} else if (item == mnuItemSaveAs || item == btnSaveAs) {
				showSaveAsDialog();
            } else if (item == mnuItemFindGridData || item == btnFindGridData
                    || item == tableDBTable) {
				showFindGridDataDialog();
			} else if (item == mnuItemShowRowNumber) {
				showRowNumberOnOff();
			} else if (item == mnuItemSelectColumns) {
				showSelectColumnsDialog();
			} else if (item == mnuItemCountRowNumber || item == btnLeftBottomCorner) {
				showTotalRowCount();
			} else if (item == mnuItemSortAsc) {
				sortColumn(mnuItemSortAsc.getActionCommand(), "ASC");
			} else if (item == mnuItemSortDesc) {
				sortColumn(mnuItemSortDesc.getActionCommand(), "DESC");
			} else if (item == mnuItemFilterColumn) {
				filterColumn(Integer.parseInt(mnuItemFilterColumn.getActionCommand()));
			} else if (item == mnuItemUpdateColumn) {
				updateColumn(Integer.parseInt(mnuItemUpdateColumn.getActionCommand()));
			}
		}
	}

	/**
	 * when scrolled it detected whether to get more data
	 * from the result set
	 *
	 */
	class ScrollChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int totalHeight = (int) scpMainTable.getViewport().getViewSize().getHeight();
			Rectangle rec = scpMainTable.getViewport().getViewRect();
			int viewHeight = (int) rec.getHeight();
			int currentY = (int) (rec.getY() + viewHeight);

			if (totalHeight > viewHeight * 2
					&& currentY + viewHeight * 2 >= totalHeight
					&& currentY > lastScrollY
					&& !isLoadOver) {
				lastScrollY = currentY;
				getMoreData();
			}
		}
	}

	/**
	 * CellEditorListener for default editor
	 */
	class ModifyCellEditorListener implements CellEditorListener {
		public void editingStopped(ChangeEvent e) {
			if (isContentChanged()) {
				lastEditedRow = tableDBTable.getSelectedRow();
			}
		}

		public void editingCanceled(ChangeEvent e) {}
	}

	/**
	 * show the content is changed from model
	 */
	boolean isContentChanged() {
		TableModel model = tableDBTable.getModel();

		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			return dbModel.isChanged;
		}

		return false;
	}

	/**
	 * table cell editor mouse click event
	 * show popup menu
	 *
	 */
	class ShowPopupMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			showPopupMenu(e);
		}
	}

	/**
	 * commit button
	 */
	void commit() {
		if (tableDBTable.isEditing()) {
			CellEditor editor = tableDBTable.getCellEditor();
			if (editor != null) {
				editor.stopCellEditing();
			}
		}

		if (lastEditedRow >= 0) {
			if (!updateRowDataToDB(lastEditedRow)) {
				return;
			}
		}

		Main.getMDIMain().commit();
	}

	/**
	 * show filter and sort window
	 */
	void showFilterAndSortDialog() {
		DialogFilterSort dialogFilterSort = new DialogFilterSort();

		DBTableModel dbModel = (DBTableModel) tableDBTable.getModel();
		Vector<String> columnName = dbModel.getRealColumnName();
		dialogFilterSort.initResources(columnName, this.tableName);
		dialogFilterSort.addParamTransferListener(this);

		dialogFilterSort.setVisible(true);
	}

	/**
	 * show popup menu by right mouse click
	 */
	void showPopupMenu(MouseEvent e) {
		if (tableDBTable.isEditing()) {
			CellEditor editor = tableDBTable.getCellEditor();
			if (editor != null) {
				editor.stopCellEditing();
			}
		}

		if (SwingUtilities.isRightMouseButton(e)) {
			Object obj = e.getSource();

			// avoid out of window, compute the show point
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Point p = e.getPoint();
			SwingUtilities.convertPointToScreen(p, (Component) obj);
			int showX = e.getX();
			int showY = e.getY();
			int menuHeight = 0;
			int menuWidth = 0;

			if (obj == tableDBTable.getTableHeader()) {
				menuHeight = headerPopupMenu.getHeight();
				menuWidth = headerPopupMenu.getWidth();
			} else {
				menuHeight = popupMenu.getHeight();
				menuWidth = popupMenu.getWidth();
			}

			if (p.getX() + menuWidth > screenSize.getWidth()) {
				showX = showX - menuWidth;
			}
			if (p.getY() + menuHeight > screenSize.getHeight()) {
				showY = showY - menuHeight;
			}

			TableColumnModel columnModel = tableDBTable.getTableHeader().getColumnModel();
			int columnIndex = columnModel.getColumnIndexAtX(e.getPoint().x);

			// process show popup menu
			if (obj == tableDBTable.getTableHeader()) {
				if (columnIndex >= 0) {
					mnuItemSortAsc.setActionCommand(String.valueOf(columnIndex));
					mnuItemSortDesc.setActionCommand(String.valueOf(columnIndex));
					mnuItemFilterColumn.setActionCommand(String.valueOf(columnIndex));
					mnuItemUpdateColumn.setActionCommand(String.valueOf(columnIndex));
					headerPopupMenu.show((JComponent) obj, showX, showY);
				}
			} else {
				int y = e.getY();
				int clickRow = (int) y / 18;

				if (obj == rowHeader) {
					if (rowHeader.getSelectedRow() != clickRow) {
						rowHeader.changeSelection(clickRow, 0, false, false);
					}
				} else if (obj == tableDBTable) {
					if (tableDBTable.getSelectedRow() != clickRow) {
						tableDBTable.changeSelection(clickRow, 0, false, false);
					}
				}

				// set column edit menu row and col index
                mnuItemEditCell.setActionCommand(String.valueOf(clickRow) + ","
                        + String.valueOf(columnIndex));
				// set row index to batch insert rows menuitem
				mnuItemBatchInsertRows.setActionCommand(String.valueOf(clickRow));

				// show table popup menu
				popupMenu.show((JComponent) obj, showX, showY);

				if (PJTableRowHeaderModel.showRowNumbers) {
					mnuItemShowRowNumber.setSelected(true);
				} else {
					mnuItemShowRowNumber.setSelected(false);
				}
			}
		}
	}

	/**
	 * show insert rows dialog
	 *
	 */
	void showBatchInsertRowsDialog() {
		int row = Integer.parseInt(mnuItemBatchInsertRows.getActionCommand());

		DBTableModel dbModel = (DBTableModel) tableDBTable.getModel();
		Vector<String> nameVector = dbModel.getRealColumnName();
		Vector<String> commentVector = dbModel.getColumnComment();
		Vector<Class<?>> typeVector = dbModel.getColumnJavaType();
		Vector<Integer> sizeVector = dbModel.getSizeVector();
		Vector<Boolean> keyVector = dbModel.getKeyVector();
		Vector<Object> rowData = dbModel.getRowData(row);

		DialogBatchInsertRows dialogBatchInsertRows = new DialogBatchInsertRows();
		dialogBatchInsertRows.initUI(nameVector, commentVector, typeVector, sizeVector, keyVector, rowData, tableName);
		dialogBatchInsertRows.addParamTransferListener(this);
		dialogBatchInsertRows.setVisible(true);
		dialogBatchInsertRows.removeParamTransferListener(this);
	}

	/**
	 * show save as dialog
	 *
	 */
	void showSaveAsDialog() {
		DialogSaveTableData dialogSaveTableData = new DialogSaveTableData();
		dialogSaveTableData.addParamTransferListener(this);
		dialogSaveTableData.initResources(tableName);
		dialogSaveTableData.setShowComment(isShowComment);
		dialogSaveTableData.setVisible(true);
		dialogSaveTableData.removeParamTransferListener(this);
	}


	/**
	 * show import data dialog
	 *
	 */
	void showImportTableDataDialog() {
		TableModel model = tableDBTable.getModel();
		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			Vector<String> columnName = dbModel.getRealColumnName();
			Vector<Class<?>> typeData = dbModel.getColumnJavaType();

			DialogImportTableData dialogImportTableData = new DialogImportTableData();
			dialogImportTableData.addParamTransferListener(this);
			dialogImportTableData.initResources(tableName, columnName, typeData);
			dialogImportTableData.setVisible(true);
			dialogImportTableData.removeParamTransferListener(this);
		}
	}

	/**
	 * show find grid data dialog
	 */
	void showFindGridDataDialog() {
		TableModel model = tableDBTable.getModel();

		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			Vector<String> columnName = dbModel.getRealColumnName();

			DialogFindGridData dialogFindGridData = new DialogFindGridData();
			dialogFindGridData.initResources(columnName);
			dialogFindGridData.addParamTransferListener(this);
			dialogFindGridData.setVisible(true);
			dialogFindGridData.removeParamTransferListener(this);
		}
	}

	/**
	 * show row numbers on/off
	 */
	void showRowNumberOnOff() {
		if (mnuItemShowRowNumber.isSelected()) {
			PJTableRowHeaderModel.showRowNumbers = true;
		} else {
			PJTableRowHeaderModel.showRowNumbers = false;
		}

		TableModel model = rowHeader.getModel();
		if (model instanceof PJTableRowHeaderModel) {
			((PJTableRowHeaderModel) model).resetTable();
			rowHeader.repaint();
		}
	}

	/**
	 * show select columns dialog
	 *
	 */
	void showSelectColumnsDialog() {
		TableModel model = tableDBTable.getModel();

		if (model instanceof DBTableModel) {
			DBTableModel dbModel = (DBTableModel) model;
			Vector<String> columnName = dbModel.getRealColumnName();

			DialogSelectColumns dialogSelectColumns = new DialogSelectColumns();
			dialogSelectColumns.addParamTransferListener(this);
			dialogSelectColumns.initResources(columnName, showColumnFlags);
			dialogSelectColumns.setVisible(true);
			dialogSelectColumns.removeParamTransferListener(this);
		}
	}

	/**
	 * get current table total row count
	 */
	void showTotalRowCount() {
		try {
            int totalCount = DBParser.getTotalRowCount(
                    Main.getMDIMain().getConnection(), tableName,
                    Main.getMDIMain().currentConnURL);
			MessageManager.showMessage("MCSTC014I", String.valueOf(totalCount));
		} catch (SQLException se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}

	/**
	 * show cell editor dialog
	 *
	 */
	void showEditCellDialog(String rowColIndex) {
	    DBTableModel model = (DBTableModel) tableDBTable.getModel();

		int sepPos = rowColIndex.indexOf(",");
		int row = Integer.parseInt(rowColIndex.substring(0, sepPos));
		int col = Integer.parseInt(rowColIndex.substring(sepPos + 1));

		String columnName = model.getRealColumnName(col);
		Class<?> type = model.getColumnJavaType(col);
		String strValue = (String) model.getValueAt(row, col);

		DialogEditOneCell dialogEditOneCell = new DialogEditOneCell();
		dialogEditOneCell.addParamTransferListener(this);
		dialogEditOneCell.initResources(row, col, columnName, type, strValue, beanType);
		dialogEditOneCell.setVisible(true);
		dialogEditOneCell.removeParamTransferListener(this);
	}


	/**
	 * Event translated by others
	 *
	 * @param pe ParamTransferEvent
	 */
	@SuppressWarnings("unchecked")
    public void paramTransfered(ParamTransferEvent pe) {
		int opFlag = pe.getOpFlag();

        switch (opFlag) {
        case PJConst.WINDOW_CONFIGFILTERSORT:
        case PJConst.WINDOW_BATCHINSERTROWS:
        case PJConst.WINDOW_IMPORTTABLEDATA:
            resetData();
            break;
        case PJConst.WINDOW_EDITONEROW:
            Object[] param = (Object[]) pe.getParam();
            updateOneRecord(((Integer) param[0]).intValue(),(Vector<Object>) param[1]);
            break;
        case PJConst.WINDOW_EDITONECOLUMN:
            param = (Object[]) pe.getParam();
            updateOneColumn(param);
            break;
        case PJConst.WINDOW_FINDGRIDDATA:
            param = (Object[]) pe.getParam();
            findGridData((Vector<String>) param[0], ((Boolean) param[1]).booleanValue(),
                    ((Boolean) param[2]).booleanValue());
            break;
        case PJConst.WINDOW_SELECTCOLUMNS:
            boolean[] retFlags = (boolean[]) pe.getParam();
            setShowColumnFlags(retFlags);
            break;
        default:
            break;
        }
	}
}

