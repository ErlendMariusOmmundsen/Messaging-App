package project.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import project.Account;
import project.Inbox;
import project.Message;

public class MessageDeserializer extends JsonDeserializer<Message> {

	@Override
	public Message deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        
        String message = node.get(MessageSerializer.message).asText();
        String subject = node.get(MessageSerializer.subject).asText();
        String fromEmail = node.get(MessageSerializer.fromEmail).asText();
        String toEmail = node.get(MessageSerializer.toEmail).asText();
        
        return new Message(subject, message, new Account(toEmail), new Account(fromEmail));
	}

}
