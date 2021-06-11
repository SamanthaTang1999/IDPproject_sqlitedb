package model;

import java.sql.*;

import common.AppointmentInfo;
import common.Log;
import common.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentInfoModel {
	
	Connection connection;
	public Log log = new Log();
		
		public AppointmentInfoModel() {
		
			connection = SqliteConnection.Connector();
			if (connection == null) {
				log.logFile(null, "severe", "SQL connection is NULL.");
				System.exit(1);
				
			}
		}
		
		
		public ObservableList<AppointmentInfo> getAppointmentInfo(Integer appID) throws SQLException {
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String query = "SELECT * FROM upcoming_appointments WHERE AppID = ?";
			 ObservableList<AppointmentInfo> appointmentInfo = FXCollections.observableArrayList();
			
			
			try {
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setInt(1, appID);
					resultSet = preparedStatement.executeQuery();
					
					while (resultSet.next()) {
						
						appointmentInfo.add(new AppointmentInfo(resultSet.getString("Date"), resultSet.getString("OwnerName"), resultSet.getString("PetName"),
								resultSet.getString("Injection"), resultSet.getString("Vaccine"), resultSet.getString("Status")));
						
					}
			
			
			} catch (Exception e) {
				log.logFile(e, "severe", e.getMessage());
				e.printStackTrace();
				
			}
			
			finally {
				preparedStatement.close();
				resultSet.close();
			}
				
				return appointmentInfo;
				
				
			}
		
		
public boolean completeAppointment(Integer appID) throws SQLException {
			
			PreparedStatement preparedStatement = null;
			String query = "UPDATE upcoming_appointments set Status = ? WHERE AppID = ?";
			
			try {
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, "Completed");
					preparedStatement.setInt(2, appID);
				
					
				if (preparedStatement.executeUpdate() == 1) {		
				
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
				
			}
		}


public boolean editAppointmentDate(Integer appID, String date) throws SQLException {
	
	PreparedStatement preparedStatement = null;
	String query = "UPDATE upcoming_appointments set Date = ? WHERE AppID = ?";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, date);
			preparedStatement.setInt(2, appID);
		
			
		if (preparedStatement.executeUpdate() == 1) {		
		
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
		
	}
}

public Integer getOwnerID(Integer appID) throws SQLException {
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT OwnerID FROM upcoming_appointments WHERE AppID = ?";
	Integer ownerID = null;
	
	try {
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, appID);
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
		ownerID = resultSet.getInt("OwnerID");
			
		}


} catch (Exception e) {
	log.logFile(e, "severe", e.getMessage());
	e.printStackTrace();

	
}

finally {
	preparedStatement.close();
	resultSet.close();
}
	return ownerID;
	
}

public String getOwnerEmail(Integer ownerID) throws SQLException {
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT Email FROM owners WHERE OwnerID = ?";
	String email = "";
	
	try {
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, ownerID);
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
		email = resultSet.getString("Email");
			
		}


} catch (Exception e) {
	log.logFile(e, "severe", e.getMessage());
	e.printStackTrace();

	
}

finally {
	preparedStatement.close();
	resultSet.close();
}
	return email;
	
}

public String getCompanyEmail() throws SQLException {
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT Email FROM email_credentials";
	String email = "";
	
	try {
		preparedStatement = connection.prepareStatement(query);
		
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
		email = resultSet.getString("Email");
			
		}


} catch (Exception e) {
	log.logFile(e, "severe", e.getMessage());
	e.printStackTrace();

	
}

finally {
	preparedStatement.close();
	resultSet.close();
}
	return email;
	
}

public String getCompanyEmailPassword() throws SQLException {
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT Password FROM email_credentials";
	String password = "";
	
	try {
		preparedStatement = connection.prepareStatement(query);
		
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
			password = resultSet.getString("Password");
			
		}


} catch (Exception e) {
	log.logFile(e, "severe", e.getMessage());
	e.printStackTrace();

	
}

finally {
	preparedStatement.close();
	resultSet.close();
}
	return password;
	
}
	
	
	

		
		
		
		
		
		
		
		

}
