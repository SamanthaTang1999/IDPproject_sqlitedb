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
	
	public void populateTables() {
		System.out.println("Populate table");
		String[] sqls = {
				"CREATE TABLE IF NOT EXISTS breed_cat(BreedCatID INTEGER PRIMARY KEY AUTOINCREMENT,BreedName varchar(45) NOT NULL)",
				"INSERT or IGNORE INTO breed_cat(BreedCatID,BreedName) VALUES(1,'Maine Coon'),(2,'Exotic Shorthair'),(3,'Persian'),(4,'Burmese'),(5,'American Shorthair'),(6,'British Shorthair'),(7,'Ragdoll'),(8,'Bengal'),(9,'Norwegian Forest'),(10,'Scottish Fold'),(11,'Mixed'),(12,'Siamese')",
				"CREATE TABLE IF NOT EXISTS breed_dog(BreedDogID INTEGER PRIMARY KEY AUTOINCREMENT,BreedName varchar(45) NOT NULL)",
				"INSERT or IGNORE INTO breed_dog(BreedDogID,BreedName) VALUES(1,'Beagle'),(2,'Chow Chow'),(3,'Corgi'),(4,'Doberman'),(5,'Golden Retriever'),(6,'Labrador'),(7,'Maltese'),(8,'Mixed'),(9,'Pomeranian'),(10,'Pug'),(11,'Poodle'),(12,'Rottweiler'),(13,'Shihtzu'),(14,'Siberian Husky'),(15,'Silky Terrier'),(16,'Schnauzer'),(17,'Yorkshire Terrier'),(18,'German Shepherd'),(19,'Miniature Pinscher')",
				"CREATE TABLE IF NOT EXISTS email_credentials(Email varchar(45) PRIMARY KEY,Password varchar(45) NOT NULL)",
				"INSERT or IGNORE INTO email_credentials(Email,Password) VALUES('projectidp2021@gmail.com','xohsy9-cYpmem-myggum')",
				"CREATE TABLE IF NOT EXISTS owners(OwnerID INTEGER PRIMARY KEY AUTOINCREMENT, FirstName varchar(32) NOT NULL, LastName varchar(32) NOT NULL, IcNumber varchar(12) UNIQUE NOT NULL, PhoneNumber varchar(50) NOT NULL, Address varchar(100) NOT NULL, Email varchar(45) NOT NULL)",
				"CREATE TABLE IF NOT EXISTS pets(PetID INTEGER PRIMARY KEY AUTOINCREMENT, OwnerID INTEGER NOT NULL, PetName varchar(32) NOT NULL, PetType varchar(32) NOT NULL, Breed varchar(32) NOT NULL, Gender varchar(32) NOT NULL, DOB varchar(50) NOT NULL, Neutered varchar(32) NOT NULL)",
				"CREATE TABLE IF NOT EXISTS upcoming_appointments(AppID INTEGER PRIMARY KEY AUTOINCREMENT, Date varchar(45) NOT NULL, OwnerID int NOT NULL, OwnerName varchar(45) NOT NULL, PetID int NOT NULL, PetName varchar(45) NOT NULL, Injection varchar(45) NOT NULL, Vaccine varchar(45) NOT NULL, Status varchar(45) NOT NULL)",
				"CREATE TABLE IF NOT EXISTS users(UserName varchar(50) PRIMARY KEY,Password varchar(50) NOT NULL)",
				"INSERT or IGNORE INTO users(UserName,Password) VALUES('Voon','test'),('admin','1')",
				"CREATE TABLE IF NOT EXISTS vaccine_cat(CatVacID INTEGER PRIMARY KEY AUTOINCREMENT,VaccineName varchar(45) NOT NULL)",
				"INSERT or IGNORE INTO vaccine_cat(CatVacID,VaccineName) VALUES(1,'4 in 1'),(2,'5 in 1'),(3,'FIP'),(4,'FelineX'),(5,'Rabies')",
				"CREATE TABLE IF NOT EXISTS vaccine_dog(DogVacID INTEGER PRIMARY KEY AUTOINCREMENT,VaccineName varchar(45) NOT NULL)",
				"INSERT or IGNORE INTO vaccine_dog(DogVacID,VaccineName) VALUES(1,'6 in 1'),(2,'7 in 1'),(3,'8 in 1'),(4,'9 in 1'),(5,'10 in 1'),(6,'Rabies')",
				"CREATE TABLE IF NOT EXISTS vaccine_records(VacID INTEGER PRIMARY KEY AUTOINCREMENT, PetID int NOT NULL, Date varchar(32) NOT NULL, NextDate varchar(32) NOT NULL, Injection varchar(32) NOT NULL, Vaccine varchar(32) NOT NULL)",
				"CREATE TABLE IF NOT EXISTS medication_records(RecordID INTEGER PRIMARY KEY AUTOINCREMENT, PetID int NOT NULL, MedDate varchar(32) NOT NULL, Time varchar(32) NOT NULL, Medication varchar(32) NOT NULL, Dosage varchar(32) NOT NULL, Frequency varchar(32) NOT NULL, Notes varchar(50) NOT NULL)"};		//
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

