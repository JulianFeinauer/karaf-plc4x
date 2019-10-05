package org.pragmaticminds.plc4x.server;

import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.spi.PlcDriver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
import org.slf4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Path("/booking")
@JaxrsResource
@Component(immediate = true, service = ProxyRest.class)
public class ProxyRest  {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ProxyRest.class);

  private final Map<Integer, MyPojo> bookings = new HashMap<>();

  @Activate
  public void activate() {
    logger.info("Proxy Rest Server was activated...");
  }

  @Reference(service = PlcDriver.class)
  public void addDriver(PlcDriver driver) {
    logger.info("Binding driver...");
    // Try to access S7 Driver
    try (final PlcConnection connect = driver.connect("s7://192.168.167.210/1/1")) {
      connect.connect();
      final PlcReadResponse response = connect.readRequestBuilder()
          .addItem("asdf", "%M0:UDINT")
          .build()
          .execute()
          .get(100, TimeUnit.MILLISECONDS);

      logger.info("I got the response code {} and the value {} from the S7 PLC", response.getResponseCode("asdf"), response.getLong("asdf"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    logger.info("I got a S7 Driver now... dunno why but I have one...");
  }

  public void removeDriver(PlcDriver driver) {
    logger.info("Unbdingind driver...");
  }


  @Path("/")
  @Produces("application/json")
  @GET
  public Collection<MyPojo> list() {
    return bookings.values();
  }

  @Path("/{id}")
  @Produces("application/json")
  @GET
  public MyPojo get(@PathParam("id") Integer id) {
    return bookings.get(id);
  }

  @Path("/")
  @Consumes("application/json")
  @POST
  public void add(MyPojo booking) {
    bookings.put(booking.getId(), booking);
  }

  @Path("/")
  @Consumes("application/json")
  @PUT
  public void update(MyPojo booking) {
    bookings.remove(booking.getId());
    bookings.put(booking.getId(), booking);
  }

  @Path("/{id}")
  @DELETE
  public void remove(@PathParam("id") Integer id) {
    bookings.remove(id);
  }
}
