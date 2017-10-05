package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import ldjp.jassistant.base.RollOverButton;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.FilterSortManager;
import ldjp.jassistant.util.ImageManager;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 */
public class PanelRightToolBarPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    ImageIcon iconRefreshTableList = ImageManager.createImageIcon("refreshtablellist.gif");
	ImageIcon iconRefreshRightPanel = ImageManager.createImageIcon("refreshrightpanel.gif");
	ImageIcon iconClearFilterSort = ImageManager.createImageIcon("filtersortnothing.gif");
	ImageIcon iconImportXlsx = ImageManager.createImageIcon("importtabledatas.gif");
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	RollOverButton btnRefreshRightPanel = new RollOverButton();
	RollOverButton btnRefreshTableList = new RollOverButton();
	RollOverButton btnClearFilterSort = new RollOverButton();
	RollOverButton btnImportXlsx = new RollOverButton();
	Component horizontalGlue = Box.createHorizontalGlue();
	JLabel lblTableName = new JLabel();

	public PanelRightToolBarPanel() {
		try {
			jbInit();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setPreferredSize(new Dimension(600, 20));
		this.setLayout(borderLayout1);
		toolBar.setBorder(null);
		toolBar.setFloatable(false);
		btnRefreshRightPanel.setEnabled(true);
		btnRefreshRightPanel.setMaximumSize(new Dimension(27, 27));
		btnRefreshRightPanel.setMinimumSize(new Dimension(27, 27));
		btnRefreshRightPanel.setPreferredSize(new Dimension(32, 32));
		btnRefreshRightPanel.setToolTipText(WordManager.getWord(WordKeyConsts.W0228));
		btnRefreshRightPanel.setIcon(iconRefreshRightPanel);
		btnRefreshRightPanel.setMargin(new Insets(0, 0, 0, 0));
		btnRefreshRightPanel.setVerifyInputWhenFocusTarget(false);
		btnRefreshTableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRefreshTableList_actionPerformed(e);
			}
		});
		btnRefreshTableList.setMargin(new Insets(0, 0, 0, 0));
		btnRefreshTableList.setIcon(iconRefreshTableList);
		btnRefreshTableList.setToolTipText(WordManager.getWord(WordKeyConsts.W0229));
		btnRefreshTableList.setPreferredSize(new Dimension(32, 32));
		btnRefreshTableList.setMinimumSize(new Dimension(27, 27));
		btnRefreshTableList.setMaximumSize(new Dimension(27, 27));
		btnRefreshTableList.setEnabled(true);
		btnClearFilterSort.setMargin(new Insets(0, 0, 0, 0));
		btnClearFilterSort.setIcon(iconClearFilterSort);
		btnClearFilterSort.setToolTipText(WordManager.getWord(WordKeyConsts.W0230));
		btnClearFilterSort.setPreferredSize(new Dimension(32, 32));
		btnClearFilterSort.setMinimumSize(new Dimension(27, 27));
		btnClearFilterSort.setMaximumSize(new Dimension(27, 27));
		btnClearFilterSort.setEnabled(true);
		btnClearFilterSort.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClearFilterSort_actionPerformed(e);
			}
		});
		btnRefreshRightPanel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRefreshRightPanel_actionPerformed(e);
			}
		});
		btnImportXlsx.setMargin(new Insets(0,0,0,0));
		btnImportXlsx.setIcon(iconImportXlsx);
		btnImportXlsx.setEnabled(true);
		btnImportXlsx.setToolTipText(WordManager.getWord(WordKeyConsts.W0231));
		btnImportXlsx.setPreferredSize(new Dimension(32, 32));
		btnImportXlsx.setMinimumSize(new Dimension(27, 27));
		btnImportXlsx.setMaximumSize(new Dimension(27, 27));
		btnImportXlsx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnImportXlsx_actionPerformed(e);
            }
        });

		lblTableName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTableName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTableName.setText(PJConst.EMPTY);
		lblTableName.setPreferredSize(new Dimension(380, 20));
		this.add(toolBar, BorderLayout.CENTER);
		toolBar.add(btnRefreshTableList, null);
		toolBar.add(btnRefreshRightPanel, null);
		toolBar.add(btnClearFilterSort, null);
		toolBar.add(btnImportXlsx);
		toolBar.add(horizontalGlue, null);
		toolBar.add(lblTableName, null);
	}

	/**
	 * refresh left panel lists
	 *
	 */
	void btnRefreshTableList_actionPerformed(ActionEvent e) {
		try {
            Main.getMDIMain().sqlBrowserPanel.leftPanel.refreshTableList(Main
                    .getMDIMain().getConnection());
		} catch (SQLException se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}

	void btnRefreshRightPanel_actionPerformed(ActionEvent e) {
		refreshRightPanel();
	}

	void btnClearFilterSort_actionPerformed(ActionEvent e) {
		if (MessageManager.showMessage("MCSTC011Q") == 0) {
			String currentConnURL = Main.getMDIMain().currentConnURL;
			FilterSortManager.clearFilterSort(currentConnURL);
			refreshRightPanel();
		}
	}

	void refreshRightPanel() {
        PanelRight rightPanel = (PanelRight) Main.getMDIMain().sqlBrowserPanel.dataMainSplitPane
                .getRightComponent();
		if (rightPanel != null) {
			rightPanel.enabledReFresh();
			rightPanel.refreshSelected();
		}
	}

	void btnImportXlsx_actionPerformed(ActionEvent e){
	    importXlsx();
	}

	void importXlsx(){
	    DialogImportXlsxData dialogImportXlsxData = new DialogImportXlsxData();
	    dialogImportXlsxData.setVisible(true);
	}
}
