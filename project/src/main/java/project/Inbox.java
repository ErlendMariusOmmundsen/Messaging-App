package project;

import java.util.List;
import java.util.ArrayList;


public class Inbox{
	
	private Account account;
	private List<Message> messages = new ArrayList<>();
	
	public Inbox(Account account) {
		setAccount(account);
	}
	
	private void setAccount(final Account account) {
		if(account.getInbox() != null) {
			throw new IllegalArgumentException("This account is already assosiated with another inbox.");
		}
		this.account = account;
		account.setInbox(this);
		
	}
	
	public Account getAccount() {
		return this.account;
	}
	
	
	
	

}
