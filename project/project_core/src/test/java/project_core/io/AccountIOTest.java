package project_core.io;

import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;
import project_core.Account;

public class AccountIOTest extends TestCase {
	
	@Test
	public void testEmptyAccount() {
		// Should fail to write account to system that is empty.
		
		Account empty = new Account("", "");
		try {
			empty.createAccount();
			fail();
		} catch (IllegalStateException e) {
			
		} catch (IOException e) {
			fail();
		}
	}
	
}
