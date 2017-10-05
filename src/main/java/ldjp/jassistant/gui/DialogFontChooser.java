package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ldjp.jassistant.base.PJDialogBase;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.MessageKeyConsts;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.PropertyManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

public class DialogFontChooser extends PJDialogBase {
    private static final long serialVersionUID = 1L;

    JPanel panelMain = new JPanel();
	JTextField txtFontName = new JTextField();
	JScrollPane scpFontName = new JScrollPane();
	JList listFontName = new JList();
	JTextField txtFontStyle = new JTextField();
	JTextField txtFontSize = new JTextField();
	JScrollPane scpFontStyle = new JScrollPane();
	JScrollPane scpFontSize = new JScrollPane();
	JList listFontStyle = new JList();
	JList listFontSize = new JList();
	JLabel lblFontName = new JLabel();
	JLabel lblFontStyle = new JLabel();
	JLabel lblFontSize = new JLabel();
	JPanel panelSample = new JPanel();
    TitledBorder titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(
            Color.white, new Color(148, 145, 140)), WordManager.getWord(WordKeyConsts.W0065));
	JButton btnOK = new JButton();
	JButton btnCancel = new JButton();
	BorderLayout borderLayoutSample = new BorderLayout();
	JLabel lblSample = new JLabel();
    ChooseValueListSelectionListener listSelectionListener = new ChooseValueListSelectionListener();
	String sampleText = "AaBbCc";
	int flag = 0;
	String[] fontStyles = {
			"Plain", "Bold",
			"Italic", "Bold Italic"
		};


	public DialogFontChooser(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
			initUI();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogFontChooser() {
		this(Main.getMDIMain(), WordManager.getWord(WordKeyConsts.W0061), true);
	}

	public void setOpFlag(int opFlag) {
		this.flag = opFlag;
		initOrignal();
	}

	void jbInit() throws Exception {
		this.setSize(new Dimension(365, 312));
		this.setResizable(false);
		panelMain.setLayout(null);
		txtFontName.setText(PJConst.EMPTY);
		txtFontName.setBounds(new Rectangle(20, 40, 142, 20));
		scpFontName.setBounds(new Rectangle(20, 64, 142, 133));
		txtFontStyle.setBounds(new Rectangle(167, 40, 92, 20));
		txtFontStyle.setText(PJConst.EMPTY);
		txtFontSize.setText(PJConst.EMPTY);
		txtFontSize.setBounds(new Rectangle(266, 40, 66, 20));
		scpFontStyle.setBounds(new Rectangle(167, 64, 93, 134));
		scpFontSize.setBounds(new Rectangle(266, 64, 67, 134));
		lblFontName.setText(WordManager.getWord(WordKeyConsts.W0062));
		lblFontName.setBounds(new Rectangle(19, 16, 132, 22));
		lblFontStyle.setText(WordManager.getWord(WordKeyConsts.W0063));
		lblFontStyle.setBounds(new Rectangle(157, 16, 120, 23));
		lblFontSize.setText(WordManager.getWord(WordKeyConsts.W0064));
		lblFontSize.setBounds(new Rectangle(282, 16, 50, 22));
		panelSample.setBorder(titledBorder1);
		panelSample.setBounds(new Rectangle(19, 212, 241, 70));
		panelSample.setLayout(borderLayoutSample);
		btnOK.setMargin(new Insets(0, 0, 0, 0));
		btnOK.setText(WordManager.getWord(WordKeyConsts.W0022));
		btnOK.setMnemonic(PJConst.MNEMONIC_O);
		btnOK.setBounds(new Rectangle(268, 224, 65, 23));
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
		btnCancel.setBounds(new Rectangle(268, 252, 66, 24));
		lblSample.setHorizontalAlignment(SwingConstants.CENTER);
		lblSample.setHorizontalTextPosition(SwingConstants.CENTER);
		getContentPane().add(panelMain);
		panelMain.add(txtFontName, null);
		panelMain.add(scpFontName, null);
		panelMain.add(txtFontStyle, null);
		panelMain.add(scpFontStyle, null);
		scpFontStyle.getViewport().add(listFontStyle, null);
		listFontStyle.addListSelectionListener(listSelectionListener);
		panelMain.add(lblFontName, null);
		panelMain.add(lblFontStyle, null);
		panelMain.add(lblFontSize, null);
		panelMain.add(scpFontSize, null);
		scpFontSize.getViewport().add(listFontSize, null);
		listFontSize.addListSelectionListener(listSelectionListener);
		panelMain.add(panelSample, null);
		panelMain.add(txtFontSize, null);
		panelMain.add(btnOK, null);
		panelMain.add(btnCancel, null);
		scpFontName.getViewport().add(listFontName, null);
		listFontName.addListSelectionListener(listSelectionListener);
		panelSample.add(lblSample, BorderLayout.CENTER);

		txtFontName.setEditable(false);
		txtFontStyle.setEditable(false);
		txtFontSize.setEditable(false);
	}

	public void setVisible(boolean b) {
		if (b) {
			initLocation(Main.getMDIMain());
		}
		super.setVisible(b);
	}

	private void initUI() {
		initDefaultSampleText();
		initFontNames();
		initFontStyles();
		initFontSize();
	}


	private void initDefaultSampleText() {
	    String msg = MessageManager.getMessage(MessageKeyConsts.FONT_SAMPL);
		if (!StringUtil.isEmpty(msg)) {
			sampleText = MessageManager.getMessage(msg);
		}
	}

	private void initFontNames() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		HashSet<String> fontSet = new HashSet<String>();
		Vector<String> listData = new Vector<String>();

		for (int i = 0; i < fonts.length; i++) {
			String fontFamily = fonts[i].getFamily();
			int sepPos = 0;
			if ((sepPos = fontFamily.lastIndexOf(".")) > 0) {
				fontFamily = fontFamily.substring(0, sepPos);
			}
			fontSet.add(fontFamily);
		}

		listData.addAll(fontSet);
		Collections.sort(listData);

		listFontName.setListData(listData);
	}

	private void initFontStyles() {
		listFontStyle.setListData(fontStyles);
	}

	private void initFontSize() {
		String[] fontSizes = {
			"8", "9", "10", "11", "12", "14",
			"16", "18", "20", "22", "24", "26",
			"28", "36", "48", "72"
		};

		listFontSize.setListData(fontSizes);
	}

	private void initOrignal() {
		String fontName = PJConst.EMPTY;
		String fontStyle = PJConst.EMPTY;
		String fontSize = PJConst.EMPTY;

		if (flag == PJConst.WINDOW_CONFIGGRIDFONT) {
			fontName = PropertyManager.getProperty(PJConst.OPTIONS_VIEW_GRIDFONT_NAME);
			fontStyle = PropertyManager.getProperty(PJConst.OPTIONS_VIEW_GRIDFONT_STYLE);
			fontSize = PropertyManager.getProperty(PJConst.OPTIONS_VIEW_GRIDFONT_SIZE);
		} else if (flag == PJConst.WINDOW_CONFIGSQLFONT) {
			fontName = PropertyManager.getProperty(PJConst.OPTIONS_VIEW_SQLFONT_NAME);
			fontStyle = PropertyManager.getProperty(PJConst.OPTIONS_VIEW_SQLFONT_STYLE);
			fontSize = PropertyManager.getProperty(PJConst.OPTIONS_VIEW_SQLFONT_SIZE);
		}

		if (StringUtil.isEmpty(fontName)) {
			fontName = "dialog";
		}

		try {
			int style = Integer.parseInt(fontStyle);
			fontStyle = getStringFontStyle(style);
		} catch (NumberFormatException nfe) {
			fontStyle = fontStyles[0];
		}

		if (StringUtil.isEmpty(fontSize)) {
			fontSize = "12";
		}

		listFontName.setSelectedValue(fontName, true);
		listFontStyle.setSelectedValue(fontStyle, true);
		listFontSize.setSelectedValue(fontSize, true);

		showSample();
	}

	/**
	 * ok button event
	 */
	void btnOK_actionPerformed(ActionEvent e) {
		setParam();
		dispose();
	}

	/**
	 * cancel button event
	 */
	void btnCancel_actionPerformed(ActionEvent e) {
		dispose();
	}

	/**
	 * set return param and fire the eventlist
	 */
	void setParam() {
		String fontName = getFontName();
		int fontStyle = getFontStyle();
		int fontSize = getFontSize();

		String[] param = {fontName, String.valueOf(fontStyle), String.valueOf(fontSize)};
		fireParamTransferEvent(param, flag);
	}

	/**
	 * list change value action
	 *
	 */
	class ChooseValueListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			Object obj = e.getSource();

			if (!e.getValueIsAdjusting()) {
				if (obj == listFontName) {
					setSelectValue(listFontName, txtFontName);
				} else if (obj == listFontStyle) {
					setSelectValue(listFontStyle, txtFontStyle);
				} else if (obj == listFontSize) {
					setSelectValue(listFontSize, txtFontSize);
				}
			}
		}
	}

	/**
	 * set list selected value to textfield
	 */
	void setSelectValue(JList list, JTextField txtField) {
		Object selectValue = list.getSelectedValue();
		txtField.setText((String) selectValue);

		showSample();
	}


	/**
	 * reset sample show
	 */
	void showSample() {
		String fontName = getFontName();
		int fontStyle = getFontStyle();
		int fontSize = getFontSize();

		Font font = new Font(fontName, fontStyle, fontSize);
		lblSample.setFont(font);
		lblSample.setText(sampleText);
	}

	/**
	 * parse font name
	 */
	String getFontName() {
		String fontName = txtFontName.getText();

		if (StringUtil.isEmpty(fontName)) {
			fontName = "dialog";
		}

		return fontName;
	}

	/**
	 * parse font style
	 */
	int getFontStyle() {
		int selectIndex = listFontStyle.getSelectedIndex();

		if (selectIndex == 0) {
			return Font.PLAIN;
		} else if (selectIndex == 1) {
			return Font.BOLD;
		} else if (selectIndex == 2) {
			return Font.ITALIC;
		} else if (selectIndex == 3) {
			return Font.BOLD + Font.ITALIC;
		}

		return Font.PLAIN;
	}

	String getStringFontStyle(int style) {
		if (style == Font.PLAIN) {
			return fontStyles[0];
		} else if (style == Font.BOLD) {
			return fontStyles[1];
		} else if (style == Font.ITALIC) {
			return fontStyles[2];
		} else if (style == Font.BOLD + Font.ITALIC) {
			return fontStyles[3];
		}

		return fontStyles[0];
	}

	/**
	 * parse font size
	 */
	int getFontSize() {
		int fontSize = 12;

		try {
			fontSize = Integer.parseInt(txtFontSize.getText());
		} catch (NumberFormatException nfe) {
			fontSize = 12;
		}

		return fontSize;
	}
}
