package project;

public class Message {
	
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
	
	
	
	
	
	
	
}
