
package project_core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import project_core.Account;


public class AccountIO {

  public static final String usersFilename = "users.txt";
  public static final String resourceFilepath =
      new File("").getAbsolutePath() + "\\src\\main\\resources\\io\\account\\";

  /**
   * Loads the Account-data to an ArrayList of Account objects.
   * @param filename - the filename to load data from.
   * @return list of accounts
   * @throws IOException - If something goes wrong reading from file.
   */
  public ArrayList<Account> loadData(String filename) throws IOException {
    String filepath = AccountIO.resourceFilepath + filename;
    InputStream in = new FileInputStream(Paths.get(filepath).normalize().toString());
    Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
    
    // Counts the lines
    LineNumberReader count = new LineNumberReader(reader);
    int result = count.getLineNumber() + 1;
    try {
      while (count.readLine() != null) {
        result++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    count.close();

    // Read the lines to list of Account objects
    ArrayList<Account> accounts = new ArrayList<>();
    in = new FileInputStream(filepath);
    reader = new InputStreamReader(in, StandardCharsets.UTF_8);
    Scanner scanner = new Scanner(reader);
    for (int i = 0; i < result - 1; i++) {
      String[] data = scanner.nextLine().toString().split("\t");
      String email = data[0];
      String password = data[1];
      Account account = new Account(email, password);
      accounts.add(account);
    }
    scanner.close();
    return accounts;

  }

  /**
   * Post an account entry to the file.
   * @param account - The account to be posted.
   * @throws IOException - If something goes wrong writing to the file.
   * @throws IllegalStateException - If the account entry already exists in the file.
   */
  public void newAccount(Account account) throws IOException, IllegalStateException {
    if (account.getMail_address().isEmpty() || account.getPassword().isEmpty()) {      
      throw new IllegalStateException("Can't write empty account to system");
    }

    String filepath = AccountIO.resourceFilepath + usersFilename;
    FileWriter fw = new FileWriter(filepath, StandardCharsets.UTF_8, true);
    PrintWriter writer;
    writer = new PrintWriter(fw);
    writer.println(account.getMail_address() + "\t" + account.getPassword());
    writer.flush();
    writer.close();
  }

}
