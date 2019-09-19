package project;


import org.junit.Assert;
import org.junit.Test;


public class AccountTest {

	@Test
	public void testAccountGetters() {
		final Account testAcc = new Account("example@ex.no", "123");
		Assert.assertEquals("example@ex.no", testAcc.getMail_address());
		Assert.assertEquals("123", testAcc.getPassword());
		Assert.assertEquals(testAcc, testAcc.getInbox().getAccount());
	}
	
	
	
	
	
	

}
