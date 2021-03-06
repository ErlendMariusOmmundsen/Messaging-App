
package project_fxui;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

public class AppController {

  @FXML private AnchorPane loginPane;
  @FXML private AnchorPane createAccountPane;
  @FXML private SplitPane splitPane;
  
  @FXML private Label inboxLabel;
  @FXML private Label welcomeLabel;
  @FXML private Label emailLabel;
  @FXML private Label errorLabel;
  @FXML private Label errorLabel2;
  @FXML private Label toLabel;
  @FXML private Label fromLabel;
  
  @FXML private TextField emailField;
  @FXML private TextField fromField;
  @FXML private TextField subjectField;
  @FXML private TextField txtCEmail;
  
  @FXML private PasswordField passwordField;
  @FXML private PasswordField txtCpassword;
  
  @FXML private ComboBox<String> toComboBox;
  @FXML private TextArea textArea;
  @FXML private ListView<String> inbox;
  
  @FXML private Button loginButton;
  @FXML private Button logoutButton;
  @FXML private Button newMessageButton;
  @FXML private Button sendButton;
  @FXML private Button btnConfirm;
  @FXML private Button backButton;

  private Account currentAccount;
  private AccountDataAccess dataAccess;

  /**
   * This constructor will make RestAccountDataAccess as the default AccountDataAccess.
   */
  public AppController() {
    dataAccess = new RestAccountDataAccess();
  }

  /**
   * Makes the app visible.
   */
  private void appVisibility() {
    loginPane.setVisible(false);
    splitPane.setVisible(true);
    createAccountPane.setVisible(false);
  }

  /**
   * Makes the login menu visible.
   */
  public void loginVisibility() {
    loginPane.setVisible(true);
    splitPane.setVisible(false);
    createAccountPane.setVisible(false);
    clear();
  }

  /**
   * Makes the create account page visible.
   */
  public void createAccountVisiblity() {
    loginPane.setVisible(false);
    splitPane.setVisible(false);
    createAccountPane.setVisible(true);
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
    txtCEmail.setText("");
    txtCpassword.setText("");
  }

  /**
   * Displays an alert box with relevant information.
   */
  private void displayAlert(AlertType type, String message, String header) {
    Alert alert = new Alert(type);
    alert.setHeaderText(header);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Checks if the login is valid with accounts in the system. If so the mail application will be
   * visible.
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
      } else {
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
   * This method initializes the new message-screen for the user.
   */
  public void initNewMessage() {
    textArea.setText("");
    fromField.setText(currentAccount.getMail_address());
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
    Message message = new Message(subject, text, toAccount, currentAccount);

    try {
      dataAccess.sendMessage(message, currentAccount);
      displayAlert(AlertType.INFORMATION, "Message Sent!", null);
    } catch (IllegalStateException e) {
      displayAlert(AlertType.ERROR, "Either of the accounts does not exist.", null);
      System.out.println(e.getMessage());
    } catch (IOException e) {
      displayAlert(AlertType.ERROR, "There was a communication error.", null);
      System.out.println(e.getMessage());
    }

    // Bare for � teste
    updateInbox();

  }

  /**
   * Loads the messages of the system to the inbox of the current account. Then adds the subjects of
   * the messages to the inbox-UI.
   */
  public void updateInbox() {

    try {
      List<Message> messages = dataAccess.getInboxMessages(currentAccount);
      currentAccount.getInbox().clear();
      currentAccount.getInbox().addMessages(messages);
    } catch (IOException e) {
      displayAlert(AlertType.ERROR, "Couldn't load new messages.", null);
      System.out.println("Couldn't load new messages.");
    }

    List<String> subjects = currentAccount.getInbox().getMessages().stream()
        .map(m -> m.getSubject()).collect(Collectors.toList());
    inbox.getItems().clear();
    inbox.getItems().addAll(subjects);
  }

  /**
   * Updates contact list.
   */
  public void updateContacts() {
    Collection<Account> contacts = currentAccount.getContacts().getAccounts();
    toComboBox.getItems().clear();
    List<String> mailAddresses =
        contacts.stream().map(a -> a.getMail_address()).collect(Collectors.toList());
    ObservableList<String> noDupsObservable = FXCollections.observableArrayList();

    for (String address : mailAddresses) {
      if (!noDupsObservable.contains(address)) {
        noDupsObservable.add(address);
      }
    }
    toComboBox.setItems(noDupsObservable);
  }

  /**
   * Deletes the current selected message in the Inbox-UI. Then it uploads the new inbox to the
   * system. (only to a test file right now)
   * This functionality is only a test right now.
   */
  public void deleteMessage() {
    int messageIndex = inbox.getSelectionModel().getSelectedIndex();
    if (messageIndex == -1) {
      return;
    }
    
    currentAccount.getInbox().deleteMessage(messageIndex);
    try {
      dataAccess.overwriteMessagesToInbox(currentAccount.getInbox().getMessages(), currentAccount);
    } catch (IOException e) {
      displayAlert(AlertType.ERROR, "Couldn't edit the Inbox file", null);
      System.out.println("Couldn't edit the Inbox file");
    }
    
    clear();
    List<String> subjects = currentAccount.getInbox().getMessages().stream()
        .map(m -> m.getSubject()).collect(Collectors.toList());
    inbox.getItems().clear();
    inbox.getItems().addAll(subjects);
  }

  /**
   * Displays the selected message in the Inbox-UI on the text area.
   */
  public void displayMessage() {
    int messageIndex = inbox.getSelectionModel().getSelectedIndex();
    if (messageIndex == -1) { 
      return;
    }
    Message message = currentAccount.getInbox().getMessage(messageIndex);

    textArea.setText(message.getMessage());
    textArea.setEditable(false);
    toComboBox.setValue(message.getTo().getMail_address());
    fromField.setText(message.getFrom().getMail_address());
    subjectField.setText(message.getSubject());
  }

  /**
   * Creates a new account object and checks if it is nonempty.
   * Returns to login page after completion.
   */
  public void handleCreateAccount() {
    String mail = txtCEmail.getText();
    String password = txtCpassword.getText();
    if (mail.length() < 1) {
      errorLabel2.setText("Error. The mail field cant be empty");
    } else if (password.length() < 1) {
      errorLabel2.setText("Error. The password field cant be empty");
    } else {
      Account newAccount = new Account(mail, password);
      try {
        dataAccess.createAccount(newAccount);
        System.out.println("Account Created");
        loginVisibility();
      } catch (IOException e) {
        errorLabel2.setText("Error. Couldn't post the account to the system");
      } catch (IllegalStateException e) {
        errorLabel2.setText("Error. The Account already exists");
      }
    }
  }
}
