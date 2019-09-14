package project;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class InboxIO implements MailReader{
	
	public static final String resourceFilepath = new File("").getAbsolutePath() + "\\src\\main\\resources\\project\\";
	
	@Override
	public void uploadMessage(Message message, String filename) throws IOException{
		String filepath = resourceFilepath + filename;
		FileWriter fr = new FileWriter(new File(filepath), true);
		PrintWriter writer = new PrintWriter(fr);
		
		writer.println(message.getTo().getMail_address());
		writer.println(message.getFrom().getMail_address());
		writer.println(message.getSubject());
		writer.println(message.getMessage());
		writer.println("");
		
		writer.flush();
		writer.close();
	}
	
	@Override
	public void uploadInbox(Inbox inbox, String filename) throws IOException {
		this.clearFile(filename);
		for (Message message : inbox.getMessages()) {
			this.uploadMessage(message, filename);
		}
	}

	@Override
	public List<Message> getMessages(String filename) throws IOException{
		String filepath = resourceFilepath + filename;
		Scanner scanner = new Scanner(new File(filepath));
		
		List<Message> messages = new ArrayList<Message>();
		
		while (scanner.hasNextLine()) {
			Account to = new Account(scanner.nextLine(), null);
			Account from = new Account(scanner.nextLine(), null);
			String subject = scanner.nextLine();
			String text = scanner.nextLine();
			
			if (scanner.hasNextLine()) scanner.nextLine();
			
			messages.add(new Message(subject, text, to, from));
		}
		
		scanner.close();
		return messages;
	}
	
	private void clearFile(String filename) throws IOException {
		String filepath = resourceFilepath + filename;
		PrintWriter writer = new PrintWriter(new File(filepath));
		writer.print("");
		writer.close();
	}
}
