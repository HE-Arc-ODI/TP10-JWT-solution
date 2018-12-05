package ch.hearc.ig.odi.filter;

import ch.hearc.ig.odi.exception.AuthenticationException;
import ch.hearc.ig.odi.util.KeyGenerator;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.Key;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  @Inject
  private KeyGenerator keyGenerator;

  private static final String REALM = "example";
  private static final String AUTHENTICATION_SCHEME = "Bearer";
  private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {

    // Get the Authorization header from the request
    String authorizationHeader =
        requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    // Validate the Authorization header
    if (!isTokenBasedAuthentication(authorizationHeader)) {
      abortWithUnauthorized(requestContext);
      return;
    }

    // Extract the token from the Authorization header
    String token = authorizationHeader
        .substring(AUTHENTICATION_SCHEME.length()).trim();

    try {

      // Validate the token
      Key key = keyGenerator.generateKey();
      Jwts.parser().setSigningKey(key).parseClaimsJws(token);
      LOGGER.info("the token has been validated");
    } catch (Exception e) {
      abortWithUnauthorized(requestContext);
    }
  }

  private boolean isTokenBasedAuthentication(String authorizationHeader) {

    // Check if the Authorization header is valid
    // It must not be null and must be prefixed with "Bearer" plus a whitespace
    // The authentication scheme comparison must be case-insensitive
    return authorizationHeader != null && authorizationHeader.toLowerCase()
        .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
  }

  private void abortWithUnauthorized(ContainerRequestContext requestContext) {

    LOGGER.fatal("Abort with unauthorized. Authentication schema : " + AUTHENTICATION_SCHEME + ", Realm : " + REALM);
    // Abort the filter chain with a 401 status code response
    // The WWW-Authenticate header is sent along with the response
    requestContext.abortWith(
        Response.status(Response.Status.UNAUTHORIZED)
            .header(HttpHeaders.WWW_AUTHENTICATE,
                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
            .build());
  }
}