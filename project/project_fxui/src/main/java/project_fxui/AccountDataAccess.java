package project_fxui;

import java.io.IOException;
import java.util.List;

import project_core.Account;
import project_core.Message;

public interface AccountDataAccess {
	
	/**
	 * 
	 * @param account - The account that we want to get the inbox messages from.
	 * @return The messages of the accounts inbox in the system.
	 * @throws IOException - If something goes wrong with the communication with the system.
	 */
	// As a REST call could be: GET accounts/{AccountName}/inbox
	List<Message> getInboxMessages(Account account) throws IOException;
	
	
	/**
	 * 
	 * This will append a message to the accounts inbox in the system.
	 * @param message - The message to upload.
	 * @param account - The account to the inbox we want to upload to.
	 * @throws IOException - If something goes wrong with the communication with the system.
	 */
	// As a REST call could be: POST accounts/{AccountName}/inbox/ | {SerializedMessage} JSON
	void uploadMessageToInbox(Message message, Account account) throws IOException;
	
	
	/**
	 * This will overwrite all the existing messages to the accounts inbox in the system.
	 * @param messages - All the messages to upload.
	 * @param account - The account that we want to overwrite the inbox of.
	 * @throws IOException - If something goes wrong with the communication with the system.
	 */
	// As a REST call could be: PUT accounts/{AccountName}/inbox/ | {SerializedMessageList} JSON
	void overwriteMessagesToInbox(List<Message> messages, Account account) throws IOException;
	

	// As a REST call could be: POST accounts/ | {SerializedAccount} JSON
	/**
	 * Will upload the account 
	 * @param account - The account to upload
	 * @throws IOException - If something goes wrong with the communication with the system.
	 * @throws IllegalStateException - If the account already exists in the system.
	 */
	void createAccount(Account account) throws IOException, IllegalStateException;
	
	
	/**
	 * 
	 * A account is valid if the username/password combination exists in the system.
	 * @param account - The account to check if is valid.
	 * @return true if it is valid, false if not.
	 * @throws IOException - If something goes wrong with the communication with the system.
	 */
	// As a REST call could be: GET accounts/ | {SerializedAccount} JSON
	boolean accountValid(Account account) throws IOException;
	
}
