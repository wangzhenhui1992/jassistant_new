package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ldjp.jassistant.base.PJDialogBase;
import ldjp.jassistant.base.PJTableListModel;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.MessageKeyConsts;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.PropertyManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 */
public class DialogConfigConnection extends PJDialogBase {
    private static final long serialVersionUID = 1L;


    JPanel panelMain = new JPanel();
	BorderLayout mainBorderLayout = new BorderLayout();
	JPanel panelMainNorth = new JPanel();
	JPanel panelMainWest = new JPanel();
	JPanel panelMainCenter = new JPanel();
	JPanel panelMainEast = new JPanel();
	JPanel panelMainSouth = new JPanel();
	JPanel panelCenterLeft = new JPanel();
	JPanel panelCenterRight = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JScrollPane scpConnectionListNames = new JScrollPane();
	JList<Object> lstConnectionNames = new JList<Object>();
	JPanel panelCenterMiddle = new JPanel();
	GridLayout gridLayout1 = new GridLayout();
	JPanel panelCenterMiddleTop = new JPanel();
	JPanel panelCenterMiddleBottom = new JPanel();
	JPanel panelCenterMiddleCenter = new JPanel();
	GridLayout gridLayout2 = new GridLayout();
	JButton btnEdit = new JButton();
	JButton btnAdd = new JButton();
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel panelCenterRightMain = new JPanel();
	GridLayout gridLayout3 = new GridLayout();
	JLabel lblConnectionName = new JLabel();
	JLabel lblDriverName = new JLabel();
	JComboBox<Object> cmbJdbcDriver = new JComboBox<Object>();
	JLabel lblConnectionURL = new JLabel();
	JComboBox<Object> cmbConnectionURL = new JComboBox<Object>();
	JLabel lblBlank = new JLabel();
	JPanel panelCenterRightBottom = new JPanel();
	GridLayout gridLayout4 = new GridLayout();
	JButton btnOK = new JButton();
	JButton btnCancel = new JButton();
	JTextField txtConnectionName = new JTextField();
	BorderLayout borderLayout3 = new BorderLayout();
	JButton btnDelete = new JButton();
	JLabel lblConnectionNames = new JLabel();
	TitledBorder titledBorder1;
	String currentConnectionName = null;

    public DialogConfigConnection(Frame frame, String title, String connectionName,
            boolean modal) {
		super(frame, title, modal);
		this.currentConnectionName = connectionName;
		try {
			jbInit();
			initConnectionNames();
			initConnectionDrivers();
			initConnectionURLs();
			initDefaultValue();
			pack();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogConfigConnection(String connectionName) {
		this(Main.getMDIMain(), WordManager.getWord(WordKeyConsts.W0017), connectionName, true);
	}

	public void setVisible(boolean b) {
		if (b) {
			initLocation(Main.getMDIMain());
		}
		super.setVisible(b);
	}

	void jbInit() throws Exception {
		titledBorder1 = new TitledBorder(PJConst.EMPTY);
		this.setModal(true);
		this.setResizable(false);
		this.setTitle(WordManager.getWord(WordKeyConsts.W0017));
		this.setSize(new Dimension(500, 350));
		panelMain.setLayout(mainBorderLayout);
		mainBorderLayout.setVgap(5);
		panelMainCenter.setLayout(borderLayout3);
		panelCenterLeft.setLayout(borderLayout1);
		panelCenterMiddle.setMinimumSize(new Dimension(80, 100));
		panelCenterMiddle.setPreferredSize(new Dimension(80, 300));
		panelCenterMiddle.setLayout(gridLayout1);
		panelCenterLeft.setPreferredSize(new Dimension(150, 300));
		panelCenterRight.setMinimumSize(new Dimension(260, 250));
		panelCenterRight.setPreferredSize(new Dimension(260, 250));
		panelCenterRight.setLayout(borderLayout2);
		panelMainCenter.setBorder(titledBorder1);
		panelMainCenter.setMinimumSize(new Dimension(100, 225));
		panelMainCenter.setPreferredSize(new Dimension(470, 300));
		gridLayout1.setRows(3);
		gridLayout1.setColumns(1);
		panelCenterMiddleCenter.setLayout(gridLayout2);
		btnEdit.setFont(new java.awt.Font(Font.MONOSPACED, 1, 12));
		btnEdit.setPreferredSize(new Dimension(60, 20));
		btnEdit.setMargin(new Insets(0, 0, 0, 0));
		btnEdit.setText(WordManager.getWord(WordKeyConsts.W0018));
		btnEdit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEdit_actionPerformed(e);
			}
		});
		gridLayout2.setRows(3);
		gridLayout2.setColumns(1);
		gridLayout2.setVgap(2);
		btnAdd.setFont(new java.awt.Font(Font.MONOSPACED, 1, 12));
		btnAdd.setPreferredSize(new Dimension(60, 20));
		btnAdd.setMargin(new Insets(0, 0, 0, 0));
		btnAdd.setText(WordManager.getWord(WordKeyConsts.W0019));
		btnAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAdd_actionPerformed(e);
			}
		});
		scpConnectionListNames.setBorder(BorderFactory.createEtchedBorder());
		scpConnectionListNames.setPreferredSize(new Dimension(120, 150));
		panelMainNorth.setPreferredSize(new Dimension(350, 10));
		panelMainSouth.setPreferredSize(new Dimension(350, 10));
		panelMainWest.setPreferredSize(new Dimension(15, 200));
		panelMainEast.setPreferredSize(new Dimension(15, 200));
		panelCenterMiddleTop.setPreferredSize(new Dimension(60, 70));
		panelCenterMiddleBottom.setPreferredSize(new Dimension(60, 70));
		panelCenterRightMain.setLayout(gridLayout3);
		gridLayout3.setRows(8);
		gridLayout3.setColumns(1);
		gridLayout3.setVgap(10);
		lblConnectionName.setText(WordManager.getWord(WordKeyConsts.W0020));
		lblConnectionURL.setToolTipText(PJConst.EMPTY);
		lblConnectionURL.setText(WordManager.getWord(WordKeyConsts.W0021));
		panelCenterRightBottom.setLayout(gridLayout4);
		gridLayout4.setColumns(3);
		gridLayout4.setHgap(10);
		btnOK.setMargin(new Insets(0, 0, 0, 0));
		btnOK.setText(WordManager.getWord(WordKeyConsts.W0022));
		btnOK.setMnemonic(PJConst.MNEMONIC_O);
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOK_actionPerformed(e);
			}
		});
		btnCancel.setMargin(new Insets(0, 0, 0, 0));
		btnCancel.setText(WordManager.getWord(WordKeyConsts.W0023));
		btnCancel.setMnemonic(PJConst.MNEMONIC_C);
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel_actionPerformed(e);
			}
		});
		borderLayout3.setHgap(6);
		btnDelete.setToolTipText(WordManager.getWord(WordKeyConsts.W0024));
		btnDelete.setMargin(new Insets(0, 0, 0, 0));
		btnDelete.setText(WordManager.getWord(WordKeyConsts.W0025));
		btnDelete.setMnemonic(PJConst.MNEMONIC_D);
		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDelete_actionPerformed(e);
			}
		});
		lblConnectionNames.setText(WordManager.getWord(WordKeyConsts.W0026));
		lstConnectionNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstConnectionNames.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				lstConnectionNames_mouseClicked(e);
			}
		});
		lstConnectionNames.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				lstConnectionNames_SelectChanged(e);
			}
		});
		lstConnectionNames.setBorder(BorderFactory.createEtchedBorder());
		lstConnectionNames.setModel(connectionNameListModel);
		lblDriverName.setText(WordManager.getWord(WordKeyConsts.W0027));
		cmbJdbcDriver.setEditable(true);
		cmbConnectionURL.setEditable(true);
		getContentPane().add(panelMain);
		panelMain.add(panelMainNorth,  BorderLayout.NORTH);
		panelMain.add(panelMainWest, BorderLayout.WEST);
		panelMain.add(panelMainCenter, BorderLayout.CENTER);
		panelMain.add(panelMainEast, BorderLayout.EAST);
		panelMain.add(panelMainSouth, BorderLayout.SOUTH);
		panelMainCenter.add(panelCenterLeft, BorderLayout.WEST);
		panelMainCenter.add(panelCenterMiddle, BorderLayout.CENTER);
		panelCenterMiddle.add(panelCenterMiddleTop, null);
		panelCenterMiddle.add(panelCenterMiddleCenter, null);
		panelCenterMiddleCenter.add(btnEdit, null);
		panelCenterMiddleCenter.add(btnDelete, null);
		panelCenterMiddleCenter.add(btnAdd, null);
		panelCenterMiddle.add(panelCenterMiddleBottom, null);
		panelMainCenter.add(panelCenterRight, BorderLayout.EAST);
		panelCenterRight.add(panelCenterRightMain, BorderLayout.CENTER);
		panelCenterRightMain.add(lblConnectionName, null);
		panelCenterRightMain.add(txtConnectionName, null);
		panelCenterRightMain.add(lblDriverName, null);
		panelCenterRightMain.add(cmbJdbcDriver, null);
		panelCenterRightMain.add(lblConnectionURL, null);
		panelCenterRightMain.add(cmbConnectionURL, null);
		panelCenterRightMain.add(lblBlank, null);
		panelCenterRightMain.add(panelCenterRightBottom, null);
		panelCenterRightBottom.add(btnOK, null);
		panelCenterRightBottom.add(btnCancel, null);
		panelCenterLeft.add(scpConnectionListNames, BorderLayout.CENTER);
		panelCenterLeft.add(lblConnectionNames, BorderLayout.NORTH);
		scpConnectionListNames.getViewport().add(lstConnectionNames, null);
	}

    /** Business Area **/
	ArrayList<String> connectionNameLists = new ArrayList<String>();
	HashMap<String,Object> connectionNameMap = new HashMap<String,Object>();
	HashSet<String> connectionDriverSet = new HashSet<String>();
	HashSet<String> connectionURLSet = new HashSet<String>();
	PJTableListModel connectionNameListModel = new PJTableListModel();

	/**
	 * init database config name
	 */
    private void initConnectionNames() {
        ArrayList<String> connectionNameDriverURLLists = PropertyManager.getProperty(
                PJConst.DATABASE_NAMES, true, true);

        for (int i = 0; i < connectionNameDriverURLLists.size(); i++) {
            String oneConnection = (String) connectionNameDriverURLLists.get(i);
            String connectionName = PJConst.EMPTY;
            String connectionDriver = PJConst.EMPTY;
            String connectionURL = PJConst.EMPTY;

            if (!StringUtil.isEmpty(oneConnection)) {
                int indexName = oneConnection.indexOf("/");
                if (indexName > 0) {
                    connectionName = oneConnection.substring(0, indexName);

                    int indexDriver = oneConnection.indexOf("/", indexName + 1);
                    if (indexDriver > 0) {
                        connectionDriver = oneConnection.substring(indexName + 1,
                                indexDriver);

                        connectionURL = oneConnection.substring(indexDriver + 1);
                    }

                    connectionNameMap.put(connectionName, new String[] {
                            connectionDriver, connectionURL });
                    connectionNameLists.add(connectionName);
                    connectionDriverSet.add(connectionDriver);
                    connectionURLSet.add(connectionURL);
                }
            }
        }

        for (int i = 0; i < PJConst.DEFAULT_DATABASE_DRIVER.length; i++) {
            connectionDriverSet.add(PJConst.DEFAULT_DATABASE_DRIVER[i]);
        }

        for (int i = 0; i < PJConst.DEFAULT_DATABASE_URL.length; i++) {
            connectionURLSet.add(PJConst.DEFAULT_DATABASE_URL[i]);
        }

        connectionNameListModel.setDataSet(connectionNameLists);
    }


	/**
	 * refresh connection names
	 */
	private void refreshConnectionNames() {

		if (!connectionNameLists.isEmpty()) {
			connectionNameLists.clear();
		}
		initConnectionNames();

	}

	/**
	 * init connection drivers combobox
	 */
	private void initConnectionDrivers() {
		cmbJdbcDriver.setModel(new DefaultComboBoxModel<Object>(connectionDriverSet.toArray()));
	}

	/**
	 * init connection urls combobox
	 */
	private void initConnectionURLs() {
		cmbConnectionURL.setModel(new DefaultComboBoxModel<Object>(connectionURLSet.toArray()));
	}

	/**
	 * init default value
	 */
	private void initDefaultValue() {
		setComponentValue(currentConnectionName);
	}

	/**
	 * init value by the given connection name
	 */
	private void setComponentValue(String connectionName) {
		if (!StringUtil.isEmpty(connectionName)) {
			lstConnectionNames.setSelectedValue(connectionName, true);
			txtConnectionName.setText(connectionName);

			String[] connectionDriverURLs = (String[]) connectionNameMap.get(connectionName);
			if (connectionDriverURLs != null && connectionDriverURLs.length > 1) {
				cmbJdbcDriver.setSelectedItem(connectionDriverURLs[0]);
				cmbConnectionURL.setSelectedItem(connectionDriverURLs[1]);
			} else {
				cmbJdbcDriver.setSelectedItem(PJConst.EMPTY);
				cmbConnectionURL.setSelectedItem(PJConst.EMPTY);
			}
		}
	}


	/**
	 * ok process
	 */
	private void processButtonOK() {
		String connectionName = txtConnectionName.getText();
		String connectionDriver = (String) cmbJdbcDriver.getSelectedItem();
		String connectionURL = (String) cmbConnectionURL.getSelectedItem();

        if (prsUpdConNms(connectionName, connectionDriver, connectionURL)) {
            fireParamTransferEvent(txtConnectionName.getText(),
                    PJConst.WINDOW_CONFIGCONNECTION);
            dispose();
        }
	}

	/**
	 * >> button process
	 */
	private void processButtonEdit() {
		int selectedIndex = lstConnectionNames.getSelectedIndex();

		if (selectedIndex >=0 ) {
			String connectionName = (String) connectionNameLists.get(selectedIndex);
			setComponentValue(connectionName);
		}
	}

	/**
	 * delete button process
	 */
	private void processButtonDelete() {
		int selectedIndex = lstConnectionNames.getSelectedIndex();

		if (selectedIndex >=0 ) {
			connectionNameLists.remove(selectedIndex);
			processUpdateConnectionNames();
			refreshConnectionNames();
			lstConnectionNames.clearSelection();
		}
	}


	/**
	 * << button process
	 */
	private void processButtonAdd() {
		String connectionName = txtConnectionName.getText();
		String connectionDriver = (String) cmbJdbcDriver.getSelectedItem();
		String connectionURL = (String) cmbConnectionURL.getSelectedItem();

		if (!prsUpdConNms(connectionName, connectionDriver, connectionURL)) {
			return;
		}

		refreshConnectionNames();
		processClear();
	}

	/**
	 * update config
	 */
	private boolean prsUpdConNms(  String connectionName,
												String connectionDriver,
												String connectionURL) {
		if (StringUtil.isEmpty(connectionName)) {
            MessageManager.showMessage(MessageKeyConsts.MCSTC001E,
                    WordManager.getWord(WordKeyConsts.W0028));
			txtConnectionName.requestFocus();
			return false;
		}
		if (connectionName.indexOf("/") >= 0) {
            MessageManager.showMessage("MCSTC006E",
                    new String[] { WordManager.getWord(WordKeyConsts.W0028), "/" });
			txtConnectionName.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(connectionDriver)) {
            MessageManager.showMessage(MessageKeyConsts.MCSTC001E,
                    WordManager.getWord(WordKeyConsts.W0029));
			cmbJdbcDriver.requestFocus();
			return false;
		}
		if (connectionDriver.indexOf("/") >= 0) {
            MessageManager.showMessage("MCSTC006E",
                    new String[] { WordManager.getWord(WordKeyConsts.W0029), "/" });
			cmbJdbcDriver.requestFocus();
			return false;
		}
		if (StringUtil.isEmpty(connectionURL)) {
            MessageManager.showMessage(MessageKeyConsts.MCSTC001E,
                    WordManager.getWord(WordKeyConsts.W0030));
			cmbConnectionURL.requestFocus();
			return false;
		}

		connectionNameMap.put(connectionName, new String[]{connectionDriver, connectionURL});
		if (!connectionNameLists.contains(connectionName)) {
			connectionNameLists.add(0, connectionName);
		}

		processUpdateConnectionNames();

		return true;
	}


	/**
	 * update properties in memory
	 */
	private void processUpdateConnectionNames() {
		PropertyManager.removeProperty(PJConst.DATABASE_NAMES, true);
		for (int i=0; i<connectionNameLists.size(); i++) {
			String connectionName = (String) connectionNameLists.get(i);
			String[] connectionDriverURLs = (String[]) connectionNameMap.get(connectionName);
			if (connectionDriverURLs != null && connectionDriverURLs.length > 1) {
				String connectionDriver = (String) connectionDriverURLs[0];
				String connectionURL = (String) connectionDriverURLs[1];
				String oneCompletelyName = connectionName + "/" + connectionDriver + "/" + connectionURL;
				PropertyManager.setProperty(PJConst.DATABASE_NAMES +"[" + i + "]", oneCompletelyName);
			}
		}
	}

	/**
	 * clear
	 */
	private void processClear() {
		lstConnectionNames.clearSelection();
		txtConnectionName.setText(PJConst.EMPTY);
		cmbJdbcDriver.setSelectedItem(PJConst.EMPTY);
		cmbConnectionURL.setSelectedItem(PJConst.EMPTY);
	}

	/** Event Handle Area**/

	void btnCancel_actionPerformed(ActionEvent e) {
		dispose();
	}

	void btnOK_actionPerformed(ActionEvent e) {
		processButtonOK();
	}

	void btnEdit_actionPerformed(ActionEvent e) {
		processButtonEdit();
	}

	void btnDelete_actionPerformed(ActionEvent e) {
		processButtonDelete();
	}

	void btnAdd_actionPerformed(ActionEvent e) {
		processButtonAdd();
	}

	void lstConnectionNames_mouseClicked(MouseEvent e) {
		if (e.getClickCount() >= 2 && lstConnectionNames.getSelectedIndex() >= 0) {
			processButtonEdit();
		}
	}

	void lstConnectionNames_SelectChanged(ListSelectionEvent e) {
		int selectedIndex = lstConnectionNames.getSelectedIndex();
		if (selectedIndex >= 0) {
			btnDelete.setEnabled(true);
			btnEdit.setEnabled(true);
		} else {
			btnDelete.setEnabled(false);
			btnEdit.setEnabled(false);
		}
	}
}
