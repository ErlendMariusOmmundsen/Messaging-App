package project_core.io;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;

import project_core.Account;

import java.io.PrintWriter;

public class AccountIO {
	
	public static final String usersFilename = "users.txt";
	public static final String resourceFilepath = new File("").getAbsolutePath() + "\\src\\main\\resources\\project_core\\io\\account\\";
	
	public ArrayList<Account> loadData(String filename) throws IOException {
		String filepath = AccountIO.resourceFilepath + filename;
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
	
	public void newAccount(Account account) throws IOException {
		String filepath = AccountIO.resourceFilepath + usersFilename;
		FileWriter fw = new FileWriter(new File(filepath), true);
		PrintWriter writer;
		writer = new PrintWriter(fw);
		writer.println(account.getMail_address() + "\t" + account.getPassword());
		writer.flush();
		writer.close();	
	}
	
}
