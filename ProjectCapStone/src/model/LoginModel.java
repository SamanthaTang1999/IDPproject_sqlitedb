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
			//System.exit(1);
			
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

