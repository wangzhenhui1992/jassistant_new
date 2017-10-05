package ldjp.jassistant.common;

/**
 * refreshable interface
 */
public interface Refreshable {

	/**
	 * set the parameter
	 */
	public void setParam(String tableType, String tableName);

	/**
	 * refresh method
	 */
	public void resetData();

	/**
	 * set refreshable flag
	 */
	public void setRefreshable(boolean b);

	/**
	 * check it is need to refresh
	 */
	public boolean isReFreshable();

	/**
	 * clear all table data
	 * Refreshable method
	 */
	public void clearData();
}
