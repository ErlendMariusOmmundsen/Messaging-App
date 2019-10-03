package project_fxui;


import org.glassfish.grizzly.http.server.HttpServer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project_restserver.GrizzlyApp;

public class FxApp extends Application {
	
	private HttpServer restServer = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		restServer = GrizzlyApp.startServer(5);
		
	    primaryStage.setTitle("My Application");
		primaryStage.setScene(new Scene(FXMLLoader.load(FxApp.class.getResource("app.fxml"))));
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		if (restServer != null) {
			GrizzlyApp.stopServer(restServer);
			System.out.println("Stopped the server");
		} else 
			System.out.println("Didn't stop the server");
		super.stop();
	}
	
	
	public static void main(String [] args) {
		launch(args);
	}
}
