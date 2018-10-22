package ch.hearc.ig.odi.restbase.resources;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resource")
public class MyResource {

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public Map<String, String> getResource() {
    HashMap<String, String> keyValuePairs = new HashMap<>();
    keyValuePairs.put("msg", "hello world!");
    return keyValuePairs;
  }

}
