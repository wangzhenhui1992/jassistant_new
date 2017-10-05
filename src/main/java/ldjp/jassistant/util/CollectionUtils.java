package ldjp.jassistant.util;

import java.util.Collection;
import java.util.Map;


public class CollectionUtils {

    private CollectionUtils(){
    }

    public static boolean isEmpty(Collection<?> c){
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m){
        return m == null || m.isEmpty();
    }
}
