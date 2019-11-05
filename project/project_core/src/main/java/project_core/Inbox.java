
package project_core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import project_core.io.InboxIO;

public class Inbox {

  private Account account;
  private List<Message> messages = new ArrayList<>();
  private List<InboxListener> listeners = new ArrayList<>();

  private InboxIO io = new InboxIO();
  private String inboxFilename;

  public Inbox(Account account) {
    this.account = account;
    this.inboxFilename = this.getAccount().getMail_address() + ".txt";
  }

  /**
   * the account assosicated with this inbox.
   * 
   * @return The account associated with this Inbox.
   */
  public Account getAccount() {
    return this.account;
  }

  /**
   * Gets the inbox-object's message specified at the index.
   * 
   * @param messageIndex
   *        - index of the message to get
   * @return the message at the index.
   */
  public Message getMessage(int messageIndex) {
    return this.messages.get(messageIndex);
  }

  /**
   * a list of all messages.
   * 
   * @return A list with all messages in this inbox-object.
   */
  public List<Message> getMessages() {
    return this.messages;
  }

  /**
   * Add new messages to the inbox.
   * 
   * @param message
   *        - The message to be added
   */
  public void addMessage(Message message) {
    this.messages.add(message);
    listeners.forEach(listener -> listener.addedMessage(message));
  }

  /**
   * Adds new messages to the inbox-object.
   * 
   * @param messages
   *        - The messages to be added
   */
  public void addMessages(Collection<Message> messages) {
    this.messages.addAll(messages);
    listeners.forEach(listener -> listener.inboxChanged(this.messages));
  }

  /**
   * Deletes the inbox-object's message at the specified index.
   * 
   * @param messageIndex
   *        - index of the message to be deleted
   */
  public void deleteMessage(int messageIndex) {
    this.messages.remove(messageIndex);
    listeners.forEach(listener -> listener.inboxChanged(this.messages));
  }

  /**
   * Clears the messages in the inbox.
   */
  public void clear() {
    this.messages.clear();
    listeners.forEach(listener -> listener.inboxChanged(this.messages));
  }

  /**
   * Loads in all the messages of the inbox in the system to this inbox-object.
   * 
   * @throws IOException
   *         - If something goes wrong with communication with the system.
   */
  public void loadMessages() throws IOException {
    this.messages = io.getMessages(this.inboxFilename);
    listeners.forEach(listener -> listener.inboxChanged(this.messages));
  }

  /**
   * Adds a message to the inbox in the system.
   * 
   * @param message
   *        - Message to be added.
   * @throws IOException
   *         - If something goes wrong with communication with the system.
   */
  public void uploadMessage(Message message) throws IOException {
    io.uploadMessage(message, this.inboxFilename);
  }

  /**
   * Overwrites the inbox in the system with this inbox-objects messages.
   * 
   * @throws IOException
   *         - If something goes wrong with communication with the system.
   */
  public void uploadInbox() throws IOException {
    io.uploadInbox(this, this.inboxFilename);
  }

  public void addListener(InboxListener listener) {
    listeners.add(listener);
  }

  public void removeListener(InboxListener listener) {
    listeners.remove(listener);
  }
}
