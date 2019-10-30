
package project_fxui;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import project_core.Account;

public abstract class AccountDataAccessTest extends TestCase {

  protected abstract AccountDataAccess getDataAccess();

  @Test
  public void testGetInboxMessages() {
    Account acc = new Account("test@hotmail.no", "abc");
    try {
      assertTrue(getDataAccess().getInboxMessages(acc).size() >= 1);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testCreateEmptyAccount() {
    Account emptyAcc = new Account("", "");
    try {
      getDataAccess().createAccount(emptyAcc);
      fail();
    } catch (IllegalStateException e) {
      return;
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  // Could test all of them, but they are already tested in the server part.

}
