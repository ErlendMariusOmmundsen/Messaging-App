package project_fxui;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;

import javafx.scene.control.Label;

public class FxAppTest extends TestFXBase {
	
	private final String ERRORLABEL = "#errorLabel";
	private final String LOGINBTN = "#loginButton";
	private FxRobot robot = new FxRobot();
	
	@Test
	public void ensureErrorMessage(){
		robot.clickOn(LOGINBTN);
		robot.sleep(1000);
		FxAssert.verifyThat(ERRORLABEL, (Label label) -> {
			String text = label.getText();
			return text == "Error: No username/password combination like that.";
		});
		
	}
}
