package project_core.json;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import project_core.Account;
import project_core.Message;

public class CompleteObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = -2806963524455733669L;
	
	/**
	 * A ObjectMapper that can serialize and deserialize all of the needed classes in project_core.
	 */
	public CompleteObjectMapper() {
		super();
		SimpleModule module = new SimpleModule();
		module.addSerializer(Message.class, new MessageSerializer());
		module.addDeserializer(Message.class, new MessageDeserializer());
		//module.addSerializer(List.class, new MessageListSerializer());
		module.addDeserializer(List.class, new MessageListDeserializer());
		module.addSerializer(Account.class, new AccountSerializer());
		module.addDeserializer(Account.class, new AccountDeserializer());
		this.registerModule(module);
	}
	
}
