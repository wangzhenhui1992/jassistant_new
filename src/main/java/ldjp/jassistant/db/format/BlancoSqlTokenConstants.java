
package ldjp.jassistant.db.format;

/**
 * Class : Constants about blancoSqlFormatter's token type
 */
public class BlancoSqlTokenConstants {
    /**
     * Space
     */
    public static final int SPACE = 0;

    /**
     * Mark with two characters like [<=]
     */
    public static final int SYMBOL = 1;

    /**
     * Keyword like [SELECT],[ORDER] and etc
     */
    public static final int KEYWORD = 2;

    /**
     * Name of tables,columns and etc
     */
    public static final int NAME = 3;

    /**
     * Value of Integer,Float,String and etc
     */
    public static final int VALUE = 4;

    /**
     * Comment
     */
    public static final int COMMENT = 5;

    /**
     * End of SQL
     */
    public static final int END = 6;

    /**
     * Unavailable token
     */
    public static final int UNKNOWN = 7;
}
