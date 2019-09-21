package project;

import org.junit.Test;

import junit.framework.TestCase;

public class InboxTest extends TestCase {
	
	@Test
	public void testGetters() {
		Account acc = new Account("test");
		Inbox inbox = new Inbox(acc);
		assertEquals(acc, inbox.getAccount());
		assertTrue(inbox.getMessages().isEmpty());
		
	}
	
	@Test
	public void testModifyList() {
		Account acc = new Account("test");
		Inbox inbox = new Inbox(acc);
		
		Message mes1 = new Message("123", "This is a 123", acc, new Account("test2"));
		
		inbox.addMessage(mes1);
		inbox.addMessage(mes1);
		
		assertEquals(2, inbox.getMessages().size());
		assertEquals(mes1, inbox.getMessage(0));
		assertEquals(mes1, inbox.getMessage(1));
		
		inbox.deleteMessage(1);
		assertEquals(1, inbox.getMessages().size());
		assertEquals(mes1, inbox.getMessage(0));
	}
	
}
