package ldjp.jassistant.model;

import java.io.Serializable;

/**
 */
public class TableDefineData implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
	 * table types
	 *
	 * "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY",
	 * "LOCAL TEMPORARY", "ALIAS", "SYNONYM"
	 */
	private String tableType = null;

	/**
	 * tableName
	 */
	private String tableName = null;

	/**
	 * tableName
	 */
	private String tableSchem = null;

	/**
	 * comment
	 */
	private String comment = null;

	/**
	 * Default Construction Function
	 */
	public TableDefineData() {}


	/**
	 * Get the tableType
	 *
	 * @return String tableType
	 */
	public String getTableType() {
		return this.tableType;
	}

	/**
	 * Set the tableType
	 *
	 * @param tableType tableType
	 */
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	/**
	 * Get the tableName
	 *
	 * @return String tableName
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * Set the tableName
	 *
	 * @param tableName tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Get the tableSchem
	 *
	 * @return String tableSchem
	 */
	public String getTableSchem() {
		return this.tableSchem;
	}

	/**
	 * Set the tableSchem
	 *
	 * @param tableSchem tableSchem
	 */
	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}


	/**
     * @return comment
     */
    public String getComment() {
        return comment;
    }


    /**
     * @param comment  comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
	 * Method For Debug
	 *
	 * @return String
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("tableType:		" + tableType + "\n");
		sb.append("tableName:		" + tableName + "\n");

		return sb.toString();
	}
}
