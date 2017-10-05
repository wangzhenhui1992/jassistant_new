package ldjp.jassistant.model;

import java.io.Serializable;

/**
 */
public class ReportSQLDesc implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * SQL name
     */
    private String name = null;

    /**
     * SQL description
     */
    private String desc = null;

    /**
     * SQL text
     */
    private String text = null;

    /**
     * skip flag
     */
    private boolean skip = false;


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
     * @return description
     */
    public String getDesc() {
        return desc;
    }


    /**
     * @param desc  description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }


    /**
     * @return text
     */
    public String getText() {
        return text;
    }


    /**
     * @param text  text
     */
    public void setText(String text) {
        this.text = text;
    }


    /**
     * @return skip
     */
    public boolean isSkip() {
        return skip;
    }


    /**
     * @param skip  skip
     */
    public void setSkip(boolean skip) {
        this.skip = skip;
    }


    /**
     * Method For Debug
     *
     * @return String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("name:        " + name + "\n");
        sb.append("desc:        " + desc + "\n");
        sb.append("text:        " + text + "\n");
        sb.append("skip:        " + skip + "\n");

        return sb.toString();
    }

}
