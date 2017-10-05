package ldjp.jassistant.db.format.valueobject;

/**
 * The source code was automatically created according to blancoFramework
 * and this class is a part of blancoSqlFormatter
 */
public class AbstractBlancoSqlToken {
    /**
     * Type
     */
    private int type;

    /**
     * The string of the token filed
     */
    private String str;

    /**
     * The position of the token of reserved words and comment and value and etc
     * It is presenting the position of a string and originally it's value is 0 while
     * the default value is -1 which means that the position is unavailable.
     */
    private int pst = -1;


    /**
     * Get the type
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Set the type
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Get the string
     * @return str
     */
    public String getStr() {
        return str;
    }

    /**
     * Set the String
     * @param str the string to set
     */
    public void setStr(String str) {
        this.str = str;
    }

    /**
     * Get the position
     * @return pst
     */
    public int getPst() {
        return pst;
    }

    /**
     * @param pst the position to set
     */
    public void setPst(int pst) {
        this.pst = pst;
    }

    /**
     * Get the string presentation of this value object B
     * @return the string presentation of this object
     */
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.commons.sql.format.valueobject.AbstractBlancoSqlToken[");
        buf.append("type=" + type);
        buf.append(",string=" + str);
        buf.append(",pos=" + pst);
        buf.append("]");
        return buf.toString();
    }
}
