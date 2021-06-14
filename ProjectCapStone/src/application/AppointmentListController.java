package application;

import java.sql.SQLException;
import java.time.LocalDate;

import common.Log;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.AppointmentListModel;


public class AppointmentListController {
	
	private LocalDate date;
	@FXML Label label_date;
	@FXML ListView<String> listView_appointmentList;
	
	public AppointmentListModel model = new AppointmentListModel();
	public Log log = new Log();

	public void setDate(LocalDate date) throws SQLException {
		this.date = date;
		
		if (this.date != null) {
			label_date.setText(date.toString());
			
			
 ObservableList<String> appointmentList = model.getAppointmentList(label_date.getText());
			 
			 if (appointmentList.isEmpty()) {
				listView_appointmentList.setItems(null);
			 }
			 
			 listView_appointmentList.setItems(appointmentList);
			 		
			
		}
		
	}
	
	
	public void goToAppointmentInfo(MouseEvent event) {
		try {
			
				
			if (event.getClickCount() == 2) {
				
				if (!listView_appointmentList.getSelectionModel().isEmpty()) {
					String listView_item = listView_appointmentList.getSelectionModel().getSelectedItem();
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

}
