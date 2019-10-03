package project_restapi;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import project_core.Account;
import project_core.Inbox;
import project_core.Message;

@Path(AccountService.ACCOUNT_SERVICE_PATH)
public class AccountService{
	
	public static final String ACCOUNT_SERVICE_PATH = "account";
	
	@POST
	@Path({"/{CreateAccount}"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(Mediatype.APPLICATION_JSON)
	public boolean CreateAccount(Account account) {
		boolean creationSuccess = false;
		try {
			account.createAccount();
			creationSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		return creationSuccess;
	}

	@GET
	@Path("/{getInboxMessages}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(Mediatype.APPLICATION_JSON)
	public List<Message> getInboxMessages(Account currentAccount) {
		currentAccount.getInbox().loadMessages();
		return currentAccount.getInbox().getMessages();
	}
	
	@POST
	@Path("/{uploadMessage}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean uploadMessageToInbox(Message message, @PathParam("account") Account account) {
		boolean uploadSuccess = false;
		try {
			account.getInbox().uploadMessage(message);
			uploadSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return uploadSuccess;
	}
	
	@PUT
	@Path("/{overwriteMessages}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean overwriteMessagesToInbox(List<Message> messages, @PathParam("accountEmail") String Email) {
		boolean overwriteSuccess = false;
		Account account = new Account(Email);
		try {
			Inbox inbox = account.getInbox();
			inbox.loadMessages();
			List<Message> inboxMessages = inbox.getMessages();
			inboxMessages = messages;
			inbox.uploadInbox();
			overwriteSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return overwriteSuccess;
	}
	
	@POST
	@Path("/{accountValid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean accountValid(@PathParam("account") Account account) {
		return account.isValid();
	}
	
	
	

}
