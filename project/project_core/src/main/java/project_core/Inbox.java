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
	
	/**
	 * Loads in all the messages of the inbox in the system to this inbox-object.
	 * @throws IOException - If something goes wrong with communication with the system.
	 */
	public void loadMessages() throws IOException {
		this.messages = io.getMessages(this.inboxFilename);
	}
	
	/**
	 * Overwrites the inbox in the system with this inbox-objects messages.
	 * @throws IOException - If something goes wrong with communication with the system.
	 */
	public void uploadInbox() throws IOException {
		io.uploadInbox(this, this.inboxFilename);
	}
	
	/**
	 * Adds a message to the inbox in the system
	 * @param message - Message to be added.
	 * @throws IOException - If something goes wrong with communication with the system.
	 */
	public void uploadMessage(Message message) throws IOException {			
		io.uploadMessage(message, this.inboxFilename);
	}
	
	/**
	 * Deletes the inbox-object's message at the specified index
	 * @param messageIndex - index of the message to be deleted
	 */
	public void deleteMessage(int messageIndex) {
		this.messages.remove(messageIndex);
	}
	
	/**
	 * Adds a message to the inbox-object's messages
	 * @param message - The message to be added
	 */
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	/**
	 * Gets the inbox-object's message specified at the index
	 * @param messageIndex - index of the message to get
	 * @return the message at the index.
	 */
	public Message getMessage(int messageIndex) {
		return this.messages.get(messageIndex);
	}
	
	/**
	 * @return A list with all messages in this inbox-object.
	 */
	public List<Message> getMessages() {
		return this.messages;
	}

}
