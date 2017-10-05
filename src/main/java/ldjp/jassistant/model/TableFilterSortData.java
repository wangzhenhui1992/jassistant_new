package ldjp.jassistant.model;

import java.io.Serializable;
import java.util.Vector;

/**
 */
public class TableFilterSortData implements Serializable {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
	 * sort value
	 */
	private Vector<Object> sort = null;

	/**
	 * filter value
	 */
	private String filter = null;


	/**
	 * Get the sort value
	 *
	 * @return ArrayList sort value
	 */
	public Vector<Object> getSort() {
		return this.sort;
	}

	/**
	 * Set the sort value
	 *
	 * @param sort sort value
	 */
	public void setSort(Vector<Object> sort) {
		this.sort = sort;
	}

	/**
	 * Get the filter value
	 *
	 * @return String filter value
	 */
	public String getFilter() {
		return this.filter;
	}

	/**
	 * Set the filter value
	 *
	 * @param filter filter value
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Method For Debug
	 *
	 * @return String
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("sort:		" + sort + "\n");
		sb.append("filter:		" + filter + "\n");

		return sb.toString();
	}
}
