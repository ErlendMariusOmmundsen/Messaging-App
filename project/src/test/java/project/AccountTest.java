package project;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

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
	public void testSetPassword() {
		testAcc = new Account("test@example.com", "abc123");
		
		testAcc.setPassword("newpassword123");
		assertTrue(testAcc.getPassword().equals("newpassword123"));
	}
	
	@Test
	public void testCreateAccount() {
		testAcc = new Account("test@example.com", "abc123");
		Account testAcc2 = new Account("example@ex.no", "123");
		
		try {
			testAcc.createAccount();
			fail();
		} catch (IllegalStateException e) {
			// The Account already exists so it should throw IllegalStateException
		} catch (IOException e) {
			fail();
		}
		
		
		try {
			assertFalse(testAcc2.exists());
			testAcc2.createAccount();
			assertTrue(testAcc2.exists());
		} catch (IOException e2) {
			fail();
		}
		
		// Prøver å lage accounten to ganger
		try {
			testAcc2.createAccount();
			fail();
		} catch (IllegalStateException e) {
			try {
				assertTrue(testAcc2.exists());
			} catch (IOException e1) {
				fail();
			}
		} catch (IOException e) {
			fail();
		}
		
		deleteLastLineOfFile();
	}
	
	@Test
	public void testExists() {
		testAcc = new Account("test@example.com", "abc123");
		
		try {
			assertTrue(testAcc.exists());
			Account noAcc = new Account("klor");
			assertFalse(noAcc.exists());
		} catch (IOException e) {
			fail();
		}
	}
	
	@Test
	public void testIsValid() {
		testAcc = new Account("test@example.com", "abc123");
		
		try {
			assertTrue(testAcc.isValid());
			testAcc.setPassword("123");
			assertFalse(testAcc.isValid());
		} catch (IOException e) {
			fail();
		}
	}
	
	@Test
	public void testSendMessage() {
		Account noAcc = new Account("ex", "abc");
		Account noAcc2 = new Account("ex2", "abc1");
		
		try {
			noAcc.sendMessage(new Message("123", "This should not work because the accounts doesn't exist", noAcc2, noAcc), noAcc2);
			fail();
		} catch (IllegalStateException e) {
			// Should be IllegalStateException
		} catch (IOException e) {
			fail();
		}
		
		testAccFrom = new Account("from@ex.no", "123");
		testAccTo = new Account("to@ex.no", "ex");
		
		try {
			testAccFrom.sendMessage(new Message("testMessage", "Message", testAccTo, testAccFrom), testAccTo);
		} catch (IllegalStateException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
	}
	
	private void deleteLastLineOfFile() {
		try {
			// Taken from https://stackoverflow.com/questions/17732417/delete-last-line-in-text-file
			RandomAccessFile f = new RandomAccessFile(new File(AccountIO.resourceFilepath + AccountIO.usersFilename), "rw");
			long length = f.length() - 1;
			byte b;
			do {                     
			  length -= 1;
			  f.seek(length);
			  b = f.readByte();
			} while(b != 10);
			f.setLength(length+1);
			f.close();
		} catch (IOException e) {
			System.out.println("Couldn't Update after the test");
		}
	}
	
	@After
	public void tearDown() {
		
	}
}
