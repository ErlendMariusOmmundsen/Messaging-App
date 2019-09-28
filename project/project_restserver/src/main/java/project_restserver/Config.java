package project_restserver;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Config extends ResourceConfig {
  public Config() {
    //register(LatLongsService.class);
    //register(LatLongObjectMapperProvider.class);
	register(JacksonFeature.class);
	
	/*
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(latLongs);
      }
    });
    */
  }
}
