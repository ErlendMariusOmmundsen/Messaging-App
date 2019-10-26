
package project_core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import project_core.Account;

public class AccountDeserializer extends JsonDeserializer<Account> {

  @Override
  public Account deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectCodec codec = parser.getCodec();
    JsonNode node = codec.readTree(parser);

    String email = node.get(AccountSerializer.email).asText();
    String password = node.get(AccountSerializer.password).asText();

    return new Account(email, password);
  }

}
