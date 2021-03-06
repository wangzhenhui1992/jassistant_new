package ldjp.jassistant.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.thoughtworks.xstream.XStream;

import ldjp.jassistant.base.PJDBCellEditor;
import ldjp.jassistant.base.PJEditorTextField;
import ldjp.jassistant.base.PJSQLTextPane;
import ldjp.jassistant.base.PJTableCellRender;
import ldjp.jassistant.base.RollOverButton;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.common.Refreshable;
import ldjp.jassistant.model.ReportParam;
import ldjp.jassistant.model.ReportSQLDesc;
import ldjp.jassistant.model.ReportTemplate;
import ldjp.jassistant.util.DateUtil;
import ldjp.jassistant.util.ImageManager;
import ldjp.jassistant.util.MessageKeyConsts;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 */
public class PanelReport extends JPanel implements Refreshable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    ImageIcon iconAddRow = ImageManager.createImageIcon("addrow.gif");
    ImageIcon iconRemoveRow = ImageManager.createImageIcon("deleterow.gif");
    ImageIcon iconUparrow = ImageManager.createImageIcon("uparrow.gif");
    ImageIcon iconDownarrow = ImageManager.createImageIcon("downarrow.gif");
    ImageIcon iconSQLExecute = ImageManager.createImageIcon("sqlexecute.gif");
    ImageIcon iconUndo = ImageManager.createImageIcon("undo.gif");
    ImageIcon iconRedo = ImageManager.createImageIcon("redo.gif");
    ImageIcon iconSQLFormat = ImageManager.createImageIcon("sqlbuilder.gif");
    ImageIcon iconClear = ImageManager.createImageIcon("deletealltabledata.gif");

    JPanel panelMain = new JPanel();
    JPanel panelBottom = new JPanel();

    JSplitPane slpMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JPanel panelInput = new JPanel();
    JPanel panelResult = new JPanel();
    JTabbedPane tbpResult = new JTabbedPane(SwingConstants.TOP,
            JTabbedPane.SCROLL_TAB_LAYOUT);

    JSplitPane slpDesign = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JPanel panelSQLDesc = new JPanel();
    JPanel panelSQLEdit = new JPanel();
    JToolBar toolBarSQLEdit = new JToolBar();
    JScrollPane scpSQLEdit = new JScrollPane();
    PJSQLTextPane txtSQLEdit = new PJSQLTextPane();

    JSplitPane slpDesc = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JPanel panelParam = new JPanel();
    JToolBar toolBarParam = new JToolBar();
    JScrollPane scpParam = new JScrollPane();
    JTable tblParam = new JTable();
    JPanel panelSQLList = new JPanel();
    JToolBar toolBarSQLList = new JToolBar();
    JScrollPane scpSQLList = new JScrollPane();
    JTable tblSQLList = new JTable();

    JLabel lblSqlName = new JLabel();
    JLabel lblSqlDesc = new JLabel();

    RollOverButton btnAddParam = new RollOverButton();
    RollOverButton btnDeleteParam = new RollOverButton();
    RollOverButton btnUpParam = new RollOverButton();
    RollOverButton btnDownParam = new RollOverButton();

    RollOverButton btnAddSQL = new RollOverButton();
    RollOverButton btnDeleteSQL = new RollOverButton();
    RollOverButton btnUpSQL = new RollOverButton();
    RollOverButton btnDownSQL = new RollOverButton();

    RollOverButton btnExecuteSQL = new RollOverButton(iconSQLExecute);
    RollOverButton btnUndo = new RollOverButton(iconUndo);
    RollOverButton btnRedo = new RollOverButton(iconRedo);
    RollOverButton btnSQLFormat = new RollOverButton(iconSQLFormat);

    JButton btnSave = new JButton();
    JButton btnAllExecute = new JButton();
    JButton btnExport = new JButton();
    JButton btnClearResult = new JButton();

    ButtonGroup grpExportType = new ButtonGroup();
    JRadioButton rdoExportExcel = new JRadioButton();
    JRadioButton rdoExportCsv = new JRadioButton();
    JCheckBox chkExportComment = new JCheckBox();

    SQLCellEditorListener sqlCellEditorListener = new SQLCellEditorListener();

    String currentReportName = null;
    String currentType = PJConst.EMPTY;

    ReportTemplate reportTemplate = new ReportTemplate();
    static File configDir = new File(Main.configPath, "report");

    static {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
    }

    public PanelReport() {
        try {
            jbInit();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        this.setLayout(new BorderLayout());

        panelMain.setLayout(new BorderLayout());
        panelBottom.setLayout(null);
        this.add(panelMain, BorderLayout.CENTER);
        this.add(panelBottom, BorderLayout.SOUTH);
        panelBottom.setPreferredSize(new Dimension(this.getWidth(), 40));

        ImageIcon iconSave = ImageManager.createImageIcon("savetabledata.gif");
        btnSave.setText(WordManager.getWord(WordKeyConsts.W0202));
        btnSave.setIcon(iconSave);
        btnSave.setBounds(15, 10, 80, 25);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveReportTemplate(true);
            }
        });
        panelBottom.add(btnSave);

        ImageIcon iconSQLAllExec = ImageManager.createImageIcon("sqlexecute.gif");
        btnAllExecute.setText(WordManager.getWord(WordKeyConsts.W0203));
        btnAllExecute.setIcon(iconSQLAllExec);
        btnAllExecute.setBounds(150, 10, 100, 25);
        btnAllExecute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processAllSQL();
            }
        });
        panelBottom.add(btnAllExecute);

        ImageIcon iconExport = ImageManager.createImageIcon("exporttofile.gif");
        btnExport.setText(WordManager.getWord(WordKeyConsts.W0195));
        btnExport.setIcon(iconExport);
        btnExport.setBounds(255, 10, 120, 25);
        btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportResultData();
            }
        });
        panelBottom.add(btnExport);

        rdoExportExcel.setText("Excel");
        rdoExportCsv.setText("CSV");
        grpExportType.add(rdoExportExcel);
        grpExportType.add(rdoExportCsv);
        rdoExportExcel.setSelected(true);

        rdoExportExcel.setBounds(380, 10, 60, 25);
        rdoExportCsv.setBounds(440, 10, 60, 25);
        panelBottom.add(rdoExportExcel);
        panelBottom.add(rdoExportCsv);

        chkExportComment.setText(WordManager.getWord(WordKeyConsts.W0179));
        chkExportComment.setBounds(500, 10, 60, 25);
        chkExportComment.setSelected(true);
        panelBottom.add(chkExportComment);

        btnClearResult.setText(WordManager.getWord(WordKeyConsts.W0204));
        btnClearResult.setIcon(iconClear);
        btnClearResult.setBounds(570, 10, 120, 25);
        btnClearResult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearResultData();
            }
        });
        panelBottom.add(btnClearResult);

        panelMain.add(slpMain, BorderLayout.CENTER);
        slpMain.setDividerSize(5);
        slpMain.setTopComponent(panelInput);
        slpMain.setBottomComponent(panelResult);

        panelResult.setLayout(new BorderLayout());
        panelResult.add(tbpResult);

        panelInput.setLayout(new BorderLayout());
        panelInput.add(slpDesign);
        slpDesign.setDividerSize(5);
        slpDesign.setTopComponent(panelSQLDesc);
        slpDesign.setBottomComponent(panelSQLEdit);

        panelSQLDesc.setLayout(new BorderLayout());
        panelSQLDesc.add(slpDesc);
        slpDesc.setDividerSize(5);
        slpDesc.setLeftComponent(panelParam);
        slpDesc.setRightComponent(panelSQLList);

        slpMain.setBorder(new BevelBorder(BevelBorder.LOWERED));
        slpDesign.setBorder(new EmptyBorder(1, 1, 1, 1));
        slpDesc.setBorder(new EmptyBorder(1, 1, 1, 1));

        initPanelParam();
        initPanelSQLDesc();
        initPanelSQLEdit();
        initPanelResult();

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reLayout();
            }
        });
    }

    PJTableCellRender defaultCellRender = new PJTableCellRender();
    String[] paramHeaders = new String[] { "No",
            WordManager.getWord(WordKeyConsts.W0205),
            WordManager.getWord(WordKeyConsts.W0174) };
    int[] paramColumnWidth = new int[]{20, 140, 140};
    Vector<Vector<Object>> vecParamData = new Vector<Vector<Object>>();
    ParamTableModel paramTblModel = new ParamTableModel();
    int IDX_PARAMNDX = 0;
    int IDX_PARAMNAM = 1;
    int IDX_PARAMVAL = 2;

    private void initPanelParam() {
        btnAddParam.setIcon(iconAddRow);
        btnAddParam.setToolTipText(WordManager.getWord(WordKeyConsts.W0206));
        btnDeleteParam.setIcon(iconRemoveRow);
        btnDeleteParam.setToolTipText(WordManager.getWord(WordKeyConsts.W0207));
        btnUpParam.setIcon(iconUparrow);
        btnUpParam.setToolTipText(WordManager.getWord(WordKeyConsts.W0208));
        btnDownParam.setIcon(iconDownarrow);
        btnDownParam.setToolTipText(WordManager.getWord(WordKeyConsts.W0209));

        toolBarParam.setFloatable(false);
        toolBarParam.add(btnAddParam);
        toolBarParam.add(btnDeleteParam);
        toolBarParam.addSeparator();
        toolBarParam.add(btnUpParam);
        toolBarParam.add(btnDownParam);

        scpParam.getViewport().add(tblParam);
        panelParam.setBorder(new TitledBorder(WordManager.getWord(WordKeyConsts.W0210)));
        panelParam.setLayout(new BorderLayout());
        panelParam.add(toolBarParam, BorderLayout.NORTH);
        panelParam.add(scpParam, BorderLayout.CENTER);

        tblParam.setModel(paramTblModel);
        tblParam.setRowHeight(18);
        tblParam.setDefaultRenderer(Object.class, defaultCellRender);
        tblParam.getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        tblParam.setColumnSelectionAllowed(false);
        tblParam.getTableHeader().setReorderingAllowed(false);
        tblParam.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        tblParam.getColumn(paramHeaders[0]).setCellRenderer(new IndexCellRender());

        for (int i = 0; i < paramColumnWidth.length; i++) {
            tblParam.getColumnModel().getColumn(i).setPreferredWidth(paramColumnWidth[i]);
        }

        btnAddParam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDefaultParamRow();
            }
        });
        btnDeleteParam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSelectParamRow();
            }
        });
        btnDownParam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveParamRowDown();
            }
        });
        btnUpParam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveParamRowUp();
            }
        });

        tblParam.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                setBtnParamEnable();
            }
        });
        setBtnParamEnable();
    }

    private void setBtnParamEnable() {
        if (tblParam.getSelectedRowCount() > 0) {
            btnDeleteParam.setEnabled(true);
            btnDownParam.setEnabled(true);
            btnUpParam.setEnabled(true);
        } else {
            btnDeleteParam.setEnabled(false);
            btnDownParam.setEnabled(false);
            btnUpParam.setEnabled(false);
        }
    }

    String[] sqlHeaders = new String[] { "No",
            WordManager.getWord(WordKeyConsts.W0211),
            WordManager.getWord(WordKeyConsts.W0205),
            WordManager.getWord(WordKeyConsts.W0194),
            WordManager.getWord(WordKeyConsts.W0212) };
    int[] sqlColumnWidth = new int[]{20, 50, 100, 150, 300};
    Vector<Vector<Object>> vecSqlData = new Vector<Vector<Object>>();
    SQLTableModel sqlTblModel = new SQLTableModel();
    int IDX_SQLINDX = 0;
    int IDX_SQLSKIP = 1;
    int IDX_SQLNAME = 2;
    int IDX_SQLDESC = 3;
    int IDX_SQLBODY = 4;

    private void initPanelSQLDesc() {
        btnAddSQL.setIcon(iconAddRow);
        btnAddSQL.setToolTipText(WordManager.getWord(WordKeyConsts.W0213));
        btnDeleteSQL.setIcon(iconRemoveRow);
        btnDeleteSQL.setToolTipText(WordManager.getWord(WordKeyConsts.W0214));
        btnUpSQL.setIcon(iconUparrow);
        btnUpSQL.setToolTipText(WordManager.getWord(WordKeyConsts.W0215));
        btnDownSQL.setIcon(iconDownarrow);
        btnDownSQL.setToolTipText(WordManager.getWord(WordKeyConsts.W0216));

        toolBarSQLList.setFloatable(false);
        toolBarSQLList.add(btnAddSQL);
        toolBarSQLList.add(btnDeleteSQL);
        toolBarSQLList.addSeparator();
        toolBarSQLList.add(btnUpSQL);
        toolBarSQLList.add(btnDownSQL);

        scpSQLList.getViewport().add(tblSQLList);

        panelSQLList
                .setBorder(new TitledBorder(WordManager.getWord(WordKeyConsts.W0217)));
        panelSQLList.setLayout(new BorderLayout());
        panelSQLList.add(toolBarSQLList, BorderLayout.NORTH);
        panelSQLList.add(scpSQLList, BorderLayout.CENTER);

        tblSQLList.setModel(sqlTblModel);
        tblSQLList.setRowHeight(18);
        tblSQLList.setDefaultRenderer(Object.class, defaultCellRender);
        tblSQLList.getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        tblSQLList.setColumnSelectionAllowed(false);
        tblSQLList.getTableHeader().setReorderingAllowed(false);
        tblSQLList.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        tblSQLList.getColumn(sqlHeaders[0]).setCellRenderer(new IndexCellRender());

        for (int i = 0; i < sqlColumnWidth.length; i++) {
            tblSQLList.getColumnModel().getColumn(i).setPreferredWidth(sqlColumnWidth[i]);
        }
        tblSQLList.getColumnModel().getColumn(IDX_SQLINDX)
                .setMinWidth(sqlColumnWidth[0]);
        tblSQLList.getColumnModel().getColumn(IDX_SQLINDX)
                .setMaxWidth(sqlColumnWidth[0]);
        tblSQLList.getColumnModel().getColumn(IDX_SQLSKIP)
                .setMinWidth(sqlColumnWidth[1]);
        tblSQLList.getColumnModel().getColumn(IDX_SQLSKIP)
                .setMaxWidth(sqlColumnWidth[1]);

        PJEditorTextField editorComp = new PJEditorTextField(String.class);
        editorComp.setBorder(null);
        PJDBCellEditor defaultCellEditor = new PJDBCellEditor(editorComp);
        defaultCellEditor.addCellEditorListener(sqlCellEditorListener);
        tblSQLList.getColumnModel().getColumn(IDX_SQLNAME)
                .setCellEditor(defaultCellEditor);
        tblSQLList.getColumnModel().getColumn(IDX_SQLDESC)
                .setCellEditor(defaultCellEditor);

        btnAddSQL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDefaultSQLRow();
            }
        });
        btnDeleteSQL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSelectSQLRow();
            }
        });
        btnUpSQL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveSQLRowUp();
            }
        });
        btnDownSQL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveSQLRowDown();
            }
        });

        tblSQLList.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                setSQLEnable();

                Runnable laterDo = new Runnable() {
                    public void run() {
                        setSQLEditContent();
                    }
                };
                SwingUtilities.invokeLater(laterDo);
            }
        });
        setSQLEnable();
    }

    private void setSQLEnable() {
        if (tblSQLList.getSelectedRowCount() > 0) {
            btnDeleteSQL.setEnabled(true);
            btnUpSQL.setEnabled(true);
            btnDownSQL.setEnabled(true);
            txtSQLEdit.setEnabled(true);
            btnExecuteSQL.setEnabled(true);
            btnUndo.setEnabled(true);
            btnRedo.setEnabled(true);
            btnSQLFormat.setEnabled(true);
        } else {
            btnDeleteSQL.setEnabled(false);
            btnUpSQL.setEnabled(false);
            btnDownSQL.setEnabled(false);
            txtSQLEdit.setEnabled(false);
            btnExecuteSQL.setEnabled(false);
            btnUndo.setEnabled(false);
            btnRedo.setEnabled(false);
            btnSQLFormat.setEnabled(false);
        }
    }

    int selectedSQLRow = -1;

    private void setSQLEditContent() {
        int selectedRow = tblSQLList.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        selectedSQLRow = selectedRow;
        String sql = (String) tblSQLList.getValueAt(selectedRow, IDX_SQLBODY);
        txtSQLEdit.setText(sql);
        txtSQLEdit.resetDefaultFontStyle();
        setSQLLabel();

        setSelectSql();
    }

    private void setSQLLabel() {
        int selectedRow = tblSQLList.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        String sqlName = (String) tblSQLList.getValueAt(selectedRow, IDX_SQLNAME);
        String sqlDesc = (String) tblSQLList.getValueAt(selectedRow, IDX_SQLDESC);
        lblSqlName.setText(sqlName);
        lblSqlDesc.setText(sqlDesc);
    }

    private void saveSQLEditContent() {
        if (selectedSQLRow < 0) {
            return;
        }
        String sql = txtSQLEdit.getText();
        tblSQLList.setValueAt(sql, selectedSQLRow, IDX_SQLBODY);
        tblSQLList.repaint();
    }

    private void initPanelSQLEdit() {

        JLabel lblNotice = new JLabel(WordManager.getWord(WordKeyConsts.W0218));
        lblNotice.setForeground(Color.blue);

        toolBarSQLEdit.setFloatable(false);
        toolBarSQLEdit.add(btnExecuteSQL);
        toolBarSQLEdit.addSeparator();
        toolBarSQLEdit.add(btnUndo);
        toolBarSQLEdit.add(btnRedo);
        toolBarSQLEdit.addSeparator();
        toolBarSQLEdit.add(btnSQLFormat);
        toolBarSQLEdit.addSeparator();
        toolBarSQLEdit.add(lblNotice);

        JPanel toolBarSQLIdenty = new JPanel();
        toolBarSQLIdenty.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 8));
        JPanel toolBarBoth = new JPanel();
        toolBarBoth.setLayout(new GridLayout(1, 2));
        toolBarBoth.add(toolBarSQLEdit);
        toolBarBoth.add(toolBarSQLIdenty);

        toolBarSQLIdenty.add(lblSqlName);
        toolBarSQLIdenty.add(lblSqlDesc);

        scpSQLEdit.getViewport().add(txtSQLEdit);
        panelSQLEdit.setLayout(new BorderLayout());
        panelSQLEdit
                .setBorder(new TitledBorder(WordManager.getWord(WordKeyConsts.W0219)));
        panelSQLEdit.add(toolBarBoth, BorderLayout.NORTH);
        panelSQLEdit.add(scpSQLEdit, BorderLayout.CENTER);

        txtSQLEdit.setEnabled(false);
        txtSQLEdit.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                saveSQLEditContent();
            }

            public void focusGained(FocusEvent e) {
            }
        });

        btnExecuteSQL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectSql = txtSQLEdit.getText();
                String name = lblSqlName.getText();
                String desc = lblSqlDesc.getText();
                processCurrentSQL(name, desc, selectSql);
            }
        });

        txtSQLEdit.setUndoRedoButton(btnUndo, btnRedo);
        btnSQLFormat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtSQLEdit.formatSql();
            }
        });
    }

    void processCurrentSQL(String name, String desc, String selectSql) {
        if (StringUtil.isEmptyWithTrim(name)) {
            MessageManager.showMessage(MessageKeyConsts.MCSTC001E,
                    WordManager.getWord(WordKeyConsts.W0220));
        }
        for (int i = 0; i < vecParamData.size(); i++) {
            String paramName = (String)vecParamData.get(i).get(IDX_PARAMNAM);
            String paramValue = (String)vecParamData.get(i).get(IDX_PARAMVAL);

            selectSql = selectSql.replaceAll(
                    "#" + paramName + "#", paramValue);
        }

        if (!StringUtil.isEmptyWithTrim(selectSql)) {
            PanelSQLResult panelResult = new PanelSQLResult();
            boolean b = panelResult.processResultShow(name, desc, selectSql);

            if (b) {
                int idx = tbpResult.indexOfTab(name);
                if (idx >= 0) {
                    tbpResult.setComponentAt(idx, panelResult);
                } else {
                    tbpResult.add(name, panelResult);
                    idx = tbpResult.indexOfTab(name);
                }
                tbpResult.setSelectedIndex(idx);
            }
        }
    }

    void processAllSQL() {
        tbpResult.removeAll();
        for (int i = 0; i < vecSqlData.size(); i++) {
            boolean isSkip = (Boolean) vecSqlData.get(i).get(IDX_SQLSKIP);
            String name = (String) vecSqlData.get(i).get(IDX_SQLNAME);
            String desc = (String) vecSqlData.get(i).get(IDX_SQLDESC);
            String sql = (String) vecSqlData.get(i).get(IDX_SQLBODY);

            if (!isSkip) {
                if (StringUtil.isEmptyWithTrim(name)) {
                    MessageManager.showMessage(MessageKeyConsts.MCSTC001E,
                            WordManager.getWord(WordKeyConsts.W0220));
                    tblSQLList.changeSelection(i, 0, false, false);
                    return;
                }

                processCurrentSQL(name, desc, sql);
            }
        }
        setSelectSql();
    }

    void setSelectSql() {
        int selectedRow = tblSQLList.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        String name = (String) tblSQLList.getValueAt(selectedRow, IDX_SQLNAME);
        int idx = tbpResult.indexOfTab(name);
        if (idx >= 0) {
            tbpResult.setSelectedIndex(idx);
        }
    }

    private void initPanelResult() {

        panelResult.setLayout(new BorderLayout());
        panelResult
                .setBorder(new TitledBorder(WordManager.getWord(WordKeyConsts.W0221)));

        panelResult.add(tbpResult, BorderLayout.CENTER);
    }


    /**
     * Add one default parameter row
     */
    private void addDefaultParamRow() {
        Vector<Object> vecOneRecord = new Vector<Object>();

        vecOneRecord.add(PJConst.EMPTY);
        vecOneRecord.add(PJConst.EMPTY);
        vecOneRecord.add(PJConst.EMPTY);
        int selectedRow = tblParam.getSelectedRow();
        if (selectedRow >= 0) {
            paramTblModel.addRow(tblParam.getSelectedRow(), vecOneRecord);
        } else {
            paramTblModel.addRow(vecOneRecord);
            selectedRow = paramTblModel.getRowCount() - 1;
        }

        tblParam.changeSelection(selectedRow, 0, false, false);
    }

    private void removeSelectParamRow() {
        if (tblParam.getSelectedRow() >= 0) {
            paramTblModel.removeRow(tblParam.getSelectedRow());
        }
        tblParam.repaint();
    }

    /**
     * move row down
     */
    void moveParamRowDown() {
        int orgRow = tblParam.getSelectedRow();

        if (orgRow >= 0) {
            paramTblModel.moveRow(tblParam.getSelectedRow(), false);
            if (orgRow < paramTblModel.getRowCount() - 1) {
                tblParam.changeSelection(orgRow + 1, 0, false, false);
            }
        }
    }

    /**
     * move row up
     */
    void moveParamRowUp() {
        int orgRow = tblParam.getSelectedRow();

        if (orgRow >= 0) {
            paramTblModel.moveRow(tblParam.getSelectedRow(), true);

            if (orgRow > 0) {
                tblParam.changeSelection(orgRow - 1, 0, false, false);
            }
        }
    }

    /**
     * add one default param row
     */
    private void addDefaultSQLRow() {
        Vector<Object> vecOneRecord = new Vector<Object>();

        vecOneRecord.add(PJConst.EMPTY);
        vecOneRecord.add(new Boolean(false));
        vecOneRecord.add(PJConst.EMPTY);
        vecOneRecord.add(PJConst.EMPTY);
        vecOneRecord.add(PJConst.EMPTY);
        int selectedRow = tblSQLList.getSelectedRow();
        if (selectedRow >= 0) {
            sqlTblModel.addRow(tblSQLList.getSelectedRow(), vecOneRecord);
        } else {
            sqlTblModel.addRow(vecOneRecord);
            selectedRow = sqlTblModel.getRowCount() - 1;
        }

        tblSQLList.changeSelection(selectedRow, 0, false, false);
    }

    private void removeSelectSQLRow() {
        if (tblSQLList.getSelectedRow() >= 0) {
            sqlTblModel.removeRow(tblSQLList.getSelectedRow());
        }
        tblSQLList.repaint();
    }

    /**
     * move row down
     */
    void moveSQLRowDown() {
        int orgRow = tblSQLList.getSelectedRow();

        if (orgRow >= 0) {
            sqlTblModel.moveRow(tblSQLList.getSelectedRow(), false);
            if (orgRow < sqlTblModel.getRowCount() - 1) {
                tblSQLList.changeSelection(orgRow + 1, 0, false, false);
            }
        }
    }

    /**
     * move row up
     */
    void moveSQLRowUp() {
        int orgRow = tblSQLList.getSelectedRow();

        if (orgRow >= 0) {
            sqlTblModel.moveRow(tblSQLList.getSelectedRow(), true);

            if (orgRow > 0) {
                tblSQLList.changeSelection(orgRow - 1, 0, false, false);
            }
        }
    }

    public void setParam(String type, String name) {
        this.currentType = type;
        this.currentReportName = name;
    }

    public void resetData() {
    }

    public void setRefreshable(boolean b) {
    }

    public boolean isReFreshable() {
        return false;
    }

    public void clearData() {
    }

    public void reLayout() {
        double dlMain = 0.6d;
        double dlSql = 0.5d;
        slpMain.setSize(new Dimension(this.getWidth(), this.getHeight() - 40));
        slpMain.setDividerLocation(dlMain);
        slpDesign.setSize(new Dimension(this.getWidth(),
                (int) ((this.getHeight() - 40) * dlMain)));
        slpDesign.setDividerLocation(dlSql);
        slpDesc.setSize(new Dimension(slpDesign.getWidth(),
                (int) (slpDesign.getHeight() * dlSql)));
        slpDesc.setDividerLocation(300);
    }

    public void refreshDisplay() {
        File templateFile = new File(configDir, currentReportName + ".xml");
        if (templateFile.exists()) {
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader(new FileInputStream(templateFile), "UTF-8");
                XStream xs = new XStream();
                reportTemplate = (ReportTemplate) xs.fromXML(isr);
            } catch (Exception e) {
                MessageManager.showMessage("MCSTC203E", e.getMessage());
                e.printStackTrace();
            } finally {
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (Exception e) {}
                }
            }
        }
        restoreGUIData();
        reLayout();
    }

    private void restoreGUIData() {
        ArrayList<ReportParam> paramList = reportTemplate.getParamList();
        if (paramList != null) {
            paramTblModel.removeAllRows();
            for (int i = 0; i < paramList.size(); i++) {
                ReportParam param = paramList.get(i);
                Vector<Object> vec = new Vector<Object>();
                vec.add(PJConst.EMPTY);
                vec.add(param.getName());
                vec.add(param.getValue());
                paramTblModel.addRow(vec);
            }
            tblParam.repaint();
        }

        ArrayList<ReportSQLDesc> descList = reportTemplate.getSqlList();
        if (descList != null) {
            sqlTblModel.removeAllRows();
            for (int i = 0; i < descList.size(); i++) {
                ReportSQLDesc desc = descList.get(i);
                Vector<Object> vec = new Vector<Object>();
                vec.add(PJConst.EMPTY);
                vec.add(desc.isSkip());
                vec.add(desc.getName());
                vec.add(desc.getDesc());
                vec.add(desc.getText());
                sqlTblModel.addRow(vec);
            }
            tblSQLList.repaint();
        }

        if (rdoExportExcel.getText().equals(reportTemplate.getExportType())) {
            rdoExportExcel.setSelected(true);
        } else if (rdoExportCsv.getText().equals(reportTemplate.getExportType())) {
            rdoExportCsv.setSelected(true);
        }
        chkExportComment.setSelected(reportTemplate.isOutComment());
    }

    public boolean copyReport(String newReportName) {
        saveReportTemplate(false);

        File templateFile = new File(configDir, currentReportName + ".xml");
        File newFile = new File(configDir, newReportName + ".xml");
        OutputStreamWriter fos = null;
        InputStreamReader fis = null;
        try {
            fis = new InputStreamReader(new FileInputStream(templateFile),  "UTF-8");
            fos = new OutputStreamWriter(new FileOutputStream(newFile),  "UTF-8");

            int c = 0;
            char[] buf = new char[256];
            while ((c = fis.read(buf)) > 0) {
                fos.write(buf, 0, c);
            }
            fis.close();
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            MessageManager.showMessage("MCSTC203E", e.getMessage());
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {}
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {}
            }
        }
        return false;
    }

    public boolean deleteReport() {
        File templateFile = new File(configDir, currentReportName + ".xml");

        if (templateFile.exists()) {
            return templateFile.delete();
        }

        return true;
    }

    private void saveGUIData() {
        ArrayList<ReportParam> paramList = new ArrayList<ReportParam>();
        for (int i = 0; i < vecParamData.size(); i++) {
            ReportParam param = new ReportParam();
            param.setName((String) vecParamData.get(i).get(IDX_PARAMNAM));
            param.setValue((String) vecParamData.get(i).get(IDX_PARAMVAL));
            paramList.add(param);
        }
        ArrayList<ReportSQLDesc> sqlList = new ArrayList<ReportSQLDesc>();
        for (int i = 0; i < vecSqlData.size(); i++) {
            ReportSQLDesc desc = new ReportSQLDesc();
            desc.setSkip((Boolean) vecSqlData.get(i).get(IDX_SQLSKIP));
            desc.setName((String) vecSqlData.get(i).get(IDX_SQLNAME));
            desc.setDesc((String) vecSqlData.get(i).get(IDX_SQLDESC));
            desc.setText((String) vecSqlData.get(i).get(IDX_SQLBODY));
            sqlList.add(desc);
        }

        reportTemplate.setName(currentReportName);
        reportTemplate.setParamList(paramList);
        reportTemplate.setSqlList(sqlList);
        if (rdoExportExcel.isSelected()) {
            reportTemplate.setExportType(rdoExportExcel.getText());
        } else if (rdoExportCsv.isSelected()) {
            reportTemplate.setExportType(rdoExportCsv.getText());
        }
        reportTemplate.setOutComment(chkExportComment.isSelected());
    }

    private void saveReportTemplate(boolean isNeedShowMsg) {
        saveGUIData();

        File templateFile = new File(configDir, currentReportName + ".xml");
        OutputStreamWriter fos = null;
        try {
            fos = new OutputStreamWriter(new FileOutputStream(templateFile),  "UTF-8");
            XStream xs = new XStream();
            xs.toXML(reportTemplate, fos);

            if (isNeedShowMsg) {
                MessageManager.showMessage("MCSTC302I", templateFile.getAbsolutePath());
            }
        } catch (Exception e) {
            MessageManager.showMessage("MCSTC203E", e.getMessage());
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {}
            }
        }
    }

    void exportResultData() {
        int cnt = tbpResult.getTabCount();
        if (cnt < 1) {
            MessageManager.showMessage("MCSTC303E", PJConst.EMPTY);
            return;
        }

        if (rdoExportExcel.isSelected()) {
            SXSSFWorkbook wb = new SXSSFWorkbook();
            Sheet sheet = wb.createSheet(currentReportName);

            int startRow = 0;
            for (int i = 0; i < cnt; i++) {
                PanelSQLResult pnlResult =
                        (PanelSQLResult) tbpResult.getComponentAt(i);

                startRow = pnlResult.exportToExcel(sheet, startRow, chkExportComment.isSelected());
                if (i < cnt - 1) {
                    startRow++;
                }
            }

            FileOutputStream fos = null;
            try {
                SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
                String ts = sdfDateTime.format(DateUtil.getSysDateTimestamp());
                final File tempFile = File.createTempFile(currentReportName + "_" + ts,
                        ".xlsx");
                fos = new FileOutputStream(tempFile);
                wb.write(fos);
                wb.close();
                wb.dispose();

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            Desktop.getDesktop().open(tempFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                            MessageManager.showMessage("MCSTC203E", e.getMessage());
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                MessageManager.showMessage("MCSTC203E", e.getMessage());
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e) {}
                }
            }

        } else if (rdoExportCsv.isSelected()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cnt; i++) {
                PanelSQLResult pnlResult =
                        (PanelSQLResult) tbpResult.getComponentAt(i);

                pnlResult.exportToCSV(sb, chkExportComment.isSelected());

                if (i < cnt - 1) {
                    sb.append("\r\n");
                }
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(sb.toString());
            clipboard.setContents(stringSelection, stringSelection);

            MessageManager.showMessage("MCSTC304I", PJConst.EMPTY);
        }
    }

    void clearResultData() {
        tbpResult.removeAll();
    }

    /**
     * Class definition for model of tables
     */
    class ParamTableModel extends AbstractTableModel {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        // default constructor
        public ParamTableModel() {
            super();
        }

        public int getColumnCount() {
            return paramHeaders.length;
        }

        public int getRowCount() {
            return vecParamData.size();
        }

        public Object getValueAt(int row, int col) {
            return vecParamData.get(row).get(col);
        }

        public String getColumnName(int column) {
            return paramHeaders[column];
        }

        public Class<?> getColumnClass(int c) {
            Object value = getValueAt(0, c);
            if (value != null) {
                return value.getClass();
            }
            return Object.class;
        }

        public boolean isCellEditable(int row, int col) {
            return (col != 0);
        }

        public void setValueAt(Object aValue, int row, int column) {
            vecParamData.get(row).set(column, aValue);
        }

        public void addRow(Vector<Object> rowData) {
            int rows = vecParamData.size();
            fireTableRowsInserted(0, rows + 1);
            vecParamData.add(rowData);
        }

        public void addRows(Vector<Vector<Object>> rowDatas) {
            vecParamData.addAll(rowDatas);
            fireTableRowsInserted(0, getRowCount());
        }

        public void addRow(int index, Vector<Object> rowData) {
            int rows = vecParamData.size();
            fireTableRowsInserted(0, rows + 1);
            vecParamData.add(index, rowData);
        }

        public void removeRow(int row) {
            int rows = vecParamData.size();
            if (rows > 0){
                fireTableRowsDeleted(0, rows - 1);
                vecParamData.remove(row);
            }
        }

        public void removeAllRows() {
            int rows = vecParamData.size();
            if (rows > 0){
                fireTableRowsDeleted(0, rows - 1);
                vecParamData.removeAllElements();
            }
        }

        public void moveRow(int row, boolean up) {
            Vector<Object> vecRec = vecParamData.get(row);

            fireTableRowsUpdated(0, vecParamData.size());

            if (up) {
                if (row > 0) {
                    Vector<Object> toVecRec = vecParamData.get(row - 1);
                    vecParamData.set(row - 1, vecRec);
                    vecParamData.set(row, toVecRec);
                }
            } else {
                if (row < vecParamData.size() - 1) {
                    Vector<Object> toVecRec = vecParamData.get(row + 1);
                    vecParamData.set(row + 1, vecRec);
                    vecParamData.set(row, toVecRec);
                }
            }
        }
    };

    /**
     * Class definition for model of tables
     */
    class SQLTableModel extends AbstractTableModel {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        // default constructor
        public SQLTableModel() {
            super();
        }

        public int getColumnCount() {
            return sqlHeaders.length;
        }

        public int getRowCount() {
            return vecSqlData.size();
        }

        public Object getValueAt(int row, int col) {
            return vecSqlData.get(row).get(col);
        }

        public String getColumnName(int column) {
            return sqlHeaders[column];
        }

        public Class<?> getColumnClass(int c) {
            Object value = getValueAt(0, c);
            if (value != null) {
                return value.getClass();
            }
            return Object.class;
        }

        public boolean isCellEditable(int row, int col) {
            return (col != 0 && col != 4);
        }

        public void setValueAt(Object aValue, int row, int column) {
            vecSqlData.get(row).set(column, aValue);
        }

        public void addRow(Vector<Object> rowData) {
            int rows = vecSqlData.size();
            fireTableRowsInserted(0, rows);
            vecSqlData.add(rowData);
        }

        public void addRows(Vector<Vector<Object>> rowDatas) {
            vecSqlData.addAll(rowDatas);
            fireTableRowsInserted(0, getRowCount());
        }

        public void addRow(int index, Vector<Object> rowData) {
            int rows = vecSqlData.size();
            fireTableRowsInserted(0, rows);
            vecSqlData.add(index, rowData);
        }

        public void removeRow(int row) {
            int rows = vecSqlData.size();
            if (rows > 0){
                fireTableRowsDeleted(0, rows - 1);
                vecSqlData.remove(row);
            }
        }

        public void removeAllRows() {
            int rows = vecSqlData.size();
            if (rows > 0){
                fireTableRowsDeleted(0, rows - 1);
                vecSqlData.removeAllElements();
            }
        }

        public void moveRow(int row, boolean up) {
            Vector<Object> vecRec = vecSqlData.get(row);

            fireTableRowsUpdated(0, vecSqlData.size());

            if (up) {
                if (row > 0) {
                    Vector<Object> toVecRec = vecSqlData.get(row - 1);
                    vecSqlData.set(row - 1, vecRec);
                    vecSqlData.set(row, toVecRec);
                }
            } else {
                if (row < vecSqlData.size() - 1) {
                    Vector<Object> toVecRec = vecSqlData.get(row + 1);
                    vecSqlData.set(row + 1, vecRec);
                    vecSqlData.set(row, toVecRec);
                }
            }
        }
    };

    /**
     * the first count button render
     */
    class IndexCellRender extends javax.swing.table.DefaultTableCellRenderer {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               boolean hasFocus,
                                               int row,
                                               int column) {

            JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            lbl.setText(String.valueOf(row + 1));

            return lbl;
        }
    }

    /**
     * CellEditorListener for default editor
     */
    class SQLCellEditorListener implements CellEditorListener {
        public void editingStopped(ChangeEvent e) {
            setSQLLabel();
        }

        public void editingCanceled(ChangeEvent e) {}
    }

}
