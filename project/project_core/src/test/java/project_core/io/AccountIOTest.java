
package project_core.io;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

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
      return;
    } catch (IOException e) {
      fail();
    }
  }

}
