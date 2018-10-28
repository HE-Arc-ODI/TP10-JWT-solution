package ch.hearc.ig.odi.config;

import ch.hearc.ig.odi.injection.ServiceBinder;
import ch.hearc.ig.odi.injection.ServiceFeature;
import ch.hearc.ig.odi.rest.resources.HelloWorld;
import ch.hearc.ig.odi.rest.resources.MarathonRest;
import ch.hearc.ig.odi.rest.resources.PersonRest;
import ch.hearc.ig.odi.service.RestService;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Registers all resources with Jersey
 */
public class ResourceLoader extends ResourceConfig {

  public ResourceLoader() {
    register(MarathonRest.class);
    register(PersonRest.class);
    register(RestService.class);
    register(HelloWorld.class);
    register(ServiceFeature.class);
    registerInstances(new ServiceBinder());
  }

}

