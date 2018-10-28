package ch.hearc.ig.odi.rest.resources;

import static org.junit.Assert.assertEquals;

import ch.hearc.ig.odi.business.Marathon;
import ch.hearc.ig.odi.injection.ServiceBinder;
import ch.hearc.ig.odi.service.RestService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.core.Application;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

public class MarathonRestTest extends JerseyTest {

  @Inject
  private RestService service;


  @Before
  public void setUpInjection() throws Exception {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ServiceBinder());
    locator.inject(this);
  }

  @Test
  public void testSayHello() {
    List<Marathon> expected = service.getMarathons();

    final List<Marathon> response = target("marathon").request().get(List.class);
    assertEquals(expected, response);
  }

  @Override
  protected Application configure() {
    return new ResourceConfig() {
      {
        register(new ServiceBinder());
        register(MarathonRest.class);
      }
    };
  }
}