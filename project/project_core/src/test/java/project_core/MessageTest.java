package project_core;

import org.junit.Assert;
import org.junit.Test;


public class MessageTest {
	
	@Test
	public void testMessageEqual() {
		final Account exAcc0 = new Account("example@example.no", "123abc");
		final Account exAcc1 = new Account("examplee@example.no", "123abc");
		final Message message = new Message("Greetings", "Some message", exAcc0, exAcc1);
		
		Assert.assertEquals("Some message", message.toString());
		Assert.assertEquals("Greetings", message.getSubject());
		Assert.assertEquals(exAcc0, message.getTo());
		Assert.assertEquals(exAcc1, message.getFrom());
	}
	
}
