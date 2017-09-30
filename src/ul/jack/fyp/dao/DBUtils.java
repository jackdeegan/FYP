package ul.jack.fyp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import ul.jack.fyp.constants.Constants;

public class DBUtils {
	
	private static String host = "localhost";
	private static String port = "3306";
	private static String name = "appDB";
	private static String username = "jdeegan";
	private static String password = "jack";
	
	private static DataSource datasource;
	
	private static DataSource getDatasource() {
		if(datasource == null) {
			setDatasource();
		}
		return datasource;
	}
	
	private static void setDatasource() {
		datasource = getDataSource(host, port, name, username, password);
	}
	
	private static DataSource getDataSource(String host, String port, String name, String username, String password) {
		PoolProperties p = new PoolProperties();
		String dbClass = "";
		String dbUrl = "";

		
		dbClass = Constants.DB_CLASS_MARIADB.getValue();
		dbUrl = "jdbc:mariadb://" + host + "\\" + name + ":" + port;
		
		
		p.setUrl(dbUrl);
		p.setDriverClassName(dbClass);
		p.setUsername(username);
		p.setPassword(password);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT * from dual");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);
		return datasource;
	}
	

	

	
	/**
	 * Get a connection to the application database.
	 * Connection details are outlined in the properties file and the username and
	 * password are taken from the logindb.environments table
	 * @return
	 */
	public static Connection getConnection() {
		try {
			return getDatasource().getConnection();
		} catch(SQLException e) {
			return null;
		}
	}
	
	public static Connection getTestConnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(
	                    "jdbc:mariadb://127.0.0.1:3306/appDB", username, password);
			
			
			return conn;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dbSafe(String value) {
		if (value == null) {
			return "";
		} else {
			StringBuffer output = new StringBuffer();

			try {
				for (int e = 0; e < value.length(); ++e) {
					if (value.charAt(e) == 39) {
						output.append("\'\'");
					} else {
						output.append(value.charAt(e));
					}
				}
			} catch (Exception e) {
				
			}

			return output.toString();
		}
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch(SQLException e) {
			
		}
	}
	
	public static void close(PreparedStatement ps) {
		try {
			if(ps != null) {
				ps.close();
			}
		} catch(SQLException e) {
			
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(SQLException e) {
			
		}
	}
	
	public static void close(PreparedStatement ps, ResultSet rs) {
		close(rs);
		close(ps);
	}
	
	public static void close(Connection conn, PreparedStatement ps) {
		close(ps);
		close(conn);
	}
	
	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		close(rs);
		close(ps);
		close(conn);
	}
	
}
