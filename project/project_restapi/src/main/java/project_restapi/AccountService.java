
package project_restapi;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
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
public class AccountService {

  public static final String ACCOUNT_SERVICE_PATH = "accounts";

  @POST
  @Path("/createAccount")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean createAccount(Account account) {
    boolean creationSuccess = false;
    try {
      account.createAccount();
      creationSuccess = true;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      creationSuccess = false;
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
    }
    return currentAccount.getInbox().getMessages();
  }

  @POST
  @Path("/{accountName}/inbox")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean sendMessage(Message message, @PathParam("accountName") String toName) {
    Account toAccount = new Account(toName);
    boolean uploadSuccess = false;
    try {
      message.getFrom().sendMessage(message, toAccount);
      uploadSuccess = true;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      uploadSuccess = false;
    }
    return uploadSuccess;
  }

  @PUT
  @Path("/{accountName}/inbox")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean overwriteMessagesToInbox(List<Message> messages,
      @PathParam("accountName") String email) {
    boolean overwriteSuccess = false;
    Account account = new Account(email);
    Inbox inbox = account.getInbox();
    try {
      if (!(messages.size() == 1 && messages.get(0).equals(Message.emptyMessage))) {        
        inbox.getMessages().addAll(messages);
      }
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

  @GET
  @Path("/{accountName}/Contacts")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<Account> getContacts(@PathParam("accountName") String accountName) {
    Account account = new Account(accountName);
    try {
      account.getInbox().loadMessages();
      Collection<Account> accounts = account.getContacts().getAccounts();
      return accounts;
    } catch (IOException e) {
      return null;
    }
  }

}
