package project_core.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import project_core.Inbox;
import project_core.Message;

public class MessageSerializer extends JsonSerializer<Message> {
	
	public static final String toEmail = "toEmail";
	public static final String fromEmail = "fromEmail";
	public static final String subject = "subject";
	public static final String message = "message";
	public static final String list = "list";
	
	@Override
	public void serialize(Message message, JsonGenerator jsonGen, SerializerProvider serializers) throws IOException {
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
