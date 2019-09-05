package project;

import java.io.FileNotFoundException;
import java.util.List;

public interface MailReader {

	void uploadMessage(Message message, String filename) throws FileNotFoundException;
	
	List<Message> getMessages(Inbox inbox, String filename) throws FileNotFoundException;
	
}
