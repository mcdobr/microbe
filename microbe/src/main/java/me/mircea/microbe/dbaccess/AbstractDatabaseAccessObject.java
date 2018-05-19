package me.mircea.microbe.dbaccess;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class AbstractDatabaseAccessObject {
	protected Connection conn;
	protected PreparedStatement statement;
	protected Properties sql;
	
	
	protected AbstractDatabaseAccessObject() {
		try {
			
			String rootPath = Thread.currentThread()
					.getContextClassLoader().getResource("").getPath();
			
			
			Properties dbProps = new Properties();
			dbProps.load(new FileInputStream(rootPath + "db.properties"));
			
			sql = new Properties();
			sql.load(new FileInputStream(rootPath + "sql.properties"));
			
			Class.forName(dbProps.getProperty("jdbcDriver"));
			conn = DriverManager.getConnection(dbProps.getProperty("url"),
												dbProps.getProperty("username"),
												dbProps.getProperty("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected ResultSet getResourceById(String sqlStatement, Integer id) throws SQLException {
		ResultSet rs = null;
		
		statement = conn.prepareStatement(sql.getProperty(sqlStatement));
		statement.setInt(1, id);
		
		rs = statement.executeQuery();
		return rs;
	}
	
	protected boolean deleteEntity(String sqlStatement, Integer id) throws SQLException {
		int rowsAffected = 0;
		statement = conn.prepareStatement(sql.getProperty(sqlStatement));
			statement.setInt(1, id);
		rowsAffected = statement.executeUpdate();

		return (rowsAffected == 1);
	}
}
