package project;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class appController {
	
	@FXML private AnchorPane loginPane;
	@FXML private SplitPane splitPane;
	@FXML private Label inboxLabel, welcomeLabel, emailLabel, errorLabel;
	@FXML private TextField emailField;
	@FXML private PasswordField passwordField;
	@FXML private TextArea textArea;
	@FXML private ListView inbox;
	@FXML private Button loginButton;
	
	private appIO io = new appIO(); 
	
	private void loginVisibility() {
		loginPane.setVisible(false);
		splitPane.setVisible(true);
	}
	
	public void loginCheck() {
		String emailInput = emailField.getText();
		String passwordInput = passwordField.getText();
		ArrayList<String> users = new ArrayList<String>();
		try {
			users = io.loadData("users.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < users.size(); i++) {
			String user = users.get(i);
			String email = user.substring(0, user.indexOf("\t"));
			String password = user.substring(user.indexOf("\t")).strip();
			if (email.equals(emailInput) && password.equals(passwordInput)) {
				loginVisibility();
				return;
			}
		}
		
		// Dersom ingen brukere matcher
		errorLabel.setText("Error: No username/password combination like that.");
		errorLabel.setVisible(true);
		
	}	
	
}
