package project.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import project.Message;

public class MessageListDeserializer extends JsonDeserializer<List<Message>>{

	// Not done
	@Override
	public List<Message> deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		MessageDeserializer messageDeserializer = new MessageDeserializer();
		
		List<Message> messages = new ArrayList<Message>();
		ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        
        for (int i = 0; i < node.size(); i++) {
        	
        }
        
        return messages;
	}

}
