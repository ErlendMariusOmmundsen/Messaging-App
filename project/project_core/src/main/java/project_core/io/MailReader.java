package project_core.io;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import project_core.Inbox;
import project_core.Message;

public interface MailReader {
	
	/**
	 * Appends the message to the file with the filename sepcified.
	 * @param message - The message to be appended.
	 * @param filename - The filename of the file where the message should be appended.
	 * @throws IOException - If something goes wrong with communication with the system.
	 */
	void uploadMessage(Message message, String filename) throws IOException;
	
	/**
	 * Overwrites the file with the messages in the inbox
	 * @param inbox - The inbox with all the messages to be uploaded.
	 * @param filename - The which should be overwritten with the new messages.
	 * @throws IOException - If something goes wrong with communication with the system.
	 */
	void uploadInbox(Inbox inbox, String filename) throws IOException;
	
	/**
	 * Gets all the messages in the file specified by the filename.
	 * @param filename - The filename of the file to get the messages from
	 * @return All messages in the file
	 * @throws IOException - If something goes wrong with communication with the system.
	 */
	List<Message> getMessages(String filename) throws IOException;
	
}
