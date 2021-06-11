package model;

import java.sql.*;

import common.Log;
import common.SqliteConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddEditPetModel {
	
Connection connection;
public Log log = new Log();
	
	public AddEditPetModel() {
		
		connection = SqliteConnection.Connector();
		if (connection == null) {
			log.logFile(null, "severe", "SQL connection is NULL.");
			System.exit(1);
			
		}
	}
	
public boolean addPetInfo(Integer ownerID, String petName, String petType ,String breed, String gender, String dob, String neutered) throws SQLException {
		
		PreparedStatement preparedStatement = null;
		String query = "INSERT into pets (OwnerID, PetName, PetType, Breed, Gender, DOB, Neutered) values (?, ?, ?, ?, ?, ?, ?)";
		
		try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, ownerID);
				preparedStatement.setString(2, petName);
				preparedStatement.setString(3, petType);
				preparedStatement.setString(4, breed);
				preparedStatement.setString(5, gender);
				preparedStatement.setString(6, dob);
				preparedStatement.setString(7, neutered);
				
				
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

public boolean editPetInfo(Integer petID, String petName, String petType ,String breed, String gender, String dob, String neutered) throws SQLException {
	
	PreparedStatement preparedStatement = null;
	String query = "UPDATE pets SET PetName = ?, PetType = ?, Breed = ?, Gender = ?, DOB = ?, Neutered = ? WHERE PetID = ?";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, petName);
			preparedStatement.setString(2, petType);
			preparedStatement.setString(3, breed);
			preparedStatement.setString(4, gender);
			preparedStatement.setString(5, dob);
			preparedStatement.setString(6, neutered);
			preparedStatement.setInt(7, petID);
			
			
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

public ObservableList<String> getDogBreedList() throws SQLException {
	
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT * FROM breed_dog";
	  ObservableList<String> breedList = FXCollections.observableArrayList();
	
	
	try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				breedList.add(resultSet.getString("BreedName"));
				
			}
	
	
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		
	}
	
	finally {
		preparedStatement.close();
		resultSet.close();
	}
		
		return breedList;
		
		
	}

public ObservableList<String> getCatBreedList() throws SQLException {
	
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT * FROM breed_cat";
	  ObservableList<String> breedList = FXCollections.observableArrayList();
	
	
	try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				breedList.add(resultSet.getString("BreedName"));
				
			}
	
	
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		
	}
	
	finally {
		preparedStatement.close();
		resultSet.close();
	}
		
		return breedList;
		
		
	}
	
	
	

}
