package ldjp.jassistant.util;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import ldjp.jassistant.common.PJConst;


public class WordManager {

    private static Properties properties = new Properties();

    private WordManager(){}


    public static String getWord(String key) {
        if (StringUtil.isEmptyWithTrim(key)){
            return PJConst.EMPTY;
        }
        Object value = properties.get(key);
        return value == null ? PJConst.EMPTY : (String) value;
    }

    public static String getWord(String key,String[] params) {
        String value = getWord(key);
        StringBuffer sb = new StringBuffer();

        if (params == null) return value;

        int index = 0;
        int pos = 0;
        while ((pos = value.indexOf("%", index)) != -1) {
            sb.append(value.substring(index, pos));
            char paramIndex = value.charAt(pos + 1);
            if (Character.isDigit(paramIndex)
                    && String.valueOf(paramIndex).getBytes().length == 1) {
                sb.append(params[Integer.parseInt(String.valueOf(paramIndex)) - 1]);
                index = pos + 2;
            } else {
                index = pos + 1;
            }
        }
        sb.append(value.substring(index));

        return sb.toString();
    }

    public static String getWord(String key,String param){
        if (param == null) {
            param = PJConst.EMPTY;
        }
        return getWord(key,new String[]{param});
    }

    /**
     * Initialization
     *
     * @param in
     */
    public static synchronized void init(String in) throws MissingResourceException {
        ResourceBundle bundle = ResourceBundle.getBundle(in);
        Enumeration<String> enums = bundle.getKeys();
        Object key = null;
        Object value = null;
        while ( enums.hasMoreElements() ) {
            key = enums.nextElement();
            value = bundle.getString(key.toString());
            properties.put(key, value);
        }
    }
}
