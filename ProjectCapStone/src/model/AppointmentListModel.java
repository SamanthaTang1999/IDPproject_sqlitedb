package model;

import java.sql.*;

import common.Log;
import common.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentListModel {
	

	Connection connection;
	public Log log = new Log();
		
		public  AppointmentListModel() {
		
			connection = SqliteConnection.Connector();
			if (connection == null) {
				log.logFile(null, "severe", "SQL connection is NULL.");
				System.exit(1);
				
			}
		}
		
		
		public ObservableList<String> getAppointmentList(String date) throws SQLException {
			
			  ObservableList<String> appointmentList = FXCollections.observableArrayList();

			    PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				String query = "SELECT * FROM upcoming_appointments WHERE Date = ? ";
				
				try {
						preparedStatement = connection.prepareStatement(query);
						preparedStatement.setString(1, date);
						resultSet = preparedStatement.executeQuery();
						
						while (resultSet.next()) {
							
							appointmentList.add(resultSet.getInt("AppID") + " . " +resultSet.getString("OwnerName") + "'s " + resultSet.getString("PetName") + " " + resultSet.getString("Injection") +
									" injection " + resultSet.getString("Vaccine"));
						}
						
					
				} catch (Exception e) {
					log.logFile(e, "severe", e.getMessage());
					e.printStackTrace();
				}
				
				finally {
					preparedStatement.close();
					resultSet.close();
				}

			    return appointmentList;
		}
		
		
		
		
		
		
		
		
		

}
