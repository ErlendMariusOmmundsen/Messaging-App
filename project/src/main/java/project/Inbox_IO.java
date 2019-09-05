package project;

import java.util.ArrayList;
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
		
		//List<Message> messages = new ArrayList<Message>();
		
		try {
			String s = "";
			scanner = new Scanner(new File(file));
			while(scanner.hasNextLine()) {
				s += scanner.nextLine()+"\n";
			}
			scanner.close();
			if(s == "") {
				return null;
			}
			else {
				// Bare for å fjerne rød linje
				return null;
			}
			
		} catch (FileNotFoundException e) {
			
		}

		// Bare for å fjerne rød linje
		return null;
	}

	
}
