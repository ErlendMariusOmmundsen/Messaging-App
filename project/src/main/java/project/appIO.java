package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;

public class appIO {
	
	public static final String usersFilename = "users.txt";
	public static final String resourceFilepath = new File("").getAbsolutePath() + "\\src\\main\\resources\\project\\";
	
	public ArrayList<Account> loadData(String filename) throws IOException {
		String filepath = appIO.resourceFilepath + filename;
		FileReader content = new FileReader(filepath);
		
		// Counts the lines
		LineNumberReader count = new LineNumberReader(content);
		int result = count.getLineNumber() + 1;
		try {
			while (count.readLine() != null){
				result++;
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Read the lines to list of Account objects
		ArrayList<Account> accounts = new ArrayList<>();
		Scanner scanner = new Scanner(new File(filepath));
		for (int i = 0; i < result-1; i++) {
			String[] data = scanner.nextLine().toString().split("\t");
			String email = data[0];
			String password = data[1];
			Account account = new Account(email, password);
			accounts.add(account);
		}
		scanner.close();
		return accounts;
		
	}
	
}
