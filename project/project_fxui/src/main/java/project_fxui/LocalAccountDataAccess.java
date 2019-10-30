
package project_fxui;

import java.io.IOException;
import java.util.List;

import project_core.Account;
import project_core.Inbox;
import project_core.Message;

public class LocalAccountDataAccess implements AccountDataAccess {

  @Override
  public List<Message> getInboxMessages(Account account) throws IOException {
    account.getInbox().loadMessages();
    return account.getInbox().getMessages();
  }

  @Override
  public void sendMessage(Message message, Account from) throws IOException {
    from.sendMessage(message, message.getTo());
  }

  @Override
  public void overwriteMessagesToInbox(List<Message> messages, Account account) throws IOException {
    Inbox inbox = account.getInbox();
    inbox.getMessages().clear();
    inbox.getMessages().addAll(messages);
    inbox.uploadInbox();
  }

  @Override
  public void createAccount(Account account) throws IOException, IllegalStateException {
    account.createAccount();
  }

  @Override
  public boolean accountValid(Account account) throws IOException {
    return account.isValid();
  }

}
