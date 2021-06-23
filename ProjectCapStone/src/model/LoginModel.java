package model;

import java.sql.*;

import common.Log;
import common.SqliteConnection;

public class LoginModel {
	
	Connection connection;
	public Log log = new Log();
	
	public LoginModel() {	
	
		connection = SqliteConnection.Connector();
		if (connection == null) {
			log.logFile(null, "severe", "VetNavigate unable to connect to SQL");
		}
		
	}
	
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	// need to create other tables like users, vaccine_cat/dog, breed_cats/dog, and etc
	// need to add default user "admin, 1"
	// need to add credential email and password
	// need to populate vaccine and breed tables
	// add more items if needed, things that I may miss
	
	public void populateTables() {
		System.out.println("Populate table");
		String[] sqls = {};		//
		try {
			
			for (String sql : sqls)
			{
				Statement statement = connection.createStatement();
				
				if (statement.execute(sql)) {
					System.out.println("Populate table success");
					log.logFile(null, "info", "New table is created");
				}
				
			}
				
			
		} catch (Exception e) {
			
			log.logFile(e, "severe", e.getMessage());
		}
	}
	
	public boolean isLogin(String user, String pass) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM users where UserName = ? and Password = ?";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				return true;
				
			}
			else {
				return false;
			}
			
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		finally {
			preparedStatement.close();
			resultSet.close();
		}
	}

}

