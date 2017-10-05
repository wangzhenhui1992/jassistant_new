package ldjp.jassistant.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.jdom.Element;

/**
 *
 * the data bean structure
 *
 */

public class BeanData implements Serializable {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
	 * packageDefine
	 */
	private String packageDefine = null;

	/**
	 * importClassesDefine
	 */
	private String importClassesDefine = null;

	/**
	 * classDescription
	 */
	private String classDescription = null;

	/**
	 * author
	 */
	private String author = null;

	/**
	 * version
	 */
	private String version = null;

	/**
	 * className
	 */
	private String className = null;

	/**
	 * needToString
	 */
	private boolean needToString = true;

	/**
	 * needDefaultConstructor
	 */
	private boolean needDefaultConstructor = true;

	/**
	 * beanFieldDataList
	 */
	private ArrayList<BeanFieldData> beanFieldDataList = null;

	/**
	 * serializable
	 */
	private String serializable = null;

	/**
	 * commentNullToName
	 */
	private boolean commentNullToName = true;


	/**
	 * Default Construction Function
	 */
	public BeanData() {}


	/**
	 * get the packageDefine
	 *
	 * @return String packageDefine
	 */
	public String getPackageDefine() {
		return this.packageDefine;
	}

	/**
	 * set the packageDefine
	 *
	 * @param packageDefine packageDefine
	 */
	public void setPackageDefine(String packageDefine) {
		this.packageDefine = packageDefine;
	}

	/**
	 * get the importClassesDefine
	 *
	 * @return String importClassesDefine
	 */
	public String getImportClassesDefine() {
		return this.importClassesDefine;
	}

	/**
	 * set the importClassesDefine
	 *
	 * @param importClassesDefine importClassesDefine
	 */
	public void setImportClassesDefine(String importClassesDefine) {
		this.importClassesDefine = importClassesDefine;
	}

	/**
	 * get the classDescription
	 *
	 * @return String classDescription
	 */
	public String getClassDescription() {
		return this.classDescription;
	}

	/**
	 * set the classDescription
	 *
	 * @param classDescription classDescription
	 */
	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}

	/**
	 * get the author
	 *
	 * @return String author
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * set the author
	 *
	 * @param author author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * get the version
	 *
	 * @return String version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * set the version
	 *
	 * @param version version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * get the className
	 *
	 * @return String className
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * set the className
	 *
	 * @param className className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * get the needToString
	 *
	 * @return boolean needToString
	 */
	public boolean getNeedToString() {
		return this.needToString;
	}

	/**
	 * set the needToString
	 *
	 * @param needToString needToString
	 */
	public void setNeedToString(boolean needToString) {
		this.needToString = needToString;
	}

	/**
	 * get the needDefaultConstructor
	 *
	 * @return boolean needDefaultConstructor
	 */
	public boolean getNeedDefaultConstructor() {
		return this.needDefaultConstructor;
	}

	/**
	 * set the needDefaultConstructor
	 *
	 * @param needDefaultConstructor needDefaultConstructor
	 */
	public void setNeedDefaultConstructor(boolean needDefaultConstructor) {
		this.needDefaultConstructor = needDefaultConstructor;
	}

	/**
	 * get the beanFieldDataList
	 *
	 * @return ArrayList beanFieldDataList
	 */
	public ArrayList<BeanFieldData> getBeanFieldDataList() {
		return this.beanFieldDataList;
	}

	/**
	 * set the beanFieldDataList
	 *
	 * @param beanFieldDataList beanFieldDataList
	 */
	public void setBeanFieldDataList(ArrayList<BeanFieldData> beanFieldDataList) {
		this.beanFieldDataList = beanFieldDataList;
	}

	/**
	 * get the Serializable
	 *
	 * @return String Serializable
	 */
	public String getSerializable() {
		return this.serializable;
	}

	/**
	 * set the serializable
	 *
	 * @param serializable serializable
	 */
	public void setSerializable(String serializable) {
		this.serializable = serializable;
	}

	/**
	 * get the commentNullToName
	 *
	 * @return boolean commentNullToName
	 */
	public boolean getCommentNullToName() {
		return this.commentNullToName;
	}

	/**
	 * set the commentNullToName
	 *
	 * @param commentNullToName commentNullToName
	 */
	public void setCommentNullToName(boolean commentNullToName) {
		this.commentNullToName = commentNullToName;
	}


	/**
	 * Method For Debug
	 *
	 * @return String
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("packageDefine:		" + packageDefine + "\n");
		sb.append("importClassesDefine:		" + importClassesDefine + "\n");
		sb.append("classDescription:		" + classDescription + "\n");
		sb.append("author:		" + author + "\n");
		sb.append("version:		" + version + "\n");
		sb.append("className:		" + className + "\n");
		sb.append("needToString:		" + needToString + "\n");
		sb.append("needDefaultConstructor:		" + needDefaultConstructor + "\n");
		sb.append("beanFieldDataList:		" + beanFieldDataList + "\n");
		sb.append("serializable:		" + serializable + "\n");
		sb.append("commentNullToName:		" + commentNullToName + "\n");

		return sb.toString();
	}

    /**
     * get Element
     *
     */
    public Element toElement() {
		Element rootElement = new Element("root");

		Element classNameElement = new Element("class_name");
		classNameElement.setText(className);
		rootElement.addContent(classNameElement);

		Element packageDefineElement = new Element("package_define");
		packageDefineElement.setText(packageDefine);
		rootElement.addContent(packageDefineElement);

//		Element importClassesDefineElement = new Element("");

		Element classDescriptionElement = new Element("class_description");
		classDescriptionElement.setText(classDescription);
		rootElement.addContent(classDescriptionElement);

//		Element authorElement = new Element("author");
//		Element versionElement = new Element("version");
//		Element needToStringElement = new Element("tostring");
//		Element needDefaultConstructorElement = new Element("constructor");
//		Element beanFieldDataList = null;
//		Element serializableElement = new Element("serializable");

		return rootElement;
	}
}
