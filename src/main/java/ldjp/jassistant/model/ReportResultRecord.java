/**
 *
 */
package ldjp.jassistant.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 */
public class ReportResultRecord implements Serializable{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Report name
     */
    String reportName = null;

    /**
     * Executing time
     */
    Timestamp execTime = null;

    /**
     * Memo
     */
    String memo = null;

    /**
     * Default Construction Function
     */
    public ReportResultRecord() {
    }

    /**
     * @return reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName reportName
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return execTime
     */
    public Timestamp getExecTime() {
        return execTime;
    }

    /**
     * @param execTime  execTime
     */
    public void setExecTime(Timestamp execTime) {
        this.execTime = execTime;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo  memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

}
