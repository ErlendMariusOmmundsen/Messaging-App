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
	
	public static final String ACCOUNT_SERVICE_PATH = "accounts";
	
	@POST
	@Path("/createAccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createAccount(Account account) {
		System.out.println(account.getMail_address());
		System.out.println(account.getPassword());
		
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
	@Path("/{accountName}/inbox")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getInboxMessages(@PathParam("accountName") String accountName) {
		Account currentAccount = new Account(accountName);
		try {
			currentAccount.getInbox().loadMessages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return currentAccount.getInbox().getMessages();
	}
	
	@POST
	@Path("/{accountName}/inbox")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean uploadMessageToInbox(Message message, @PathParam("accountName") String accountName) {
		Account account = new Account(accountName);
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
	@Path("/{accountName}/inbox")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean overwriteMessagesToInbox(List<Message> messages, @PathParam("accountName") String Email) {
		boolean overwriteSuccess = false;
		Account account = new Account(Email);
		try {
			Inbox inbox = account.getInbox();
			inbox.getMessages().addAll(messages);
			inbox.uploadInbox();
			overwriteSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return overwriteSuccess;
	}
	
	@POST
	@Path("/isValid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean accountValid(Account account) {
		try {
			return account.isValid();
		} catch (IOException e) {
			return false;
		}
	}

}
