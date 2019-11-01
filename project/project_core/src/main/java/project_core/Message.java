
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
   * @param obj - The message to check equality with.
   * @return true if equal, otherwise false.
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Message)) {
      return false;
    }
    Message message = (Message) obj;
    return this.from.getMail_address().equals(message.from.getMail_address())
        && this.to.getMail_address().equals(message.to.getMail_address())
        && this.message.equals(message.message) && this.subject.equals(message.subject);
  }

  @Override
  // Eclipse generated hasCode
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((from == null) ? 0 : from.hashCode());
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    result = prime * result + ((subject == null) ? 0 : subject.hashCode());
    result = prime * result + ((to == null) ? 0 : to.hashCode());
    return result;
  }
}
