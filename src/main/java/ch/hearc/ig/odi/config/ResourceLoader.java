package ch.hearc.ig.odi.config;

import ch.hearc.ig.odi.filter.AuthenticationFilter;
import ch.hearc.ig.odi.injection.ServiceBinder;
import ch.hearc.ig.odi.injection.ServiceFeature;
import ch.hearc.ig.odi.rest.resources.AuthenticationEndpoint;
import ch.hearc.ig.odi.rest.resources.MarathonRest;
import ch.hearc.ig.odi.rest.resources.PersonRest;
import ch.hearc.ig.odi.service.RestService;
import ch.hearc.ig.odi.util.KeyGenerator;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Registers all resources with Jersey
 */
public class ResourceLoader extends ResourceConfig {

  public ResourceLoader() {
    register(MarathonRest.class);
    register(PersonRest.class);
    register(AuthenticationEndpoint.class);
    register(AuthenticationFilter.class);
    register(KeyGenerator.class);
    register(RestService.class);
    register(ServiceFeature.class);
    registerInstances(new ServiceBinder());
  }

}

