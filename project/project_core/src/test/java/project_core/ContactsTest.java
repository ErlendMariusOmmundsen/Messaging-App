
package project_core;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;


public class ContactsTest extends TestCase {

  @Test
  public void testConstruction() {
    Account testAcc = new Account("test", "123test");

    assertEquals(0, testAcc.getContacts().getAccounts().size());
    assertTrue(testAcc.getContacts().getAccounts() != null);
  }

  @Test
  public void testInboxAddMessage() {
    Account testAcc = new Account("test", "123test");
    testAcc.getInbox()
        .addMessage(new Message("testSubject", "testMessage", testAcc, new Account("fromAcc")));
    Contacts accContacts = testAcc.getContacts();

    // Skal ha fått en account i contacts som har navn fromAcc
    assertEquals(1, accContacts.getAccounts().size());
    assertTrue(accContacts.getAccounts().iterator().next().getMail_address().equals("fromAcc"));
  }

  @Test
  public void testInboxRemoveMessage() {
    Account testAcc = new Account("test", "123test");
    testAcc.getInbox()
        .addMessage(new Message("testSubject", "testMessage", testAcc, new Account("fromAcc")));
    testAcc.getInbox()
        .addMessage(new Message("testSubject", "testMessage", testAcc, new Account("fromAcc2")));
    testAcc.getInbox().deleteMessage(0);
    Contacts accContacts = testAcc.getContacts();

    // Skal kun ha account som heter fromAcc2
    assertEquals(1, accContacts.getAccounts().size());
    assertTrue(accContacts.getAccounts().iterator().next().getMail_address().equals("fromAcc2"));
  }

  private void uploadSetup() {
    Account testAcc = new Account("testContactsAccount", "123test");
    testAcc.getInbox()
        .addMessage(new Message("testSubject", "testMessage", testAcc, new Account("fromAcc")));
    testAcc.getInbox()
        .addMessage(new Message("testSubject", "testMessage", testAcc, new Account("fromAcc2")));
    try {
      testAcc.createAccount();
      testAcc.getInbox().uploadInbox();
    } catch (IllegalStateException e) {
      return;
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testInboxloadMessages() {
    uploadSetup();
    Account testAcc = new Account("testContactsAccount", "123test");
    Contacts contacts = testAcc.getContacts();

    assertTrue(contacts.getAccounts().isEmpty());

    // Loads in accounts from the system, and the Contacts should be upated
    try {
      testAcc.getInbox().loadMessages();

      assertEquals(2, contacts.getAccounts().size());
      // Just check if there are any accounts with the two names I expect
      assertTrue(contacts.getAccounts().stream().map(acc -> acc.getMail_address())
          .anyMatch(s -> s.equals("fromAcc")));
      assertTrue(contacts.getAccounts().stream().map(acc -> acc.getMail_address())
          .anyMatch(s -> s.equals("fromAcc2")));
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testDuplicate() {
    Account from = new Account("fromAcc");
    Account testAcc = new Account("testAcc", "123");
    Message testMessage = new Message("testSubject", "testMessage", testAcc, from);

    // Tester med flere meldinger i inboxen fra samme account
    testAcc.getInbox().addMessage(testMessage);
    testAcc.getInbox().addMessage(testMessage);
    testAcc.getInbox().addMessage(new Message("testSubject2", "testMessage2", testAcc, from));

    // Da skal det fortsatt bare være 1 account med navn fromAcc
    assertEquals(1, testAcc.getContacts().getAccounts().size());
    assertTrue(
        testAcc.getContacts().getAccounts().iterator().next().getMail_address().equals("fromAcc"));
  }
}
