package project_restserver;

import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class GrizzlyApp {

	public static final URI BASE_URI = URI.create("http://localhost:8080/");
	
	public static HttpServer startServer(int waitSecondsForServer) {
	    final ResourceConfig resourceConfig = new Config();
	    final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
	    return httpServer;
	}
	
	public static void stopServer(HttpServer server) {
		server.shutdown();
	}
}
