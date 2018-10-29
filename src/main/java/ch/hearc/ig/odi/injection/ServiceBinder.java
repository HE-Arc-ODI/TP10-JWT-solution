package ch.hearc.ig.odi.injection;

import ch.hearc.ig.odi.service.RestService;
import java.text.ParseException;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Performs the singleton binding for the RestService mockup persistence object.
 */
public class ServiceBinder extends AbstractBinder {

  @Override
  protected void configure() {
    try {
      bind(new RestService()).to(RestService.class);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
