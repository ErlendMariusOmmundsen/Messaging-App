package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;

public class appIO {
	
	public ArrayList<String> loadData(String filename) throws FileNotFoundException {
		String filepath = new File("").getAbsolutePath() + "\\src\\main\\resources\\project\\" + filename;
		FileReader content = new FileReader(filepath);
		LineNumberReader count = new LineNumberReader(content);
		int result = count.getLineNumber() + 1;
		try {
			while (count.readLine() != null){
				result++;
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> table = new ArrayList<>();
		Scanner scanner = new Scanner(new File(filepath));
		for (int i = 0; i < result-1; i++) {
			table.add(scanner.nextLine().toString());
		}
		scanner.close();
		return table;
		
		
	}
}
