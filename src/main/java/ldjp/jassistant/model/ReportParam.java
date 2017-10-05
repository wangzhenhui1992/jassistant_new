/**
 *
 */
package ldjp.jassistant.model;

import java.io.Serializable;

/**
 *
 */
public class ReportParam implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * name
     */
    private String name = null;

    /**
     * value
     */
    private String value = null;


    /**
     * get the name
     *
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * set the name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the value
     *
     * @return String value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * set the value
     *
     * @param value new value
     */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * Method For Debug
     *
     * @return String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("name:        " + name + "\n");
        sb.append("value:        " + value + "\n");

        return sb.toString();
    }

}
