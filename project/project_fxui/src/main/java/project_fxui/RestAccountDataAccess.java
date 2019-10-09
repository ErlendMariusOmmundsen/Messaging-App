package project_fxui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import project_core.Account;
import project_core.Message;
import project_core.json.CompleteObjectMapper;
import project_restserver.GrizzlyApp;

public class RestAccountDataAccess implements AccountDataAccess {
	
	private String getBaseURI() {
		return GrizzlyApp.BASE_URI + "accounts";
	}
	
	// REST CALL: GET {GET BASE_URI}/accounts/{accountName}/inbox/
	@Override
	public List<Message> getInboxMessages(Account account) throws IOException {
		try {
			URI clientURI = new URI(getBaseURI() + "/" + account.getMail_address() + "/inbox/");
			System.out.println("GET: " + clientURI.toString());
			final HttpRequest request = HttpRequest.newBuilder(clientURI)
					.header("Accept", "application/json")
					.GET()
					.build();
			final HttpResponse<InputStream> response = HttpClient.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofInputStream());
			
			@SuppressWarnings("unchecked")
			List<Message> messages = (List<Message>) new CompleteObjectMapper()
				.readValue(response.body(), new TypeReference<List<Message>>() 
				{ /* Dette er en tom implementasjon av compareTo i TyepReference, som man bare må ha */ });
			return messages;
		} catch(IOException | InterruptedException | URISyntaxException e) {
			throw new IOException(e);
		}
	}

	// REST CALL: POST {GET BASE_URI}/accounts/{accountName}/inbox/
	// (JSON serialized Message in body)
	@Override
	public void sendMessage(Message message, Account from) throws IOException {
		boolean responseValue = false;
		try {
			URI clientURI = new URI(getBaseURI() + "/" + message.getTo().getMail_address() + "/inbox/");
			System.out.println("POST: " + clientURI.toString());
			final HttpRequest request = HttpRequest.newBuilder(clientURI)
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.POST(BodyPublishers.ofString(new CompleteObjectMapper().writeValueAsString(message)))
					.build();
			final HttpResponse<InputStream> response = HttpClient.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofInputStream());
			responseValue = new CompleteObjectMapper().readValue(response.body(), Boolean.class);
		} catch(IOException | InterruptedException | URISyntaxException e) {
			throw new IOException(e);
		}
		
		if (!responseValue) throw new IllegalStateException("Both accounts in the transaction needs to exist in the system");
	}
	
	// REST CALL: PUT {GET BASE_URI}/accounts/{accountName}/inbox/ 
	// (JSON serialized List<Message> in body)
	@Override
	public void overwriteMessagesToInbox(List<Message> messages, Account account) throws IOException {
		// Add a empty message so the api will know it is a List<Message> object, so the right call will be made.
		if (messages.isEmpty()) messages.add(Message.emptyMessage);
		try {
			URI clientURI = new URI(getBaseURI() + "/" + account.getMail_address() + "/inbox/");
			System.out.println("PUT " + clientURI.toString());
			final HttpRequest request = HttpRequest.newBuilder(clientURI)
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.PUT(BodyPublishers.ofString(new CompleteObjectMapper().writeValueAsString(messages)))
					.build();
			final HttpResponse<InputStream> response = HttpClient.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofInputStream());
		} catch(IOException | InterruptedException | URISyntaxException e) {
			throw new IOException(e);
		}
	}
	
	// REST CALL: POST {GET BASE_URI}/accounts/createAccount 
	// (JSON serialized Account in body)
	@Override
	public void createAccount(Account account) throws IOException, IllegalStateException {
		try {			
			URI clientURI = new URI(getBaseURI() + "/createAccount");
			System.out.println("POST: " + clientURI.toString());
			final HttpRequest request = HttpRequest.newBuilder(clientURI)
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.POST(BodyPublishers.ofString(new CompleteObjectMapper().writeValueAsString(account)))
					.build();
			final HttpResponse<InputStream> response = HttpClient.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofInputStream());
			boolean responseValue = new CompleteObjectMapper().readValue(response.body(), Boolean.class);
			if (!responseValue) throw new IllegalStateException("Account already exists");
		} catch (IOException | URISyntaxException | InterruptedException e) {
			throw new IOException(e);
		}
	}
	
	
	// REST CALL: POST {GET BASE_URI}/accounts/isValid 
	// (JSON serialized Account in body)
	@Override
	public boolean accountValid(Account account) throws IOException {
		try {
			URI clientURI = new URI(getBaseURI() + "/isValid");
			System.out.println("POST: " + clientURI.toString());
			final HttpRequest request = HttpRequest.newBuilder(clientURI)
					.header("Content-Type", "application/json")
					.header("Accept", "application/json")
					.POST(BodyPublishers.ofString(new CompleteObjectMapper().writeValueAsString(account)))
					.build();
			final HttpResponse<InputStream> response = HttpClient.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofInputStream());
			boolean responseValue = new CompleteObjectMapper().readValue(response.body(), Boolean.class);
			return responseValue;
		} catch(IOException | InterruptedException | URISyntaxException e) {
			throw new IOException(e);
		}
	}

}
