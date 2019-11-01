
package project_fxui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


public abstract class TestFxBase {
  private FxRobot robot = new FxRobot();
  
  /**
   * Launches the app before the tests.
   * @throws Exception - something isnot working.
   */
  @Before
  public void setUpClass() throws Exception {
    ApplicationTest.launch(FxApp.class);
  }
  
  /**
   * Starts the test by showing a stage.
   * @param stage - the stage to show
   * @throws Exception - something is not working.
   */
  public void start(Stage stage) throws Exception {
    stage.show();
  }
  
  /**
   * Cleans up after each test.
   * @throws Exception - something is not working.
   */
  @After
  public void afterEachTest() throws Exception {
    FxToolkit.hideStage();
    robot.release(new KeyCode[] {});
    robot.release(new MouseButton[] {});
  }

}
