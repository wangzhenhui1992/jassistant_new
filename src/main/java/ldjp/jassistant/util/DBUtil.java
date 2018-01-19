package ldjp.jassistant.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import ldjp.jassistant.common.PJConst;

/**
 * DB Utility Class
 */

public class DBUtil {

	public static Connection getConnection(String driverName,
											String connectionURL,
											String userName,
											String password)
									throws SQLException {
		Connection conn = null;

		try {
			Driver driver = (Driver) ResourceManager.loadDynamicJar(driverName).newInstance();
			Properties props = new Properties();
			if (userName != null) {
			    props.setProperty("user", userName);
			}
			if (password != null) {
				props.setProperty("password", password);
			}
			if ("com.ibm.db2.jcc.DB2Driver".equals(driverName)) {
			    props.setProperty("retrieveMessagesFromServerOnGetMessage", "true");
			}
			props.setProperty("remarks", "true");
			props.setProperty("useInformationSchema", "true");
			props.setProperty("nullNamePatternMatchesAll", "true");
			props.setProperty("zeroDateTimeBehavior", "CONVERT_TO_NULL");
			conn = driver.connect(connectionURL, props);

			return conn;
		} catch (ClassNotFoundException ce) {
			try {
				Class.forName(driverName).newInstance();
				conn = DriverManager.getConnection(connectionURL, userName, password);

				return conn;
			} catch (SQLException ex) {
			    ex.printStackTrace();
				throw ex;
			} catch (Exception e) {
			    e.printStackTrace();
				throw new SQLException(e.getMessage());
			}
		} catch (Exception e) {
		    e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * test if the connection support transaction
	 *
	 */
	public static boolean isTransactionSupported(Connection conn) {
		try {
			DatabaseMetaData dbMetaData = conn.getMetaData();
			return dbMetaData.supportsTransactions();
		} catch (SQLException se) {
			return false;
		}
	}

	/**
	 * get driver name
	 *
	 */
	public static String getDriverName(Connection conn) {
		if (conn == null) {
			return PJConst.EMPTY;
		}

		try {
			DatabaseMetaData dbMetaData = conn.getMetaData();
			return dbMetaData.getDriverName();
		} catch (SQLException se) {
			return PJConst.EMPTY;
		}
	}
}
