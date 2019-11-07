
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
  
  /**
   * This methods is a POST method that takes an account object that you want to create and save in the Accounts file. 
   * @param account object to be saved in the Accounts file.
   * @return boolean that indicates if this was successful.
   */

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
  
  /**
   * This method is a GET method that returns all the messages in an accounts inbox. 
   * @param String containing the mail of the recipient.
   * @return List with message objects.
   */
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

  /**
   * This method is a POST method that takes a message object and sends it to an account.
   * @param message object to be sent.
   * @param String containing the mail of the recipient.
   * @return boolean that indicates if this was successful.
   */
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
  
  /**
   * This method overwrites all the messages in the inbox of the given account with a set of new, not necessarily distinct, messages. 
   * @param List of message objects.
   * @param String containing the mail of the account which inbox should be overwritten. 
   * @return boolean that indicates if this was successful.
   */
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
  
  /**
   * This method takes an account and checks if the account/password combination is valid.
   * @param account object to check.
   * @return boolean that indicates if the account is valid or not.
   */
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
  
  /**
   * This method takes an account and returns all accounts that have messaged said account. 
   * @param String containing the mail of the account.
   * @return Collection of account objects.
   */
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
