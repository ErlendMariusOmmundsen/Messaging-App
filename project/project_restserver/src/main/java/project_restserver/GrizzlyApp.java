package project_restserver;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

// Copied from simpleexample2
public class GrizzlyApp {

	private static final URI BASE_URI = URI.create("http://localhost:8080/");

	public static void main(final String[] args) {
		try {
			final ResourceConfig resourceConfig = null;
			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
			Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
			Thread.currentThread().join();
		} catch (final InterruptedException ex) {
			Logger.getLogger(GrizzlyApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
