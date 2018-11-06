/*
 * Company : HEG-ARC
 * Lesson: ODI SA17
 * Project: Marathon
 * Autor: Myriam Schaffter
 * Date: 30.11.17 12:33
 * Module: sa17-projet1
 */

package ch.hearc.ig.odi.rest.resources;

import ch.hearc.ig.odi.business.Marathon;
import ch.hearc.ig.odi.business.Person;
import ch.hearc.ig.odi.exception.PersonException;
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
      return service.getPerson(id);
    } catch (PersonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.NOT_FOUND);
    }
  }

  @POST
  public Person createPerson(@FormParam("id") Long id, @FormParam("firstName") String firstName,
      @FormParam("lastName") String lastName, @FormParam("birthDate") String birthdate) {
    try {
      Date d = service.getDate(birthdate);
      return service.createPerson(id, firstName, lastName, d);
    } catch (ParseException | PersonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

  }

  @PUT
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
  public List<Marathon> runsMarathon(@PathParam("id") Long id) {
    try {
      return service.runsMarathon(id);
    } catch (PersonException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.NOT_FOUND);
    }
  }
}