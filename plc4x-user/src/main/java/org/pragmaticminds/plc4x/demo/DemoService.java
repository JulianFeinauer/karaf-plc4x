package org.pragmaticminds.plc4x.demo;

import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.spi.PlcDriver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class DemoService {

  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DemoService.class);

  private Set<PlcDriver> drivers = new HashSet<>();

  @Reference(service = PlcDriver.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
  public void addDriver(PlcDriver driver) {
    drivers.add(driver);
    logger.info("Detected new PLC4X driver {}. Currently {} drivers are available.", driver.getProtocolName(), drivers.size());
    // Do a quick request
//    // Try to access S7 Driver
//    try (final PlcConnection connect = driver.connect("s7://192.168.167.210/1/1")) {
//      connect.connect();
//      final PlcReadResponse response = connect.readRequestBuilder()
//          .addItem("asdf", "%M0:UDINT")
//          .build()
//          .execute()
//          .get(100, TimeUnit.MILLISECONDS);
//
//      logger.info("I got the response code {} and the value {} from the PLC", response.getResponseCode("asdf"), response.getLong("asdf"));
//    } catch (Exception e) {
//      logger.error("Unable to get a response from the PLC", e);
//    }
  }

  public void removeDriver(PlcDriver driver) {
    drivers.remove(driver);
    logger.info("PLC4X driver was removed {}. Currently {} drivers are available.", driver.getProtocolName(), drivers.size());
  }

}
