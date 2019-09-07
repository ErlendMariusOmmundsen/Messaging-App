package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class appController {
	
	@FXML private AnchorPane loginPane;
	@FXML private SplitPane splitPane;
	@FXML private Label inboxLabel, welcomeLabel, emailLabel, errorLabel, toLabel, fromLabel;
	@FXML private TextField emailField;
	@FXML private PasswordField passwordField;
	@FXML private TextArea textArea;
	@FXML private ListView<String> inbox;
	@FXML private Button loginButton;
	
	private appIO io = new appIO();
	
	private Account currentAccount;
	
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
				this.currentAccount = new Account(email, password);
				loginVisibility();
				updateInbox();
				return;
			}
		}
		
		// Dersom ingen brukere matcher
		errorLabel.setText("Error: No username/password combination like that.");
		errorLabel.setVisible(true);
		
	}
	
	public void updateInbox() {
		
		try {
			currentAccount.loadInboxMessages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> subjects = currentAccount.getInbox().getMessages().stream().map(m -> m.getSubject()).collect(Collectors.toList());
		System.out.println(subjects);
		inbox.getItems().addAll(subjects);
	}
	
	public void displayMessage() {
		int messageIndex = inbox.getSelectionModel().getSelectedIndex();
		Message message = currentAccount.getInbox().getMessages().get(messageIndex); 
		
		textArea.setText("Subject: " + message.getSubject() + "\n\n" + message.getMessage());
		toLabel.setText("To: " + message.getTo().getMail_address());
		fromLabel.setText("From: " + message.getFrom().getMail_address());
		
	}
}
