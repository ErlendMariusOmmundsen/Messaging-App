package project_core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import project_core.Account;

import java.io.IOException;

public class AccountSerializer extends JsonSerializer<Account>{
	
	
	public static final String email = "mail_adress";
	public static String password = "password";
	
	
	@Override
	public void serialize(final Account account, final JsonGenerator jsonGen, final SerializerProvider provider) throws IOException {
		jsonGen.writeStartObject();
		jsonGen.writeFieldName(email);
		jsonGen.writeString(account.getMail_address());
		jsonGen.writeFieldName(password);
		jsonGen.writeString(account.getPassword());
		jsonGen.writeEndObject();
	}

}
