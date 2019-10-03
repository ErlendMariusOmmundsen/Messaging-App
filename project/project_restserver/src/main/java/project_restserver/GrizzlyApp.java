package project_restserver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import project_core.Account;
import project_core.Message;
import project_core.json.CompleteObjectMapper;
import project_restapi.AccountService;

// Copied from simpleexample2
public class GrizzlyApp {

	private static final URI BASE_URI = URI.create("http://localhost:8080/");
	
	public static HttpServer startServer(int waitSecondsForServer) {
	    final ResourceConfig resourceConfig = new Config();
	    final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
	    
	    /*
	    if (waitSecondsForServer < 0) {
	        return httpServer;
	      }
	      while (waitSecondsForServer > 0) {
	        try {
	          
	          final URI clientURI = new URI(BASE_URI + AccountService.ACCOUNT_SERVICE_PATH);
	          
	          final HttpURLConnection connection = (HttpURLConnection) clientUrl.openConnection();
	          final int responseCode = connection.getResponseCode();
	          
	          connection.disconnect();
	          if (responseCode == 200) {
	            return httpServer;
	          }
	        } catch (final Exception e) {
	        }
	        try {
	          Thread.sleep(1000);
	          waitSecondsForServer -= 1;
	        } catch (final InterruptedException e) {
	          return null;
	        }
	      }
			*/
	      return httpServer;
	}
	
	public static void stopServer(HttpServer server) {
		server.shutdown();
	}
	
	// Dette er et rest-call som man kan kjøre i eclipse for å teste
	// Det vil gi mye røde errors i starten, men kan bare ignorere det
	public static void main(final String[] args) {
		HttpServer server = GrizzlyApp.startServer(5);
		
		URI clientURI = null;
		String jsonMessage = null;
		try {
			Account account = new Account("123", "456");
			Message message = new Message("subjectTest", "messageTest", account, account);
			jsonMessage = new CompleteObjectMapper().writeValueAsString(message);
			clientURI = new URI(BASE_URI + AccountService.ACCOUNT_SERVICE_PATH + "/account");
		} catch (URISyntaxException | JsonProcessingException e1) {
			e1.printStackTrace();
		}
		final HttpRequest request = HttpRequest.newBuilder(clientURI)
		          .header("Content-Type", "application/json")
		          .header("Accept", "application/json")
		          .POST(BodyPublishers.ofString(jsonMessage))
		          .build();
	      try {
	    	  
	    	  final HttpResponse<String> response = HttpClient.newBuilder()
			        .build()
			        .send(request, HttpResponse.BodyHandlers.ofString());
		      final String responseString = response.body();
		      Message message = new CompleteObjectMapper().readValue(responseString, Message.class);
		      System.out.println(message.getMessage());
		      System.out.println(message.getSubject());
		      System.out.println(message.getFrom().getMail_address());
		      System.out.println(message.getTo().getMail_address());
	      } catch (JsonParseException | JsonMappingException e) {
	      } catch (IOException | InterruptedException e) {
	     }
	      
	     GrizzlyApp.stopServer(server);
	}
}
