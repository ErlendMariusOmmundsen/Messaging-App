package project;



import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class AccountTest extends TestCase{
	
	private Account testAcc;
	private Account testAccFrom;
	private Account testAccTo;
	
	@Before
	public void setup() {
		
		testAcc = new Account("test@example.com", "abc123");
	}
	
	@Test
	public void testAccountGetters() {
		final Account testAcc = new Account("example@ex.no", "123");
		Assert.assertEquals("example@ex.no", testAcc.getMail_address());
		Assert.assertEquals("123", testAcc.getPassword());
		Assert.assertEquals(testAcc, testAcc.getInbox().getAccount());
	}
	
	@Test
	public void testCreateAccount() {
		/*
		try {
			assertFalse(testAcc.exists());
			testAcc.createAccount();
			assertTrue(testAcc.exists());
		} catch (IOException e2) {
			fail();
		}
		
		// Prøver å lage accounten to ganger
		try {
			testAcc.createAccount();
			fail();
		} catch (IllegalStateException e) {
			try {
				assertTrue(testAcc.exists());
			} catch (IOException e1) {
				fail();
			}
		} catch (IOException e) {
			fail();
		}
		*/
	}

	@Test
	public void testIsValid() {
		
	}
	
	@Test
	public void testSendMessage() {
		
	}
	
	@After
	public void tearDown() {
		
	}
}
