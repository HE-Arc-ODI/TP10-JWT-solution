package ch.hearc.ig.odi.rest.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
public class HelloRestTest extends JerseyTest {

  @Test
  public void testSayHello() {

    final String hello = target("hello").request().get(String.class);

    assertEquals("Hello World!", hello);
  }

  @Override
  protected Application configure() {
    return new ResourceConfig(HelloWorld.class);
  }

}