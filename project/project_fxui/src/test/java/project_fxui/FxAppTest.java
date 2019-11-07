
package project_fxui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;


public class FxAppTest extends TestFXBase {

  private final String errorLabel = "#errorLabel";
  private final String loginBtn = "#loginButton";
  private final String messageBtn = "#newMessageButton";
  private final String sendBtn = "#sendButton";
  private final String deleteBtn = "#deleteButton";
  private final String logoutBtn = "#logoutButton";
  private final String emailField = "#emailField";
  private final String passwordField = "#passwordField";
  private final String inbox = "#inbox";
  private final String inboxLabel = "#inboxLabel";
  private final String textArea = "#textArea";
  private final String fromField = "#fromField";
  private final String subjectField = "#subjectField";
  private final String toComboBox = "#toComboBox";

  private FxRobot robot = new FxRobot();

  /**
   *  This test logs in with an already made user and creates a message, sends it, deletes it and
   *  logs out.
   */
  @Test
  public void test() {
    robot.clickOn(loginBtn);
    robot.sleep(500);
    FxAssert.verifyThat(errorLabel, (Label label) -> {
      String text = label.getText();
      return text.equals("Error: No username/password combination like that.");
    });

    login();
    sendMessage();
    messageCheck();
    deleteMessage();
  }

  /**
   *  Logs into the app with premade user.
   */
  public void login() {
    robot.clickOn(emailField);
    robot.write('1');
    robot.clickOn(passwordField);
    robot.write('1');
    robot.sleep(500);
    FxAssert.verifyThat(emailField, (TextField field) -> {
      String text = field.getText();
      return text.equals("1");
    });

    robot.clickOn(loginBtn);
    robot.sleep(500);
  }

  /**
   *  Sends a test message.
   */
  public void sendMessage() {
    robot.clickOn(messageBtn);
    robot.clickOn(toComboBox);
    robot.write("1");
    robot.clickOn(subjectField);
    robot.write("test");
    robot.clickOn(textArea);
    robot.write("test");
    robot.clickOn(sendBtn);
    robot.sleep(500);
    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);
    robot.sleep(500);
  }

  /**
   *  Checks if the message is recieved.
   */
  public void messageCheck() {
    robot.clickOn(inboxLabel);
    robot.moveBy(0, 50);
    robot.clickOn(MouseButton.PRIMARY);
    robot.sleep(500);
    // Checks if the text area contains the correct text
    FxAssert.verifyThat(textArea, (TextArea area) -> {
      String text = area.getText();
      return text.equals("test");
    });

    // Checks if the To comboBox contains the correct text
    FxAssert.verifyThat(toComboBox, (ComboBox<String> box) -> {
      String text = (String) box.getValue();
      return text.equals("1");
    });

    // Checks if the From field contains the correct text
    FxAssert.verifyThat(fromField, (TextField field) -> {
      String text = field.getText();
      return text.equals("1");
    });

    // Checks if the Subject field contains the correct text
    FxAssert.verifyThat(subjectField, (TextField field) -> {
      String text = field.getText();
      return text.equals("test");
    });
  }

  /**
   *  Deletes the test message, checks if deleted. Logs out of user, checks if logged out
   */
  public void deleteMessage() {
    robot.clickOn(deleteBtn);
    robot.sleep(500);
    // Checks if the inbox is empty
    FxAssert.verifyThat(inbox, (ListView list) -> {
      Object element = list.getSelectionModel().getSelectedItem();
      return element == null;
    });

    robot.clickOn(logoutBtn);
    robot.sleep(200);
    // Checks if the Login button is visible after logout
    FxAssert.verifyThat(loginBtn, (Button button) -> {
      Boolean boo = button.isVisible();
      return boo;
    });
  }

}
