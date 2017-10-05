package ldjp.jassistant.model;

import java.io.Serializable;

/**
 * Description Data
 */
public class ColumnDescriptionData implements Serializable {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
	 * the designated column's name
	 */
	private String columnName = null;

	/**
	 * the designated column's database-specific type name.
	 */
	private String columnTypeName = null;

	/**
	 * the designated column's SQL type
	 */
	private int columnType = 0;

	/**
	 * the designated column's normal maximum width in characters
	 */
	private int columnSize = 0;

	/**
	 * the designated column's number of decimal digits
	 */
	private int precision = 0;

	/**
	 * define whether the column is nullable
	 */
	private String isNullable = "Yes";

	/**
	 * defined the column is primary key, return the sequence of the primary key
	 */
	private int primaryKeySeq = 0;

	/**
	 * comment
	 */
	private String comment = null;

	/**
	 * default value
	 */
	private String defaultValue = null;

	/**
	 * Default Construction Function
	 */
	public ColumnDescriptionData() {}


	/**
	 * the designated column's name
	 *
	 * @return String the designated column's name
	 */
	public String getColumnName() {
		return this.columnName;
	}

	/**
	 * the designated column's name
	 *
	 * @param columnName the designated column's name
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * the designated column's database-specific type name.
	 *
	 * @return String the designated column's database-specific type name.
	 */
	public String getColumnTypeName() {
		return this.columnTypeName;
	}

	/**
	 * the designated column's database-specific type name.
	 *
	 * @param columnTypeName the designated column's database-specific type name.
	 */
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	/**
	 * the designated column's SQL type
	 *
	 * @return int the designated column's SQL type
	 */
	public int getColumnType() {
		return this.columnType;
	}

	/**
	 * the designated column's SQL type
	 *
	 * @param columnType the designated column's SQL type
	 */
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	/**
	 * the designated column's normal maximum width in characters
	 *
	 * @return int the designated column's normal maximum width in characters
	 */
	public int getColumnSize() {
		return this.columnSize;
	}

	/**
	 * the designated column's normal maximum width in characters
	 *
	 * @param columnSize the designated column's normal maximum width in characters
	 */
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	/**
	 * the designated column's number of decimal digits
	 *
	 * @return int the designated column's number of decimal digits
	 */
	public int getPrecision() {
		return this.precision;
	}

	/**
	 * the designated column's number of decimal digits
	 *
	 * @param precision the designated column's number of decimal digits
	 */
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	/**
	 * Define whether the column is able to be Null
	 *
	 * @return String Define whether the column is able to be Null
	 */
	public String getIsNullable() {
		return this.isNullable;
	}

	/**
	 * Define whether the column is able to be Null
	 *
	 * @param isNullable Define whether the column is able to be Null
	 */
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	/**
	 * defined the column is primary key, return the sequence of the primary key
	 *
	 * @return int defined the column is primary key, return the sequence of the primary key
	 */
	public int getPrimaryKeySeq() {
		return this.primaryKeySeq;
	}

	/**
	 * defined the column is primary key, return the sequence of the primary key
	 *
	 * @param primaryKeySeq defined the column is primary key, return the sequence of the primary key
	 */
	public void setPrimaryKeySeq(int primaryKeySeq) {
		this.primaryKeySeq = primaryKeySeq;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	/**
	 * @return defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}


	/**
	 * @param defaultValue  defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	/**
	 * Method For Debug
	 *
	 * @return String
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("columnName:		" + columnName + "\n");
		sb.append("columnTypeName:		" + columnTypeName + "\n");
		sb.append("columnType:		" + columnType + "\n");
		sb.append("columnSize:		" + columnSize + "\n");
		sb.append("precision:		" + precision + "\n");
		sb.append("isNullable:		" + isNullable + "\n");
		sb.append("primaryKeySeq:		" + primaryKeySeq + "\n");
		sb.append("comment:		" + comment + "\n");

		return sb.toString();
	}
}
