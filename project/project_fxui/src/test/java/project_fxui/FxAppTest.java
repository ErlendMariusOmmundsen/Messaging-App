package project_fxui;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import com.sun.glass.events.KeyEvent;
import com.sun.scenario.effect.impl.prism.PrMergePeer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import net.bytebuddy.implementation.bind.annotation.Super;
import project_fxui.FxApp;
import project_restserver.GrizzlyApp;

public class FxAppTest extends TestFXBase {
	
	private final String ERRORLABEL = "#errorLabel";
	private final String LOGINBTN = "#loginButton";
	private final String NEWMESSAGEBTN = "#newMessageButton";
	private final String SENDBUTTON = "#sendButton";
	private final String DELETEBUTTON = "#deleteButton";
	private final String LOGOUTBUTTON = "#logoutButton";
	private final String EMAILFIELD = "#emailField";
	private final String PASSWORDFIELD = "#passwordField";
	private final String INBOX = "#inbox";	
	private final String INBOXLABEL = "#inboxLabel";
	private final String TEXTAREA = "#textArea";
	private final String FROMFIELD = "#fromField";
	private final String SUBJECTFIELD = "#subjectField";
	private final String TOEMAILCOMBOBOX = "#toComboBox";
	
	private FxRobot robot = new FxRobot();
	
	//This test logs in with an already made user and creates a message, sends it, deletes it and logs out.
	@Test
	public void test(){
		robot.clickOn(LOGINBTN);
		robot.sleep(500);
		FxAssert.verifyThat(ERRORLABEL, (Label label) -> {
			String text = label.getText();
			return text.equals("Error: No username/password combination like that.");
		});
		
		login();
		sendMessage();
		messageCheck();
		deleteMessage();
	}
	
	//Logs into the app with premade user
	public void login() {
		robot.clickOn(EMAILFIELD);
		robot.write('1');
		robot.clickOn(PASSWORDFIELD);
		robot.write('1');
		robot.sleep(500);
		FxAssert.verifyThat(EMAILFIELD, (TextField field) -> {
			String text = field.getText();
			return text.equals("1");
		});
		
		robot.clickOn(LOGINBTN);
		robot.sleep(500);
	}
	
	//Sends a test message
	public void sendMessage() {
		robot.clickOn(NEWMESSAGEBTN);
		robot.clickOn(TOEMAILCOMBOBOX);
		robot.write("1");
		robot.clickOn(SUBJECTFIELD);
		robot.write("test");
		robot.clickOn(TEXTAREA);
		robot.write("test");
		robot.clickOn(SENDBUTTON);
		robot.sleep(500);
		robot.press(KeyCode.ENTER);
		robot.release(KeyCode.ENTER);
		robot.sleep(500);
	}
	
	//Checks if the message is recieved
	public void messageCheck() {
		robot.clickOn(INBOXLABEL);
		robot.moveBy(0, 50);
		robot.clickOn(MouseButton.PRIMARY);
		robot.sleep(500);
		//Checks if the text area contains the correct text
		FxAssert.verifyThat(TEXTAREA, (TextArea area) -> {
			String text = area.getText();
			return text.equals("test");
		});
		
		//Checks if the To comboBox contains the correct text
		FxAssert.verifyThat(TOEMAILCOMBOBOX, (ComboBox<String> box) -> {
			String text = (String) box.getValue();
			return text.equals("1");
		});
		
		//Checks if the From field contains the correct text
		FxAssert.verifyThat(FROMFIELD, (TextField field) -> {
			String text = field.getText();
			return text.equals("1");
		});
		
		//Checks if the Subject field contains the correct text
		FxAssert.verifyThat(SUBJECTFIELD, (TextField field) -> {
			String text = field.getText();
			return text.equals("test");
		});
	}
	
	//Deletes the test message, checks if deleted. Logs out of user, checks if logged out
	public void deleteMessage() {
		robot.clickOn(DELETEBUTTON);
		robot.sleep(500);
		//Checks if the inbox is empty 
		FxAssert.verifyThat(INBOX, (ListView list) -> {
			Object element = list.getSelectionModel().getSelectedItem();
			return element == null;
		});
		
		robot.clickOn(LOGOUTBUTTON);
		robot.sleep(200);
		//Checks if the Login button is visible after logout
		FxAssert.verifyThat(LOGINBTN, (Button button) -> {
			Boolean boo = button.isVisible();
			return boo;
		});
	}
	

	
}
