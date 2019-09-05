package project;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Inbox_IO implements MailReader{

	@Override
	public void uploadMessage(Message message, String filename) throws FileNotFoundException{
		
		
	}

	@Override
	public List<Message> getMessages(Inbox inbox, String filename) throws FileNotFoundException{
		String file = new File("").getAbsolutePath() + "/project/src/main/jave/"+filename;
		Scanner scanner;
		
		try {
			String s = "";
			scanner = new Scanner(new File(file));
			while(scanner.hasNextLine()) {
				s += scanner.nextLine()+"\n";
			}
			scanner.close();
			if(s == "") {
				return "No data";
			}
			else {
				return s;
			}
			
		} catch (FileNotFoundException e) {
			
		}
		
		
	}

	
}
