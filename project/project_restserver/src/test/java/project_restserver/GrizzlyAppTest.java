
package project_restserver;

import junit.framework.TestCase;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GrizzlyAppTest extends TestCase {

  private HttpServer server;
    
  @Test
  public void testStartServer() {
    server = GrizzlyApp.startServer(5);
    if (server == null) {
      fail();
    }
    GrizzlyApp.stopServer(server);
  }
}