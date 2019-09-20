package project.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import project.Message;

public class MessageSerializer {
	
	public static final String toEmail = "toEmail";
	public static final String fromEmail = "fromEmail";
	public static final String subject = "subject";
	public static final String message = "message";
	
	public void serialize(Message message, JsonGenerator jsonGen, SerializerProvider provider) throws IOException {
		  jsonGen.writeStartObject();
		  jsonGen.writeFieldName(toEmail);
		  jsonGen.writeString(message.getTo().getMail_address());
		  jsonGen.writeFieldName(fromEmail);
		  jsonGen.writeString(message.getFrom().getMail_address());
		  jsonGen.writeFieldName(subject);
		  jsonGen.writeString(message.getSubject());
		  jsonGen.writeFieldName(MessageSerializer.message);
		  jsonGen.writeString(message.getMessage());
		  jsonGen.writeEndObject();
	  }
	
}
