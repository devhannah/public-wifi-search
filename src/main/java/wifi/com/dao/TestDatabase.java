package wifi.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// database connection 관리 

public class TestDatabase {
	
	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	
	private static Connection connection = null;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		TestDatabase.connection = DriverManager.getConnection(url, dbUserId, dbPassword);
		return connection;
		
	}
	
	
}
