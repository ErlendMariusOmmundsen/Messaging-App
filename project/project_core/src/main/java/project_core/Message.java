
package project_core;

public class Message {

  public static final Message emptyMessage = new Message("", "", new Account(""), new Account(""));

  private String subject;
  private String message;
  private Account to;
  private Account from;

  /**
   * Creates an Message object.
   * @param subject - The subject of the message.
   * @param message - The actual message text.
   * @param to - The account the message is to.
   * @param from - The account the message is from.
   */
  public Message(String subject, String message, Account to, Account from) {
    this.subject = subject;
    this.message = message;
    this.to = to;
    this.from = from;
  }

  public String getSubject() {
    return subject;
  }

  public String getMessage() {
    return message;
  }

  public Account getTo() {
    return to;
  }

  public Account getFrom() {
    return from;
  }

  public String toString() {
    return this.getMessage();
  }

  /**
   * This message is equal another message if the to/from accounts have the equal usernames and the
   * message and the subject is equal.
   * 
   * @param message - The message to check equality with.
   * @return true if equal, otherwise false.
   */
  public boolean equals(Message message) {
    return this.from.getMail_address().equals(message.from.getMail_address())
        && this.to.getMail_address().equals(message.to.getMail_address())
        && this.message.equals(message.message) && this.subject.equals(message.subject);
  }

}
