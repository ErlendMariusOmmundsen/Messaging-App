package project;

import java.util.Arrays;

public class Account {
	
	final static String[] gyldigLKode = {"ad", "ae", "af", "ag", "ai", "al", "am", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bl", "bm", "bn", "bo", "bq", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cw", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "er", "es", "et", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", "mf", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "ss", "st", "sv", "sx", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "za", "zm", "zw"};
	private String name;
	private String mail_address;
	private String password;
	private Inbox inbox;

	public Account(String mail_address, String password) throws IllegalArgumentException{
		this.mail_address = mail_address;
		//setEmail_address(mail_address);
		this.password = password;
		this.inbox = new Inbox(this);
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
	
	
	
	
	
}
