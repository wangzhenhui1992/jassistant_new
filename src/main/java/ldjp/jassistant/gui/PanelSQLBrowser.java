package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.MessageManager;

/**
 */
public class PanelSQLBrowser extends JPanel {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    BorderLayout borderLayoutMain = new BorderLayout();
	JSplitPane dataMainSplitPane = new JSplitPane();
	PanelTableList leftPanel = new PanelTableList();
	PanelRight rightPanel = new PanelRight();
    Border borderMainSplit = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.gray, Color.white, Color.white, new Color(103, 101, 98));

	public PanelSQLBrowser() {
		try {
			jbInit();
			initUI();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setLayout(borderLayoutMain);
		this.add(dataMainSplitPane, BorderLayout.CENTER);
		dataMainSplitPane.setVisible(true);
		dataMainSplitPane.setDividerLocation(220);
		dataMainSplitPane.setBorder(borderMainSplit);
		dataMainSplitPane.setDividerSize(5);
		dataMainSplitPane.setLeftComponent(leftPanel);
		dataMainSplitPane.setRightComponent(rightPanel);
        dataMainSplitPane.setPreferredSize(new Dimension(220, dataMainSplitPane
                .getHeight()));
	}

	void initUI() {
		leftPanel.setParent(this);

		if (Main.getMDIMain().getConnection() == null) {
			rightPanel.disableToolBar();
		}

		tabbedPanelMap.put(PJConst.BEAN_TYPE_TABLE, panelTablesColumnDescMap);
		tabbedPanelMap.put(PJConst.BEAN_TYPE_VIEW, panelViewsColumnDescMap);
		tabbedPanelMap.put(PJConst.BEAN_TYPE_NEWBEAN, panelNewBeansColumnDescMap);
        tabbedPanelMap.put(PJConst.BEAN_TYPE_REPORT, panelReportMap);
	}

	/**Business Area**/
	HashMap<String,Object> tabbedPanelMap = new HashMap<String, Object>();
	HashMap<String, Object> panelTablesColumnDescMap = new HashMap<String, Object>();
	HashMap<String, Object> panelViewsColumnDescMap = new HashMap<String, Object>();
	HashMap<String, Object> panelNewBeansColumnDescMap = new HashMap<String, Object>();
    HashMap<String, Object> panelReportMap = new HashMap<String, Object>();


	/**
	 * create new java bean that does not connect to database
	 */
	public void createNewBean(String beanName) {
		leftPanel.createNewBean(beanName);
	}

	/**
	 * get exists right panel, previous table description has been cached.
	 */
	@SuppressWarnings("unchecked")
    public JPanel getExistsRightBeanPanel(String beanType, String beanName) {
        HashMap<String, Object> tableMap = (HashMap<String, Object>) tabbedPanelMap
                .get(beanType);

		if (tableMap != null) {
			return (JPanel) tableMap.get(beanName);
		}

		return null;
	}

	/**
	 * delete one right panel
	 */
	@SuppressWarnings("unchecked")
    public void removeRightPanel(String beanType, String beanName) {
        HashMap<String, Object> tableMap = (HashMap<String, Object>) tabbedPanelMap
                .get(beanType);

		if (tableMap != null) {
			tableMap.remove(beanName);
		}

		dataMainSplitPane.setRightComponent(rightPanel);
	}

	/**
	 * cached new right bean panel.
	 */
    @SuppressWarnings("unchecked")
    public void saveRightBeanPanel(String beanType, String beanName, JPanel newPanel) {
        HashMap<String, Object> tableMap = (HashMap<String, Object>) tabbedPanelMap
                .get(beanType);
        if (tableMap != null) {
            tableMap.put(beanName, newPanel);
        }
    }

	/**
	 * show the right panel.
	 */
	public void showRightPanel(PanelRight newPanel) {
		dataMainSplitPane.setRightComponent(newPanel);
        dataMainSplitPane.setDividerLocation(dataMainSplitPane.getDividerLocation());
	}

	/**
	 * get current selected right panel
	 *
	 */
	public PanelRight getSelectedRightPanel() {
		PanelRight rightPanel = (PanelRight) dataMainSplitPane.getRightComponent();
		return rightPanel;
	}


	public void panelBeanCreateCopy() {
		PanelRight rightPanel = (PanelRight) dataMainSplitPane.getRightComponent();

		if (rightPanel != null) {
			PanelBeanCreate panelBeanCreate = rightPanel.panelBeanCreate;

			if (panelBeanCreate != null) {
				panelBeanCreate.copy_Performed();
			}
		}
	}

	public void panelBeanCreatePaste() {
		PanelRight rightPanel = (PanelRight) dataMainSplitPane.getRightComponent();

		if (rightPanel != null) {
			PanelBeanCreate panelBeanCreate = rightPanel.panelBeanCreate;

			if (panelBeanCreate != null) {
				panelBeanCreate.paste_Performed();
			}
		}
	}

	public void panelBeanCreateCut() {
		PanelRight rightPanel = (PanelRight) dataMainSplitPane.getRightComponent();

		if (rightPanel != null) {
			PanelBeanCreate panelBeanCreate = rightPanel.panelBeanCreate;

			if (panelBeanCreate != null) {
				panelBeanCreate.cut_Performed();
			}
		}
	}

	public void panelBeanCreateDelete() {
		PanelRight rightPanel = (PanelRight) dataMainSplitPane.getRightComponent();

		if (rightPanel != null) {
			PanelBeanCreate panelBeanCreate = rightPanel.panelBeanCreate;

			if (panelBeanCreate != null) {
				panelBeanCreate.delete_Performed();
			}
		}
	}

	/**
	 * Refresh table name list
	 */
	public void refreshTableNameList(Connection conn) {
		try {
			leftPanel.refreshTableList(conn);
		} catch (SQLException se) {
			MessageManager.showMessage("MCSTC202E", se.getMessage());
		}
	}


	/**
	 * clear all cached right panels
	 */
	public void cleanAllRightPanel() {
		showRightPanel(rightPanel);
		panelTablesColumnDescMap.clear();
		panelViewsColumnDescMap.clear();
	}


	@SuppressWarnings("unchecked")
    public void updateAllPanelUI() {
		SwingUtilities.updateComponentTreeUI(leftPanel);
		SwingUtilities.updateComponentTreeUI(rightPanel);
		leftPanel.repaint();

		Iterator<String> beanTypeIterator = tabbedPanelMap.keySet().iterator();
		while (beanTypeIterator.hasNext()) {
            HashMap<String, Object> tableMap = (HashMap<String, Object>) tabbedPanelMap
                    .get(beanTypeIterator.next());

            if (tableMap == null) {
                continue;
            }
            Iterator<String> listIterator = tableMap.keySet().iterator();
            while (listIterator.hasNext()) {
                PanelRight rightPanel = (PanelRight) tableMap.get(listIterator.next());
                updateOneRightPanelUI(rightPanel);
            }

		}
	}

	private void updateOneRightPanelUI(PanelRight rightPanel) {
		if (rightPanel != null) {
			SwingUtilities.updateComponentTreeUI(rightPanel);
			if (rightPanel.toolBarPanel != null) {
				SwingUtilities.updateComponentTreeUI(rightPanel.toolBarPanel);
				rightPanel.toolBarPanel.repaint();
			}
			if (rightPanel.panelColumnDesc != null) {
				SwingUtilities.updateComponentTreeUI(rightPanel.panelColumnDesc);
				rightPanel.panelColumnDesc.repaint();
			}
            if (rightPanel.panelIndexInfos != null) {
                SwingUtilities.updateComponentTreeUI(rightPanel.panelIndexInfos);
                rightPanel.panelIndexInfos.repaint();
            }
			if (rightPanel.panelKeyReference != null) {
				SwingUtilities.updateComponentTreeUI(rightPanel.panelKeyReference);
				rightPanel.panelKeyReference.repaint();
			}
			if (rightPanel.panelBeanCreate != null) {
				SwingUtilities.updateComponentTreeUI(rightPanel.panelBeanCreate);
				rightPanel.panelBeanCreate.repaint();
			}
			if (rightPanel.panelTableModify != null) {
				SwingUtilities.updateComponentTreeUI(rightPanel.panelTableModify);
				rightPanel.panelTableModify.repaint();
			}
            if (rightPanel.panelReport != null) {
                SwingUtilities.updateComponentTreeUI(rightPanel.panelReport);
                rightPanel.panelReport.repaint();
            }
            if (rightPanel.panelHistory != null) {
                SwingUtilities.updateComponentTreeUI(rightPanel.panelHistory);
                rightPanel.panelHistory.repaint();
            }
		}
	}

}
