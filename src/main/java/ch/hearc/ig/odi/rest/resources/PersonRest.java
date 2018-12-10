/*
 * 2018. Cours outils de développement intégré. ulysse.rosselet@he-arc.ch
 */


package ch.hearc.ig.odi.rest.resources;

import ch.hearc.ig.odi.business.Category;
import ch.hearc.ig.odi.business.Person;
import ch.hearc.ig.odi.exception.PersonException;
import ch.hearc.ig.odi.filter.Secured;
import ch.hearc.ig.odi.service.RestService;
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
import javax.ws.rs.core.Response.Status;


@Named
@Path("/person")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class PersonRest {

  @Inject
  private RestService service;

  @GET
  public List<Person> getPersons() {
    return service.getPersons();
  }

  @Path("{id}")
  @GET
  public Person getPerson(@PathParam("id") Long id) {
    try {
      Person person = service.getPerson(id);
      return person;
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.NOT_FOUND);
    }
  }

  @POST
  @Secured
  public Person createPerson(@FormParam("id") Long id, @FormParam("firstName") String firstName,
      @FormParam("lastName") String lastName, @FormParam("birthDate") String birthdate) {
    try {
      Date d = service.getDate(birthdate);
      return service.createPerson(id, firstName, lastName, d);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

  }

  @PUT
  @Secured
  public Person updatePerson(@FormParam("id") Long id, @FormParam("firstName") String firstName,
      @FormParam("lastName") String lastName) {
    try {
      return service.updatePerson(id, firstName, lastName);
    } catch (PersonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

  }

  @Path("{id}")
  @DELETE
  @Secured
  public void deletePerson(@PathParam("id") Long id) {
    try {
      service.deletePerson(id);
    } catch (PersonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.NOT_FOUND);
    }
  }

  @Path("{id}/runs")
  @GET
  public List<Category> runsMarathon(@PathParam("id") Long id) {
    try {
      return service.runsMarathon(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.NOT_FOUND);
    }
  }
}