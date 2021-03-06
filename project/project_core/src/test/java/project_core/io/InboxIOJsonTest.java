
package project_core.io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project_core.Account;
import project_core.Inbox;
import project_core.Message;
import project_core.io.InboxIOjson;
import project_core.json.CompleteObjectMapper;

public class InboxIOJsonTest extends TestCase {

  private InboxIOjson io;
  private Inbox testInbox;
  private Message testMessage;

  private static final String testFilename = "testInbox.json";

  @Before
  public void setup() {
    makeEmptyFile();
  }

  @Test
  public void testUploadInbox() {
    makeEmptyFile();

    io = new InboxIOjson();
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

      ObjectMapper mapper = new CompleteObjectMapper();
      String filepath = InboxIO.resourceFilepath + testFilename;
      InputStream in = new FileInputStream(filepath);
      Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
      Scanner scanner = new Scanner(reader);

      Message message = mapper.readValue(scanner.nextLine(), Message.class);
      assertTrue(message.getTo().getMail_address().equals(mes1.getTo().getMail_address()));
      assertTrue(message.getFrom().getMail_address().equals(mes1.getFrom().getMail_address()));
      assertTrue(message.getSubject().equals(mes1.getSubject()));
      assertTrue(message.getMessage().equals(mes1.getMessage()));

      message = mapper.readValue(scanner.nextLine(), Message.class);
      assertTrue(message.getTo().getMail_address().equals(mes2.getTo().getMail_address()));
      assertTrue(message.getFrom().getMail_address().equals(mes2.getFrom().getMail_address()));
      assertTrue(message.getSubject().equals(mes2.getSubject()));
      assertTrue(message.getMessage().equals(mes2.getMessage()));

      message = mapper.readValue(scanner.nextLine(), Message.class);
      assertTrue(message.getTo().getMail_address().equals(testMessage.getTo().getMail_address()));
      assertTrue(
          message.getFrom().getMail_address().equals(testMessage.getFrom().getMail_address()));
      assertTrue(message.getSubject().equals(testMessage.getSubject()));
      assertTrue(message.getMessage().equals(testMessage.getMessage()));

      scanner.close();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testGetMessages() {
    makeEmptyFile();

    io = new InboxIOjson();
    testInbox = new Inbox(new Account("test"));
    testMessage = new Message("testMessage", "This is a test Message", new Account("testTo"),
        new Account("testFrom"));
    Message mes1 = new Message("test", "123", new Account("testTo"), new Account("testFrom"));
    Message mes2 =
        new Message("testHallo", "Hallo", new Account("testTo1"), new Account("testFrom1"));

    try {
      FileWriter fr = new FileWriter(new File(InboxIO.resourceFilepath + testFilename),
          StandardCharsets.UTF_8, true);
      PrintWriter writer = new PrintWriter(fr);

      ObjectMapper mapper = new CompleteObjectMapper();

      String text = mapper.writeValueAsString(testMessage);
      writer.println(text);
      text = mapper.writeValueAsString(mes1);
      writer.println(text);
      text = mapper.writeValueAsString(mes2);
      writer.println(text);

      writer.close();

      List<Message> messages = io.getMessages(testFilename);
      assertEquals(3, messages.size());

      assertTrue(messages.get(0).getMessage().equals(testMessage.getMessage()));
      assertTrue(messages.get(1).getMessage().equals(mes1.getMessage()));
      assertTrue(messages.get(2).getMessage().equals(mes2.getMessage()));

    } catch (IOException e) {
      fail();
    }

  }

  @After
  public void tearDown() {
    makeEmptyFile();
  }

  private void makeEmptyFile() {
    try {
      PrintWriter writer = new PrintWriter(new File(InboxIO.resourceFilepath + testFilename),
          StandardCharsets.UTF_8);
      writer.print("");
      writer.close();
    } catch (IOException e) {
      System.out.println("Couldn't setup/tear down properly");
    }
  }

}
