package ldjp.jassistant.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ldjp.jassistant.base.PJDialogBase;
import ldjp.jassistant.base.PJEditorTextField;
import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.db.DBParser;
import ldjp.jassistant.model.ColumnDescriptionData;
import ldjp.jassistant.util.CollectionUtils;
import ldjp.jassistant.util.MessageManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.WordKeyConsts;
import ldjp.jassistant.util.WordManager;

/**
 * Import all tables' data from a XLSX file
 *
 */
public class DialogImportXlsxData extends PJDialogBase {

    private static final long serialVersionUID = 1L;

    JPanel panelContent = new JPanel();
    JPanel panelFrom = new JPanel();
    JPanel panelButton = new JPanel();
    JButton btnOK = new JButton();
    JButton btnCancel = new JButton();
    JButton btnAbort = new JButton();
    TitledBorder titledBorderFrom;
    PJEditorTextField txtFilePath = new PJEditorTextField();
    JButton btnBrowseFile = new JButton();
    JProgressBar progressBarImport = new JProgressBar();
    JRadioButton rdoImportFromFile = new JRadioButton();
    ButtonGroup btnGroupFrom = new ButtonGroup();
    ButtonActionListener buttonActionListener = new ButtonActionListener();
    TitledBorder titledBorder1;
    ButtonGroup rdoGroupIncludeHeader = new ButtonGroup();
    JRadioButton rdoIncludeHeader_not = new JRadioButton();
    JRadioButton rdoIncludeHeader_1 = new JRadioButton();
    JRadioButton rdoIncludeHeader_2 = new JRadioButton();
    FileDialog fileDialog = null;
    boolean shouldBreak = false;
    boolean processedInsert = false;


    public DialogImportXlsxData(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public DialogImportXlsxData() {
        this(Main.getMDIMain(), WordManager.getWord(WordKeyConsts.W0076), true);
    }

    void jbInit() throws Exception {
        titledBorderFrom = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"From");
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Format");
        panelContent.setLayout(null);
        this.setResizable(false);
        panelContent.setMaximumSize(new Dimension(300, 210));
        panelContent.setMinimumSize(new Dimension(300, 210));
        panelContent.setPreferredSize(new Dimension(300, 210));
        panelFrom.add(txtFilePath, null);
        panelFrom.add(btnBrowseFile, null);
        panelFrom.add(rdoImportFromFile, null);
        panelFrom.add(rdoIncludeHeader_not,null);
        panelFrom.add(rdoIncludeHeader_1,null);
        panelFrom.add(rdoIncludeHeader_2,null);
        panelFrom.setBorder(titledBorderFrom);
        panelFrom.setBounds(new Rectangle(10, 10, 282, 130));
        panelFrom.setLayout(null);
        txtFilePath.setBounds(new Rectangle(85, 23, 158, 21));
        btnBrowseFile.setMargin(new Insets(0, 0, 0, 0));
        btnBrowseFile.setText("...");
        btnBrowseFile.setBounds(new Rectangle(247, 23, 24, 22));
        rdoImportFromFile.setSelected(true);
        rdoImportFromFile.setText(WordManager.getWord(WordKeyConsts.W0077));
        rdoImportFromFile.setBounds(new Rectangle(15, 25, 69, 18));
        btnGroupFrom.add(rdoImportFromFile);
        rdoIncludeHeader_not.setText(WordManager.getWord(WordKeyConsts.W0078));
        rdoIncludeHeader_not.setBounds(new Rectangle(15, 50, 156, 25));
        rdoIncludeHeader_not.setSelected(true);
        rdoIncludeHeader_1.setText(WordManager.getWord(WordKeyConsts.W0079));
        rdoIncludeHeader_1.setBounds(new Rectangle(15, 75, 156, 25));
        rdoIncludeHeader_2.setText(WordManager.getWord(WordKeyConsts.W0080));
        rdoIncludeHeader_2.setBounds(new Rectangle(15, 100, 156, 25));
        rdoGroupIncludeHeader.add(rdoIncludeHeader_not);
        rdoGroupIncludeHeader.add(rdoIncludeHeader_1);
        rdoGroupIncludeHeader.add(rdoIncludeHeader_2);
        // progressBarImport area 150-170
        progressBarImport.setBounds(new Rectangle(10, 150, 282, 20));

        // panelButton area 175-200
        panelButton.setBounds(new Rectangle(10, 175, 282, 25));
        panelButton.setLayout(null);
        panelButton.add(btnOK, null);
        panelButton.add(btnAbort, null);
        panelButton.add(btnCancel, null);
        btnOK.setMargin(new Insets(0, 0, 0, 0));
        btnOK.setMnemonic(PJConst.MNEMONIC_O);
        btnOK.setText(WordManager.getWord(WordKeyConsts.W0022));
        btnOK.setBounds(new Rectangle(52, 0, 55, 22));
        btnCancel.setBounds(new Rectangle(172, 0, 55, 22));
        btnCancel.setMargin(new Insets(0, 0, 0, 0));
        btnCancel.setMnemonic(PJConst.MNEMONIC_C);
        btnCancel.setText(WordManager.getWord(WordKeyConsts.W0023));
        btnAbort.setText(WordManager.getWord(WordKeyConsts.W0011));
        btnAbort.setMargin(new Insets(0, 0, 0, 0));
        btnAbort.setMnemonic(PJConst.MNEMONIC_A);
        btnAbort.setBounds(new Rectangle(112, 0, 55, 22));
        btnAbort.setEnabled(false);

        // DialogImportXlsxData
        getContentPane().add(panelContent);
        panelContent.add(progressBarImport, null);
        panelContent.add(panelButton, null);
        panelContent.add(panelFrom, null);
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
     * select import from file
     *
     */
    void selectImportFromFile() {
        if (fileDialog == null) {
            fileDialog = new FileDialog(Main.getMDIMain());
        }
        fileDialog.setVisible(true);
        fileDialog.setMode(FileDialog.LOAD);
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

    String errorMessage = null;

    /**
     * close the window
     *
     */
    void closeWindow() {
        dispose();

        if (processedInsert) {
            shouldBreak = true;
            fireParamTransferEvent(null, PJConst.WINDOW_IMPORTTABLEDATA);
        }
    }

    /**
     * use a thread to do saving
     *
     */
    class ThreadDoInsert extends Thread {
        String filePath;
        int realDataIndex;

        /**
         * default constructor
         */
        public ThreadDoInsert(String filePath,int realDataIndex) {
            this.realDataIndex = realDataIndex;
            this.filePath = filePath;
        }

        private Map<String,List<Vector<String>>> getDataMap() throws IOException{
            File f = new File(filePath);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                // TODO
            }
            XSSFWorkbook wk = new XSSFWorkbook(fis);
            XSSFSheet sht = wk.getSheetAt(0);
            int rowSum = sht.getLastRowNum()+1;
            String tableName = null;
            int index = 0;
            Map<String,List<Vector<String>>> dataMap = new HashMap<String,List<Vector<String>>>();
            Vector<String> rowData = null;
            for (int i = 0; i < rowSum; i++) {
                XSSFRow rw = sht.getRow(i);
                if (rw == null || StringUtil.isEmpty(rw.getCell(0).getStringCellValue())) {
                    tableName = null;
                    index = 0;
                    continue;
                }
                index++;
                if (index == 1) {
                    tableName = rw.getCell(0).getStringCellValue().split("\\(")[0];
                    dataMap.put(tableName, new ArrayList<Vector<String>>());
                } else if (index>realDataIndex+1){
                    rowData = new Vector<String>();
                    int cs = rw.getPhysicalNumberOfCells();
                    for (int j = 0; j < cs; j++) {
                        XSSFCell c = rw.getCell(j);
                        if (null == c) continue;
                        rowData.add(c.getCellType() ==  Cell.CELL_TYPE_NUMERIC  ? String.valueOf(c
                                .getNumericCellValue()) : c.getStringCellValue());
                    }
                    dataMap.get(tableName).add(rowData);
                } else {
                    continue;
                }
            }
            fis.close();
            wk.close();
            return dataMap;
        }

        /**
         * start method
         */
        public void run() {
            try {
                String errTbl = null;
                try {
                    Map<String, List<Vector<String>>> dataMap = getDataMap();
                    btnOK.setEnabled(false);
                    btnAbort.setEnabled(true);
                    progressBarImport.setMaximum(dataMap.size());
                    int i = 0;
                    for (String tableName : dataMap.keySet()) {
                        errTbl = tableName;
                        List<ColumnDescriptionData> columns = DBParser
                                .getColumnDescription(Main
                                        .getMDIMain().getConnection(), tableName);
                        Vector<String> columnNms = new Vector<String>();
                        Vector<Class<?>> columnTps = new Vector<Class<?>>();
                        Vector<Boolean> keyColumNms =  new Vector<Boolean>();
                        for (ColumnDescriptionData column : columns) {
                            columnNms.add(column.getColumnName());
                            columnTps.add(StringUtil.getSimpleJavaType(column
                                    .getColumnType()));
                            keyColumNms.add(column.getPrimaryKeySeq()>0);
                        }
                        for (Vector<String> rowData : dataMap.get(tableName)) {
                            if (CollectionUtils.isEmpty(rowData)) continue;
                            Vector<Object> rowDataTrans = new Vector<Object>();
                            Vector<Object> keyColVals = new Vector<Object>();
                            for (int ii=0;ii<rowData.size();ii++) {
                                Object colVal = StringUtil.getConvertValueOfType(columnTps.get(ii), rowData.get(ii));
                                rowDataTrans.add(colVal);
                                if (keyColumNms.get(ii))
                                    keyColVals.add(colVal);
                            }
                            rowDataTrans.add(keyColVals);
                            DBParser.deleteRowData(Main.getMDIMain().getConnection(),
                                    columnNms, columnTps, keyColumNms, rowDataTrans,
                                    tableName);
                            DBParser.insertRowData(Main.getMDIMain().getConnection(),
                                    columnNms, columnTps, rowDataTrans, tableName);
                        }
                        progressBarImport.setValue(++i);
                    }
                } catch (Exception e) {
                    errorMessage = e.getMessage() + " || TBL : "+errTbl;
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            if (MessageManager.showMessage("MCSTC016Q", errorMessage) == 0) {
                                shouldBreak = false;
                            } else {
                                shouldBreak = true;
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressBarImport.setValue(0);
                btnOK.setEnabled(true);
                btnAbort.setEnabled(false);
                shouldBreak = false;
            }

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    closeWindow();
                }
            });
        }
    }

    private void doInsert(){
        int realDataIndex = 0;
        if (rdoIncludeHeader_1.isSelected()){
            realDataIndex = 1;
        } else if (rdoIncludeHeader_2.isSelected()) {
            realDataIndex = 2;
        }
        new ThreadDoInsert(txtFilePath.getText(),realDataIndex).start();
    }

    class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();

            if (obj == btnOK) {
                doInsert();
            } else if (obj ==  btnCancel) {
                closeWindow();
            } else if (obj == btnAbort) {
                shouldBreak = true;
            } else if (obj == btnBrowseFile) {
                selectImportFromFile();
            }
        }
    }
}
