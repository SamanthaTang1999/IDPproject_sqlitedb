package application;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import common.ComboBoxAutoComplete;
import common.Log;
import common.Pet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import model.AddEditPetModel;
import model.MainInterfaceModel;

public class AddEditPetController implements Initializable {
	
	@FXML private Label label_petInfo;
	
	@FXML private TextField textField_petName;
	@FXML private TextField tf_otherBreed;
	@FXML private ComboBox<String> comboBox_breed;
	
	@FXML private RadioButton radioButton_dog;
	@FXML private RadioButton radioButton_cat;
	@FXML private RadioButton radioButton_other;
	@FXML private RadioButton radioButton_male;
	@FXML private RadioButton radioButton_female;
	@FXML private RadioButton radioButton_neuteredYes;
	@FXML private RadioButton radioButton_neuteredNo;
	
	@FXML private DatePicker datePicker_dob;
	
	public Log log = new Log();

	
	private Integer ownerID;	
	private Integer petID;	
	private String mode;

	public AddEditPetModel model = new AddEditPetModel();
	public MainInterfaceModel mainModel = new MainInterfaceModel();
	
	public void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
		
	}
	
	public void setPetID(Integer petID) throws SQLException {
		this.petID = petID;
		
		if (this.petID != null) {
			
			 ObservableList<Pet> petList = mainModel.getPetInfo(petID);
			 
			 if (petList.isEmpty()) {
				 Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("No pet found");
					alert.show();
					return;
			 }
				textField_petName.setText(petList.get(0).getPetName());
				comboBox_breed.setValue(petList.get(0).getBreed());
				datePicker_dob.setValue(LocalDate.parse(petList.get(0).getDob()));
				
				if (petList.get(0).getGender().matches("Male")) {
					radioButton_male.setSelected(true);
				}
				
				if (petList.get(0).getGender().matches("Female")) {
					radioButton_female.setSelected(true);
				}
				
				if (petList.get(0).getPetType().matches("Dog")) {
					radioButton_dog.setSelected(true);
				}
				
				if (petList.get(0).getPetType().matches("Cat")) {
					radioButton_cat.setSelected(true);
				}
				
				if (petList.get(0).getPetType().matches("Others")) {
					radioButton_other.setSelected(true);
				}
				
				if (petList.get(0).getNeutered().matches("Yes")) {
					radioButton_neuteredYes.setSelected(true);
				}
				
				if (petList.get(0).getNeutered().matches("No")) {
					radioButton_neuteredNo.setSelected(true);
				}
				
				if (radioButton_dog.isSelected()) {
					ObservableList<String> breedList =  model.getDogBreedList();
					if (!breedList.isEmpty()) {
						comboBox_breed.setItems(breedList);
						new ComboBoxAutoComplete<String>(comboBox_breed);
					}
				}
					
					else if (radioButton_cat.isSelected()) {
						ObservableList<String> breedList =  model.getCatBreedList();
						if (!breedList.isEmpty()) {
							comboBox_breed.setItems(breedList);
							new ComboBoxAutoComplete<String>(comboBox_breed);
						}
					}
					
						else  {
							ObservableList<String> breedList =  FXCollections.observableArrayList("Others");
							if (!breedList.isEmpty()) {
								comboBox_breed.setItems(breedList);
								new ComboBoxAutoComplete<String>(comboBox_breed);
							}
						}
					
				
					
			
		}
	}
	
	public void setMode(String mode) {
		this.mode = mode;
		
		if (this.mode.matches("ADD")) {
			label_petInfo.setText("Add New Pet");
			comboBox_breed.setItems(null);
		}
		
		else if (mode.matches("EDIT")) {
			label_petInfo.setText("Edit Pet Info");
			comboBox_breed.setItems(null);
		}
		
	}
	
	public void selectPetTypeRadioButton(ActionEvent event) throws SQLException {
		
		if (radioButton_dog.isSelected()) {
			comboBox_breed.setVisible(true);
			tf_otherBreed.setVisible(false);
			ObservableList<String> breedList = model.getDogBreedList();
			if (!breedList.isEmpty()) {
				comboBox_breed.setItems(breedList);
				new ComboBoxAutoComplete<String>(comboBox_breed);
			}
		}
		
			else if (radioButton_cat.isSelected()) {
				comboBox_breed.setVisible(true);
				tf_otherBreed.setVisible(false);
				ObservableList<String> breedList = model.getCatBreedList();
				if (!breedList.isEmpty()) {
					comboBox_breed.setItems(breedList);
					new ComboBoxAutoComplete<String>(comboBox_breed);
				}
			}
			
				else  {
					
					comboBox_breed.setVisible(false);
					tf_otherBreed.setVisible(true);
					
				}
	}
	
	
	public void savePetInfo(ActionEvent event) {
		
		String petName = textField_petName.getText().trim();
		String breed = "";
		String petType = "";
		String gender = "";
		String dob = "";
		String neutered = "";
		
		try {
			
			if (comboBox_breed.getSelectionModel().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet info is incomplete.");
				alert.show();
				return;
			}
			
				
			
				if (radioButton_dog.isSelected()) {
					petType = radioButton_dog.getText();
					breed = comboBox_breed.getValue();
				}
				
				if (radioButton_cat.isSelected()) {
					petType = radioButton_cat.getText();
					breed = comboBox_breed.getValue();
				}
				
				if (radioButton_other.isSelected()) {
					petType = radioButton_other.getText();
					breed = tf_otherBreed.getText();
				}
				
				if (radioButton_male.isSelected()) {
					gender = radioButton_male.getText();
				}
				
				if (radioButton_female.isSelected()) {
					gender = radioButton_female.getText();
				}
				
				if (radioButton_neuteredYes.isSelected()) {
					neutered = radioButton_neuteredYes.getText();
				}
				
				if (radioButton_neuteredNo.isSelected()) {
					neutered = radioButton_neuteredNo.getText();
				}
				
				LocalDate lDate = datePicker_dob.getValue();
				
				if (lDate == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet info is incomplete.");
					alert.show();
					return;
				}
				
				dob = lDate.toString();
			
			if (petName.isEmpty() || breed.isEmpty() || petType.isEmpty() || gender.isEmpty() || neutered.isEmpty()) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Program says");
				alert.setHeaderText("Pet info is incomplete.");
				alert.show();
				return;
				
			}
			

			if (mode.matches("ADD")) {
				
				if (model.addPetInfo(ownerID, petName, petType, breed, gender, dob, neutered)) {
					
					((Node)event.getSource()).getScene().getWindow().hide();
				
					
					
					log.logFile(null, "info", petName + " the " + petType + " is registered into database.");
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet info is saved.");
					alert.show();
					
					
				}
				
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet info is not saved.");
					alert.show();
				}
			}
			
			else if (mode.matches("EDIT")) {
				
					if (model.editPetInfo(petID, petName, petType, breed, gender, dob, neutered)) {
					
					((Node)event.getSource()).getScene().getWindow().hide();
					
					
					
					log.logFile(null, "info", petName + " with PetID : " + petID + " is edited in database.");
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet info is updated.");
					alert.show();
					
					
					}
				
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Program says");
					alert.setHeaderText("Pet info is not updated.");
					alert.show();
				}
				
			}
				
			
			
			
		} catch (SQLException e) {
			log.logFile(e, "severe", e.getMessage());
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tf_otherBreed.setVisible(false);
		
	}

}
