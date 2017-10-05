package ldjp.jassistant.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ReportTemplate implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Report name
     */
    private String name = null;

    /**
     * parameter list
     */
    private ArrayList<ReportParam> paramList = null;

    /**
     * SQL list
     */
    private ArrayList<ReportSQLDesc> sqlList = null;

    /**
     * Output flag of EXCEL/CSV
     */
    private String exportType = null;

    /**
     * Comment output flag
     */
    private boolean isOutComment = true;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name  name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return paramList
     */
    public ArrayList<ReportParam> getParamList() {
        return paramList;
    }

    /**
     * @param paramList  paramList
     */
    public void setParamList(ArrayList<ReportParam> paramList) {
        this.paramList = paramList;
    }

    /**
     * @return sqlList
     */
    public ArrayList<ReportSQLDesc> getSqlList() {
        return sqlList;
    }

    /**
     * @param sqlList  sqlList
     */
    public void setSqlList(ArrayList<ReportSQLDesc> sqlList) {
        this.sqlList = sqlList;
    }

    /**
     * @return exportType
     */
    public String getExportType() {
        return exportType;
    }

    /**
     * @param exportType  exportType
     */
    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    /**
     * @return isOutComment
     */
    public boolean isOutComment() {
        return isOutComment;
    }

    /**
     * @param isOutComment  isOutComment
     */
    public void setOutComment(boolean isOutComment) {
        this.isOutComment = isOutComment;
    }

    @Override
    public String toString() {
        return "ReportTemplate [paramList=" + paramList + ", sqlList="
                + sqlList + "]";
    }

}
