package ldjp.jassistant.model;

import java.io.Serializable;
import java.util.Vector;

import ldjp.jassistant.util.CollectionUtils;


public class DispatchModel implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private Vector<String> clmNms;

    private Vector<Class<?>> types;

    private Vector<Vector<Object>> vals;

    private Vector<Integer> sizes;

    private Vector<Boolean> keys;

    private Vector<String> cmts;

    public void setRowVals(int row, Vector<Object> newData){
        if (CollectionUtils.isEmpty(vals) || row >= vals.size()) return;
        vals.set(row, newData);
    }

    public void removeRow(int row){
        vals.remove(row);
    }

    public void insertRow(int row,Vector<Object> rowVals){
        vals.add(row,rowVals);
    }

    public void addRow(Vector<Object> rowVals){
        if (null == vals) {
            vals = new Vector<Vector<Object>>();
        }
        vals.add(rowVals);
    }

    public void addAllRows(Vector<Vector<Object>> rowsVals){
        if (null == vals) {
            vals = rowsVals;
        } else {
            vals.addAll(rowsVals);
        }
    }

    public Vector<Object> getRowVals(int row){
        return CollectionUtils.isEmpty(vals) || row >= vals.size() ? null : vals.get(row);
    }

    public int size(){
        return clmNms.size() + types.size() + vals.size();
    }

    public boolean isEmpty(){
        return CollectionUtils.isEmpty(clmNms)
               && CollectionUtils.isEmpty(types)
               && CollectionUtils.isEmpty(vals);
    }

    /**
     * @return clmNms
     */
    public Vector<String> getClmNms() {
        return clmNms;
    }

    /**
     * @param clmNms column names to set
     */
    public void setClmNms(Vector<String> clmNms) {
        this.clmNms = clmNms;
    }

    /**
     * @return types
     */
    public Vector<Class<?>> getTypes() {
        return types;
    }

    /**
     * @param types types to set
     */
    public void setTypes(Vector<Class<?>> types) {
        this.types = types;
    }

    /**
     * @return vals
     */
    public Vector<Vector<Object>> getVals() {
        return vals;
    }

    /**
     * @param vals values to set
     */
    public void setVals(Vector<Vector<Object>> vals) {
        this.vals = vals;
    }

    /**
     * @return sizes
     */
    public Vector<Integer> getSizes() {
        return sizes;
    }

    /**
     * @param sizes sizes to set
     */
    public void setSizes(Vector<Integer> sizes) {
        this.sizes = sizes;
    }

    /**
     * @return keys
     */
    public Vector<Boolean> getKeys() {
        return keys;
    }

    /**
     * @param keys keys to set
     */
    public void setKeys(Vector<Boolean> keys) {
        this.keys = keys;
    }


    /**
     * @return cmts
     */
    public Vector<String> getCmts() {
        return cmts;
    }


    /**
     * @param cmts comments to set
     */
    public void setCmts(Vector<String> cmts) {
        this.cmts = cmts;
    }

}
