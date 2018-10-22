package ch.hearc.ig.odi.restbase.config;

import ch.hearc.ig.odi.restbase.resources.RestResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//Defines the base URI for all resource URIs.
@ApplicationPath("/rest")
//The java class declares root resource and provider classes
public class ResourceLoader extends Application {

  //The method returns a non-empty collection with classes, that must be included in the published JAX-RS application
  @Override
  public Set<Class<?>> getClasses() {
    HashSet resources = new HashSet<Class<?>>();
    resources.add(RestResource.class);
    return resources;
  }

}

