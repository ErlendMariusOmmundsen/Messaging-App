package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface MailReader {

	void uploadMessage(Message message, String filename) throws IOException;
	void uploadInbox(Inbox inbox, String filename) throws IOException;
	List<Message> getMessages(String filename) throws IOException;
	
}
