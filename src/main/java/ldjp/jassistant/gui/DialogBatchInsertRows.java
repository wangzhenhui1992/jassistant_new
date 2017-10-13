package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import ldjp.jassistant.base.DBTableModel;
import ldjp.jassistant.base.PJDBDataTable;
import ldjp.jassistant.base.PJDialogBase;
import ldjp.jassistant.base.PJEditorTextField;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.db.DBParser;
import ldjp.jassistant.model.DispatchModel;
import ldjp.jassistant.util.MessageKeyConsts;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 * batch insert rows dialog
 */
public class DialogBatchInsertRows extends PJDialogBase {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    JPanel panelMain = new JPanel();
	BorderLayout borderLayoutMain = new BorderLayout();
	Border border1;
	JPanel panelTop = new JPanel();
	JPanel panelCenter = new JPanel();
	TitledBorder titledBorderRefRow;
	BorderLayout borderLayoutTop = new BorderLayout();
	JScrollPane scpRefRow = new JScrollPane();
	PJDBDataTable tblRefRow = new PJDBDataTable();
	TitledBorder titledBorderAutoIncrease;
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel panelKeySet = new JPanel();
	JComboBox<Object> cmbKeys = new JComboBox<Object>();
	PJEditorTextField txtKeyOrginalValue = new PJEditorTextField(null);
	PJEditorTextField txtToInsertRows = new PJEditorTextField(Integer.class);
	JLabel lblKeys = new JLabel();
	JLabel lblStartValue = new JLabel();
	JLabel lblToInsertRows = new JLabel();
	JButton btnStart = new JButton();
	JButton btnAbort = new JButton();
	JLabel lblWarning = new JLabel();
	JButton btnClose = new JButton();
	JPanel panelBottom = new JPanel();
	JProgressBar progressBarInsertedRows = new JProgressBar();
	JLabel lblStatus = new JLabel();
	Vector<String> nameVector;
	Vector<String> commentVector;
	Vector<Class<?>> typeVector;
	Vector<Integer> sizeVector;
	Vector<Boolean> keyVector;
	Vector<Object> rowData;
	Vector<Class<?>> keyTypeVector;
	Vector<Object> keyValueVector;
	Vector<Vector<String>> importedKeyVector;
	String tableName;
	String errorMessage = null;
	boolean processedInsert = false;
	ThreadDoInsert threadDoInsert = null;
	boolean breakInsert = false;

	public DialogBatchInsertRows(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogBatchInsertRows() {
		this(Main.getMDIMain(), WordManager.getWord(WordKeyConsts.W0006), true);
	}

	void jbInit() throws Exception {
		border1 = BorderFactory.createEmptyBorder(5,4,5,4);
		titledBorderRefRow = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Referenced Row");
		titledBorderAutoIncrease = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Auto Increase Key");
		panelMain.setLayout(borderLayoutMain);
		panelMain.setBorder(border1);
		panelTop.setBorder(titledBorderRefRow);
		panelTop.setMinimumSize(new Dimension(400, 100));
		panelTop.setPreferredSize(new Dimension(400, 100));
		panelTop.setLayout(borderLayoutTop);
		borderLayoutMain.setVgap(5);
		panelCenter.setBorder(titledBorderAutoIncrease);
		panelCenter.setMaximumSize(new Dimension(400, 120));
		panelCenter.setMinimumSize(new Dimension(400, 120));
		panelCenter.setPreferredSize(new Dimension(400, 120));
		panelCenter.setLayout(borderLayout1);
		panelKeySet.setLayout(null);
		cmbKeys.setBounds(new Rectangle(25, 22, 135, 21));
		txtKeyOrginalValue.setBounds(new Rectangle(178, 23, 100, 21));
		txtToInsertRows.setBounds(new Rectangle(290, 23, 62, 21));
		lblKeys.setText(WordManager.getWord(WordKeyConsts.W0007));
		lblKeys.setBounds(new Rectangle(25, 3, 121, 17));
		lblStartValue.setText(WordManager.getWord(WordKeyConsts.W0008));
		lblStartValue.setBounds(new Rectangle(178, 2, 65, 17));
		lblToInsertRows.setText(WordManager.getWord(WordKeyConsts.W0009));
		lblToInsertRows.setBounds(new Rectangle(290, 2, 80, 17));
		btnStart.setMaximumSize(new Dimension(45, 23));
		btnStart.setMinimumSize(new Dimension(45, 23));
		btnStart.setPreferredSize(new Dimension(45, 23));
		btnStart.setMargin(new Insets(0, 0, 0, 0));
		btnStart.setMnemonic(PJConst.MNEMONIC_S);
		btnStart.setText(WordManager.getWord(WordKeyConsts.W0010));
		btnStart.setBounds(new Rectangle(130, 65, 45, 23));
		btnStart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processInsert();
			}
		});
		btnAbort.setMaximumSize(new Dimension(45, 23));
		btnAbort.setMinimumSize(new Dimension(45, 23));
		btnAbort.setPreferredSize(new Dimension(45, 23));
		btnAbort.setMargin(new Insets(0, 0, 0, 0));
		btnAbort.setMnemonic(PJConst.MNEMONIC_A);
		btnAbort.setText(WordManager.getWord(WordKeyConsts.W0011));
		btnAbort.setBounds(new Rectangle(178, 65, 45, 23));
		btnAbort.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				breakInsert = true;
			}
		});
		btnAbort.setEnabled(false);
		this.setModal(true);
		this.setResizable(false);
		lblWarning.setForeground(Color.red);
		lblWarning.setText(WordManager.getWord(WordKeyConsts.W0012));
		lblWarning.setBounds(new Rectangle(0, 47, 395, 17));
		lblWarning.setVisible(false);
		btnClose.setBounds(new Rectangle(226, 65, 45, 23));
		btnClose.setText(WordManager.getWord(WordKeyConsts.W0013));
		btnClose.setMnemonic(PJConst.MNEMONIC_C);
		btnClose.setVerifyInputWhenFocusTarget(false);
		btnClose.setMargin(new Insets(0, 0, 0, 0));
		btnClose.setPreferredSize(new Dimension(45, 23));
		btnClose.setMinimumSize(new Dimension(45, 23));
		btnClose.setMaximumSize(new Dimension(45, 23));
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
		lblStatus.setText(PJConst.EMPTY);
		lblStatus.setBounds(new Rectangle(3, 6, 130, 15));
		panelBottom.setLayout(null);
		progressBarInsertedRows.setBounds(new Rectangle(142, 6, 257, 15));
		panelBottom.setMaximumSize(new Dimension(400, 30));
		panelBottom.setMinimumSize(new Dimension(400, 30));
		panelBottom.setPreferredSize(new Dimension(400, 30));
		getContentPane().add(panelMain);
		panelMain.add(panelTop,  BorderLayout.NORTH);
		panelTop.add(scpRefRow, BorderLayout.CENTER);
		scpRefRow.getViewport().add(tblRefRow, null);
		tblRefRow.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					viewRefRowData();
				}
			}
		});
		tblRefRow.setToolTipText(WordManager.getWord(WordKeyConsts.W0014));
		panelMain.add(panelCenter, BorderLayout.CENTER);
		panelMain.add(panelBottom,  BorderLayout.SOUTH);
		panelBottom.add(lblStatus, null);
		panelBottom.add(progressBarInsertedRows, null);
		panelCenter.add(panelKeySet, BorderLayout.CENTER);
		panelKeySet.add(cmbKeys, null);
		panelKeySet.add(lblKeys, null);
		panelKeySet.add(txtKeyOrginalValue, null);
		panelKeySet.add(lblStartValue, null);
		panelKeySet.add(lblWarning, null);
		panelKeySet.add(txtToInsertRows, null);
		panelKeySet.add(lblToInsertRows, null);
		panelKeySet.add(btnClose, null);
		panelKeySet.add(btnAbort, null);
		panelKeySet.add(btnStart, null);
	}

	public void setVisible(boolean b) {
		if (b) {
			initLocation(Main.getMDIMain());
		}
		super.setVisible(b);
	}


	/**
	 * init ui from data
	 */
    public void initUI(Vector<String> nameVector, Vector<String> commentVector,
            Vector<Class<?>> typeVector, Vector<Integer> sizeVector,
            Vector<Boolean> keyVector, Vector<Object> rowData, String tableName) {
		this.nameVector = nameVector;
		this.commentVector = commentVector;
		this.typeVector = typeVector;
		this.sizeVector = sizeVector;
		this.keyVector = keyVector;
		this.rowData = rowData;
		this.tableName = tableName;

		initTableData();
		intRefKey();
		initKeyCombobox();
	}

	/**
	 * init ref table data and listenerns
	 *
	 */
	void initTableData() {
		DispatchModel data = new DispatchModel();
		data.setClmNms(nameVector);
		data.setTypes(typeVector);
		data.setSizes(sizeVector);
		data.setKeys(keyVector);
		data.setCmts(commentVector);
		data.addRow(rowData);

        DBTableModel dataModel = new DBTableModel(tblRefRow, null, data,
                PJConst.TABLE_TYPE_READONLY);
		tblRefRow.setModel(dataModel);
		dataModel.resetTable();
	}

	/**
	 * init the primary key reference
	 *
	 */
	void intRefKey() {
		try {
			importedKeyVector = DBParser.getImportKeys(Main.getMDIMain().getConnection(), tableName);
		} catch (SQLException se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}

	/**
	 * init primary key combobox values
	 *
	 */
	void initKeyCombobox() {
		cmbKeys.addItem(PJConst.EMPTY);
		keyTypeVector = new Vector<Class<?>>();
		keyTypeVector.add(null);
		keyValueVector = new Vector<Object>();
		keyValueVector.add(null);

		for (int i = 0,size = nameVector.size(); i < size; i++) {
			if (((Boolean) keyVector.get(i)).booleanValue()) {
				cmbKeys.addItem(nameVector.get(i));
				keyTypeVector.add(typeVector.get(i));
				keyValueVector.add(rowData.get(i));
			}
		}

		setKeyValueStatus();
		cmbKeys.addItemListener(new ChangePrimaryKeyListener());
	}

	/**
	 * if the key changed
	 *
	 */
	void setKeyValueStatus() {
		String selectedValue = (String) cmbKeys.getSelectedItem();
		int selectIndex = cmbKeys.getSelectedIndex();

		if (!StringUtil.isEmpty(selectedValue)) {
			if (isImportedKey(selectedValue)) {
				txtKeyOrginalValue.setEditable(false);
				txtToInsertRows.setEditable(false);
				btnStart.setEnabled(false);
				lblWarning.setVisible(true);
			} else {
				txtKeyOrginalValue.setEditable(true);
				txtToInsertRows.setEditable(true);
				btnStart.setEnabled(true);
				lblWarning.setVisible(false);
			}

			Class<?> keyType = keyTypeVector.get(selectIndex);
			txtKeyOrginalValue.resetType(keyType);
			txtKeyOrginalValue.setText(StringUtil.getStringValue(keyType, keyValueVector.get(selectIndex)));
		} else {
			txtKeyOrginalValue.setEditable(false);
			txtToInsertRows.setEditable(false);
			txtKeyOrginalValue.setText(PJConst.EMPTY);
			txtToInsertRows.setText(PJConst.EMPTY);
			lblWarning.setVisible(false);
			btnStart.setEnabled(false);
		}
	}

	/**
	 * view ref row data
	 *
	 */
	void viewRefRowData() {
		DialogEditOneRow dialogEditOneRow = new DialogEditOneRow();
		dialogEditOneRow.initUI(nameVector, nameVector, typeVector,
								sizeVector, keyVector,
								rowData, -1, PJConst.TABLE_TYPE_READONLY);
		dialogEditOneRow.setVisible(true);
	}

	/**
	 * check the key is imported from other table
	 *
	 */
	boolean isImportedKey(String keyName) {
		if (importedKeyVector != null && !importedKeyVector.isEmpty()) {
			for (int i = 0 , size = importedKeyVector.size(); i < size ; i++) {
				String oneKey =  importedKeyVector.get(i).get(0);
				if (oneKey.equals(keyName)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * start insert
	 *
	 */
	void processInsert() {
		String strToInsertRows = txtToInsertRows.getText();
        if (StringUtil.isEmptyWithTrim(strToInsertRows)) {
			MessageManager.showMessage(MessageKeyConsts.MCSTC001E, WordManager.getWord(WordKeyConsts.W0015));
			return;
		}

		String startValue = txtKeyOrginalValue.getText();
		if (StringUtil.isEmptyWithTrim(startValue)) {
			MessageManager.showMessage(MessageKeyConsts.MCSTC001E, WordManager.getWord(WordKeyConsts.W0016));
			return;
		}

		int insertRows = Integer.parseInt(strToInsertRows);
		int totalLen = String.valueOf(insertRows).length();
		String selectedKeyName = (String) cmbKeys.getSelectedItem();
		int columnIndex = nameVector.indexOf(selectedKeyName);
		Class<?> keyType = typeVector.get(columnIndex);

		PreparedStatement pstmt = null;
		try {
			// show info message.
			if (MessageManager.showMessage("MCSTC017Q", new String[]{selectedKeyName,
										StringUtil.getStringValue(keyType, StringUtil.getIncreaseConvertValueOfType(keyType, startValue, 1, totalLen)),
										StringUtil.getStringValue(keyType, StringUtil.getIncreaseConvertValueOfType(keyType, startValue, insertRows, totalLen))}) != 0) {
				return;
			}

			// get prepared statement
			pstmt = DBParser.getInsertStatement(Main.getMDIMain().getConnection(),
										nameVector, typeVector,
										rowData, tableName);
			processedInsert = true;

			threadDoInsert = new ThreadDoInsert(insertRows, totalLen, columnIndex, startValue, keyType, pstmt);
			threadDoInsert.start();
		} catch (Exception se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}


	/**
	 * use a separate thread to do insert rows
	 *
	 */
	class ThreadDoInsert extends Thread {
		int insertRows = 0;
		int totalLen = 0;
		int columnIndex;
		String startValue;
		Class<?> keyType;
		PreparedStatement pstmt = null;

		/**
		 * constructor
		 */
		public ThreadDoInsert(int insertRows, int totalLen, int columnIndex, String startValue,
							Class<?> keyType, PreparedStatement pstmt) {
			this.insertRows = insertRows;
			this.totalLen = totalLen;
			this.columnIndex= columnIndex;
			this.startValue= startValue;
			this.keyType= keyType;
			this.pstmt = pstmt;
		}

		/**
		 * the start method
		 */
		public void run() {
			progressBarInsertedRows.setMaximum(insertRows);
			btnStart.setEnabled(false);
			btnAbort.setEnabled(true);

			try {
				for (int i = 1; i <= insertRows; i++) {
					if (breakInsert) {
						return;
					}

					try {
						Object obj = StringUtil.getIncreaseConvertValueOfType(keyType, startValue, i, totalLen);
						DBParser.setParameter(pstmt, columnIndex + 1, keyType, obj);

						pstmt.executeUpdate();

						lblStatus.setText("Inserted " + i + " Rows");
						progressBarInsertedRows.setValue(i);
					} catch (Exception e) {
						errorMessage = e.getMessage();
						SwingUtilities.invokeAndWait(new Runnable() {
							public void run() {
								if (MessageManager.showMessage("MCSTC016Q", errorMessage) == 0) {
									breakInsert = false;
								} else {
									breakInsert = true;
								}
							}
						});
					}
				}

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						closeWindow();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException se) {}
				progressBarInsertedRows.setValue(0);
				btnStart.setEnabled(true);
				btnAbort.setEnabled(false);
				breakInsert = false;
			}
		}
	}

	/**
	 * close the window
	 *
	 */
	void closeWindow() {
		dispose();

		if (processedInsert) {
			breakInsert = true;
			fireParamTransferEvent(null, PJConst.WINDOW_BATCHINSERTROWS);
		}
	}


	/**
	 * change primary key combobox
	 */
	class ChangePrimaryKeyListener implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() != ItemEvent.SELECTED) {
				return;
			}
			setKeyValueStatus();
		}
	}

}
