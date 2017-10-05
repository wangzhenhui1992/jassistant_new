package ldjp.jassistant.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ldjp.jassistant.base.PJDialogBase;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.ImageManager;
import ldjp.jassistant.util.MessageKeyConsts;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

public class DialogAbout extends PJDialogBase {


    private static final long serialVersionUID = 1L;
    JPanel panelMain = new JPanel();
	JLabel lblIcon = new JLabel();
	JLabel lblDescription = new JLabel();
	JLabel lblAuthor = new JLabel();
	JLabel lblCorp = new JLabel();
	JLabel lblVersion = new JLabel();
	JButton btnOK = new JButton();
	ImageIcon iconHead = ImageManager.createImageIcon(PJConst.IMAGE_SYSTEM);

	public DialogAbout(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogAbout() {
		this(null, WordManager.getWord(WordKeyConsts.W0001), true);
	}

	public DialogAbout(Frame parent) {
		this(Main.getMDIMain(), WordManager.getWord(WordKeyConsts.W0001), true);
	}

	public void setVisible(boolean b) {
		if (b) {
			initLocation(Main.getMDIMain());
		}
		super.setVisible(b);
	}

	void jbInit() throws Exception {
		panelMain.setLayout(null);
		this.setResizable(false);
		setSize(new Dimension(343, 160));
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setHorizontalTextPosition(SwingConstants.CENTER);
		lblIcon.setIcon(iconHead);
		lblIcon.setBounds(new Rectangle(8, 10, 100, 110));
		lblDescription.setText(WordManager.getWord(WordKeyConsts.W0002));
		lblDescription.setBounds(new Rectangle(120, 20, 190, 16));
		lblVersion.setText(WordManager.getWord(WordKeyConsts.W0003));
		lblVersion.setBounds(new Rectangle(120, 36, 190, 16));
        lblAuthor.setText(WordManager.getWord(WordKeyConsts.W0004)
                + MessageManager.getMessage(MessageKeyConsts.AUTHOR_NAME));
		lblAuthor.setBounds(new Rectangle(120, 52, 190, 16));
		lblCorp.setText(MessageManager.getMessage(MessageKeyConsts.AUTHOR_CORP));
		lblCorp.setBounds(new Rectangle(165, 68, 145, 16));
		btnOK.setMargin(new Insets(1, 1, 1, 1));
		btnOK.setText(WordManager.getWord(WordKeyConsts.W0005));
		btnOK.setBounds(new Rectangle(200, 105, 50, 20));
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOK_actionPerformed(e);
			}
		});
		getContentPane().add(panelMain);
		panelMain.add(lblIcon, null);
		panelMain.add(btnOK, null);
		panelMain.add(lblDescription, null);
		panelMain.add(lblVersion, null);
		panelMain.add(lblAuthor, null);
		panelMain.add(lblCorp, null);
	}


	void btnOK_actionPerformed(ActionEvent e) {
		dispose();
	}
}
