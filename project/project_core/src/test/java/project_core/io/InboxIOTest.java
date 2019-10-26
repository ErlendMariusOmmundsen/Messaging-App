
package project_core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project_core.Account;
import project_core.Inbox;
import project_core.Message;

public class InboxIOTest extends TestCase {

  private InboxIO io;
  private Inbox testInbox;
  private Message testMessage;

  private final String testFilename = "testInbox.txt";

  @Before
  public void setup() {
    makeEmptyFile();

    io = new InboxIO();
    testInbox = new Inbox(new Account("test"));
    testMessage = new Message("testMessage", "This is a test Message", new Account("testTo"),
        new Account("testFrom"));
    Message mes1 = new Message("test", "123", new Account("testTo"), new Account("testFrom"));
    Message mes2 =
        new Message("testHallo", "Hallo", new Account("testTo1"), new Account("testFrom1"));
    testInbox.getMessages().add(mes1);
    testInbox.getMessages().add(mes2);
    testInbox.getMessages().add(mes1);
    testInbox.getMessages().add(testMessage);
  }

  @Test
  public void testUploadMessage() {
    makeEmptyFile();

    io = new InboxIO();
    testMessage = new Message("testMessage", "This is a test Message", new Account("testTo"),
        new Account("testFrom"));
    try {
      io.uploadMessage(testMessage, testFilename);

      Scanner scanner = new Scanner(new File(InboxIO.resourceFilepath + testFilename));
      assertTrue(scanner.nextLine().equals("testTo"));
      assertTrue(scanner.nextLine().equals("testFrom"));
      assertTrue(scanner.nextLine().equals("testMessage"));
      assertTrue(scanner.nextLine().equals("This is a test Message"));
      assertTrue(scanner.nextLine().equals(""));
      scanner.close();

    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testUploadInbox() {
    makeEmptyFile();

    io = new InboxIO();
    testInbox = new Inbox(new Account("test"));
    testMessage = new Message("testMessage", "This is a test Message", new Account("testTo"),
        new Account("testFrom"));
    Message mes1 = new Message("test", "123", new Account("testTo"), new Account("testFrom"));
    Message mes2 =
        new Message("testHallo", "Hallo", new Account("testTo1"), new Account("testFrom1"));
    testInbox.getMessages().add(mes1);
    testInbox.getMessages().add(mes2);
    testInbox.getMessages().add(testMessage);

    try {
      io.uploadInbox(testInbox, testFilename);

      Scanner scanner = new Scanner(new File(InboxIO.resourceFilepath + testFilename));
      assertTrue(scanner.nextLine().equals("testTo"));
      assertTrue(scanner.nextLine().equals("testFrom"));
      assertTrue(scanner.nextLine().equals("test"));
      assertTrue(scanner.nextLine().equals("123"));
      assertTrue(scanner.nextLine().equals(""));

      assertTrue(scanner.nextLine().equals("testTo1"));
      assertTrue(scanner.nextLine().equals("testFrom1"));
      assertTrue(scanner.nextLine().equals("testHallo"));
      assertTrue(scanner.nextLine().equals("Hallo"));
      assertTrue(scanner.nextLine().equals(""));

      assertTrue(scanner.nextLine().equals("testTo"));
      assertTrue(scanner.nextLine().equals("testFrom"));
      assertTrue(scanner.nextLine().equals("testMessage"));
      assertTrue(scanner.nextLine().equals("This is a test Message"));
      assertTrue(scanner.nextLine().equals(""));
      scanner.close();

    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testgetMessages() {
    makeEmptyFile();

    io = new InboxIO();
    testInbox = new Inbox(new Account("test"));
    testMessage = new Message("testMessage", "This is a test Message", new Account("testTo"),
        new Account("testFrom"));
    Message mes1 = new Message("test", "123", new Account("testTo"), new Account("testFrom"));
    Message mes2 =
        new Message("testHallo", "Hallo", new Account("testTo1"), new Account("testFrom1"));
    testInbox.getMessages().add(mes1);
    testInbox.getMessages().add(mes2);
    testInbox.getMessages().add(testMessage);

    try {
      assertTrue(io.getMessages(testFilename).isEmpty());

      PrintWriter writer = new PrintWriter(new File(InboxIO.resourceFilepath + testFilename));
      writer.println("testTo");
      writer.println("testFrom");
      writer.println("test");
      writer.println("123");
      writer.println("");

      writer.println("testTo1");
      writer.println("testFrom1");
      writer.println("testHallo");
      writer.println("Hallo");
      writer.println("");
      writer.close();

      List<Message> messages = io.getMessages(testFilename);

      assertEquals(2, messages.size());

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() {
    makeEmptyFile();
  }

  private void makeEmptyFile() {
    try {
      PrintWriter writer = new PrintWriter(new File(InboxIO.resourceFilepath + testFilename));
      writer.print("");
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't setup/tear down properly");
    }
  }

}
