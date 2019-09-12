package project;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;


public class Inbox{
	
	private Account account;
	private List<Message> messages = new ArrayList<>();
	private Inbox_IO io = new Inbox_IO();
	
	public Inbox(Account account) {
		this.account = account;
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
	
	public void loadMessages() throws IOException {
		// I starten bare 1 felles Inbox, men etter hvert får hver account en inbox.
		this.messages = io.getMessages("testInbox.txt");
	}
	
	public void uploadInbox(String filename) throws IOException {
		io.uploadInbox(this, "testInboxDelete.txt");
	}
	
	public void deleteMessage(int messageIndex) {
		this.messages.remove(messageIndex);
	}
	
	public Message getMessage(int messageIndex) {
		return this.messages.get(messageIndex);
	}
	
	public List<Message> getMessages() {
		return this.messages;
	}

}
