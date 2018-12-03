/*
 * Company : HEG-ARC
 * Lesson: ODI SA17
 * Project: Marathon
 * Autor: Myriam Schaffter
 * Date: 23.11.17 10:51
 * Module: sa17-projet1
 */

package ch.hearc.ig.odi.rest.resources;

import ch.hearc.ig.odi.business.Category;
import ch.hearc.ig.odi.business.Marathon;
import ch.hearc.ig.odi.exception.MarathonException;
import ch.hearc.ig.odi.exception.PersonException;
import ch.hearc.ig.odi.filter.Secured;
import ch.hearc.ig.odi.service.RestService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@Path("marathon")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class MarathonRest {

  @Inject
  private RestService service;

  @GET
  public List<Marathon> getMarathons() {
    return service.getMarathons();
  }

  @Path("{id}")
  @GET
  public Marathon getMarathon(@PathParam("id") Long id) {
    return service.getMarathon(id);
  }

  @PUT
  public Marathon updateMarathon(@FormParam("id") Long id, @FormParam("name") String name,
      @FormParam("city") String city) {
    return service.updateMarathon(id, name, city);
  }

  @POST
  @Secured
  public Marathon createMarathon(@FormParam("id") Long id, @FormParam("name") String name, // FIXME: remove ID from form params!
      @FormParam("city") String city) {
    try {
      return service.createMarathon(id, name, city);
    } catch (MarathonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }

  @Path("{id}")
  @DELETE
  public void deleteMarathon(@PathParam("id") Long id) {
    try {
      service.deleteMarathon(id);
    } catch (MarathonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
  }

  @Path("{id}/category")
  @POST
  public Marathon createCategory(@PathParam("id") Long id, @FormParam("idCategory") Long idCategory,
      @FormParam("nameCategory") String nameCategory,
      @FormParam("DateOfRunCategory") String dateOfRunCategory,
      @FormParam("maxPersonCategory") Integer maxPerson,
      @FormParam("RegistrationFees") String registrationFees, @FormParam("maxAge") int maxAge,
      @FormParam("minAge") int minAge) {
    try {
      Date d = service.getDate(dateOfRunCategory);
      return service.createCategory(id, idCategory, nameCategory, d, maxPerson,
          Double.parseDouble(registrationFees), maxAge, minAge);
    } catch (ParseException e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

  }

  @Path("{id}/category")
  @PUT
  public Marathon updateCategory(@PathParam("id") Long id, @FormParam("idCategory") Long idCategory,
      @FormParam("nameCategory") String nameCategory) {
    return service.updateNameCategory(id, idCategory, nameCategory);

  }

  @Path("{id}/category/{idCategory}")
  @DELETE
  public void deleteCategory(@PathParam("id") Long id, @PathParam("idCategory") Long idCategory) {
    try {
      service.deleteCategory(id, idCategory);
    } catch (MarathonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
  }

  @Path("{id}/category/{idCategory}/person/{idPerson}")
  @PUT
  public Category addPersonCategory(@PathParam("id") Long id,
      @PathParam("idCategory") Long idCategory, @PathParam("idPerson") Long idPerson) {
    try {
      return service.addPersonCategory(id, idCategory, idPerson);
    } catch (MarathonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }

  @Path("{id}/category/{idCategory}/person/{idPerson}")
  @DELETE
  public Category deletePersonCategory(@PathParam("id") Long id,
      @PathParam("idCategory") Long idCategory, @PathParam("idPerson") Long idPerson) {
    try {
      return service.removePersonFromCategory(id, idCategory, idPerson);
    } catch (MarathonException | PersonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }


}
