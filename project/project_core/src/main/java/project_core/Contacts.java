package project_core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Contacts implements InboxListener {
	
	private Collection<Account> accounts;
	
	public Contacts(Account account) {
		accounts = new HashSet<Account>();
		account.getInbox().addListener(this);
	}

	@Override
	public void inboxChanged(List<Message> messages) {
		accounts.clear();
		// Adds all the accounts you have gotten messages from to the contacts
		messages.stream()
			.map(message -> message.getFrom())
			.distinct()
			.forEach(acc -> accounts.add(acc));
	}

	@Override
	public void addedMessage(Message message) {
		Account from = message.getFrom();
		if (!accounts.contains(from))
			accounts.add(from);
	}

	public Collection<Account> getAccounts() {
		return accounts;
	}
	
}
