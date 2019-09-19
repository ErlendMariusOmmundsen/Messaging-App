package project;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;


public class Inbox{
	
	private Account account;
	private List<Message> messages = new ArrayList<>();
	private InboxIO io = new InboxIO();
	
	private String inboxFilename;
	
	public Inbox(Account account) {
		this.account = account;
		this.inboxFilename = this.getAccount().getMail_address() + ".txt";
	}
	
	
	//Vi trenger ikke denne lengre. -Aleksander (PS! logisk feil: den looper uendelig.)
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
		this.messages = io.getMessages(this.inboxFilename);
	}
	
	public void uploadInbox() throws IOException {
		io.uploadInbox(this, this.inboxFilename);
	}
	
	public void uploadMessage(Message message) throws IOException, IllegalStateException {			
		io.uploadMessage(message, this.inboxFilename);
	}
	
	public void deleteMessage(int messageIndex) {
		this.messages.remove(messageIndex);
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public Message getMessage(int messageIndex) {
		return this.messages.get(messageIndex);
	}
	
	public List<Message> getMessages() {
		return this.messages;
	}

}
