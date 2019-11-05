package project_fxui;

import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public abstract class TestFXBase extends ApplicationTest {
	private FxRobot robot = new FxRobot();
	@Before
	public void setUpClass() throws Exception{
		ApplicationTest.launch(FxApp.class);
	}
	
	public void start(Stage stage) throws Exception{
		stage.show();
	}
	
	@After
	public void afterEachTest() throws Exception{
		FxToolkit.hideStage();
		robot.release(new KeyCode[] {});
		robot.release(new MouseButton[] {});
	}
	
}
