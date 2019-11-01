
package project_restserver;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import project_restapi.AccountObjectMapperProvider;
import project_restapi.AccountService;

public class Config extends ResourceConfig {

  /**
   * This constructor just registers the AccountService (the api) and some JSON things.
   */
  public Config() {
    register(AccountService.class);
    register(AccountObjectMapperProvider.class);
    register(JacksonFeature.class);
  }
}
