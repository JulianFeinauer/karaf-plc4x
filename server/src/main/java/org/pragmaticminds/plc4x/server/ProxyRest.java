package org.pragmaticminds.plc4x.server;

import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.s7.S7PlcDriver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
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
    // Try to access S7 Driver
    final S7PlcDriver s7PlcDriver = new S7PlcDriver();
    try (final PlcConnection connect = s7PlcDriver.connect("s7://192.168.167.210/1/1")) {
      final PlcReadResponse response = connect.readRequestBuilder()
          .addItem("asdf", "%M0:UDINT")
          .build()
          .execute()
          .get(100, TimeUnit.MILLISECONDS);

      logger.info("I got the response {}", response);
    } catch (PlcConnectionException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    logger.info("I got a S7 Driver now... dunno why but I have one...");
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
