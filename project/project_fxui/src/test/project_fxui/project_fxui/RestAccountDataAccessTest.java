package project_fxui;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;

import project_restserver.GrizzlyApp;

public class RestAccountDataAccessTest extends AccountDataAccessTest {
	
	private HttpServer server;

	@Before
	public void setUp() {
		server = GrizzlyApp.startServer(5);
	}
	
	@Override
	public AccountDataAccess getDataAccess() {
		return new RestAccountDataAccess();
	}
	
	@After
	public void shutDown() {
		GrizzlyApp.stopServer(server);
	}
}
