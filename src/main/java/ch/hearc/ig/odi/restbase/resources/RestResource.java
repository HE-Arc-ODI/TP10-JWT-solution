package ch.hearc.ig.odi.restbase.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resource")
public class RestResource {

  @GET
  @Path("/")
  @Produces({MediaType.APPLICATION_JSON})
  public String getResource() {
    return "hello world!";
  }

}
