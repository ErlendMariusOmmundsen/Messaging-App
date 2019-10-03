package project_core;


import java.util.List;

import project_core.io.InboxIO;

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
	
	/**
	 * @param messageIndex
	 */
	public void deleteMessage(int messageIndex) {
		this.messages.remove(messageIndex);
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public Message getMessage(int messageIndex) {
		return this.messages.get(messageIndex);
	}
	
	/**
	 * @return A list with all messages in this inbox.
	 */
	public List<Message> getMessages() {
		return this.messages;
	}

}
