package ldjp.jassistant.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import ldjp.jassistant.base.DBTableModel;
import ldjp.jassistant.base.PJDialogBase;
import ldjp.jassistant.base.PJEditorTextField;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 * Dialog save to
 */
public class DialogSaveResultData extends PJDialogBase {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    JPanel panelContent = new JPanel();
	Border borderMainEmpty;
	JPanel panelFormat = new JPanel();
	JPanel panelSaveTo = new JPanel();
	TitledBorder titledBorderFormat;
	JRadioButton rdoCommarDelim = new JRadioButton();
	JRadioButton rdoTabDelim = new JRadioButton();
	JRadioButton rdoHtml = new JRadioButton();
	JPanel panelButton = new JPanel();
	JButton btnOK = new JButton();
	JButton btnCancel = new JButton();
	JButton btnAbort = new JButton();
	JCheckBox chkIncludeHeader = new JCheckBox();
	TitledBorder titledBorderSaveTo;
	PJEditorTextField txtFilePath = new PJEditorTextField();
	JButton btnBrowseFile = new JButton();
	JProgressBar progressBarSave = new JProgressBar();
	JRadioButton rdoSaveToFile = new JRadioButton();
	JRadioButton rdoSaveToClipBoard = new JRadioButton();
	ButtonGroup btnGroupFormat = new ButtonGroup();
	ButtonGroup btnGroupSaveTo = new ButtonGroup();
	ButtonActionListener buttonActionListener = new ButtonActionListener();
	FileDialog fileDialog = null;
	DBTableModel tableModel;
	String sqlScript = null;
	ThreadDoInsert threadDoInsert = null;
	boolean shouldBreak = false;

	public DialogSaveResultData(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogSaveResultData() {
		this(Main.getMDIMain(), WordManager.getWord(WordKeyConsts.W0120), true);
	}

	void jbInit() throws Exception {
		borderMainEmpty = BorderFactory.createEmptyBorder(4,4,4,4);
        titledBorderFormat = new TitledBorder(BorderFactory.createEtchedBorder(
                Color.white, new Color(148, 145, 140)),
                WordManager.getWord(WordKeyConsts.W0068));
        titledBorderSaveTo = new TitledBorder(BorderFactory.createEtchedBorder(
                Color.white, new Color(148, 145, 140)),
                WordManager.getWord(WordKeyConsts.W0035));
		panelContent.setLayout(null);
		this.setResizable(false);
		panelContent.setBorder(borderMainEmpty);
		panelContent.setMaximumSize(new Dimension(315, 260));
		panelContent.setMinimumSize(new Dimension(315, 260));
		panelContent.setPreferredSize(new Dimension(315, 260));
		panelFormat.setBorder(titledBorderFormat);
		panelFormat.setMaximumSize(new Dimension(300, 80));
		panelFormat.setMinimumSize(new Dimension(300, 80));
		panelFormat.setPreferredSize(new Dimension(300, 80));
		panelFormat.setBounds(new Rectangle(16, 12, 193, 124));
		panelFormat.setLayout(null);
		rdoCommarDelim.setText(WordManager.getWord(WordKeyConsts.W0072));
		rdoCommarDelim.setBounds(new Rectangle(15, 26, 123, 14));
		rdoTabDelim.setBounds(new Rectangle(15, 48, 90, 14));
		rdoTabDelim.setText(WordManager.getWord(WordKeyConsts.W0073));
		rdoHtml.setText("Html");
		rdoHtml.setBounds(new Rectangle(110, 48, 58, 14));
		panelSaveTo.setBorder(titledBorderSaveTo);
		panelSaveTo.setBounds(new Rectangle(16, 142, 282, 80));
		panelSaveTo.setLayout(null);
		panelButton.setBounds(new Rectangle(215, 20, 86, 109));
		panelButton.setLayout(null);
		btnOK.setMargin(new Insets(0, 0, 0, 0));
		btnOK.setMnemonic(PJConst.MNEMONIC_O);
		btnOK.setText(WordManager.getWord(WordKeyConsts.W0022));
		btnOK.setBounds(new Rectangle(10, 4, 68, 25));
		btnCancel.setBounds(new Rectangle(9, 34, 68, 25));
		btnCancel.setMargin(new Insets(0, 0, 0, 0));
		btnCancel.setMnemonic(PJConst.MNEMONIC_C);
		btnCancel.setText(WordManager.getWord(WordKeyConsts.W0023));
		btnAbort.setText(WordManager.getWord(WordKeyConsts.W0011));
		btnAbort.setMargin(new Insets(0, 0, 0, 0));
		btnAbort.setMnemonic(PJConst.MNEMONIC_A);
		btnAbort.setBounds(new Rectangle(9, 65, 68, 25));
		btnAbort.setEnabled(false);
		chkIncludeHeader.setText(WordManager.getWord(WordKeyConsts.W0122));
		chkIncludeHeader.setBounds(new Rectangle(15, 89, 156, 25));
		chkIncludeHeader.setSelected(false);
		txtFilePath.setBounds(new Rectangle(58, 23, 185, 21));
		btnBrowseFile.setMargin(new Insets(0, 0, 0, 0));
		btnBrowseFile.setText("...");
		btnBrowseFile.setBounds(new Rectangle(247, 23, 24, 22));
		progressBarSave.setBounds(new Rectangle(18, 232, 279, 15));
		rdoSaveToFile.setText(WordManager.getWord(WordKeyConsts.W0069));
		rdoSaveToFile.setBounds(new Rectangle(15, 25, 42, 18));
		rdoSaveToClipBoard.setText(WordManager.getWord(WordKeyConsts.W0070));
		rdoSaveToClipBoard.setBounds(new Rectangle(15, 46, 76, 25));
		getContentPane().add(panelContent);
		panelFormat.add(rdoCommarDelim, null);
		panelFormat.add(rdoTabDelim, null);
		panelFormat.add(chkIncludeHeader, null);
		panelFormat.add(rdoHtml, null);
		panelContent.add(progressBarSave, null);
		panelContent.add(panelSaveTo, null);
		panelSaveTo.add(txtFilePath, null);
		panelSaveTo.add(btnBrowseFile, null);
		panelSaveTo.add(rdoSaveToFile, null);
		panelSaveTo.add(rdoSaveToClipBoard, null);
		panelContent.add(panelButton, null);
		panelButton.add(btnAbort, null);
		panelButton.add(btnOK, null);
		panelButton.add(btnCancel, null);
		panelContent.add(panelFormat, null);

		btnGroupFormat.add(rdoCommarDelim);
		btnGroupFormat.add(rdoTabDelim);
		btnGroupFormat.add(rdoHtml);
		rdoCommarDelim.setSelected(true);
		btnGroupSaveTo.add(rdoSaveToFile);
		btnGroupSaveTo.add(rdoSaveToClipBoard);
		rdoSaveToFile.setSelected(true);

		btnOK.addActionListener(buttonActionListener);
		btnCancel.addActionListener(buttonActionListener);
		btnAbort.addActionListener(buttonActionListener);
		btnBrowseFile.addActionListener(buttonActionListener);
	}

	public void setVisible(boolean b) {
		if (b) {
			initLocation(Main.getMDIMain());
		}
		super.setVisible(b);
	}

	/**
	 * get parameter from parent
	 *
	 */
	public void initResources(DBTableModel model, String lastSQL) {
		this.tableModel = model;
		this.sqlScript = lastSQL;
	}


	/**
	 * process save
	 *
	 */
	void processSave() {
		threadDoInsert = new ThreadDoInsert();
		threadDoInsert.start();
	}

	/**
	 * append one line
	 *
	 */
	void appendOneLine(PrintWriter out, StringBuffer sb, String oneLine) {
		if (out != null) {
			out.println(oneLine);
		} else if (sb != null) {
			sb.append(oneLine + "\n");
		}
	}

	/**
	 * get one row data
	 */
    String getOneLine(Vector<String> columnNameVector, Vector<Object> dataVector,
            Vector<Class<?>> typeVector, Vector<Integer> sizeVector, boolean isHeader) {
		if (rdoCommarDelim.isSelected()) {
			if (isHeader) {
				return StringUtil.joinList(columnNameVector, ',');
			} else {
				return StringUtil.joinList(dataVector, typeVector, ',', true);
			}
		} else if (rdoTabDelim.isSelected()) {
			if (isHeader) {
				return StringUtil.joinList(columnNameVector, '\t');
			} else {
				return StringUtil.joinList(dataVector, typeVector, '\t', false);
			}
		} else if (rdoHtml.isSelected()) {
            return StringUtil.joinHtmlString(columnNameVector, dataVector, typeVector,
                    sizeVector, isHeader);
		}
		return PJConst.EMPTY;
	}

	/**
	 * select save to file
	 *
	 */
	void selectSaveToFile() {
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

		txtFilePath.setText(dir + file);
	}


	/**
	 * use a thread to do save
	 *
	 */
	class ThreadDoInsert extends Thread {
		public void run() {
			btnOK.setEnabled(false);
			btnAbort.setEnabled(true);

			try {
				int totalSize = tableModel.getRowCount();
				progressBarSave.setMaximum(totalSize);

				Vector<String> columnNameVector = tableModel.getRealColumnName();
				Vector<Class<?>> typeVector = tableModel.getColumnJavaType();
				Vector<Integer> sizeVector = tableModel.getSizeVector();
				// Vector<Boolean> keyVector = tableModel.getKeyVector();

				PrintWriter out = null;
				StringBuffer sb = null;
				if (rdoSaveToFile.isSelected()) {
                    out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
                            txtFilePath.getText())));
				} else {
					sb = new StringBuffer();
				}

				int totalTableWidth = 0;
				for (int i = 0; i < sizeVector.size(); i++) {
                    totalTableWidth += StringUtil.getTDWidthSize(typeVector.get(i),
                            sizeVector.get(i).intValue());
				}

                if (rdoHtml.isSelected()) {
                    appendOneLine(out, sb, "<html><body>");
                    appendOneLine(out, sb, "<h1 align=center>" + sqlScript + "</h1>");
                    appendOneLine(out, sb, "<table cellspacing=1 width="
                            + totalTableWidth + " border=1>");
                }

				if (chkIncludeHeader.isSelected()) {
					String headerLine = getOneLine(columnNameVector, null, typeVector, sizeVector, true);
					appendOneLine(out, sb, headerLine);
				}

				int count = 0;
				for (int i = 0; i < totalSize; i++) {
					Vector<Object> oneRow = tableModel.getRowData(i);
                    String oneLine = getOneLine(columnNameVector, oneRow, typeVector,
                            sizeVector, false);
					appendOneLine(out, sb, oneLine);
					progressBarSave.setValue(++count);

					if (shouldBreak) {
						return;
					}
				}

				if (rdoHtml.isSelected()) {
					appendOneLine(out, sb, "</table></body></html>");
				}

				if (out != null) {
					out.close();
				} else if (sb != null) {
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					StringSelection stringSelection = new StringSelection(sb.toString());
					clipboard.setContents(stringSelection, stringSelection);
				}

				dispose();
			} catch (IOException e) {
				try {
                    SwingUtilities.invokeAndWait(new InvokeAndWaitDoThread("MCSTC002E",
                            e.getMessage()));
					return;
				} catch (Exception ite) {}
			} finally {
				progressBarSave.setValue(0);
				btnOK.setEnabled(true);
				btnAbort.setEnabled(false);
				shouldBreak = false;
			}
		}
	}

 	/**
 	 * avoid thread volite, use it show message
 	 *
 	 */
	class InvokeAndWaitDoThread implements Runnable {
		String messageID;
		String message;

		public InvokeAndWaitDoThread(String messageID, String message) {
			this.messageID = messageID;
			this.message = message;
		}

		public void run() {
			MessageManager.showMessage(messageID, message);
		}
	}

    /** Event Handle Area **/

	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj == btnOK) {
				processSave();
			} else if (obj ==  btnCancel) {
				shouldBreak = true;
				dispose();
			} else if (obj == btnAbort) {
				shouldBreak = true;
			} else if (obj == btnBrowseFile) {
				selectSaveToFile();
			}
		}
	}
}
