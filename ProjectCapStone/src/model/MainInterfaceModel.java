package model;

import java.sql.*;

import common.Log;
import common.MedicationRecord;
import common.SqliteConnection;
import common.Owner;
import common.Pet;
import common.VaccineRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainInterfaceModel {
	
Connection connection;

public Log log = new Log();
	
	public  MainInterfaceModel() {
	
		connection = SqliteConnection.Connector();
		if (connection == null) {
			log.logFile(null, "severe", "SQL connection is NULL.");
			System.exit(1);
			
		}
	}
	
	public boolean addOwnerInfo(String firstName, String lastName, String icNumber, String pNumber, String address, String email) throws SQLException {
		
		PreparedStatement preparedStatement = null;
		String query = "INSERT into owners (FirstName, LastName, IcNumber, PhoneNumber, Address, Email) values (?, ?, ?, ?, ?, ?)";
		
		try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, icNumber);
				preparedStatement.setString(4, pNumber);
				preparedStatement.setString(5, address);
				preparedStatement.setString(6, email);
				
				
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
	
	public ObservableList<Owner> searchOwner(String icNumber) throws SQLException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM owners WHERE IcNumber = ? ";
		ObservableList<Owner> owners = FXCollections.observableArrayList();
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, icNumber);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				owners.add(new Owner(resultSet.getString("FirstName"), resultSet.getString("LastName"), 
						resultSet.getString("IcNumber"), resultSet.getString("PhoneNumber"), resultSet.getString("Address"), resultSet.getString("Email")));
			}
			
		
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
	}
	
	finally {
		preparedStatement.close();
		resultSet.close();
	}
		 
		 
		 
		 
		 
		return owners;
	}
	
	
	public boolean editOwnerInfo(String firstName, String lastName, String pNumber, String address, String email ,Integer ownerID) throws SQLException {
		
		PreparedStatement preparedStatement = null;
		String query = "UPDATE owners set FirstName = ?, LastName = ?, PhoneNumber = ?, Address = ?, Email = ? WHERE OwnerID = ?";
		
		try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, pNumber);
				preparedStatement.setString(4, address);
				preparedStatement.setString(5, email);
				preparedStatement.setInt(6, ownerID);
				
				
				
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
	
	
	public ObservableList<Pet> getPetList(Integer ownerID) throws SQLException {
	  
	    ObservableList<Pet> pet = FXCollections.observableArrayList();

	    PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM pets WHERE OwnerID = ? ";
		
		try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, ownerID);
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					
					pet.add(new Pet(resultSet.getString("PetName"), resultSet.getString("PetType") ,
							resultSet.getString("Breed"), resultSet.getString("Gender"), resultSet.getString("DOB"), resultSet.getString("Neutered")));
				}
				
			
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
		
		finally {
			preparedStatement.close();
			resultSet.close();
		}

	    return pet;
	  }
	
	public Integer getOwnerID(String icNumber) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT OwnerID FROM owners WHERE ICNumber = ?";
		Integer id = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, icNumber);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				 id = resultSet.getInt("OwnerID");
				
			}
			
			return id;
			
		
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		return null;
	}
	
	finally {
		preparedStatement.close();
		resultSet.close();
	}
		
		
	}
	
	public Integer getPetID(Integer ownerID, String petName, String petType) throws SQLException{
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String query = "SELECT PetID FROM pets WHERE (OwnerID = ? AND PetName = ? AND PetType = ?)";
			Integer id = null;
			
			try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, ownerID);
				preparedStatement.setString(2, petName);
				preparedStatement.setString(3, petType);
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					
					 id = resultSet.getInt("PetID");
					
				}
				
				return id;
				
			
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		finally {
			preparedStatement.close();
			resultSet.close();
		}
			
			
		}
	
	public ObservableList<Pet> getPetInfo(Integer PetID) throws SQLException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM pets WHERE PetID = ?";
		 ObservableList<Pet> petInfo = FXCollections.observableArrayList();
		
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, PetID);
			resultSet = preparedStatement.executeQuery();
			
			
			
			
			while (resultSet.next()) {
				
				petInfo.add(new Pet(resultSet.getString("PetName"), resultSet.getString("PetType") 
						,resultSet.getString("Breed"), resultSet.getString("Gender"), resultSet.getString("DOB"), resultSet.getString("Neutered")));
				
			}
			
			
			
		
			
		
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		
	}
	
	finally {
		preparedStatement.close();
		resultSet.close();
	}
		
		return petInfo;
		
		
	}
	
	public ObservableList<VaccineRecord> getVaccineRecord(Integer petID) throws SQLException {
		  ObservableList<VaccineRecord> vaccineRecords = FXCollections.observableArrayList();

		    PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String query = "SELECT * FROM vaccine_records WHERE PetID = ? ";
			
			try {
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setInt(1, petID);
					resultSet = preparedStatement.executeQuery();
					
					while (resultSet.next()) {
						
						vaccineRecords.add(new VaccineRecord(resultSet.getString("Vaccine") ,resultSet.getString("Injection"), resultSet.getString("Date"), resultSet.getString("NextDate")));
					}
					
				
			} catch (Exception e) {
				log.logFile(e, "severe", e.getMessage());
				e.printStackTrace();
			}
			
			finally {
				preparedStatement.close();
				resultSet.close();
			}

		    return vaccineRecords;
	}
	
	public boolean deletePetInfo(Integer petID) throws SQLException {
		PreparedStatement preparedStatement = null;
		String query = "DELETE from pets WHERE PetID = ?";
		
		try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, petID);
				
				
			if (preparedStatement.executeUpdate() == 0) {	

				return false;
				
				
			}
			else {
				
				return true;
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
	
	public boolean deleteVacRecord(Integer petID) throws SQLException {
		PreparedStatement preparedStatement = null;
		String query = "DELETE from vaccine_records WHERE PetID = ?";
		
		try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, petID);
				
				
			if (preparedStatement.executeUpdate() == 0) {	

				return false;
			
			}
			else {
				
				return true;
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
	
	public boolean deleteMedRecord(Integer petID) throws SQLException {
		PreparedStatement preparedStatement = null;
		String query = "DELETE from medication_records WHERE PetID = ?";
		
		try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, petID);
				
				
			if (preparedStatement.executeUpdate() == 0) {	

				return false;
			
			}
			else {
				
				return true;
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
	
	
	public ObservableList<String> getAppointmentList() throws SQLException {
		
		  ObservableList<String> appointmentList = FXCollections.observableArrayList();

		    PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String query = "SELECT * FROM upcoming_appointments WHERE Status = ? ";
			
			try {
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, "Confirmed");
					resultSet = preparedStatement.executeQuery();
					
					while (resultSet.next()) {
						
						appointmentList.add(resultSet.getInt("AppID") + " . " +resultSet.getString("OwnerName") + "'s " + resultSet.getString("PetName") + " " + resultSet.getString("Injection") +
								" injection " + resultSet.getString("Vaccine") + " on " + resultSet.getString("Date"));
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
	
	
	public ObservableList<MedicationRecord> getMedicationRecord(Integer petID) throws SQLException {
		
		  ObservableList<MedicationRecord> medicationRecords = FXCollections.observableArrayList();

	        PreparedStatement preparedStatement = null;
		    ResultSet resultSet = null;
		    String query = "SELECT * FROM medication_records WHERE PetID = ? ";
		 
		    try {
		    	    preparedStatement = connection.prepareStatement(query);
			        preparedStatement.setInt(1, petID);
			        resultSet = preparedStatement.executeQuery();
				
			        while (resultSet.next()) {
					
					    medicationRecords.add(new MedicationRecord(resultSet.getString("MedDate") ,resultSet.getString("Time"), resultSet.getString("Medication"), resultSet.getString("Dosage"), resultSet.getString("Frequency"), resultSet.getString("Notes")));
				    }
				
			
		    } catch (Exception e) {
		    	log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		    }
		
		    finally {
		    	preparedStatement.close();
			    resultSet.close();
		    }

	    return medicationRecords;

	}
	
	
	
	
	
	
	
	

}
