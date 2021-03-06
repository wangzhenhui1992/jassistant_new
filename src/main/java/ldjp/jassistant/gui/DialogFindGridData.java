package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ldjp.jassistant.base.PJDialogBase;
import ldjp.jassistant.base.PJEditorTextField;
import ldjp.jassistant.base.PJTableListModel;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.UIUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 * find grid data
 */
public class DialogFindGridData extends PJDialogBase {
	/**
     *
     */
    private static final long serialVersionUID = 1L;


    JPanel panelMain = new JPanel();
	Border borderMainEmpty;
	GridLayout gridLayoutMain = new GridLayout();
	JPanel panelValueInput = new JPanel();
	JPanel panelColumnList = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JLabel lblColumn = new JLabel();
	JScrollPane scpColumnList = new JScrollPane();
	JList<Object> listColumn = new JList<Object>();
	BorderLayout borderLayout2 = new BorderLayout();
	JLabel lblValue = new JLabel();
	JPanel panelValueCenter = new JPanel();
	PJEditorTextField txtValue = new PJEditorTextField(null);
	JButton btnAdd = new JButton();
	JButton btnRemove = new JButton();
	JScrollPane scpValues = new JScrollPane();
	JList<Object> listValues = new JList<Object>();
	JCheckBox chkCaseInsensive = new JCheckBox();
	JCheckBox chkPartialMatch = new JCheckBox();
	JButton btnOK = new JButton();
	JButton btnCancel = new JButton();
	PJTableListModel listValueModel = new PJTableListModel(new Vector<String>());

	public DialogFindGridData(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogFindGridData() {
		this(Main.getMDIMain(), WordManager.getWord(WordKeyConsts.W0054), true);
	}

	void jbInit() throws Exception {
		borderMainEmpty = BorderFactory.createEmptyBorder(4, 4, 4, 0);
		this.setResizable(false);
		panelMain.setPreferredSize(new Dimension(420, 300));
		panelMain.setLayout(gridLayoutMain);
		panelMain.setBorder(borderMainEmpty);
		gridLayoutMain.setColumns(2);
		gridLayoutMain.setHgap(5);
		panelColumnList.setLayout(borderLayout1);
		panelValueInput.setLayout(borderLayout2);
		lblColumn.setText(WordManager.getWord(WordKeyConsts.W0055));
		lblValue.setText(WordManager.getWord(WordKeyConsts.W0056));
		panelValueCenter.setLayout(null);
		borderLayout1.setVgap(3);
		borderLayout2.setVgap(3);
		txtValue.setBounds(new Rectangle(0, 0, 193, 21));
		txtValue.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				setAddButtonStatus();
			}
		});
		btnAdd.setMargin(new Insets(0, 0, 0, 0));
		btnAdd.setText(WordManager.getWord(WordKeyConsts.W0057));
		btnAdd.setMnemonic(PJConst.MNEMONIC_A);
		btnAdd.setEnabled(false);
		btnAdd.setBounds(new Rectangle(0, 25, 54, 21));
		btnAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAdd_actionPerformed(e);
			}
		});
		btnRemove.setBounds(new Rectangle(57, 25, 54, 21));
		btnRemove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRemove_actionPerformed(e);
			}
		});
		btnRemove.setEnabled(false);
		btnRemove.setText(WordManager.getWord(WordKeyConsts.W0058));
		btnRemove.setMnemonic(PJConst.MNEMONIC_R);
		btnRemove.setMargin(new Insets(0, 0, 0, 0));
		scpValues.setBounds(new Rectangle(0, 52, 193, 133));
		chkCaseInsensive.setText(WordManager.getWord(WordKeyConsts.W0059));
		chkCaseInsensive.setSelected(true);
		chkCaseInsensive.setBounds(new Rectangle(3, 189, 189, 20));
		chkPartialMatch.setText(WordManager.getWord(WordKeyConsts.W0060));
		chkPartialMatch.setSelected(true);
		chkPartialMatch.setBounds(new Rectangle(3, 208, 187, 18));
		btnOK.setMargin(new Insets(0, 0, 0, 0));
		btnOK.setText(WordManager.getWord(WordKeyConsts.W0022));
		btnOK.setMnemonic(PJConst.MNEMONIC_O);
		btnOK.setBounds(new Rectangle(60, 247, 56, 24));
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOK_actionPerformed(e);
			}
		});
		btnCancel.setMargin(new Insets(0, 0, 0, 0));
		btnCancel.setText(WordManager.getWord(WordKeyConsts.W0023));
		btnCancel.setMnemonic(PJConst.MNEMONIC_C);
		btnCancel.setBounds(new Rectangle(127, 246, 57, 25));
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel_actionPerformed(e);
			}
		});
		panelColumnList.setPreferredSize(new Dimension(200, 300));
		panelValueInput.setPreferredSize(new Dimension(200, 300));
		getContentPane().add(panelMain);
		panelMain.add(panelColumnList, null);
		panelMain.add(panelValueInput, null);
		panelValueInput.add(lblValue,  BorderLayout.NORTH);
		panelValueInput.add(panelValueCenter, BorderLayout.CENTER);
		panelValueCenter.add(btnAdd, null);
		panelValueCenter.add(btnRemove, null);
		panelValueCenter.add(txtValue, null);
		panelValueCenter.add(scpValues, null);
		panelValueCenter.add(chkCaseInsensive, null);
		panelValueCenter.add(chkPartialMatch, null);
		panelValueCenter.add(btnOK, null);
		scpValues.getViewport().add(listValues, null);
		listValues.setFont(UIUtil.getDefaultFont(UIUtil.GRID_FONT));
		listValues.setModel(listValueModel);
		listValues.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				setRemoveButtonStatus();
			}
		});
		panelColumnList.add(lblColumn, BorderLayout.NORTH);
		panelColumnList.add(scpColumnList, BorderLayout.CENTER);
		scpColumnList.getViewport().add(listColumn, null);
		listColumn.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (listColumn.getSelectedValue() != null) {
					txtValue.requestFocus();
				}
				setAddButtonStatus();
			}
		});
		panelValueCenter.add(btnCancel, null);
	}

	public void setVisible(boolean b) {
		if (b) {
			initLocation(Main.getMDIMain());
		}
		super.setVisible(b);
	}

	/**
	 * init columns
	 */
	void initResources(Vector<String> columnVector) {
		listColumn.setListData(columnVector);

		if (columnVector != null && !columnVector.isEmpty()) {
			listColumn.setSelectedIndex(0);
		}
	}

	/**
	 * set add button status
	 */
	void setAddButtonStatus() {
		if (!txtValue.getText().equals(PJConst.EMPTY)) {
			if (listColumn.getSelectedValue() != null) {
				btnAdd.setEnabled(true);
			} else {
				btnAdd.setEnabled(false);
			}
		} else {
			btnAdd.setEnabled(false);
		}
	}

	/**
	 * set remove button status
	 */
	void setRemoveButtonStatus() {
		if (listValues.getSelectedValue() != null) {
			btnRemove.setEnabled(true);
		} else {
			btnRemove.setEnabled(false);
		}
	}

	/**
	 * add value to list
	 */
	void btnAdd_actionPerformed(ActionEvent e) {
		String selectedColumn = (String) listColumn.getSelectedValue();
		String textValue = txtValue.getText();

		if (selectedColumn != null && !textValue.equals(PJConst.EMPTY)) {
			listValueModel.add(selectedColumn + "=" + textValue);
			txtValue.setText(PJConst.EMPTY);
			txtValue.requestFocus();
		}
	}

	/**
	 * remove added value
	 */
	void btnRemove_actionPerformed(ActionEvent e) {
		String selectedValue = (String) listValues.getSelectedValue();

		if (selectedValue != null) {
			listValueModel.remove(selectedValue);
			listValues.clearSelection();
		}
	}

	/**
	 * process find grid data
	 */
	void btnOK_actionPerformed(ActionEvent e) {
		dispose();

		Vector<String> findValue = (Vector<String>) listValueModel.getDataSet();
		if (findValue != null && !findValue.isEmpty()) {
			Object[] param = new Object[]{
							findValue,
							new Boolean(chkCaseInsensive.isSelected()),
							new Boolean(chkPartialMatch.isSelected())
			};
			fireParamTransferEvent(param, PJConst.WINDOW_FINDGRIDDATA);
		}
	}

	/**
	 * cancel find data
	 */
	void btnCancel_actionPerformed(ActionEvent e) {
		dispose();
	}

}
