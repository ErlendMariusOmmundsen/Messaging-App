package project_fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project_core.Account;
import project_core.Message;
import project_core.io.AccountIO;

public class appController {
	
	@FXML private AnchorPane loginPane, CreateAccountPane;
	@FXML private SplitPane splitPane;
	@FXML private Label inboxLabel, welcomeLabel, emailLabel, errorLabel, errorLabel2, toLabel, fromLabel;
	@FXML private TextField emailField, fromField, subjectField, txt_C_Email;
	@FXML private PasswordField passwordField, txt_C_password;
	@FXML private ComboBox<String> toComboBox;	
	@FXML private TextArea textArea;
	@FXML private ListView<String> inbox;
	@FXML private Button loginButton, logoutButton, newMessageButton, sendButton, btnConfirm, backButton;
	
	private Account currentAccount;
	private AccountDataAccess dataAccess;
	
	public appController() {
		dataAccess = new RestAccountDataAccess();
	}
	
	/**
	 * Makes the app visible
	 */
	private void appVisibility() {
		loginPane.setVisible(false);
		splitPane.setVisible(true);
		CreateAccountPane.setVisible(false);
	}
	
	/**
	 * Makes the login menu visible
	 */
	private void loginVisibility() {
		loginPane.setVisible(true);
		splitPane.setVisible(false);
		CreateAccountPane.setVisible(false);
	}
	
	private void createAccountVisiblity() {
		loginPane.setVisible(false);
		splitPane.setVisible(false);
		CreateAccountPane.setVisible(true);
	}
	
	/**
	 * This method clears text in the app: Inbox, Textareas, TextFields, other Labels, etc.
	 */
	private void clear() {
		inbox.getItems().clear();
		textArea.setText("");
		toLabel.setText("To: ");
		fromLabel.setText("From: ");
		passwordField.setText("");
		errorLabel.setText("");
		errorLabel2.setText("");
		toComboBox.setValue("");
		fromField.setText("");
		subjectField.setText("");
	}
	
	/**
	 * Checks if the login is valid with accounts in the system. If so the mail application will be visible.
	 */
	public void loginCheck() {
		String emailInput = emailField.getText();
		String passwordInput = passwordField.getText();
		Account account = new Account(emailInput, passwordInput);
		try {
			if (dataAccess.accountValid(account)) {
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
			System.out.println(AccountIO.resourceFilepath);
		}
		
	}
	
	/**
	 * logs this account out, and returns back to the login menu.
	 */
	public void logout() {
		clear();
		this.currentAccount = null;
		loginVisibility();
	}
	
	/**
	 * This method initializes the new message-screen for the user
	 * 
	 * (Not done)
	 */
	public void initNewMessage() {
		textArea.setText("");
		fromField.setText("");
		subjectField.setText("");
		textArea.setEditable(true);
	}
	
	/**
	 * Sends the message with the current information filled in.
	 */
	public void sendMessage() {
		Account toAccount = new Account(toComboBox.getValue());
		String subject = subjectField.getText();
		String text = textArea.getText();
		Message message = new Message(subject , text, toAccount, currentAccount);
		
		try {
			dataAccess.sendMessage(message, currentAccount);
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		// Bare for å teste
		updateInbox();
		
		System.out.println("Message sent");
	}
	
	/**
	 * Loads the messages of the system to the inbox of the current account.
	 * Then adds the subjects of the messages to the inbox-UI.
	 */
	public void updateInbox() {
		
		try {
			List<Message> messages = dataAccess.getInboxMessages(currentAccount);
			currentAccount.getInbox().clear();
			currentAccount.getInbox().addMessages(messages);
		} catch (IOException e) {
			System.out.println("Couldn't load new messages.");
		}
		
		List<String> subjects = currentAccount.getInbox().getMessages().stream().map(m -> m.getSubject()).collect(Collectors.toList());
		inbox.getItems().clear();
		inbox.getItems().addAll(subjects);
	}
	
	public void updateContacts() {
		Collection<Account> contacts = currentAccount.getContacts().getAccounts();
		toComboBox.getItems().clear();
		List<String> mailAddresses = contacts.stream().map(a -> a.getMail_address()).collect(Collectors.toList());		
		ObservableList<String> noDupsObservable = FXCollections.observableArrayList();
		
		for (String address : mailAddresses) {
			if (!noDupsObservable.contains(address)) {
				noDupsObservable.add(address);
			}
		}
		toComboBox.setItems(noDupsObservable);
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
			dataAccess.overwriteMessagesToInbox(currentAccount.getInbox().getMessages(), currentAccount);
		} catch (IOException e) {
			System.out.println("Couldn't edit the Inbox file");
		}
		
		List<String> subjects = currentAccount.getInbox().getMessages().stream().map(m -> m.getSubject()).collect(Collectors.toList());
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
		
		textArea.setText(message.getMessage());
		textArea.setEditable(false);
		toComboBox.setValue(message.getTo().getMail_address());
		fromField.setText(message.getFrom().getMail_address());
		subjectField.setText(message.getSubject());
		
		System.out.println(currentAccount.getContacts().getAccounts().stream().map(a -> a.getMail_address()).collect(Collectors.toList()));
	}
	
	/**
	 * Makes the CA pane visible when the user clicks on the "Create Account" button on the login pane
	 */
	
	public void handle_switch_CA() {
		createAccountVisiblity();
	}
	
	public void handle_switch_LI() {
		loginVisibility();
	}
	
	/**
	 * Creates a new account object and checks if it is nonempty.
	 * 
	 * Returns to login page after completion.
	 */
	public void handleCreateAccount() {
		String mail = txt_C_Email.getText();
		String password = txt_C_password.getText();
		if(mail.length() < 1) {
			errorLabel2.setText("Error. The mail field cant be empty");
		}
		else if(password.length() < 1) {
			errorLabel2.setText("Error. The password field cant be empty");
		}
		else {
			Account newAccount = new Account(mail, password);
			try {
				dataAccess.createAccount(newAccount);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			}
			loginVisibility();
		}
	}
}
