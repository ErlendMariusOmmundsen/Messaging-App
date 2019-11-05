
package project_restserver;

import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class GrizzlyApp {

  public static final URI BASE_URI = URI.create("http://localhost:8080/");

  /**
   * Static method that starts and return the started server.
   * @param waitSecondsForServer - this is not used (forgot to remove)
   * @return a new server that is started.
   */
  public static HttpServer startServer(int waitSecondsForServer) {
    final ResourceConfig resourceConfig = new Config();
    final HttpServer httpServer =
        GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
    return httpServer;
  }

  /**
   * Static method thats stops the given server.
   * 
   * @param server
   *        - The server to be stopped.
   */
  public static void stopServer(HttpServer server) {
    server.shutdown();
  }
}
