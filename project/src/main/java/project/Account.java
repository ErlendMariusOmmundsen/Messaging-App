package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Account {
	
	final static String[] gyldigLKode = {"ad", "ae", "af", "ag", "ai", "al", "am", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bl", "bm", "bn", "bo", "bq", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cw", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "er", "es", "et", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", "mf", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "ss", "st", "sv", "sx", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "za", "zm", "zw"};
	private String name;
	private String mail_address;
	private String password;
	private Inbox inbox;
	
	private appIO io = new appIO();
	
	public Account(String mail_address) {
		this.mail_address = mail_address;
		this.inbox = new Inbox(this);
	}
	
	public Account(String mail_address, String password) throws IllegalArgumentException{
		this(mail_address);
		//setEmail_address(mail_address);
		this.password = password;
	}
	
	public void setInbox(final Inbox inbox) {
		if(inbox.getAccount() != null) {
			throw new IllegalArgumentException("This Inbox is already associated with an account.");
		}
		this.inbox = inbox;
	}
	
	private void setEmail_address(String mail_address) throws IllegalArgumentException{
		String[] part = mail_address.split("\\."); //escaper "."
		String[] buffer = part[1].split("@");
		String nameL = this.name.toLowerCase();
		if(part.length < 3) {
			throw new IllegalArgumentException();
		}
		if(nameL.contains(part[0].toLowerCase()) && nameL.contains(buffer[0].toLowerCase()) && Arrays.asList(gyldigLKode).contains(part[2])) {
			this.mail_address = mail_address;
		}
		else {
			throw new IllegalArgumentException();
		}
		
	}

	public String getMail_address() {
		return mail_address;
	}
	
	public String getPassword() {
		return this.password;
	}

	public Inbox getInbox() {
		return inbox;
	}
	
	public boolean exists() throws IOException {
		return new appIO().loadData(appIO.usersFilename).stream()
				.map(acc -> acc.getMail_address())
				.anyMatch(email -> this.mail_address.equals(email));
	}
	
	public boolean isValid() throws IOException {
		List<Account> validAccounts;
		try {			
			validAccounts = io.loadData(appIO.usersFilename);
		} catch (IOException e) {
			throw new IOException("Couldn't access user files");
		}
		
		validAccounts.stream().forEach(acc -> System.out.println(acc.getMail_address() + ", " + acc.getPassword()));
		
		return validAccounts.stream()
			.anyMatch(acc -> this.password.equals(acc.getPassword()) 
					      && this.mail_address.equals(acc.getMail_address()));
	}
	
	public boolean sendMessage(Message message, Account to) throws IOException {
		if (to.exists()) {			
			Inbox toInbox = to.getInbox();
			toInbox.addMessage(message);
			toInbox.uploadInbox();
			return true;
		}
		return false;
	}
}
