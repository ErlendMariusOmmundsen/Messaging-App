package project;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Inbox_IO implements MailReader{
	
	// Dette er en liten test som man kan kjøre i eclipse
	// Skrive til test.txt og skriver ut alle meldingene i test.txt 
	public static void main(String[] args) {
		Inbox_IO io = new Inbox_IO();
		try {
			Account test = new Account("123@hotmail.no", "123");
			Account lukas = new Account("lukasnt@ntnu.no", "123");
			io.uploadMessage(new Message("test", "hallo", test, lukas), "test.txt");
			List<Message> messages = io.getMessages("test.txt");
			System.out.println(messages);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void uploadMessage(Message message, String filename) throws IOException{
		String filepath = new File("").getAbsolutePath() + "\\" + filename;
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
	public List<Message> getMessages(String filename) throws IOException{
		String filepath = new File("").getAbsolutePath() + "\\" + filename;
		Scanner scanner = new Scanner(new File(filepath));
		
		List<Message> messages = new ArrayList<Message>();
		
		while (scanner.hasNextLine()) {
			Account to = new Account(scanner.nextLine(), null);
			Account from = new Account(scanner.nextLine(), null);
			String subject = scanner.nextLine();
			String text = scanner.nextLine();
			scanner.nextLine();
			
			messages.add(new Message(subject, text, to, from));
		}
		
		return messages;
	}
	
}
