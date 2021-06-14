package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import common.Log;
import common.MedicationRecord;
import common.Owner;
import common.Pet;
import common.VaccineRecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.AddEditPetModel;
import model.AddVacRecordModel;
import model.MainInterfaceModel;

public class MainInterfaceController implements Initializable {
	
	// My Dash board tab
	@FXML private TabPane tabPane;
	@FXML private Tab myDashBoard;
	@FXML private Tab petOwner;
	@FXML private Tab petInfo;
	@FXML private Tab calender;
	@FXML private Tab user;
	@FXML private Tab database;
	@FXML private Tab statistics;
	
	
	// Pet Owner Tab
	@FXML private TextField textField_ownerFirstName;
	@FXML private TextField textField_ownerLastName;
	@FXML private TextField textField_ownerPhoneNumber;
	@FXML private TextField textField_ownerIcNumber;
	@FXML private TextField textField_ownerAddress;
	@FXML private TextField textField_ownerEmail;
	
	// table view for pets in pet owner tab
	@FXML private TableView<Pet> petTable;
	@FXML private TableColumn<Pet, String> petName;
	@FXML private TableColumn<Pet, String> petType;
	@FXML private TableColumn<Pet, String> breed;
	@FXML private TableColumn<Pet, String> gender;
	@FXML private TableColumn<Pet, String> dob;
	@FXML private TableColumn<Pet, String> neutered;

	// Pet Info Tab
	@FXML private Label label_petName;
	@FXML private Label label_petType;
	@FXML private Label label_breed;
	@FXML private Label label_gender;
	@FXML private Label label_dob;
	@FXML private Label label_neutered;
	
	// Table View for Vaccination Record
	@FXML private TableView<VaccineRecord> vacRecordTable;
	@FXML private TableColumn<VaccineRecord, String> injection;
	@FXML private TableColumn<VaccineRecord, String> vaccineType;
	@FXML private TableColumn<VaccineRecord, String> date;
	@FXML private TableColumn<VaccineRecord, String> nextDate;
	
	// Table View for Medication Record
	@FXML private TableView<MedicationRecord> medRecordTable;
	@FXML private TableColumn<MedicationRecord, String> meddate;
	@FXML private TableColumn<MedicationRecord, String> time;
	@FXML private TableColumn<MedicationRecord, String> medication;
	@FXML private TableColumn<MedicationRecord, String> dosage;
	@FXML private TableColumn<MedicationRecord, String> frequency;
	@FXML private TableColumn<MedicationRecord, String> notes;
	
	// Calender Tab
	@FXML Pane calendarPane;
	@FXML ListView<String> appointmentListView;
	
	// User Tab
	@FXML private Label label_userName;
	@FXML private TextField tf_newUser;
	@FXML private PasswordField pf_oldPF;
	@FXML private PasswordField pf_editPF;
	@FXML private PasswordField pf_newPF;
	@FXML private PasswordField pf_confirmPF;
	@FXML private ListView<String> listView_users;

	// Database Tab
	@FXML private TextField tf_newEntry;
	@FXML private Label	label_listName;
	@FXML private ListView<String> listView_database;
	@FXML private RadioButton rb_catVaccine;
	@FXML private RadioButton rb_dogVaccine;
	@FXML private RadioButton rb_catBreed;
	@FXML private RadioButton rb_dogBreed;
	@FXML private Button button_viewAllOwners;
	@FXML private Button button_viewAllPets;
	@FXML private TableView tb_database;
	
	private ObservableList<ObservableList> database_data;
	 
	 // Statistics Tab
	 
	 private ObservableList piechart_data;
	 
	 @FXML private PieChart pieChart;
	 @FXML private Label label_pieChartNumber;
	
	
	

	
	// Main Interface Model
	public MainInterfaceModel model = new MainInterfaceModel();
	public AddVacRecordModel model_VacRecord = new AddVacRecordModel();
	public AddEditPetModel model_breed = new AddEditPetModel();
	
	// System log
	public Log log = new Log();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		  tabPane.getTabs().remove( petInfo );
		  tabPane.getTabs().remove( petOwner );
		  tabPane.getTabs().remove( calender );
		  tabPane.getTabs().remove( user );
		  tabPane.getTabs().remove( database );
		  tabPane.getTabs().remove( statistics );
		  
	}
	
/////////////////////////////////////////////// MY DASHBOARD TAB ////////////////////////////////////////////////	
	
	// Get current user in My dash board tab
	
	public void getUser(String user) throws SQLException {
		label_userName.setText("Current User : " + user); 	// set username labal in user profile tab
	}
	
	
	public void logout(ActionEvent event) {
		
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Login.fxml").openStream());
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.show();
			
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
		
		
		
	}
	

	
	public void goToCalender(ActionEvent event) {
		
		 if(!tabPane.getTabs().contains(calender)) {
			 
			   tabPane.getTabs().add(calender);  
			   appointmentListView.setItems(null);
			  }
		 
		 tabPane.getSelectionModel().select(calender);
		 refreshAppointmentList(event);
		
		
	}
	
	// Go to Pet Owner tab in My dash board tab
	
	public void petOwner(ActionEvent event) {
		
		 if(!tabPane.getTabs().contains(petOwner)) {
			 
			   tabPane.getTabs().add(petOwner);  
			   petTable.setItems(null);
			   textField_ownerFirstName.clear();
			   textField_ownerLastName.clear();
			   textField_ownerIcNumber.clear();
			   textField_ownerPhoneNumber.clear();
			   textField_ownerAddress.clear();
			   textField_ownerEmail.clear();
			   
			  }
		 
		 tabPane.getSelectionModel().select(petOwner);
		
	}
	
	public void goToUser(ActionEvent event) throws SQLException 
	{
		if(!tabPane.getTabs().contains(user)) {
			 
			   tabPane.getTabs().add(user);  
			  }
		 tabPane.getSelectionModel().select(user);
		 viewAllUsers(event);
	}
	
	
	public void goToSettings(ActionEvent event) 
	{
		String userName = label_userName.getText().substring(15);
		if (userName.equals("admin")) {
			
			if(!tabPane.getTabs().contains(database)) {
				 
				   tabPane.getTabs().add(database);  
				   
				  }
			 tabPane.getSelectionModel().select(database);
		}
		
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Program says");
			alert.setHeaderText("Access Denied");
			alert.setContentText("Only admin can access to database.");
			alert.show();
		}
		
	}
	
	public void goToStatistics(ActionEvent event) 
	{
		if(!tabPane.getTabs().contains(statistics)) { 
			   tabPane.getTabs().add(statistics);   
			  }
		 tabPane.getSelectionModel().select(statistics);
		 pieChart.getData().clear();
		 buildPieChart();
		
		 if (!pieChart.getData().containsAll(piechart_data)) {
			 pieChart.getData().addAll(piechart_data); 
		}
		 
		 if (pieChart.getData().isEmpty()) {
			label_pieChartNumber.setText("No data is available.");
		}

			for (final PieChart.Data data: pieChart.getData()) {
				data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						label_pieChartNumber.setText(data.getName() + " Vaccine has a total of " + String.valueOf(data.getPieValue()).replace(".0", "") + " shot(s)");
						
					}
				});
				
			}
		}
		 
	
////////////////////////////////////////////////PET OWNER TAB //////////////////////////////////////////////////	
	
	public void closePetOwnerTab(ActionEvent event)
	{
		
		 EventHandler<Event> handler = petOwner.getOnClosed();
	        if (null != handler) {
	            handler.handle(null);
	        } else {
	        	petOwner.getTabPane().getTabs().remove(petOwner);
	        }
	}
	
	
	public void saveOwnerInfo(ActionEvent event) {
		
		String firstName = textField_ownerFirstName.getText().trim();
		String lastName = textField_ownerLastName.getText().trim();
		String pNumber = textField_ownerPhoneNumber.getText().trim();
		String icNumber = textField_ownerIcNumber.getText().trim();
		String address = textField_ownerAddress.getText();
		String email = textField_ownerEmail.getText().trim();
		
		
	
		
		try {
			

			if (firstName.isEmpty() || lastName.isEmpty() || pNumber.isEmpty() || icNumber.isEmpty() || address.isEmpty() || email.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Owner info is incomplete.");
				alert.show();
				return;
			}
			
			if (icNumber.length() != 12) {  		// validate IC number
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("IC Number is invalid.");
				alert.show();
				textField_ownerIcNumber.clear();
				return;
			}
			
			if (isNumeric(pNumber) == false || pNumber.length() != 10) {				// validate phone number
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Phone Number is invalid.");
				alert.show();
				textField_ownerPhoneNumber.clear();
				return;
			}
			
			
			if(model.addOwnerInfo(firstName, lastName, icNumber, pNumber, address, email))
			{
				

				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Program says");
				alert.setHeaderText("Owner info is saved.");
				alert.show();
				log.logFile(null, "info", firstName + " " + lastName + " is registered into database.");
				
				
				
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Owner info is not saved.");
				alert.show();
				
				
			}
			
		} catch (SQLException e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		} 
		
	
		
	}
	
	// Search for owner name based on Name
	
	public void searchOwner(ActionEvent event) {
		
		String icNumber = textField_ownerIcNumber.getText().trim();
	
		try {
			
			if (icNumber.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Please key in IC Number");
				alert.show();
				return;
			}
			
			if (icNumber.length() != 12) {  		// validate IC number
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("IC Number is invalid.");
				alert.show();
				textField_ownerIcNumber.clear();
				return;
			}
			
			ObservableList<Owner> owners = model.searchOwner(icNumber);
			if (owners.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Owner not found");
				alert.show();
				textField_ownerIcNumber.clear();
				return;
			}
			
			textField_ownerFirstName.setText(owners.get(0).getFirstName());
			textField_ownerLastName.setText(owners.get(0).getLastName());
			textField_ownerPhoneNumber.setText(owners.get(0).getTelephoneNumber());
			textField_ownerAddress.setText(owners.get(0).getAddress());
			textField_ownerEmail.setText(owners.get(0).getEmail());
			
			refreshPetTable(event);		// refresh pet list table
			
			
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	// Edit Owner info based on IC Number -> Owner ID
	
	public void editOwnerInfo(ActionEvent event) {
		
		 Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		 alertConfirm.setTitle("Program says");
		 alertConfirm.setHeaderText("IC Number should not be edited.");
		 alertConfirm.setContentText("Are you ok with this?");

         Optional<ButtonType> result = alertConfirm.showAndWait();
         if (result.get() == ButtonType.OK){
           
        	String firstName = textField_ownerFirstName.getText().trim();
     		String lastName = textField_ownerLastName.getText().trim();
     		String pNumber = textField_ownerPhoneNumber.getText().trim();
     		String icNumber = textField_ownerIcNumber.getText().trim();
     		String address = textField_ownerAddress.getText();
     		String	email = textField_ownerEmail.getText().trim();
     		
     		try {

     			if (firstName.isEmpty() || lastName.isEmpty() || pNumber.isEmpty() || icNumber.isEmpty() || address.isEmpty() || email.isEmpty()) {
     				Alert alert = new Alert(AlertType.ERROR);
     				alert.setTitle("Program says");
     				alert.setHeaderText("Owner info is incomplete.");
     				alert.show();
     				return;
     			}
     			
     			Integer OwnerID = model.getOwnerID(icNumber);
				
				if (OwnerID == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("IC Number is changed.");
					alert.show();
					petTable.setItems(null);
					return;
				}
				
				if (model.editOwnerInfo(firstName, lastName, pNumber, address, email, OwnerID)) {
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Program says");
					alert.setHeaderText("Owner info is edited.");
					alert.show();
					log.logFile(null, "info", firstName + " " + lastName + " is edited.");
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Owner info is not edited.");
					alert.show();
				}
    	
     		} catch (Exception e) {
     			log.logFile(e, "severe", e.getMessage());
     			e.printStackTrace();
     		}
     	 
        	 
         }
	}
	
	// Clear all text field in Pet Owner tab
	
	public void clearInfo(ActionEvent event) {
		textField_ownerFirstName.clear();
		textField_ownerLastName.clear();
		textField_ownerIcNumber.clear();
		textField_ownerAddress.clear();
		textField_ownerPhoneNumber.clear();
		textField_ownerEmail.clear();
		
	}
	
	
	public void deleteOwner(ActionEvent event) {
		
		String icNumber = textField_ownerIcNumber.getText().trim();
 		
 		if (icNumber.isEmpty()) {
 			Alert alert = new Alert(AlertType.ERROR);
 			alert.setTitle("Program says");
 			alert.setHeaderText("Pet Owner IC is missing.");
 			alert.show();
 			return;
 		}
 		
		
		 Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		 alertConfirm.setTitle("Program says");
		 alertConfirm.setHeaderText("Are you sure you want to delete?");
		

         Optional<ButtonType> result = alertConfirm.showAndWait();
         if (result.get() == ButtonType.OK){
        	
        	 try {
         			Integer ownerID = model.getOwnerID(icNumber);
         			
         			
         			if (ownerID == null) {
         				Alert alert = new Alert(AlertType.ERROR);
         				alert.setTitle("Program says");
         				alert.setHeaderText("Pet Owner not found in database");
         				alert.show();
         				return;
         			}
         			
         			List<Integer> petID = model.getAllPetID(ownerID);
         			
         			if (!petID.isEmpty()) {
						
         				for(Integer id : petID)
             			{
         					if (model.deletePetInfo(id)) {
								
         						if (model.deleteMedRecord(id)) {
									
         							log.logFile(null, "info", "Medication Record for petID: " + id + "is successful");
								}
         						
         						else {
									
         							log.logFile(null, "info", "Medication Record for petID: " + id + "not found");
								}
         						
         						if (model.deleteVacRecord(id)) {
         							log.logFile(null, "info", "Vaccination Record for petID: " + id + "is successful");
								}

         						else {
         							log.logFile(null, "info", "Vaccination Record for petID: " + id + "not found");
								}
							}
             			}
             			
					}
         			
         			else {
         				log.logFile(null, "info", "Owner with IC:" + icNumber + "has not pet");
					}
         			
         			if (model.deleteOwnerInfo(ownerID)) {
         				log.logFile(null, "info", "Pet Owner with IC: " + icNumber + " is deleted successfully!");
         				Alert alert = new Alert(AlertType.INFORMATION);
         				alert.setTitle("Program says");
         				alert.setHeaderText("Pet Owner with IC: " + icNumber + " is deleted successfully!");
         				alert.show();
         				clearInfo(event);
					}
         			
         			else {
         				log.logFile(null, "info", "Pet Owner with IC: " + icNumber + " is not deleted");
         				Alert alert = new Alert(AlertType.ERROR);
         				alert.setTitle("Program says");
         				alert.setHeaderText("Pet Owner with IC: " + icNumber + " is not deleted");
         				alert.show();
					}
         			
         			if (model.deleteOwnerAppointment(ownerID)) {
         				log.logFile(null, "info", "Pet Owner with IC: " + icNumber + " upcoming appointments are deleted successfully!");
					}
         			else {
         				log.logFile(null, "info", "Pet Owner with IC: " + icNumber + " upcoming appointments not found");
					}
         			
         		
			      	
 			} catch (Exception e) {
 				log.logFile(e, "severe", e.getMessage());
 				e.printStackTrace();
 			}
        	 
        	 
        	 
         
         }
	}
	
	public void refreshPetTable(ActionEvent event) {
		
		String icNumber = textField_ownerIcNumber.getText().trim();
	
		
		try {	
			
				if (icNumber.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet Owner IC is missing.");
					alert.show();
					petTable.setItems(null);
					return;
				}
			
				
				Integer OwnerID = model.getOwnerID(icNumber);
				
				if (OwnerID == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet Owner not found in database");
					alert.show();
					petTable.setItems(null);
					return;
				}
			
			
				ObservableList<Pet> petList = model.getPetList(OwnerID);
				
				if (petList.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("No Pet Record found.");
					alert.show();
					petTable.setItems(null);
					return;
				}
				
				petName.setCellValueFactory(new PropertyValueFactory<Pet, String>("PetName"));
				petType.setCellValueFactory(new PropertyValueFactory<Pet, String>("PetType"));
				breed.setCellValueFactory(new PropertyValueFactory<Pet, String>("Breed"));
				gender.setCellValueFactory(new PropertyValueFactory<Pet, String>("Gender"));
				dob.setCellValueFactory(new PropertyValueFactory<Pet, String>("dob"));
				neutered.setCellValueFactory(new PropertyValueFactory<Pet, String>("Neutered"));
				
				petTable.setItems(petList);
			
		} catch (SQLException e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	public void addPet(ActionEvent event) {
		
		String icNumber = textField_ownerIcNumber.getText();
		
	
		try {
				
				if (icNumber.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet Owner IC is missing.");
					alert.show();
					return;
				}
				
				Integer ownerID = model.getOwnerID(icNumber);
				
				
				if (ownerID == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet Owner not found in database");
					alert.show();
					return;
				}
			
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/AddEditPet.fxml").openStream());
				AddEditPetController addEditPetController = (AddEditPetController)loader.getController();
				addEditPetController.setOwnerID(ownerID);
				addEditPetController.setMode("ADD");
			
				
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
				
			
		} catch (SQLException e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}	
	}
	

// double click on the table row to go to pet info	
	public void goToPetInfo(MouseEvent event) {
		try {
			
			if(event.getClickCount() == 2)
				
			{
				String petName = "";
				String petType = "";
			
			
				String icNumber = textField_ownerIcNumber.getText().trim();
				
				if (icNumber.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet Owner IC is missing.");
					alert.show();
					return;
				}
				
				Integer ownerID = model.getOwnerID(icNumber);
				
				if (ownerID == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet Owner not found in database");
					alert.show();
					return;
				}
				
				 	ObservableList<Pet> petTableList = petTable.getSelectionModel().getSelectedItems();		// get all selected items
				
					for(Pet item : petTableList)
					{
						petName = item.getPetName();
						petType = item.getPetType();
					}
			
				 if(!tabPane.getTabs().contains(petInfo) && !petName.isEmpty()) {
					 
					   tabPane.getTabs().add(petInfo);  
					   tabPane.getSelectionModel().select(petInfo);
					   vacRecordTable.setItems(null);
					   medRecordTable.setItems(null);
					   
					  }
				 
				 Integer petID = model.getPetID(ownerID, petName, petType);
					
					if (petID == null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Program says");
						alert.setHeaderText("Pet not found");
						alert.show();
						return;
					}
				 
				 ObservableList<Pet> petList = model.getPetInfo(petID);
				 
				 if (petList.isEmpty()) {
					 Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Program says");
						alert.setHeaderText("Pet not found");
						alert.show();
						return;
				 }
				 
				 label_petName.setText("Pet Name : " + petList.get(0).getPetName());
				 label_breed.setText("Breed : " + petList.get(0).getBreed());
				 label_petType.setText("Pet Type : " + petList.get(0).getPetType());
				 label_gender.setText("Gender : " + petList.get(0).getGender());
				 label_dob.setText("DOB : " + petList.get(0).getDob());
				 label_neutered.setText("Neutered : " + petList.get(0).getNeutered());
			}
			
		} catch (SQLException e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		
		}

		
	}
	
////////////////////////////////////////////////PET INFO TAB //////////////////////////////////////////////////		
	
	// close tab
	public void closePetInfoTab(ActionEvent event)
	{
		//tabPane.getTabs().remove(petOwner);
		 EventHandler<Event> handler = petInfo.getOnClosed();
	        if (null != handler) {
	            handler.handle(null);
	        } else {
	        	petInfo.getTabPane().getTabs().remove(petInfo);
	        }
	}

	// Edit pet info
	public void editPetInfo(ActionEvent event) {
		if (label_petName.getText().length() < 11 || label_petType.getText().length() < 11 ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Program says");
			alert.setHeaderText("Pet Record is empty");
			alert.show();
			return;
		}
		
		
		String petType = label_petType.getText().substring(11);
		String petName = label_petName.getText().substring(11);
		String icNumber = textField_ownerIcNumber.getText().trim();
		
		if (icNumber.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Program says");
			alert.setHeaderText("Pet Owner IC is missing.");
			alert.show();
			return;
		}
		try {
			
				Integer ownerID = model.getOwnerID(icNumber);
			
			
			if (ownerID == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet Owner not found in database");
				alert.show();
				return;
			}
			
			Integer petID = model.getPetID(ownerID, petName, petType);
			
			if (petID == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet is not registered.");
				alert.show();
				return;
			}
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/AddEditPet.fxml").openStream());
			AddEditPetController addEditPetController = (AddEditPetController)loader.getController();
			addEditPetController.setOwnerID(ownerID);
			addEditPetController.setMode("EDIT");
			addEditPetController.setPetID(petID);
		
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Delete pet info and vaccination record
	
	public void deletePetInfo(ActionEvent event) {

		 Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		 alertConfirm.setTitle("Program says");
		 alertConfirm.setHeaderText("Are you sure you want to delete Pet Info?");
		 alertConfirm.setContentText("This action will erase all data including pet vaccination record.");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
        	
        	try {
        		
        		if (label_petName.getText().length() < 11 || label_petType.getText().length() < 11 ) {
        			Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Program says");
        			alert.setHeaderText("Pet Record is empty");
        			alert.show();
        			return;
        		}
        		
        		
        		String petType = label_petType.getText().substring(11);
        		String petName = label_petName.getText().substring(11);
        		String icNumber = textField_ownerIcNumber.getText().trim();
        		
        		if (icNumber.isEmpty()) {
        			Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Program says");
        			alert.setHeaderText("Pet Owner IC is missing.");
        			alert.show();
        			return;
        		}
        		
        		
        			
        			Integer ownerID = model.getOwnerID(icNumber);
        			
        			
        			if (ownerID == null) {
        				Alert alert = new Alert(AlertType.ERROR);
        				alert.setTitle("Program says");
        				alert.setHeaderText("Pet Owner not found in database");
        				alert.show();
        				return;
        			}
        			
        			Integer petID = model.getPetID(ownerID, petName, petType);
        			
        			if (petID == null) {
        				Alert alert = new Alert(AlertType.ERROR);
        				alert.setTitle("Program says");
        				alert.setHeaderText("Pet is not registered.");
        				alert.show();
        				return;
        			}
        			
        			
        			if (model.deletePetInfo(petID)) {
						
        				Alert alert = new Alert(AlertType.INFORMATION);
        				alert.setTitle("Program says");
        				alert.setHeaderText("Pet info is deleted.");
        				alert.show();
        				log.logFile(null, "info", "PetID : " + petID + " is deleted into from pets.");
        				
        				 EventHandler<Event> handler = petInfo.getOnClosed();
        			        if (null != handler) {
        			            handler.handle(null);
        			        } else {
        			        	petInfo.getTabPane().getTabs().remove(petInfo);
        			        	refreshPetTable(event);
        			        }
        				
					}
	        			
	        			else {
	        				Alert alert = new Alert(AlertType.ERROR);
	            			alert.setTitle("Program says");
	            			alert.setHeaderText("Pet info is not deleted.");
	            			alert.show();
						}
        			
        			if (model.deleteVacRecord(petID)) {
        				Alert alert = new Alert(AlertType.INFORMATION);
        				alert.setTitle("Program says");
        				alert.setHeaderText("Vaccine record is deleted.");
        				alert.show();
        				log.logFile(null, "info", " Vaccination record of PetID : " + petID + " is deleted.");
        				
        				
        				
        				
					}
	        			else {
	        				Alert alert = new Alert(AlertType.ERROR);
	            			alert.setTitle("Program says");
	            			alert.setHeaderText("Vaccination record is not deleted.");
	            			alert.show();
						}
        			
        			if (model.deleteMedRecord(petID)) {
        				Alert alert = new Alert(AlertType.INFORMATION);
        				alert.setTitle("Program says");
        				alert.setHeaderText("Medical Record is deleted.");
        				alert.show();
        				log.logFile(null, "info", " Medical record of PetID : " + petID + " is deleted.");
        				
        				
        				
        				
					}
	        			else {
	        				Alert alert = new Alert(AlertType.ERROR);
	            			alert.setTitle("Program says");
	            			alert.setHeaderText("Medical record is not deleted.");
	            			alert.show();
						}
    			      	
			} catch (Exception e) {
				log.logFile(e, "severe", e.getMessage());
				e.printStackTrace();
			}
        		
        }
		
	}
	
	// Add vaccination Record at Pet Info Tab
	
	public void addVaccinationRecord(ActionEvent event) {
		
		if (label_petName.getText().length() < 11 || label_petType.getText().length() < 11 ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Program says");
			alert.setHeaderText("Pet Record is empty");
			alert.show();
			return;
		}
		
		
		String petType = label_petType.getText().substring(11);
		String petName = label_petName.getText().substring(11);
		String icNumber = textField_ownerIcNumber.getText().trim();
		String ownerName = textField_ownerFirstName.getText().trim() + " " + textField_ownerLastName.getText().trim();
		
		if (icNumber.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Program says");
			alert.setHeaderText("Pet Owner IC is missing.");
			alert.show();
			return;
		}
		
		try {
			
			Integer ownerID = model.getOwnerID(icNumber);
			
			
			if (ownerID == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet Owner not found in database");
				alert.show();
				return;
			}
			
			Integer petID = model.getPetID(ownerID, petName, petType);
			
			if (petID == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet is not registered.");
				alert.show();
				return;
			}
			
			
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/AddVacRecord.fxml").openStream());
			AddVacRecordController addVacRecordController = (AddVacRecordController)loader.getController();
			addVacRecordController.setPetType(petType);
			addVacRecordController.setPetID(petID);
			addVacRecordController.setPetName(petName);
			addVacRecordController.setOwnerName(ownerName);
			addVacRecordController.setOwnerID(ownerID);
		
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	public void refreshVacRecordTable(ActionEvent event) {
		
		if (label_petName.getText().length() < 11 || label_petType.getText().length() < 11 ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Program says");
			alert.setHeaderText("Pet record is empty");
			alert.show();
			return;
		}
		
		String petType = label_petType.getText().substring(11);
		String petName = label_petName.getText().substring(11);
		String icNumber = textField_ownerIcNumber.getText().trim();
		
		if (icNumber.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Program says");
			alert.setHeaderText("Pet Owner IC is missing.");
			alert.show();
			vacRecordTable.setItems(null);
			return;
		}
		
		try {
			
			Integer ownerID = model.getOwnerID(icNumber);
			
			
			if (ownerID == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet Owner not found in databasel");
				alert.show();
				vacRecordTable.setItems(null);
				return;
			}
			
			Integer petID = model.getPetID(ownerID, petName, petType);
			
			if (petID == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet is not registered.");
				alert.show();
				vacRecordTable.setItems(null);
				return;
			}
			
			ObservableList<VaccineRecord> vaccineRecords = model.getVaccineRecord(petID);
			
			if (vaccineRecords.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("No Vaccination Record found.");
				alert.show();
				vacRecordTable.setItems(null);
				return;
			}
			
			
			injection.setCellValueFactory(new PropertyValueFactory<VaccineRecord, String>("Injection"));
			vaccineType.setCellValueFactory(new PropertyValueFactory<VaccineRecord, String>("VaccineType"));
			date.setCellValueFactory(new PropertyValueFactory<VaccineRecord, String>("Date"));
			nextDate.setCellValueFactory(new PropertyValueFactory<VaccineRecord, String>("NextDate"));
			
			vacRecordTable.setItems(vaccineRecords);
			
			
			
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Add medication Record at Pet Info Tab
	
		public void addMedicationRecord(ActionEvent event) {
				
			if (label_petName.getText().length() < 11 || label_petType.getText().length() < 11 ) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet Record is empty");
				alert.show();
				return;
			}
				
				
				String petType = label_petType.getText().substring(11);
				String petName = label_petName.getText().substring(11);
				String icNumber = textField_ownerIcNumber.getText().trim();
				String ownerName = textField_ownerFirstName.getText().trim() + " " + textField_ownerLastName.getText().trim();
				
			if (icNumber.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet Owner IC is missing.");
				alert.show();
				return;
			}
				
			try {
				
				Integer ownerID = model.getOwnerID(icNumber);
					
					
				if (ownerID == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet Owner not found in database");
					alert.show();
					return;
				}
					
				Integer petID = model.getPetID(ownerID, petName, petType);
					
				if (petID == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet is not registered.");
					alert.show();
					return;
				}
					
							
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/AddMedRecord.fxml").openStream());
				AddMedRecordController addMedRecordController = (AddMedRecordController)loader.getController();
				addMedRecordController.setPetID(petID);
				addMedRecordController.setPetName(petName);
				addMedRecordController.setOwnerName(ownerName);
				addMedRecordController.setOwnerID(ownerID);
				
					
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				log.logFile(e, "severe", e.getMessage());
				e.printStackTrace();
			}
				
		}
			
			
		public void refreshMedRecordTable(ActionEvent event) {
				
			if (label_petName.getText().length() < 11) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet record is empty");
				alert.show();
				return;
			}
			
			String petType = label_petType.getText().substring(11);
			String petName = label_petName.getText().substring(11);
			String icNumber = textField_ownerIcNumber.getText().trim();
				
			if (icNumber.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet Owner IC is missing.");
				alert.show();
				medRecordTable.setItems(null);
				return;
			}
				
			try {
					
				Integer ownerID = model.getOwnerID(icNumber);
					
					
				if (ownerID == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet Owner not found in database");
					alert.show();
					medRecordTable.setItems(null);
					return;
				}
					
				Integer petID = model.getPetID(ownerID, petName, petType);
					
				if (petID == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet is not registered.");
					alert.show();
					medRecordTable.setItems(null);
					return;
				}
					
				ObservableList<MedicationRecord> medicationRecords = model.getMedicationRecord(petID);
					
				if (medicationRecords.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("No Medication Record found.");
					alert.show();
					medRecordTable.setItems(null);
					return;
				}
					
				meddate.setCellValueFactory(new PropertyValueFactory<MedicationRecord, String>("MedDate"));	
				time.setCellValueFactory(new PropertyValueFactory<MedicationRecord, String>("Time"));
				medication.setCellValueFactory(new PropertyValueFactory<MedicationRecord, String>("Medication"));
				dosage.setCellValueFactory(new PropertyValueFactory<MedicationRecord, String>("Dosage"));
				frequency.setCellValueFactory(new PropertyValueFactory<MedicationRecord, String>("Frequency"));
				notes.setCellValueFactory(new PropertyValueFactory<MedicationRecord, String>("Notes"));
					
				medRecordTable.setItems(medicationRecords);
					
			} catch (Exception e) {
				log.logFile(e, "severe", e.getMessage());
				e.printStackTrace();
		    }
		}
	
	
	
////////////////////////////////////////////////CALENDER TAB //////////////////////////////////////////////////		
		
		// close tab
		public void closeCalenderTab(ActionEvent event)
		{
			
			 EventHandler<Event> handler = calender.getOnClosed();
		        if (null != handler) {
		            handler.handle(null);
		        } else {
		        	calender.getTabPane().getTabs().remove(calender);
		        }
		}
	
	// refresh appointment list in calander tab
	public void refreshAppointmentList(ActionEvent event) {
		try {
			 ObservableList<String> appointmentList = model.getAppointmentList();
			 
			 if (!appointmentList.isEmpty()) {
				 appointmentListView.setItems(appointmentList);
			 }
			 
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	public void goToAppointmentInfo(MouseEvent event) {
		try {
			
				
			if (event.getClickCount() == 2) {
				
				if (!appointmentListView.getSelectionModel().isEmpty()) {
					String listView_item = appointmentListView.getSelectionModel().getSelectedItem();
					Integer appID = Integer.parseInt(listView_item.substring(0, listView_item.indexOf(".")).trim());
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/fxml/AppointmentInfo.fxml").openStream());
					AppointmentInfoController appointmentInfoController = (AppointmentInfoController)loader.getController();
					appointmentInfoController.setAppID(appID);
				
					
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();
				}
				else {
					 Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Program says");
						alert.setHeaderText("No record is selected.");
						alert.show();
				}
				
				
			}
				
			
		} catch (Exception e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean isNumeric(String num)
	{
		if (num == null) {
	        return false;
	    }
	    try {
	        @SuppressWarnings("unused")
			double d = Double.parseDouble(num);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	
//////////////////////////////////////////////// USER TAB //////////////////////////////////////////////////		
	
	public void closeUserTab(ActionEvent event)
	{
		
		 EventHandler<Event> handler = user.getOnClosed();
	        if (null != handler) {
	            handler.handle(null);
	        } else {
	        	user.getTabPane().getTabs().remove(user);
	        	clearUserTextField(event);
	        	pf_oldPF.clear();
	        	pf_editPF.clear();
	        	listView_users.setItems(null);
	        }
	}
	
	public void viewAllUsers(ActionEvent event) throws SQLException {
		
		 ObservableList<String> userList = model.getUserList();
		 listView_users.setItems(userList);
		 
	}
	
	
	
	public void editPassword(ActionEvent event) throws SQLException
	{
		String userName = listView_users.getSelectionModel().getSelectedItem();
		
		if (userName.isEmpty()) {
			 Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("To edit password, please select user from list, retype old password and new password");
				alert.show();
				return;
			
		}
		
		if (pf_oldPF.getText().isEmpty() || pf_editPF.getText().isEmpty()) {
			 Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("To edit password, please retype old password and new password");
				alert.show();
				return;
		}
		
		if (userName.contentEquals("admin")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Program says");
			alert.setHeaderText("Admin password cannot be changed!");
			alert.show();
			log.logFile(null, "info", userName + " attempt to change admin password");
			pf_oldPF.clear();
			pf_editPF.clear();
			return;
		}
		
		String pwd = model.getUserPwd(userName);
		
		
		if (pwd != null) {
			
			
			if (pf_oldPF.getText().contentEquals(pwd)) {
				
				if (model.editUserPwd(userName, pf_editPF.getText().trim())) {
					
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("User Password is updated");
						alert.show();
						pf_oldPF.clear();
						pf_editPF.clear();
						log.logFile(null, "info", userName + " updated password successfully");
				}
				
				else {
						log.logFile(null, "warning", userName + " updated password not successful.");
				}
				
				
			}
			
			else {
				 Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Old Password is incorrect");
					alert.show();
					pf_oldPF.clear();
					pf_editPF.clear();
					
			}
		}
		
		
	}
	
	public void registerNewUser(ActionEvent event) throws SQLException
	{
		
		String new_user = tf_newUser.getText().trim();
		String new_pf = pf_newPF.getText().trim();
		String confirm_pf = pf_confirmPF.getText().trim();
		
		
		if (new_user.isEmpty()) {
			 Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Text Field is empty");
				alert.show();
				return;
		}
		
		if (new_pf.isEmpty() || confirm_pf.isEmpty()) {
			 Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Password Field is empty");
				alert.show();
				return;
		}
		
		if (new_pf.contentEquals(confirm_pf)) {
			
			if (model.registerNewUser(new_user, confirm_pf)) {
				
				log.logFile(null, "info", new_user + " is registered successfully");
				
				 Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Program says");
					alert.setHeaderText("User is registered");
					alert.show();
					viewAllUsers(event);
					clearUserTextField(event);
			}
		}
		
		else {
			
			 Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Password is not the same");
				alert.show();
				pf_newPF.clear();
				pf_confirmPF.clear();
				
				return;
		}
		
		
	}
	
	public void deleteUser(ActionEvent event) throws SQLException {
		
		String userName = label_userName.getText().substring(15);

		 Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		 alertConfirm.setTitle("Program says");
		 alertConfirm.setHeaderText("Are you sure you want to delete?");
		

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
        	
        	if (userName.contentEquals("admin")) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Program says");
    			alert.setHeaderText("Admin user cannot be deleted!");
    			alert.show();
    			log.logFile(null, "info", userName + " attempt to delete admin user.");
    			return;
    		}
        	
        	if (model.deleteUser(userName)) {
        		log.logFile(null, "info", userName + " is deleted successfully");
				
				 Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Program says");
					alert.setHeaderText("User is registered");
					alert.show();
					logout(event);
			}
        	
        	else {
				log.logFile(null, "warning", userName + " is not deleted");
			}
        	
        	
        	
        	}
        }
	
	

	public void clearUserTextField(ActionEvent event)
	{
		tf_newUser.clear();
		pf_newPF.clear();
		pf_confirmPF.clear();
	}
	
	
/////////////////////////////////////////////// DATABASE TAB ////////////////////////////////////////////////	
	
	public void closeDatabaseTab(ActionEvent event)
	{
		
		 EventHandler<Event> handler = database.getOnClosed();
	        if (null != handler) {
	            handler.handle(null);
	        } else {
	        	database.getTabPane().getTabs().remove(database);
	        	tf_newEntry.clear();
	        	listView_database.setItems(null);
	        	tb_database.getItems().clear();
	        	tb_database.getColumns().clear();
	        	label_listName.setText("");
	        }
	}
	
	public void selectType(ActionEvent event) {
		try {
			
			if (rb_catVaccine.isSelected()) {
				 ObservableList<String> vacCatList = model_VacRecord.getVaccineCat();
				 listView_database.setItems(vacCatList);
				 label_listName.setText("Cat Vaccine");
			}
			
			else if(rb_dogVaccine.isSelected()) {
				
				 ObservableList<String> vacDogList = model_VacRecord.getVaccineDog();
				 listView_database.setItems(vacDogList);
				 label_listName.setText("Dog Vaccine");
			}
			
			else if(rb_catBreed.isSelected()) {
				
				 ObservableList<String> catBreed = model_breed.getCatBreedList();
				 listView_database.setItems(catBreed);
				 label_listName.setText("Cat Breed");
			}
			
			else if(rb_dogBreed.isSelected()) {
				
				 ObservableList<String> dogBreed = model_breed.getDogBreedList();
				 listView_database.setItems(dogBreed);
				 label_listName.setText("Dog Breed");
			}
			
			else {
				listView_database.setItems(null);
			}
			 
			
			
		} catch (Exception e) {
			 Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText(e.getMessage());
				alert.show();
		}
		
	}
	
	public void addNewEntry(ActionEvent event) {
	
		try {
			
			String newEntry = tf_newEntry.getText().trim();
			
			if (newEntry.isEmpty()) {
				 Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Text Field is empty");
					alert.show();
					return;
			}
			
			if (rb_catVaccine.isSelected()) {
				
				if (model.addNewCatVac(newEntry)) {
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("New Vaccine Added Successfully!");
						alert.show();
						tf_newEntry.clear();
						selectType(event);
						log.logFile(null, "info", newEntry + " is added successfully");
				}
				else {
						log.logFile(null, "warning", newEntry + " unable to add vaccine");
				}
			}
			
			else if (rb_dogVaccine.isSelected()) {

				if (model.addNewDogVac(newEntry)) {
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("New Vaccine Added Successfully!");
						alert.show();
						tf_newEntry.clear();
						selectType(event);
						log.logFile(null, "info", newEntry + " is added successfully");
				}
				else {
					log.logFile(null, "warning", newEntry + " unable to add vaccine");
				}
			}
			
			else if(rb_catBreed.isSelected()){
				
				if (model.addNewBreed(newEntry, true)) {
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("New Breed Added Successfully!");
						alert.show();
						tf_newEntry.clear();
						selectType(event);
						log.logFile(null, "info", newEntry + " is added successfully");
				}
				else {
					log.logFile(null, "warning", newEntry + " unable to add breed");
				}
			
			}
			
			else if (rb_dogBreed.isSelected()) {
				if (model.addNewBreed(newEntry, false)) {
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("New Breed Added Successfully!");
						alert.show();
						tf_newEntry.clear();
						selectType(event);
						log.logFile(null, "info", newEntry + " is added successfully");
				}
				
				else {
					log.logFile(null, "warning", newEntry + " unable to add breed");
				}
			}
			
			
		} catch (Exception e) {
			log.logFile(null, "severe", e.getMessage());
		}
		
		
		
	}
	
	public void deleteItem(ActionEvent event) throws SQLException {
		
		if (!listView_database.getSelectionModel().isEmpty()) {
			
			String delete_item = listView_database.getSelectionModel().getSelectedItem();
			
			if (rb_catVaccine.isSelected()) {
				if (model.deleteCatVac(delete_item)) {
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("Vaccine is Deleted Successfully!");
						alert.show();
						selectType(event);
						log.logFile(null, "info", delete_item + " deleted successfully");
						
				}
				
				else {
						log.logFile(null, "warning", delete_item + " unable to delete vaccine");
				}
			
			}
			
			else if (rb_dogVaccine.isSelected()) {
				if (model.deleteDogVac(delete_item)) {
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("Vaccine is Deleted Successfully!");
						alert.show();
						selectType(event);
						log.logFile(null, "info", delete_item + " deleted successfully");
				}
				
				else {
					log.logFile(null, "warning", delete_item + " unable to delete vaccine");
				}
			
			}
			
			else if (rb_catBreed.isSelected()){
				if (model.deleteBreed(delete_item, true)) {
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("Breed is Deleted Successfully!");
						alert.show();
						selectType(event);
						log.logFile(null, "info", delete_item + " deleted successfully");
				}
				else {
					log.logFile(null, "warning", delete_item + " unable to delete breed");
				}
				
			}
			
			else if (rb_dogBreed.isSelected()){
				if (model.deleteBreed(delete_item, false)) {
					 Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Program says");
						alert.setHeaderText("Breed is Deleted Successfully!");
						alert.show();
						selectType(event);
						log.logFile(null, "info", delete_item + " deleted successfully");
				}
				else {
						log.logFile(null, "warning", delete_item + " unable to delete breed.");
				}
				
			}
			
		}
		
		else {
			 Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("No Item is selected");
				alert.show();
		}
		
		
	}
	
	public void getTable(ResultSet rs) {
		
		 database_data = FXCollections.observableArrayList();
		
		try {
			
			 for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
				 
	                //We are using non property style for making dynamic table
				 
	                final int j = i;                
	                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
	                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
	                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
	                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
	                    }                    
	                });

	                tb_database.getColumns().addAll(col); 
	                
	            }

			 
	            while(rs.next()){
	                //Iterate Row
	            	
	                ObservableList<String> row = FXCollections.observableArrayList();
	                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
	                	
	                    //Iterate Column
	                	
	                    row.add(rs.getString(i));
	                }
	              
	                database_data.add(row);

	            }

	            //FINALLY ADDED TO TableView
	            
	            tb_database.setItems(database_data);
	            
		} catch (Exception e) {
				log.logFile(null, "severe", "unable to show table. " + e.getMessage() );
				e.printStackTrace();
		}
	}

	
	public void viewOwnerTable(ActionEvent event) {
		tb_database.getItems().clear();
		tb_database.getColumns().clear();
		 ResultSet rs = model.getTable(true);
		 getTable(rs);
		
	}
	
	
	public void viewPetTable(ActionEvent event) {
		tb_database.getItems().clear();
		tb_database.getColumns().clear();
		 ResultSet rs = model.getTable(false);
		 getTable(rs);
		
	}
	
	public void viewVacTable(ActionEvent event) {
		tb_database.getItems().clear();
		tb_database.getColumns().clear();
		 ResultSet rs = model.getRecord(true);
		 getTable(rs);
		
	}
	
	public void viewMedTable(ActionEvent event) {
		tb_database.getItems().clear();
		tb_database.getColumns().clear();
		 ResultSet rs = model.getRecord(false);
		 getTable(rs);
		
	}
	
	
	public void clearDatabaseTextField(ActionEvent event) {
		tf_newEntry.clear();
	}
	
	public void resetDatabase(ActionEvent event) throws SQLException {
		 Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		 alertConfirm.setTitle("Program says");
		 alertConfirm.setHeaderText("Are you sure you want to reset database?");
		 alertConfirm.setContentText("This action will delete all Owners, Pets, Vaccination and Medication Records.");
		

         Optional<ButtonType> result = alertConfirm.showAndWait();
         if (result.get() == ButtonType.OK){
        	 int count = model.deleteAll();
     		if (count > 1) {
     			Alert alert = new Alert(AlertType.INFORMATION);
     			alert.setTitle("Program says");
     			alert.setHeaderText("Database Reset Successful.");
     			alert.show();
     			log.logFile(null, "info", "Database Reset Successful.");
     		}
     		else {
     			log.logFile(null, "warning", "Database Reset Not Successful.");
     		}
         }
		
	}
	
	
/////////////////////////////////////////////// STATISTICS TAB ////////////////////////////////////////////////	
	
	public void closeStatisticsTab(ActionEvent event) {
		 EventHandler<Event> handler = statistics.getOnClosed();
		if (null != handler) {
            handler.handle(null);
        } else {
        	statistics.getTabPane().getTabs().remove(statistics);
        	label_pieChartNumber.setText("");;
        	
        }
	}
	
	public void buildPieChart() {
		 piechart_data = FXCollections.observableArrayList();
		 
		 try {
			 ResultSet rs = model.getPieChartData();
			 while(rs.next()){
				 
	                //adding data on piechart data
	                piechart_data.add(new PieChart.Data(rs.getString("Vaccine"),rs.getInt("count(Vaccine)")));
			 }
			 
		} catch (Exception e) {
			
			log.logFile(null, "severe", "unable to show pie chart. " + e.getMessage());
		
		}
		
	}
	
	

	
}
