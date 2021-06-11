package model;

import java.sql.*;

import common.Log;
import common.SqliteConnection;


public class AddMedRecordModel {
	
	Connection connection;
	public Log log = new Log();
		
		public AddMedRecordModel() {
		
			connection = SqliteConnection.Connector();
			if (connection == null) {
				log.logFile(null, "severe", "SQL connection is NULL.");
				System.exit(1);
				
			}
		}
		
		public boolean addMedRecord(Integer petID, String meddate, String time ,String medication, String dosage, String frequency, String notes) throws SQLException {
			
			PreparedStatement preparedStatement = null;
			String query = "INSERT into medication_records (PetID, MedDate, Time, Medication, Dosage, Frequency, Notes) values (?, ?, ?, ?, ?, ?, ?)";
			
			try {
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setInt(1, petID);
					preparedStatement.setString(2, meddate);
					preparedStatement.setString(3, time);
					preparedStatement.setString(4, medication);
					preparedStatement.setString(5, dosage);
					preparedStatement.setString(6, frequency);
					preparedStatement.setString(7, notes);	
					
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

