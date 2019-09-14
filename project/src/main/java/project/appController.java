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
	@FXML private Button loginButton, logoutButton, newMessageButton;
	
	private appIO io = new appIO();
	
	private Account currentAccount;
	
	/**
	 * Makes the app visible
	 */
	private void appVisibility() {
		loginPane.setVisible(false);
		splitPane.setVisible(true);
	}
	
	/**
	 * Makes the login menu visible
	 */
	private void loginVisibility() {
		loginPane.setVisible(true);
		splitPane.setVisible(false);
	}
	
	/**
	 * This method clears text in the app: Inbox, Textareas, other Labels, etc.
	 */
	private void clear() {
		inbox.getItems().clear();
		textArea.setText("");
		toLabel.setText("To: ");
		fromLabel.setText("From: ");
		passwordField.setText("");
		errorLabel.setText("");
	}
	
	/**
	 * Checks if the login is valid with accounts in the system. If so the mail application will be visible.
	 */
	public void loginCheck() {
		String emailInput = emailField.getText();
		String passwordInput = passwordField.getText();
		Account account = new Account(emailInput, passwordInput);
		try {
			if (account.isValid()) {
				currentAccount = account;
				clear();
				appVisibility();
				updateInbox();
			}
			else {
				errorLabel.setText("Error: No username/password combination like that.");
				errorLabel.setVisible(true);
			}
		} catch (IOException e) {
			errorLabel.setText("Error: " + e.getMessage());
			errorLabel.setVisible(true);
		}
		
	}
	
	/**
	 * logs this account out, and return back to the login menu.
	 */
	public void logout() {
		clear();
		this.currentAccount = null;
		loginVisibility();
	}
	
	/**
	 * This method initializes the new message-screen for the user
	 *
	 */
	public void initNewMessage() {
		textArea.setText("");
		textArea.setEditable(true);
	}
	
	/**
	 * Loads the messages of the system to the inbox of the current account.
	 * Then adds the subjects of the messages to the inbox-UI.
	 */
	public void updateInbox() {
		
		try {
			currentAccount.getInbox().loadMessages();
		} catch (IOException e) {
			System.out.println("Couldn't load new messages.");
		}
		
		List<String> subjects = currentAccount.getInbox().getMessages().stream().map(m -> m.getSubject()).collect(Collectors.toList());
		inbox.getItems().clear();
		inbox.getItems().addAll(subjects);
	}
	
	/**
	 * Deletes the current selected message in the Inbox-UI.
	 * Then it uploads the new inbox to the system. (only to a test file right now)
	 * 
	 * This functionality is only a test right now.
	 */
	public void deleteMessage() {
		int messageIndex = inbox.getSelectionModel().getSelectedIndex();
		if (messageIndex == -1) return;
		
		currentAccount.getInbox().deleteMessage(messageIndex);
		try {
			currentAccount.getInbox().uploadInbox();
		} catch (IOException e) {
			System.out.println("Couldn't edit the Inbox file");
		}
		//this.updateInbox();
		List<String> subjects = currentAccount.getInbox().getMessages().stream().map(m -> m.getSubject()).collect(Collectors.toList());
		System.out.println(subjects);
		System.out.println(currentAccount.getInbox().getMessages());
		inbox.getItems().clear();
		inbox.getItems().addAll(subjects);
	}
	
	/**
	 * Displays the selected message in the Inbox-UI on the text area.
	 */
	public void displayMessage() {
		int messageIndex = inbox.getSelectionModel().getSelectedIndex();
		if (messageIndex == -1) return;
		Message message = currentAccount.getInbox().getMessage(messageIndex);
		
		textArea.setText("Subject: " + message.getSubject() + "\n\n" + message.getMessage());
		textArea.setEditable(false);
		toLabel.setText("To: " + message.getTo().getMail_address());
		fromLabel.setText("From: " + message.getFrom().getMail_address());
	}
}
