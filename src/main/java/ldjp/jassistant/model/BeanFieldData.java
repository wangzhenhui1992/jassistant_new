package ldjp.jassistant.model;

import java.io.Serializable;

import org.jdom.Element;

/**
 *
 */
public class BeanFieldData implements Serializable{

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
	 * columnName
	 */
	private String columnName = null;

	/**
	 * upperColumnName
	 */
	private String upperColumnName = null;

	/**
	 * columnType
	 */
	private String columnType = null;

	/**
	 * comment
	 */
	private String comment = null;

	/**
	 * needGetter
	 */
	private boolean needGetter = true;

	/**
	 * needSetter
	 */
	private boolean needSetter = true;

	/**
	 * defaultValue
	 */
	private String defaultValue = null;

	/**
	 * primaryKey
	 */
	private boolean primaryKey = false;


	/**
	 * Default Construction Function
	 */
	public BeanFieldData() {}


	/**
	 * get the columnName
	 *
	 * @return String columnName
	 */
	public String getColumnName() {
		return this.columnName;
	}

	/**
	 * set the columnName
	 *
	 * @param columnName columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * get the upperColumnName
	 *
	 * @return String upperColumnName
	 */
	public String getUpperColumnName() {
		return this.upperColumnName;
	}

	/**
	 * set the upperColumnName
	 *
	 * @param upperColumnName upperColumnName
	 */
	public void setUpperColumnName(String upperColumnName) {
		this.upperColumnName = upperColumnName;
	}

	/**
	 * get the columnType
	 *
	 * @return String columnType
	 */
	public String getColumnType() {
		return this.columnType;
	}

	/**
	 * set the columnType
	 *
	 * @param columnType columnType
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	/**
	 * get the comment
	 *
	 * @return String comment
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * set the comment
	 *
	 * @param comment comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * get the needGetter
	 *
	 * @return boolean needGetter
	 */
	public boolean getNeedGetter() {
		return this.needGetter;
	}

	/**
	 * set the needGetter
	 *
	 * @param needGetter needGetter
	 */
	public void setNeedGetter(boolean needGetter) {
		this.needGetter = needGetter;
	}

	/**
	 * get the needSetter
	 *
	 * @return boolean needSetter
	 */
	public boolean getNeedSetter() {
		return this.needSetter;
	}

	/**
	 * set the needSetter
	 *
	 * @param needSetter needSetter
	 */
	public void setNeedSetter(boolean needSetter) {
		this.needSetter = needSetter;
	}

	/**
	 * get the defaultValue
	 *
	 * @return String defaultValue
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * set the defaultValue
	 *
	 * @param defaultValue defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * get the primaryKey
	 *
	 * @return boolean primaryKey
	 */
	public boolean getPrimaryKey() {
		return this.primaryKey;
	}

	/**
	 * set the primaryKey
	 *
	 * @param primaryKey primaryKey
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}


	/**
	 * Method For Debug
	 *
	 * @return String
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("columnName:		" + columnName + "\n");
		sb.append("upperColumnName:		" + upperColumnName + "\n");
		sb.append("columnType:		" + columnType + "\n");
		sb.append("comment:		" + comment + "\n");
		sb.append("needGetter:		" + needGetter + "\n");
		sb.append("needSetter:		" + needSetter + "\n");
		sb.append("defaultValue:		" + defaultValue + "\n");
		sb.append("primaryKey:		" + primaryKey + "\n");

		return sb.toString();
	}


    /**
     * create xml element node
     *
     */
    public Element toElement() {
    	Element columnElement = new Element("column");

		Element columnNameElement = new Element("name");
		columnNameElement.setText(columnName);
		columnElement.addContent(columnNameElement);

		Element upperColumnNameElement = new Element("uppername");
		upperColumnNameElement.setText(upperColumnName);
		columnElement.addContent(upperColumnNameElement);

		Element columnTypeElement = new Element("type");
		columnTypeElement.setText(columnType);
		columnElement.addContent(columnTypeElement);

		Element commentElement = new Element("comment");
		commentElement.setText(comment);
		columnElement.addContent(commentElement);

		if (needGetter) {
			Element needGetterElement = new Element("getter");
			columnElement.addContent(needGetterElement);
		}

		if (needSetter) {
			Element needSetterElement = new Element("setter");
			columnElement.addContent(needSetterElement);
		}

		Element defaultValueElement = new Element("default");
		defaultValueElement.setText(defaultValue);
		columnElement.addContent(defaultValueElement);

		if (primaryKey) {
			Element primaryKeyElement = new Element("key");
			columnElement.addContent(primaryKeyElement);
		}

		return columnElement;
	}
}
