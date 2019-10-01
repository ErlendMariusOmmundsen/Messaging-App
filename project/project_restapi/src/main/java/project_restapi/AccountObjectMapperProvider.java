package project_restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

import project_core.json.CompleteObjectMapper;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountObjectMapperProvider implements ContextResolver<ObjectMapper>{
	
	private final ObjectMapper mapper;
	
	public AccountObjectMapperProvider() {
		mapper = new CompleteObjectMapper();
	}
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}
		
}
