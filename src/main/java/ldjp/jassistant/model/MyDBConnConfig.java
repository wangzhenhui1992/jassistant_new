/**
 *
 */
package ldjp.jassistant.model;

import java.util.ArrayList;

/**
 * Setting Information saved by connections
 */
public class MyDBConnConfig {

    ArrayList<String> favorTableList = new ArrayList<String>();

    /**
     *
     */
    public MyDBConnConfig() {
    }

    /**
     * @return favorTableList
     */
    public ArrayList<String> getFavorTableList() {
        return favorTableList;
    }

    /**
     * @param favorTableList favorTableList
     */
    public void setFavorTableList(ArrayList<String> favorTableList) {
        this.favorTableList = favorTableList;
    }

}
