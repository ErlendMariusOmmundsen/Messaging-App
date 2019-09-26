package project.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import project.Account;

import java.io.IOException;

public class AccountDeserializer extends JsonDeserializer<Account>{

	@Override
	public Account deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        
        String Email = node.get(AccountSerializer.email).asText();
        String password = node.get(AccountSerializer.password).asText();
       
        return new Account(Email, password);
	}
	
	
}
