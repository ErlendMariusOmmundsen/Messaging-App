package project_fxui;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;

import project_restserver.GrizzlyApp;

public class RestAccountDataAccessTest extends AccountDataAccessTest {
	
	private HttpServer server;	
	
	@Override
	public AccountDataAccess getDataAccess() {
		return new RestAccountDataAccess();
	}
	
	@Override
	public void testCreateEmptyAccount() {
		server = GrizzlyApp.startServer(5);
		super.testCreateEmptyAccount();
		GrizzlyApp.stopServer(server);
	}
	
	@Override
	public void testGetInboxMessages() {
		server = GrizzlyApp.startServer(5);
		super.testGetInboxMessages();
		GrizzlyApp.stopServer(server);
	}
	
}
