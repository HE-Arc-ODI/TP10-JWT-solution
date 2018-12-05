package ch.hearc.ig.odi.rest.resources;

import ch.hearc.ig.odi.exception.AuthenticationException;
import ch.hearc.ig.odi.service.RestService;
import ch.hearc.ig.odi.util.KeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/authentication")
public class AuthenticationEndpoint {

  @Inject
  private RestService service;

  @Inject
  private KeyGenerator keyGenerator;

  @Context
  private UriInfo uriInfo;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response authenticateUser(@FormParam("username") String username,
      @FormParam("password") String password) {

    try {

      // Authenticate the user using the credentials provided
      authenticate(username, password);

      // Issue a token for the user
      String token = issueToken(username);

      // Return the token on the response
      return Response.ok(token).build();

    } catch (Exception e) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  private void authenticate(String username, String password) throws Exception {
    // Authenticate against a database, LDAP, file or whatever
    // Throw an Exception if the credentials are invalid
    String dbPassword = service.getUsers().get(username);
    if (dbPassword == null || !dbPassword.equals(password)) {
      throw new AuthenticationException("invalid credentials");
    }


  }

  private String issueToken(String login) {
    Key key = keyGenerator.generateKey();
    String jwtToken = Jwts.builder()
        .setSubject(login)
        .setIssuer(uriInfo.getAbsolutePath().toString())
        .setIssuedAt(new Date())
        .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
        .signWith(SignatureAlgorithm.HS512, key)
        .compact();
    System.out.println("#### generating token for a key : " + jwtToken + " - " + key);
    return jwtToken;
  }

  private Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
