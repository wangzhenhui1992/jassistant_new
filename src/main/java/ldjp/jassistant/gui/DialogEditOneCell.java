package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import ldjp.jassistant.base.PJDialogBase;
import ldjp.jassistant.base.PJEditorTextField;
import ldjp.jassistant.base.RollOverButton;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.ImageManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.UIUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 */
public class DialogEditOneCell extends PJDialogBase {
    private static final long serialVersionUID = 1L;

    ImageIcon iconLoadFromFile = ImageManager.createImageIcon(PJConst.IMAGE_IMPROTFILE);
	ImageIcon iconSaveToFile = ImageManager.createImageIcon(PJConst.IMAGE_EXPROTFILE);
	JPanel panelMain = new JPanel();
	BorderLayout borderLayoutMain = new BorderLayout();
	JToolBar toolBarTop = new JToolBar();
	JPanel panelCenter = new JPanel();
	JScrollPane scpAreaMain = new JScrollPane();
	BorderLayout borderLayoutEdit = new BorderLayout();
	RollOverButton btnLoadFromFile = new RollOverButton(iconLoadFromFile);
	RollOverButton btnSaveToFile = new RollOverButton(iconSaveToFile);
	JToolBar toolBarBottom = new JToolBar();
	JButton btnOK = new JButton();
	JButton btnCancel = new JButton();
	Component horizontalGlue = Box.createHorizontalGlue();
	ButtonActionListener buttonActionListener = new ButtonActionListener();
	static FileDialog fileDialog;
	JTextArea txtAreaEdit = null;
	PJEditorTextField txtFieldEdit = null;
	Class<?> type = null;
	int row = -1;
	int col = -1;

	public DialogEditOneCell(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogEditOneCell() {
		this(Main.getMDIMain(), PJConst.EMPTY, true);
	}

	void jbInit() throws Exception {
		panelMain.setLayout(borderLayoutMain);
		panelCenter.setLayout(borderLayoutEdit);
		toolBarTop.setFloatable(false);
		btnOK.setMaximumSize(new Dimension(45, 22));
		btnOK.setMinimumSize(new Dimension(45, 22));
		btnOK.setPreferredSize(new Dimension(45, 22));
		btnOK.setMargin(new Insets(0, 0, 0, 0));
		btnOK.setText(WordManager.getWord(WordKeyConsts.W0022));
		btnOK.setMnemonic(PJConst.MNEMONIC_O);
		btnOK.addActionListener(buttonActionListener);
		btnCancel.setMaximumSize(new Dimension(45, 22));
		btnCancel.setMinimumSize(new Dimension(45, 22));
		btnCancel.setPreferredSize(new Dimension(45, 22));
		btnCancel.setMargin(new Insets(0, 0, 0, 0));
		btnCancel.setText(WordManager.getWord(WordKeyConsts.W0023));
		btnCancel.setMnemonic(PJConst.MNEMONIC_C);
		btnCancel.addActionListener(buttonActionListener);
		btnCancel.setVerifyInputWhenFocusTarget(false);
		btnLoadFromFile.setPreferredSize(new Dimension(32, 32));
		btnLoadFromFile.setMaximumSize(new Dimension(27, 27));
		btnLoadFromFile.setMinimumSize(new Dimension(27, 27));
		btnLoadFromFile.setToolTipText(WordManager.getWord(WordKeyConsts.W0031));
		btnLoadFromFile.addActionListener(buttonActionListener);
		btnSaveToFile.setPreferredSize(new Dimension(32, 32));
		btnSaveToFile.setMaximumSize(new Dimension(27, 27));
		btnSaveToFile.setMinimumSize(new Dimension(27, 27));
		btnSaveToFile.setToolTipText(WordManager.getWord(WordKeyConsts.W0032));
		btnSaveToFile.addActionListener(buttonActionListener);
		toolBarTop.add(btnLoadFromFile, null);
		toolBarTop.add(btnSaveToFile, null);
		getContentPane().add(panelMain);
		panelMain.add(toolBarTop, BorderLayout.NORTH);
		panelMain.add(panelCenter, BorderLayout.CENTER);
		panelMain.add(toolBarBottom,  BorderLayout.SOUTH);
		toolBarBottom.setFloatable(false);
		toolBarBottom.add(btnOK, null);
		toolBarBottom.add(horizontalGlue, null);
		toolBarBottom.add(btnCancel, null);
	}

	/**
	 * override setVisible
	 */
	public void setVisible(boolean b) {
		if (b) {
			pack();
			initLocation(Main.getMDIMain());
		}
		super.setVisible(b);
	}

	/**
	 * init default value.
	 */
	public void initResources(int row, int col,
							String columnName, Class<?> type,
							String strValue, String beanType) {
		setTitle(WordManager.getWord(WordKeyConsts.W0033) + columnName);

		if (type == null) {
			type = Object.class;
		}

		this.type = type;
		this.row = row;
		this.col = col;

		if (type == String.class) {
			panelMain.setPreferredSize(new Dimension(350, 300));
			txtAreaEdit = new JTextArea();
			txtAreaEdit.setFont(UIUtil.getDefaultFont(UIUtil.GRID_FONT));
			panelCenter.add(scpAreaMain, BorderLayout.CENTER);
			scpAreaMain.getViewport().add(txtAreaEdit);
			txtAreaEdit.setText(strValue);
			txtAreaEdit.requestFocus();
			if (beanType.equals(PJConst.BEAN_TYPE_VIEW)) {
				txtAreaEdit.setEditable(false);
				btnOK.setEnabled(false);
			}
			btnLoadFromFile.setEnabled(false);
			btnSaveToFile.setEnabled(false);
		} else if (type == Object.class) {
			panelMain.setPreferredSize(new Dimension(300, 80));
			txtFieldEdit = new PJEditorTextField(null);
			panelCenter.add(txtFieldEdit, BorderLayout.CENTER);
			txtFieldEdit.setEditable(false);
			btnSaveToFile.setEnabled(true);
			if (beanType.equals(PJConst.BEAN_TYPE_VIEW)) {
				btnLoadFromFile.setEnabled(false);
			} else {
				btnSaveToFile.setEnabled(true);
			}
		} else {
			panelMain.setPreferredSize(new Dimension(250, 80));
			txtFieldEdit = new PJEditorTextField(type);
			panelCenter.add(txtFieldEdit, BorderLayout.CENTER);
			txtFieldEdit.setText(strValue);
			txtFieldEdit.requestFocus();
			if (beanType.equals(PJConst.BEAN_TYPE_VIEW)) {
				txtFieldEdit.setEditable(false);
				btnOK.setEnabled(false);
			}
			btnLoadFromFile.setEnabled(false);
			btnSaveToFile.setEnabled(false);
		}
	}

	/**
	 * when ok button clicked, process column value update
	 */
	void updateColumnValue() {
		if (type == String.class) {
			String areaText = txtAreaEdit.getText();
            fireParamTransferEvent(
                    new Object[] { String.valueOf(row), String.valueOf(col), areaText,
                            type }, PJConst.WINDOW_EDITONECOLUMN);
		} else if (type == Object.class) {
			String opText = txtFieldEdit.getText();
			if (!StringUtil.isEmpty(opText)) {
                fireParamTransferEvent(
                        new Object[] { String.valueOf(row), String.valueOf(col), opText,
                                type }, PJConst.WINDOW_EDITONECOLUMN);
			}
		} else {
			String fieldText = txtFieldEdit.getText();
            fireParamTransferEvent(
                    new Object[] { String.valueOf(row), String.valueOf(col), fieldText,
                            type }, PJConst.WINDOW_EDITONECOLUMN);
		}
	}

	/**
	 * load from file
	 */
	void loadFromFile() {
		if (fileDialog == null) {
			fileDialog = new FileDialog(Main.getMDIMain());
		}
		fileDialog.setMode(FileDialog.LOAD);
		fileDialog.setVisible(true);
		String file = fileDialog.getFile();
		if (file == null) {
			return;
		}
		String dir = fileDialog.getDirectory();

		txtFieldEdit.setText(WordManager.getWord(WordKeyConsts.W0034) + (dir + file));
	}

	/**
	 * save to file
	 */
	void saveToFile() {
		if (fileDialog == null) {
			fileDialog = new FileDialog(Main.getMDIMain());
		}
		fileDialog.setMode(FileDialog.SAVE);
		fileDialog.setVisible(true);
		String file = fileDialog.getFile();
		if (file == null) {
			return;
		}
		String dir = fileDialog.getDirectory();

		txtFieldEdit.setText(WordManager.getWord(WordKeyConsts.W0035) + (dir + file));
	}


    /** Event Handle Area **/
	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj == btnLoadFromFile) {
				loadFromFile();
			} else if (obj ==  btnSaveToFile) {
				saveToFile();
			} else if (obj == btnCancel) {
				dispose();
			} else if (obj == btnOK) {
				updateColumnValue();
				dispose();
			}
		}
	}

}
