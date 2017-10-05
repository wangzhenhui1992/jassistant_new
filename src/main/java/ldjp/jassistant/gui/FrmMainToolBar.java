package ldjp.jassistant.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;

import ldjp.jassistant.base.RollOverButton;
import ldjp.jassistant.util.ImageManager;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 * main frame tool bar
 */
public class FrmMainToolBar extends JToolBar {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    FrmMain parent = null;
	RollOverButton toolBarOpenConnection = new RollOverButton();
	RollOverButton toolBarCloseConnection = new RollOverButton();
	RollOverButton toolBarSchemaBrowser = new RollOverButton();
	RollOverButton toolBarSQLEdit = new RollOverButton();
	RollOverButton toolBarNewJavaBean = new RollOverButton();
	RollOverButton toolBarOptions = new RollOverButton();
	ImageIcon iconNewJavaBean = ImageManager.createImageIcon("newbean.gif");
	ImageIcon iconOpenConnection = ImageManager.createImageIcon("open.gif");
	ImageIcon iconCloseConnection = ImageManager.createImageIcon("close.gif");
	ImageIcon iconCopy = ImageManager.createImageIcon("copy.gif");
	ImageIcon iconPaste = ImageManager.createImageIcon("paste.gif");
	ImageIcon iconCut = ImageManager.createImageIcon("cut.gif");
	ImageIcon iconDelete = ImageManager.createImageIcon("delete.gif");
	ImageIcon iconOptions = ImageManager.createImageIcon("options.gif");
	ImageIcon iconDataBrowse = ImageManager.createImageIcon("databrowse.gif");
	ImageIcon iconSQLInput = ImageManager.createImageIcon("sqlscript.gif");

	public FrmMainToolBar(FrmMain parent) {
		this.parent = parent;
		try {
			jbInit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
        this.setBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145,
                140)));
		toolBarOpenConnection.setToolTipText(WordManager.getWord(WordKeyConsts.W0157));
		toolBarOpenConnection.setIcon(iconOpenConnection);
		toolBarOpenConnection.setMargin(new Insets(1, 1, 1, 1));
		toolBarOpenConnection.addActionListener(parent.buttonMenuActionListener);
		toolBarCloseConnection.setMargin(new Insets(1, 1, 1, 1));
		toolBarCloseConnection.addActionListener(parent.buttonMenuActionListener);
		toolBarCloseConnection.setToolTipText(WordManager.getWord(WordKeyConsts.W0158));
		toolBarCloseConnection.setIcon(iconCloseConnection);
		toolBarSQLEdit.setToolTipText(WordManager.getWord(WordKeyConsts.W0159));
		toolBarSQLEdit.setIcon(iconSQLInput);
		toolBarSQLEdit.setEnabled(false);
		toolBarSQLEdit.setMargin(new Insets(1, 1, 1, 1));
		toolBarSQLEdit.addActionListener(parent.buttonMenuActionListener);
		toolBarSchemaBrowser.addActionListener(parent.buttonMenuActionListener);
		toolBarSchemaBrowser.setToolTipText(WordManager.getWord(WordKeyConsts.W0160));
		toolBarSchemaBrowser.setIcon(iconDataBrowse);
		toolBarSchemaBrowser.setEnabled(false);
		toolBarSchemaBrowser.setMargin(new Insets(1, 1, 1, 1));
		toolBarNewJavaBean.addActionListener(parent.buttonMenuActionListener);
		toolBarNewJavaBean.setMargin(new Insets(1, 1, 1, 1));
		toolBarNewJavaBean.setIcon(iconNewJavaBean);
		toolBarNewJavaBean.setToolTipText(WordManager.getWord(WordKeyConsts.W0161));
		toolBarOptions.setToolTipText(WordManager.getWord(WordKeyConsts.W0088));
		toolBarOptions.setIcon(iconOptions);
		toolBarOptions.setMargin(new Insets(1, 1, 1, 1));
		toolBarOptions.addActionListener(parent.buttonMenuActionListener);

		this.add(toolBarOpenConnection, null);
		this.add(toolBarCloseConnection, null);
		JToolBar.Separator separator1 = new JToolBar.Separator(new Dimension(2, 28));
		separator1.setBorder(BorderFactory.createEtchedBorder());
		this.add(separator1, null);
		this.add(toolBarSchemaBrowser, null);
		this.add(toolBarSQLEdit, null);
		this.add(toolBarNewJavaBean, null);
		JToolBar.Separator separator2 = new JToolBar.Separator(new Dimension(2, 28));
		separator2.setBorder(BorderFactory.createEtchedBorder());
		this.add(separator2, null);
		this.add(toolBarOptions, null);
		JToolBar.Separator separator3 = new JToolBar.Separator(new Dimension(2, 28));
		separator3.setBorder(BorderFactory.createEtchedBorder());
	}
}
