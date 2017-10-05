package ldjp.jassistant.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.ImageManager;
import ldjp.jassistant.util.PropertyManager;
import ldjp.jassistant.util.ResourceManager;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 * main frame JMenubar
 */
public class FrmMainMenuBar extends JMenuBar {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    FrmMain parent = null;
	JMenu mnuFile = new JMenu();
	JMenuItem mnuFileItemOpen = new JMenuItem();
	JMenuItem mnuFileItemClose = new JMenuItem();
	JMenuItem mnuFileItemNewJavaBean = new JMenuItem();
	JMenuItem mnuFileItemExit = new JMenuItem();
	JMenu mnuEdit = new JMenu();
	JMenuItem mnuEditItemCopy = new JMenuItem();
	JMenuItem mnuEditItemPaste = new JMenuItem();
	JMenuItem mnuEditItemCut = new JMenuItem();
	JMenuItem mnuEditItemDelete = new JMenuItem();
	JMenu mnuDatabase = new JMenu();
	JMenuItem mnuDatabaseItemSchemaBrowser = new JMenuItem();
	JMenuItem mnuDatabaseItemSQLEdit = new JMenuItem();
    JCheckBoxMenuItem mnuItemAutoCommitOnOff = new JCheckBoxMenuItem(
            WordManager.getWord(WordKeyConsts.W0131));
	JMenuItem mnuDatabaseItemCommit = new JMenuItem();
	JMenuItem mnuDatabaseItemRollback = new JMenuItem();
	JMenu mnuView = new JMenu();
	JMenuItem mnuViewItemWindows = new JMenuItem();
	JMenuItem mnuViewItemMotif = new JMenuItem();
	JMenuItem mnuViewItemMeta = new JMenuItem();
	JMenuItem mnuViewItemOption = new JMenuItem();
	JMenu mnuWindow = new JMenu();
	JMenuItem mnuWindowItemCascade = new JMenuItem();
	JMenuItem mnuWindowItemTileHorizontal = new JMenuItem();
	JMenuItem mnuWindowItemTileVertical = new JMenuItem();
	JMenuItem mnuWindowItemCloseAllFrames = new JMenuItem();
	JMenu mnuHelp = new JMenu();
	JMenuItem mnuHelpItemAbout = new JMenuItem();
	JMenuItem mnuHelpItemContent = new JMenuItem();
	ImageIcon iconNewJavaBean = ImageManager.createImageIcon("newbean.gif");
	ImageIcon iconOpenConnection = ImageManager.createImageIcon("open.gif");
	ImageIcon iconCloseConnection = ImageManager.createImageIcon("close.gif");
	ImageIcon iconCopy = ImageManager.createImageIcon("copy.gif");
	ImageIcon iconPaste = ImageManager.createImageIcon("paste.gif");
	ImageIcon iconCut = ImageManager.createImageIcon("cut.gif");
	ImageIcon iconDelete = ImageManager.createImageIcon("delete.gif");
	ImageIcon iconOptions = ImageManager.createImageIcon("options.gif");
	ImageIcon iconCascadeWindows = ImageManager.createImageIcon("cascadewindows.gif");
    ImageIcon iconHorizontalTileWindows = ImageManager
            .createImageIcon("horizontaltilewindows.gif");
    ImageIcon iconVerticalTileWindows = ImageManager
            .createImageIcon("verticaltilewindows.gif");
	ImageIcon iconCloseAllWindows = ImageManager.createImageIcon("closeallwindows.gif");
	ImageIcon iconHelp = ImageManager.createImageIcon("help.gif");
	ImageIcon iconDataBrowse = ImageManager.createImageIcon("databrowse.gif");
	ImageIcon iconSQLInput = ImageManager.createImageIcon("sqlscript.gif");
	ImageIcon iconAutoCommitOnOff = ImageManager.createImageIcon("autocommit.gif");
	ImageIcon iconCommit = ImageManager.createImageIcon("commit.gif");
	ImageIcon iconRollback = ImageManager.createImageIcon("rollback.gif");
	ButtonGroup lafMenuGroup = new ButtonGroup();

	//Buttons for maximized internal frame in MDI mode placed to main window menu bar.
	private static java.awt.Component horizontalGlue;
	private static JButton iconButton;
	private static JButton minButton;
	private static JButton closeButton;
	private static boolean buttonsAdded;
	/** Close frame action */
	//private static CloseMaximizedFrameAction closeFrame;
	/** Minimize frame action */
	//private static MinimizeMaximizedFrameAction minimizeFrame;
	/** Restore frame action */
	//private static RestoreMaximizedFrameAction restoreFrame;

	/**
	 * default constructor
	 *
	 */
	public FrmMainMenuBar(FrmMain parent) {
		this.parent = parent;
		try {
			jbInit();
			initViewMenu();
			//createButtons();
			//addButtons();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		mnuFile.setText(WordManager.getWord(WordKeyConsts.W0069));
		mnuFile.setMnemonic(PJConst.MNEMONIC_F);
		mnuFileItemOpen.setIcon(iconOpenConnection);
		mnuFileItemOpen.setText(WordManager.getWord(WordKeyConsts.W0132));
		mnuFileItemOpen.addActionListener(parent.buttonMenuActionListener);
		mnuFileItemClose.setIcon(iconCloseConnection);
		mnuFileItemClose.setText(WordManager.getWord(WordKeyConsts.W0133));
		mnuFileItemClose.addActionListener(parent.buttonMenuActionListener);
		mnuFileItemNewJavaBean.setIcon(iconNewJavaBean);
		mnuFileItemNewJavaBean.setText(WordManager.getWord(WordKeyConsts.W0134));
		mnuFileItemNewJavaBean.addActionListener(parent.buttonMenuActionListener);
		mnuFileItemExit.setText(WordManager.getWord(WordKeyConsts.W0135));
		mnuFileItemExit.addActionListener(parent.buttonMenuActionListener);

		mnuEdit.setText(WordManager.getWord(WordKeyConsts.W0136));
		mnuEdit.setMnemonic(PJConst.MNEMONIC_E);
		mnuEditItemCopy.setEnabled(false);
		mnuEditItemCopy.setIcon(iconCopy);
		mnuEditItemCopy.setText(WordManager.getWord(WordKeyConsts.W0137));
        mnuEditItemCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_C,
                java.awt.event.KeyEvent.CTRL_MASK, false));
		mnuEditItemCopy.addActionListener(parent.buttonMenuActionListener);
		mnuEditItemPaste.setEnabled(false);
		mnuEditItemPaste.setIcon(iconPaste);
		mnuEditItemPaste.setText(WordManager.getWord(WordKeyConsts.W0138));
        mnuEditItemPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                KeyEvent.VK_V, java.awt.event.KeyEvent.CTRL_MASK, false));
		mnuEditItemPaste.addActionListener(parent.buttonMenuActionListener);
		mnuEditItemCut.setEnabled(false);
		mnuEditItemCut.setIcon(iconCut);
		mnuEditItemCut.setText(WordManager.getWord(WordKeyConsts.W0139));
        mnuEditItemCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_X,
                java.awt.event.KeyEvent.CTRL_MASK, false));
		mnuEditItemCut.addActionListener(parent.buttonMenuActionListener);
		mnuEditItemDelete.setEnabled(false);
		mnuEditItemDelete.setIcon(iconDelete);
		mnuEditItemDelete.setText(WordManager.getWord(WordKeyConsts.W0140));
        mnuEditItemDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                KeyEvent.VK_DELETE, 0, false));
		mnuEditItemDelete.addActionListener(parent.buttonMenuActionListener);

		mnuDatabase.setText(WordManager.getWord(WordKeyConsts.W0115));
		mnuDatabase.setMnemonic(PJConst.MNEMONIC_D);
		mnuDatabaseItemSchemaBrowser.setIcon(iconDataBrowse);
		mnuDatabaseItemSchemaBrowser.setText(WordManager.getWord(WordKeyConsts.W0141));
		mnuDatabaseItemSchemaBrowser.addActionListener(parent.buttonMenuActionListener);
        mnuDatabaseItemSchemaBrowser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                KeyEvent.VK_F11, ActionEvent.CTRL_MASK, true));
		mnuDatabaseItemSQLEdit.setIcon(iconSQLInput);
		mnuDatabaseItemSQLEdit.setText(WordManager.getWord(WordKeyConsts.W0142));
		mnuDatabaseItemSQLEdit.addActionListener(parent.buttonMenuActionListener);
        mnuDatabaseItemSQLEdit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                KeyEvent.VK_F12, ActionEvent.CTRL_MASK, true));
		mnuItemAutoCommitOnOff.addActionListener(parent.buttonMenuActionListener);
		mnuItemAutoCommitOnOff.setIcon(iconAutoCommitOnOff);
		mnuDatabaseItemCommit.setIcon(iconCommit);
		mnuDatabaseItemCommit.setText(WordManager.getWord(WordKeyConsts.W0143));
		mnuDatabaseItemCommit.addActionListener(parent.buttonMenuActionListener);
		mnuDatabaseItemRollback.setIcon(iconRollback);
		mnuDatabaseItemRollback.setText(WordManager.getWord(WordKeyConsts.W0144));
		mnuDatabaseItemRollback.addActionListener(parent.buttonMenuActionListener);

		mnuView.setText(WordManager.getWord(WordKeyConsts.W0116));
		mnuView.setMnemonic(PJConst.MNEMONIC_V);
		mnuViewItemOption.setIcon(iconOptions);
		mnuViewItemOption.setText(WordManager.getWord(WordKeyConsts.W0145));
		mnuViewItemOption.addActionListener(parent.buttonMenuActionListener);

		mnuWindow.setText(WordManager.getWord(WordKeyConsts.W0146));
		mnuWindow.setMnemonic(PJConst.MNEMONIC_W);
		mnuWindowItemCascade.setText(WordManager.getWord(WordKeyConsts.W0147));
		mnuWindowItemCascade.setIcon(iconCascadeWindows);
		mnuWindowItemCascade.addActionListener(parent.buttonMenuActionListener);
		mnuWindowItemTileHorizontal.setText(WordManager.getWord(WordKeyConsts.W0148));
		mnuWindowItemTileHorizontal.setIcon(iconHorizontalTileWindows);
		mnuWindowItemTileHorizontal.addActionListener(parent.buttonMenuActionListener);
		mnuWindowItemTileVertical.setText(WordManager.getWord(WordKeyConsts.W0149));
		mnuWindowItemTileVertical.setIcon(iconVerticalTileWindows);
		mnuWindowItemTileVertical.addActionListener(parent.buttonMenuActionListener);
		mnuWindowItemCloseAllFrames.setText(WordManager.getWord(WordKeyConsts.W0150));
		mnuWindowItemCloseAllFrames.setIcon(iconCloseAllWindows);
		mnuWindowItemCloseAllFrames.addActionListener(parent.buttonMenuActionListener);

		mnuHelp.setText(WordManager.getWord(WordKeyConsts.W0151));
		mnuHelp.setMnemonic(PJConst.MNEMONIC_H);
		mnuHelpItemAbout.setText(WordManager.getWord(WordKeyConsts.W0152));
		mnuHelpItemAbout.addActionListener(parent.buttonMenuActionListener);
		mnuHelpItemContent.setText(WordManager.getWord(WordKeyConsts.W0153));
		mnuHelpItemContent.setIcon(iconHelp);
		mnuHelpItemContent.addActionListener(parent.buttonMenuActionListener);

		this.add(mnuFile);
		this.add(mnuEdit);
		this.add(mnuDatabase);
		this.add(mnuView);
		this.add(mnuWindow);
		this.add(mnuHelp);

		mnuFile.add(mnuFileItemOpen);
		mnuFile.add(mnuFileItemClose);
		mnuFile.addSeparator();
		mnuFile.add(mnuFileItemNewJavaBean);
		mnuFile.addSeparator();
		mnuFile.add(mnuFileItemExit);
		mnuEdit.add(mnuEditItemCopy);
		mnuEdit.add(mnuEditItemPaste);
		mnuEdit.add(mnuEditItemCut);
		mnuEdit.add(mnuEditItemDelete);
		mnuDatabase.add(mnuDatabaseItemSchemaBrowser);
		mnuDatabase.add(mnuDatabaseItemSQLEdit);
		mnuDatabase.addSeparator();
		mnuDatabase.add(mnuItemAutoCommitOnOff);
		mnuDatabase.add(mnuDatabaseItemCommit);
		mnuDatabase.add(mnuDatabaseItemRollback);

		mnuWindow.add(mnuWindowItemCascade);
		mnuWindow.add(mnuWindowItemTileHorizontal);
		mnuWindow.add(mnuWindowItemTileVertical);
		mnuWindow.add(mnuWindowItemCloseAllFrames);
		mnuWindow.addSeparator();
		mnuHelp.add(mnuHelpItemAbout);
	}

	void initViewMenu() {
		mnuView.removeAll();
        mnuViewItemWindows = createLafMenuItem(mnuView,
                WordManager.getWord(WordKeyConsts.W0154), "0");
        mnuViewItemMotif = createLafMenuItem(mnuView,
                WordManager.getWord(WordKeyConsts.W0155), "1");
        mnuViewItemMeta = createLafMenuItem(mnuView,
                WordManager.getWord(WordKeyConsts.W0156), "2");

		String lafIndex = PropertyManager.getProperty(PJConst.OPTIONS_DEFAULT_LOOKANDFEEL);
		if ("0".equals(lafIndex)) {
			mnuViewItemWindows.setSelected(true);
		} else if ("1".equals(lafIndex)) {
			mnuViewItemMotif.setSelected(true);
		} else if ("2".equals(lafIndex)) {
			mnuViewItemMeta.setSelected(true);
		}

		mnuView.add(mnuViewItemWindows);
		mnuView.add(mnuViewItemMotif);
		mnuView.add(mnuViewItemMeta);
		mnuView.addSeparator();
		mnuView.add(mnuViewItemOption);
	}

	/**
	 * Creates a JRadioButtonMenuItem for the Look and Feel menu
	 */
	public JMenuItem createLafMenuItem(JMenu menu, String label, String index) {
		JMenuItem mi = (JRadioButtonMenuItem) menu.add(new JRadioButtonMenuItem(label));
		lafMenuGroup.add(mi);
		mi.setActionCommand(index);
		mi.addActionListener(parent.changeLookAndFeelAction);
		mi.setEnabled(ResourceManager.isAvailableLookAndFeel(index));

		return mi;
	}

	/**
	 * menu and toolbar button, copy, cut, paste, delete status
	 * by the column selection status
	 */
	public void setColumnOperationStatus(boolean enabled) {
		if (enabled) {
			mnuEditItemCopy.setEnabled(true);
			mnuEditItemPaste.setEnabled(true);
			mnuEditItemCut.setEnabled(true);
			mnuEditItemDelete.setEnabled(true);
		} else {
			mnuEditItemCopy.setEnabled(false);
			mnuEditItemPaste.setEnabled(false);
			mnuEditItemCut.setEnabled(false);
			mnuEditItemDelete.setEnabled(false);
		}
	}


	/** Create and initialize buttons for maximized frame in MDI mode */
	/*
	private synchronized void createButtons() {
		//if (closeFrame == null) {
		//	closeFrame = (CloseMaximizedFrameAction) SystemAction.get(CloseMaximizedFrameAction.class);
		//}
		//if (minimizeFrame == null) {
		//	minimizeFrame = (MinimizeMaximizedFrameAction) SystemAction.get(MinimizeMaximizedFrameAction.class);
		//}
		//if (restoreFrame == null) {
		//	restoreFrame = (RestoreMaximizedFrameAction) SystemAction.get(RestoreMaximizedFrameAction.class);
		//}
		//if (horizontalGlue == null) {
		//	horizontalGlue = Box.createHorizontalGlue();
		//}

		if (iconButton == null) {
			iconButton = new JButton();
			Icon iconIcon = UIManager.getIcon("InternalFrame.iconifyIcon");
			iconButton.setIcon(iconIcon);
			iconButton.setBorder(BorderFactory.createEmptyBorder());
			//iconButton.addActionListener(minimizeFrame);
		}
		if (minButton == null) {
			minButton = new JButton();
			Icon minIcon = UIManager.getIcon("InternalFrame.minimizeIcon");
			minButton.setIcon(minIcon);
			minButton.setBorder(BorderFactory.createEmptyBorder());
			//minButton.addActionListener(restoreFrame);
		}
		if (closeButton == null) {
			closeButton = new JButton();
			Icon closeIcon = UIManager.getIcon("InternalFrame.closeIcon");
			closeButton.setIcon(closeIcon);
			closeButton.setBorder(BorderFactory.createEmptyBorder());
			//closeButton.addActionListener(closeFrame);
		}
		buttonsAdded = false;
	}

	/** Add buttons for maximized frame in MDI mode to menu bar.
	 * It must be called from AWT thread.
	 */
	void addButtons() {
		if (buttonsAdded) {
			return;
		}
		buttonsAdded = true;

		//Add buttons to menu bar
		this.add(horizontalGlue);
		this.add(iconButton);
		this.add(minButton);
		this.add(closeButton);
		this.revalidate();
		this.repaint();
	}

	/** Remove buttons for maximized frame in MDI mode to menu bar.
	 * It must be called from AWT thread.
	 */
	void removeButtons() {
		if (!buttonsAdded) {
			return;
		}
		buttonsAdded = false;

		//Remove buttons from menu bar
		this.remove(horizontalGlue);
		this.remove(iconButton);
		this.remove(minButton);
		this.remove(closeButton);
		this.revalidate();
		this.repaint();
	}

}
