package ch.hearc.ig.odi.config;

import ch.hearc.ig.odi.injection.ServiceFeature;
import ch.hearc.ig.odi.rest.resources.MarathonRest;
import ch.hearc.ig.odi.rest.resources.PersonRest;
import ch.hearc.ig.odi.service.RestService;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class MarathonAPIAppConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> s = new HashSet<Class<?>>();
    s.add(MarathonRest.class);
    s.add(PersonRest.class);
    s.add(RestService.class);
    s.add(ServiceFeature.class);
    return s;
  }
}
