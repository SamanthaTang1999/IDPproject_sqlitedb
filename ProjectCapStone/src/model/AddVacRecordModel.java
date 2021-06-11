package model;

import java.sql.*;

import common.Log;
import common.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddVacRecordModel {
	
	Connection connection;
	public Log log = new Log();
		
		public AddVacRecordModel() {
		
			connection = SqliteConnection.Connector();
			if (connection == null) {
				log.logFile(null, "severe", "SQL connection is NULL.");
				System.exit(1);
				
			}
		}
		
	public boolean addVacRecord(Integer petID, String todayDate, String nextDate ,String injection, String vaccine) throws SQLException {
			
			PreparedStatement preparedStatement = null;
			String query = "INSERT into vaccine_records (PetID, Date, NextDate, Injection, Vaccine) values (?, ?, ?, ?, ?)";
			
			try {
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setInt(1, petID);
					preparedStatement.setString(2, todayDate);
					preparedStatement.setString(3, nextDate);
					preparedStatement.setString(4, injection);
					preparedStatement.setString(5, vaccine);
					
					
					
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
	
	
public ObservableList<String> getVaccineDog() throws SQLException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM vaccine_dog";
		  ObservableList<String> vaccineDogs = FXCollections.observableArrayList();
		
		
		try {
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				
				
				while (resultSet.next()) {
					
					vaccineDogs.add(resultSet.getString("VaccineName"));
					
				}		
		
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		
	}
	
	finally {
		preparedStatement.close();
		resultSet.close();
	}
		
		return vaccineDogs;
		
		
	}

public ObservableList<String> getVaccineCat() throws SQLException {
	
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT * FROM vaccine_cat";
	  ObservableList<String> vaccineCats = FXCollections.observableArrayList();
	
	
	try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				vaccineCats.add(resultSet.getString("VaccineName"));
				
			}
	
	
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		
	}
	
	finally {
		preparedStatement.close();
		resultSet.close();
	}
		
		return vaccineCats;
		
		
	}

public boolean addAppointmentInfo( Integer ownerID,Integer petID, String ownerName, String petName, String date, String nextInjection, String vaccine) throws SQLException {
	PreparedStatement preparedStatement = null;
	
	String query = "INSERT into upcoming_appointments (Date, OwnerID, OwnerName, PetID, PetName, Injection, Vaccine, Status) values (?, ?, ?, ?, ?, ?, ?, ?)";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, date);
			preparedStatement.setInt(2, ownerID);
			preparedStatement.setString(3, ownerName);
			preparedStatement.setInt(4, petID);
			preparedStatement.setString(5, petName);
			preparedStatement.setString(6, nextInjection);
			preparedStatement.setString(7, vaccine);
			preparedStatement.setString(8, "Confirmed");
					
			
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
			
			
		

}
