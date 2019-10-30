
package project_core;

import java.io.IOException;
import java.util.List;

import project_core.io.AccountIO;

public class Account {

  private String mailAddress;
  private String password;
  private Inbox inbox;
  private Contacts contacts;
  
  /**
   * Makes an account with account with only username. The password has to be set in setPassword 
   * eventually if needed.
   * @param mailAddress - username to give Account
   */
  public Account(String mailAddress) {
    this.mailAddress = mailAddress;
    this.password = "";
    this.inbox = new Inbox(this);
    this.contacts = new Contacts(this);
  }

  public Account(String mailAddress, String password) throws IllegalArgumentException {
    this(mailAddress);
    this.password = password;
  }

  public String getMail_address() {
    return mailAddress;
  }

  public Inbox getInbox() {
    return inbox;
  }

  public Contacts getContacts() {
    return contacts;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  /**
   * Creates the account in the system.
   * 
   * @throws IOException
   *         - If something goes wrong with communication with the system.
   * @throws IllegalStateException
   *         - If the account already exists in the system.
   */
  public void createAccount() throws IOException, IllegalStateException {
    if (!this.exists()) {
      AccountIO io = new AccountIO();
      io.newAccount(this);
    } else {
      throw new IllegalStateException("The account already exists");
    }
  }

  /**
   * checks if the account exist in the system.
   * 
   * @return true if the email adress of this account exists in the system, else return false.
   * @throws IOException
   *         if the io can't read from the system
   */
  public boolean exists() throws IOException {
    return new AccountIO().loadData(AccountIO.usersFilename).stream()
        .map(acc -> acc.getMail_address()).anyMatch(email -> this.mailAddress.equals(email));
  }

  /**
   * Checks if the account is valid in the system.
   * 
   * @return true if the email/password combination of this account exists in the system, else
   *         return false.
   * @throws IOException
   *         if the io can't read from the system.
   */
  public boolean isValid() throws IOException {
    List<Account> validAccounts;
    try {
      AccountIO io = new AccountIO();
      validAccounts = io.loadData(AccountIO.usersFilename);
    } catch (IOException e) {
      throw new IOException("Couldn't access user files");
    }

    return validAccounts.stream().anyMatch(acc -> this.password.equals(acc.getPassword())
        && this.mailAddress.equals(acc.getMail_address()));
  }

  /**
   * This account sends a message to another account. This is an transaction with the system.
   * 
   * @param message
   *        - The message to be sent.
   * @param to
   *        - The account to send the message to.
   * @throws IOException
   *         If something goes wrong reading or writing to the system.
   * @throws IllegalStateException
   *         If not both of the accounts exists in the system.
   */
  public void sendMessage(Message message, Account to) throws IOException, IllegalStateException {
    if (this.exists() && to.exists()) {
      to.getInbox().uploadMessage(message);
    } else {
      throw new IllegalStateException(
          "The accounts in the transaction needs to exist in the system");
    }
  }
}
