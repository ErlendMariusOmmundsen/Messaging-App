package project_core;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import project_core.io.AccountIO;

public class Account {
	
	private String mail_address;
	private String password;
	private Inbox inbox;
	
	public Account(String mail_address) {
		this.mail_address = mail_address;
		this.inbox = new Inbox(this);
	}
	
	public Account(String mail_address, String password) throws IllegalArgumentException{
		this(mail_address);
		this.password = password;
	}

	public String getMail_address() {
		return mail_address;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Inbox getInbox() {
		return inbox;
	}
	
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}
	
	public void createAccount() throws IOException, IllegalStateException {
		if (!this.exists()) {
			AccountIO io = new AccountIO();
			io.newAccount(this);
		} else {
			throw new IllegalStateException("The account already exists");
		}
	}
	
	/**
	 * 
	 * @return true if the email adress of this account exists in the system, else return false.
	 * @throws IOException if the io can't read from the system
	 */
	public boolean exists() throws IOException {
		return new AccountIO().loadData(AccountIO.usersFilename).stream()
				.map(acc -> acc.getMail_address())
				.anyMatch(email -> this.mail_address.equals(email));
	}
	
	
	/**
	 * 
	 * @return true if the email/password combination of this account exists in the system, else return false.
	 * @throws IOException if the io can't read from the system.
	 */
	public boolean isValid() throws IOException {
		List<Account> validAccounts;
		try {
			AccountIO io = new AccountIO();
			validAccounts = io.loadData(AccountIO.usersFilename);
		} catch (IOException e) {
			throw new IOException("Couldn't access user files");
		}
		
		return validAccounts.stream()
			.anyMatch(acc -> this.password.equals(acc.getPassword()) 
					      && this.mail_address.equals(acc.getMail_address()));
	}
	
	/**
	 * This account sends a message to another account. This is an transaction with the system.
	 * @param message - The message to be sent.
	 * @param to - The account to send the message to.
	 * @throws IOException If something goes wrong reading or writing to the system.
	 * @throws IllegalStateException If not both of the accounts exists in the system.
	 */
	public void sendMessage(Message message, Account to) throws IOException, IllegalStateException {
		if (this.exists() && to.exists()) {
			to.getInbox().uploadMessage(message);
		} else {
			throw new IllegalStateException("The accounts in the transaction needs to exist in the system");
		}
	}
}
