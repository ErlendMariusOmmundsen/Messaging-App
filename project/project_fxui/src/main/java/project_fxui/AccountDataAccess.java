package project_fxui;

import java.util.List;

import project_core.Account;
import project_core.Message;

public interface AccountDataAccess {
	
	/**
	 * 
	 * @param account - The account that we want to get the inbox messages from.
	 * @return The messages of the accounts inbox in the system.
	 */
	// As a REST call could be: GET accounts/{SerializedAccount}/inbox
	List<Message> getInboxMessages(Account account);
	
	
	/**
	 * This will append a message to the accounts inbox in the system.
	 * @param message - The message to upload.
	 * @param account - The account to the inbox we want to upload to.
	 */
	// As a REST call could be: POST accounts/{SerializedAccount}/inbox/{SerializedMessage}
	void uploadMessageToInbox(Message message, Account account);
	
	
	/**
	 * This will overwrite all the existing messages to the accounts inbox in the system.
	 * @param messages - All the messages to upload.
	 * @param account - The account that we want to overwrite the inbox of.
	 */
	// As a REST call could be: PUT accounts/{SerializedAccount}/inbox/{SerializedMessageList}
	void overwriteMessagesToInbox(List<Message> messages, Account account);
	
	
	/**
	 * Will upload the account 
	 * @param account - The account to upload
	 */
	// As a REST call could be: POST accounts/{SerializedAccount}
	void uploadAccount(Account account);
	
	
	/**
	 * A account is valid if the username/password combination exists in the system.
	 * @param account - The account to check if is valid.
	 * @return true if it is valid, false if not.
	 */
	// As a REST call could be: GET accounts/{SerializedAccount}
	boolean accountValid(Account account);
	
}
