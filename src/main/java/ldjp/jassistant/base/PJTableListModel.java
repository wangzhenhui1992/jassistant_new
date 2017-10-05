package ldjp.jassistant.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractListModel;

/**
 * the model for table list
 */
public class PJTableListModel extends AbstractListModel {

    private static final long serialVersionUID = 1L;

    private Collection<String> items;

    private HashMap<String, String> hideItems = null;

    public PJTableListModel() {
    }

    public void setDataSet(Collection<String> dataSet) {
        items = dataSet;
        fireContentsChanged(this, 0, getSize());
    }

    public void setDataSet(Collection<String> dataSet, HashMap<String, String> hideItems) {
        this.hideItems = hideItems;
        this.items = dataSet;
        fireContentsChanged(this, 0, getSize());
    }

    public PJTableListModel(Collection<String> dataSet) {
        setDataSet(dataSet);
    }

    public PJTableListModel(Collection<String> dataSet, HashMap<String, String> hideItems) {
        this.hideItems = hideItems;
        setDataSet(dataSet);
    }

    public void setHideItems(HashMap<String, String> hideItems) {
        this.hideItems = hideItems;
        fireContentsChanged(this, 0, getSize());
    }

    public HashMap<String, String> getHideItems() {
        return this.hideItems;
    }

    public int getSize() {
        if (items == null) {
            return 0;
        } else {
            if (hideItems == null) {
                return items.size();
            } else {
                Iterator<String> it = items.iterator();
                int count = 0;

                while (it.hasNext()) {
                    if (!hideItems.containsKey(it.next())) {
                        count++;
                    }
                }

                return count;
            }
        }
    }

    public void addDataSet(Collection<String> dataSet) {
        Iterator<String> it = dataSet.iterator();

        while (it.hasNext()) {
            add(it.next());
        }
    }

    public void add(String o) {
        if (!contains(o)) {
            items.add(o);
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void remove(Object o) {
        if (contains(o)) {
            items.remove(o);
            fireContentsChanged(this, 0, getSize());
        }
    }

    public boolean contains(Object o) {
        return items.contains(o);
    }

    public Collection<String> getDataSet() {
        return items;
    }

    public Object getElementAt(int index) {
        Object tmpObject;

        if (getSize() > 0 && index >= 0 && index < getSize()) {
            Iterator<String> it = items.iterator();
            int i = 0;

            while (it.hasNext()) {
                tmpObject = it.next();

                if (hideItems != null && hideItems.containsKey(tmpObject)) {
                    continue;
                }

                if ((i++) == index) {
                    return (String) tmpObject;
                }
            }
        }

        return null;
    }

    public int like(String o, int lastSearchIdx) {
        String obj;
        Iterator<String> it = items.iterator();
        int i = 0;

        while (it.hasNext()) {
            obj = it.next();

            if (hideItems != null && hideItems.containsKey(obj)) {
                continue;
            }

            if (i > lastSearchIdx
                    && obj.toUpperCase().indexOf(o.toUpperCase()) >= 0) {
                return i;
            }
            i++;
        }

        return -1;
    }
}
