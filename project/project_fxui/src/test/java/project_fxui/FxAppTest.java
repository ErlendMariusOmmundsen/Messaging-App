
package project_fxui;

import javafx.scene.control.Label;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;


public class FxAppTest extends TestFxBase {

  private final String errorLabel = "#errorLabel";
  private final String loginBtn = "#loginButton";
  private FxRobot robot = new FxRobot();

  @Test
  public void ensureErrorMessage() {
    robot.clickOn(loginBtn);
    robot.sleep(1000);
    FxAssert.verifyThat(errorLabel, (Label label) -> {
      String text = label.getText();
      return text == "Error: No username/password combination like that.";
    });

  }
}
