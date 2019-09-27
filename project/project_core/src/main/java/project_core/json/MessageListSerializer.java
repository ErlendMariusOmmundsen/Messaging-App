package project_core.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import project_core.Message;

public class MessageListSerializer extends JsonSerializer<List<Message>> {

	@Override
	public void serialize(List<Message> messages, JsonGenerator jsonGen, SerializerProvider serializers) throws IOException {
		MessageSerializer messageSerializer = new MessageSerializer();
		
		jsonGen.writeStartArray();
		for (Message message : messages) {
			messageSerializer.serialize(message, jsonGen, serializers);
		}
		jsonGen.writeEndArray();
	}

}
