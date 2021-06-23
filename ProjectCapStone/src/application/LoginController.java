package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ResourceBundle;

import common.FullCalendarView;
import common.Log;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.LoginModel;


public class LoginController implements Initializable {
	
	
	@FXML private TextField textField_userName;
	@FXML private TextField textField_password;
	
	public LoginModel loginModel = new LoginModel();
	public Log log = new Log();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (loginModel.isDbConnected()) {
			
			log.logFile(null, "info", "Connected to SQLite database");
			loginModel.populateTables();
		}
		else {
			
			log.logFile(null, "warning", "Not connected to SQLite database");
		}
		
	}
	
	@FXML
	public void onEnter(ActionEvent event){
	   login(event);
	}
	
	public void login(ActionEvent event) {
		
		try {
			if (loginModel.isLogin(textField_userName.getText().trim(), textField_password.getText().trim())) {
				
				log.logFile(null, "info", textField_userName.getText() + " log in successfully.");
				
				
					((Node)event.getSource()).getScene().getWindow().hide();
					
					
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/fxml/MainInterface.fxml").openStream());
					MainInterfaceController mainInterfaceController = (MainInterfaceController)loader.getController();
					mainInterfaceController.getUser(textField_userName.getText());
					mainInterfaceController.calendarPane.getChildren().add(new FullCalendarView(YearMonth.now()).getView());
				
					
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setResizable(false);
					primaryStage.setScene(scene);
					primaryStage.show();
				
			}
			else {
				
				log.logFile(null, "warning", "Credential is invalid for " + textField_userName.getText());
				textField_userName.clear();
				textField_password.clear();
			}
			
		} catch (SQLException e) {
			
			
			log.logFile(e, "severe", e.getMessage()  + " for " + textField_userName.getText());
			textField_userName.clear();
			textField_password.clear();
			e.printStackTrace();
		} catch (IOException e) {
			
			log.logFile(e, "severe", e.getMessage()  + " for " + textField_userName.getText());
			textField_userName.clear();
			textField_password.clear();
			e.printStackTrace();
		}
		
	}
	
	public void close(ActionEvent event)
	{
		Platform.exit();
        System.exit(0);
	}

}
