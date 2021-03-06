
package project_core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project_core.Account;
import project_core.Inbox;
import project_core.Message;
import project_core.io.MailReader;

/**
 * This class implements MailReader with text format of the messages. The Format of a single message
 * is the following: accountToEmail \n accountFromEmail \n messageSubject \n messageText \n
 */
public class InboxIO implements MailReader {

  public static final String resourceFilepath =
      new File("").getAbsolutePath() + "\\src\\main\\resources\\io\\inbox\\";

  @Override
  public void uploadMessage(Message message, String filename) throws IOException {
    String filepath = resourceFilepath + filename;
    FileWriter fr = new FileWriter(new File(filepath), StandardCharsets.UTF_8, true);
    PrintWriter writer = new PrintWriter(fr);

    writer.println(message.getTo().getMail_address());
    writer.println(message.getFrom().getMail_address());
    writer.println(message.getSubject());
    writer.println(message.getMessage());
    writer.println();

    writer.flush();
    writer.close();
  }

  @Override
  public void uploadInbox(Inbox inbox, String filename) throws IOException {
    this.clearFile(filename);
    System.out.println(inbox.getMessages().size());
    for (Message message : inbox.getMessages()) {
      this.uploadMessage(message, filename);
    }
  }

  @Override
  public List<Message> getMessages(String filename) throws IOException {
    String filepath = resourceFilepath + filename;
    InputStream in = new FileInputStream(filepath);
    Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
    Scanner scanner = new Scanner(reader);

    List<Message> messages = new ArrayList<Message>();

    while (scanner.hasNextLine()) {
      Account to = new Account(scanner.nextLine(), null);
      Account from = new Account(scanner.nextLine(), null);
      String subject = scanner.nextLine();
      String text = scanner.nextLine();

      if (scanner.hasNextLine()) {
        scanner.nextLine();        
      }

      messages.add(new Message(subject, text, to, from));
    }

    scanner.close();
    return messages;
  }

  private void clearFile(String filename) throws IOException {
    String filepath = resourceFilepath + filename;
    PrintWriter writer = new PrintWriter(new File(filepath), StandardCharsets.UTF_8);
    writer.print("");
    writer.close();
  }
}
