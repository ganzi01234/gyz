package service.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MyOpenconnection {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	public static void getParam(){		
		InputStream is;
		try {
			is = MyOpenconnection.class.getClassLoader().getResourceAsStream("service/dao/mysql.properties");
			Properties props=new Properties();
			props.load(is);
			driver=props.getProperty("driver");
			url=props.getProperty("url");
			username=props.getProperty("username");
			password=props.getProperty("password");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	} 
	public static Connection getconnection(){
		getParam();
		try {
			Class.forName(driver).newInstance();
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
		}
	}
}
