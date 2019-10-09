package project_core;


public class Message {
	
	public static final Message emptyMessage = new Message("", "", new Account(""), new Account(""));
	
	private String subject, message;
	private Account to, from;
	
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
	
	public boolean equals(Message message) {
		return this.from.getMail_address().equals(message.from.getMail_address())
			&& this.to.getMail_address().equals(message.to.getMail_address())
			&& this.message.equals(message.message)
			&& this.subject.equals(message.subject);
	}
	
}
