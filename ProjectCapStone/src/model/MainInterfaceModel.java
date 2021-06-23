package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
	
	
public boolean registerNewUser(String userName, String pwd) throws SQLException {
		
		PreparedStatement preparedStatement = null;
		String query = "INSERT into users (UserName, Password) values (?, ?)";
		
		try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, userName);
				preparedStatement.setString(2, pwd);
				
				
				
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

public String getUserPwd(String userName) throws SQLException{
	
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT Password FROM users WHERE UserName = ? ";
	String pwd = null;
	
	try {
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, userName);
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
			 pwd = resultSet.getString("Password");
			
			
		}
		
		return pwd;
		
	
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

public boolean editUserPwd(String userName, String new_pf) throws SQLException {
	
	PreparedStatement preparedStatement = null;
	String query = "UPDATE users set Password = ? WHERE UserName = ?";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, new_pf);
			preparedStatement.setString(2, userName);
			
			
			
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

public ObservableList<String> getUserList() throws SQLException {
	
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT * FROM users";
	  ObservableList<String> userList = FXCollections.observableArrayList();
	
	
	try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				userList.add(resultSet.getString("UserName"));
				
			}
	
	
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		
	}
	
	finally {
		preparedStatement.close();
		resultSet.close();
	}
		
		return userList;
		
		
	}

public boolean deleteUser(String userName) throws SQLException {
	PreparedStatement preparedStatement = null;
	String query = "DELETE from users WHERE UserName = ? ";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			
			
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

public boolean deleteOwnerInfo(Integer ownerID) throws SQLException {
	PreparedStatement preparedStatement = null;
	String query = "DELETE from owners WHERE OwnerID = ?";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, ownerID);
			
			
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

public List<Integer> getAllPetID(Integer ownerID) throws SQLException{
	
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = " SELECT PetID FROM pets WHERE OwnerID = ? ";
	List<Integer> id = new ArrayList<>();
	
	try {
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, ownerID);
		
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
			// id = resultSet.getInt("PetID");
			id.add(resultSet.getInt("PetID"));
			
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

public boolean deleteOwnerAppointment(Integer ownerID) throws SQLException {
	PreparedStatement preparedStatement = null;
	String query = "DELETE from upcoming_appointments WHERE (OwnerID = ? AND Status = ? )";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, ownerID);
			preparedStatement.setString(2, "Confirmed");
			
			
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
	
public boolean addNewCatVac(String vac) throws SQLException {
	
	PreparedStatement preparedStatement = null;
	String query = "INSERT into vaccine_cat (VaccineName) values (?)";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, vac);
		
			
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
	
public boolean addNewDogVac(String vac) throws SQLException {
	
	PreparedStatement preparedStatement = null;
	String query = "INSERT into vaccine_dog (VaccineName) values (?)";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, vac);
		
			
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

public boolean deleteCatVac(String vac) throws SQLException {
	PreparedStatement preparedStatement = null;
	String query = "DELETE from vaccine_cat WHERE VaccineName = ? ";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, vac);
			
			
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

public boolean deleteDogVac(String vac) throws SQLException {
	PreparedStatement preparedStatement = null;
	String query = "DELETE from vaccine_dog WHERE VaccineName = ? ";
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, vac);
			
			
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

public boolean addNewBreed(String breed, Boolean isCat) throws SQLException {
	
	PreparedStatement preparedStatement = null;
	String query = "";
	if (isCat) {
		 query = "INSERT into breed_cat (BreedName) values (?)";
	}
	
	else {
		query = "INSERT into breed_dog (BreedName) values (?)";
	}
	
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, breed);
		
			
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

public boolean deleteBreed(String breed, Boolean isCat) throws SQLException {
	PreparedStatement preparedStatement = null;
	String query = "";
	if (isCat) {
		query = "DELETE from breed_cat WHERE BreedName = ? ";
	}
	else {
		query = "DELETE from breed_dog WHERE BreedName = ? ";
	}
	
	
	try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, breed);
			
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

public ResultSet getTable(Boolean isOwner) {
	
	String query = "";
	
	if (isOwner) {
		query = "SELECT * FROM owners";
	}
	
	else {
		query = "SELECT * FROM pets";
	}
	

	try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			
	return rs;
	
		
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		return null;
		
	}
	
	
}

public ResultSet getRecord(Boolean isVac) {
	
	String query = "";
	
	if (isVac) {
		query = "SELECT * FROM vaccine_records";
	}
	
	else {
		query = "SELECT * FROM medication_records";
	}
	

	try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			
	return rs;
	
		
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		return null;
		
	}
	
	
}

public ResultSet getPieChartData() {
	
	String query = "select Vaccine, count(Vaccine) from vaccine_records group by Vaccine";
	
	try {
		ResultSet rs = connection.createStatement().executeQuery(query);
		return rs;
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
		return null;
	}
	
}
	
public void deleteAll() throws SQLException {
	PreparedStatement preparedStatement = null;
	String [] queries = {
						 "DROP TABLE IF EXISTS owners;","CREATE TABLE IF NOT EXISTS owners(OwnerID INTEGER PRIMARY KEY AUTOINCREMENT, FirstName varchar(32) NOT NULL, LastName varchar(32) NOT NULL, IcNumber varchar(12) UNIQUE NOT NULL, PhoneNumber varchar(50) NOT NULL, Address varchar(100) NOT NULL, Email varchar(45) NOT NULL)",
						 "DROP TABLE IF EXISTS pets;","CREATE TABLE IF NOT EXISTS pets(PetID INTEGER PRIMARY KEY AUTOINCREMENT, OwnerID INTEGER NOT NULL, PetName varchar(32) NOT NULL, PetType varchar(32) NOT NULL, Breed varchar(32) NOT NULL, Gender varchar(32) NOT NULL, DOB varchar(50) NOT NULL, Neutered varchar(32) NOT NULL)",
						 "DROP TABLE IF EXISTS upcoming_appointments;","CREATE TABLE IF NOT EXISTS upcoming_appointments(AppID INTEGER PRIMARY KEY AUTOINCREMENT, Date varchar(45) NOT NULL, OwnerID int NOT NULL, OwnerName varchar(45) NOT NULL, PetID int NOT NULL, PetName varchar(45) NOT NULL, Injection varchar(45) NOT NULL, Vaccine varchar(45) NOT NULL, Status varchar(45) NOT NULL)",
						 "DROP TABLE IF EXISTS vaccine_records;","CREATE TABLE IF NOT EXISTS vaccine_records(VacID INTEGER PRIMARY KEY AUTOINCREMENT, PetID int NOT NULL, Date varchar(32) NOT NULL, NextDate varchar(32) NOT NULL, Injection varchar(32) NOT NULL, Vaccine varchar(32) NOT NULL)",
						 "DROP TABLE IF EXISTS medication_records;","CREATE TABLE IF NOT EXISTS medication_records(RecordID INTEGER PRIMARY KEY AUTOINCREMENT, PetID int NOT NULL, MedDate varchar(32) NOT NULL, Time varchar(32) NOT NULL, Medication varchar(32) NOT NULL, Dosage varchar(32) NOT NULL, Frequency varchar(32) NOT NULL, Notes varchar(50) NOT NULL)"};
	
	try {
		
		for(String query:queries)
		{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
		}
			
	
		
	} catch (Exception e) {
		log.logFile(e, "severe", e.getMessage());
		e.printStackTrace();
	}
	
	finally {
		preparedStatement.close();
		
	}
}

public Integer getOwnerNumbers() throws SQLException {
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT COUNT(*) FROM owners ";
	Integer count = null;
	
	try {
		preparedStatement = connection.prepareStatement(query);
		
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
			count = resultSet.getInt("COUNT(*)");
			
			
		}
		
		return count;
		
	
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
public Integer getAppNumbers(Boolean isComplete) throws SQLException {
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String query = "SELECT COUNT(*) FROM upcoming_appointments WHERE Status = ? ";
	Integer count = null;
	String status = "";
	
	if (isComplete) {
		status = "Completed";
	}
	
	else {
		status = "Confirmed";
	}
	
	try {
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, status);
		
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			
			count = resultSet.getInt("COUNT(*)");
			
		}
		
		return count;
		
	
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


}
