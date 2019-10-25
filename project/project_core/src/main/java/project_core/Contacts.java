
package project_core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This class will be automatically added in the accounts class and can be used there by
 * account.getContacts(). <br>
 * <br>
 * A simple use case: <br>
 * account.getInbox().loadMessages() // need to catch exceptions too <br>
 * account.getContacts().getAccounts() // should now have a Collection of all the account contacts
 * with this line <br>
 * 
 * @author Lukas Tveiten
 *
 */
public class Contacts implements InboxListener {

  private Collection<Account> accounts;

  public Contacts(Account account) {
    accounts = new HashSet<Account>();
    account.getInbox().addListener(this);
  }

  /**
   * The contatcs will be updated so that every account that has sent to Message to Inbox will be
   * added
   */
  @Override
  public void inboxChanged(List<Message> messages) {
    accounts.clear();
    // Adds all the accounts you have gotten messages from to the contacts
    // Make also sure to not get any duplicates by first mapping to the names
    messages.stream().map(message -> message.getFrom().getMail_address()).distinct()
        .map(name -> new Account(name)).forEach(acc -> accounts.add(acc));
  }

  /**
   * The contacts will be updated if the added message have a contact that isn't yet in the
   * contacts.
   */
  @Override
  public void addedMessage(Message message) {
    Account from = message.getFrom();
    if (!accounts.stream().map(acc -> acc.getMail_address())
        .anyMatch(name -> name.equals(from.getMail_address()))) {
      accounts.add(from);
    }
  }

  public Collection<Account> getAccounts() {
    return accounts;
  }

}
