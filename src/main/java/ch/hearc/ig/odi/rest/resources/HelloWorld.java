package ch.hearc.ig.odi.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public final class HelloWorld {

  @GET
  @Path("/hello")
  @Produces(MediaType.TEXT_PLAIN)
  public String sayHelloWorld() {

    return "Hello World!";
  }
}
