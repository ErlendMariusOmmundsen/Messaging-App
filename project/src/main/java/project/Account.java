package project;

public class Account {
	
	private final String mail_address;
	private final String password;
	private Inbox inbox;

	public Account(String mail_address, String password) {
		this.mail_address = mail_address;
		this.password = password;
	}
	
	public void setInbox(final Inbox inbox) {
		if(inbox.getAccount() != null) {
			throw new IllegalArgumentException("This Inbox is already associated with an account.");
		}
		this.inbox = inbox;
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
	
	
	
}
