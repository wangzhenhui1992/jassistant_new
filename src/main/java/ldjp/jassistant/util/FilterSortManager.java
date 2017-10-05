package ldjp.jassistant.util;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import ldjp.jassistant.model.TableFilterSortData;

/**
 */
public class FilterSortManager {

	/**
	 * key is the connection string
	 * value is also a HashMap, the map's key is Table Name
	 * value is a TableFilterSortData object
	 */
	static HashMap<String,Object> filterSortMap = new HashMap<String,Object>();


	/**
	 * default constructor
	 */
	public FilterSortManager() {}

	/**
	 * initialize the original filter and sort
	 */
	@SuppressWarnings("unchecked")
    public static synchronized void init(File file) {
		if (file.exists()) {
			try {
				filterSortMap = (HashMap<String,Object>) FileManager.readObjectFromFile(file);
			} catch (Exception e) {}
		}
	}

	/**
	 * update the filter and sort value
	 */
	public static void updateFile(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			FileManager.printObjectToFile(file, filterSortMap);
		} catch (Exception e) {}
	}

	/**
	 * show whether it has filter or sort
	 */
	public static boolean hasFilterAndSort(String connectionStr, String table) {
		String filter = getFilter(connectionStr, table);
		if (!StringUtil.isEmpty(filter)) {
			return true;
		}
		String sort = getSort(connectionStr, table);
		if (!StringUtil.isEmpty(sort)) {
			return true;
		}

		return false;
	}

	/**
	 * get the sort string
	 */
	@SuppressWarnings("unchecked")
    public static String getSort(String connectionStr, String table) {
        HashMap<String, Object> tableFSMap = (HashMap<String, Object>) filterSortMap
                .get(connectionStr);

		if (tableFSMap != null) {
			TableFilterSortData tableFSData = (TableFilterSortData) tableFSMap.get(table);

			if (tableFSData != null) {
				Vector<Object> sortList = tableFSData.getSort();

				if (sortList == null || sortList.isEmpty()) {
					return null;
				}

				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < sortList.size(); i++) {
					sb.append(sortList.get(i));
					if (i < sortList.size() - 1) {
						sb.append(",");
					}
				}

				return sb.toString();
			}
		}

		return null;
	}

	/**
	 * get the sort as vector
	 */
	@SuppressWarnings("unchecked")
    public static Vector<Object> getSortVector(String connectionStr, String table) {
        HashMap<String, Object> tableFSMap = (HashMap<String, Object>) filterSortMap
                .get(connectionStr);

		if (tableFSMap != null) {
			TableFilterSortData tableFSData = (TableFilterSortData) tableFSMap.get(table);

			if (tableFSData != null) {
				return tableFSData.getSort();
			}
		}

		return null;
	}

	/**
	 * set sort data from the GUI
	 */
    @SuppressWarnings("unchecked")
    public static void setSort(String connectionStr, String table,
            Vector<Object> sortData) {
        HashMap<String, Object> tableFSMap = (HashMap<String, Object>) filterSortMap
                .get(connectionStr);

		if (tableFSMap == null) {
			tableFSMap = new HashMap<String, Object>();
			filterSortMap.put(connectionStr, tableFSMap);
		}

		TableFilterSortData tableFSData = (TableFilterSortData) tableFSMap.get(table);
		if (tableFSData == null) {
			tableFSData = new TableFilterSortData();
			tableFSMap.put(table, tableFSData);
		}

		tableFSData.setSort(sortData);
	}

	/**
	 * get the table filter
	 */
	@SuppressWarnings("unchecked")
    public static String getFilter(String connectionStr, String table) {
        HashMap<String, Object> tableFSMap = (HashMap<String, Object>) filterSortMap
                .get(connectionStr);

		if (tableFSMap != null) {
			TableFilterSortData tableFSData = (TableFilterSortData) tableFSMap.get(table);

			if (tableFSData != null) {
				return tableFSData.getFilter();
			}
		}

		return null;
	}

	/**
	 * set the filter to the table
	 */
	@SuppressWarnings("unchecked")
    public static void setFilter(String connectionStr, String table, String filter) {
        HashMap<String, Object> tableFSMap = (HashMap<String, Object>) filterSortMap
                .get(connectionStr);

		if (tableFSMap == null) {
			tableFSMap = new HashMap<String,Object>();
			filterSortMap.put(connectionStr, tableFSMap);
		}

		TableFilterSortData tableFSData = (TableFilterSortData) tableFSMap.get(table);
		if (tableFSData == null) {
			tableFSData = new TableFilterSortData();
			tableFSMap.put(table, tableFSData);
		}

		tableFSData.setFilter(filter);
	}

	/**
	 * clear filter and sorter by connectionStr
	 *
	 */
	@SuppressWarnings("unchecked")
    public static void clearFilterSort(String connectionStr) {
		HashMap<String, Object> tableFSMap = (HashMap<String, Object>) filterSortMap.get(connectionStr);

		if (tableFSMap != null) {
			filterSortMap.remove(connectionStr);
		}
	}
}
